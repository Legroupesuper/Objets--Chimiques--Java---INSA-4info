package fr.insa.rennes.info.chemical.backend;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.insa.rennes.info.chemical.user.Dontreact;
import fr.insa.rennes.info.chemical.user.ReactionRule;

public class BuilderSubIndexProviderSolution {
	private SubIndexProviderSolution _sipSol;
	private ReactionRule _rr;
	private Field[] _rrFields;
	private ParameterizedType _paramType;
	private Solution _solution;
	private Field _rrSubSolField;
	private boolean _complete;

	public BuilderSubIndexProviderSolution() {
		_rr = null;
		_rrFields = null;
		_paramType = null;
		_solution = null;
		_rrSubSolField = null;
		_complete = false;
	}

	public SubIndexProviderSolution getSubIndexProvider() throws ChemicalException {
		if(!_complete)
			throw new ChemicalException("The element index provider is not entirely built yet.");

		return _sipSol;
	}

	public void setReactionRule(ReactionRule rr) {
		this._rr = rr;
	}

	public void setReactionRuleFields(Field[] rrFields) {
		this._rrFields = rrFields;
	}

	public void setParamType(ParameterizedType paramType) {
		this._paramType = paramType;
	}

	public void setSolution(Solution sol) {
		this._solution = sol;
	}

	public void setSubSolutionField(Field subSolField) {
		this._rrSubSolField = subSolField;
	}


	public void build() throws ChemicalException {
		if(_solution == null || 	_rr == null)
			throw new ChemicalException("The map element and the reaction rule need to be given to build the IndexProvider.");

		if(_paramType != null) {
			//Recursive build is used during the recursion
			recursiveBuild();
		} else {
			//Root build is used only once, for the root SubIndexProviderSolution of the IndexProvider object
			rootBuild();
		}

		_complete = true;
	}



	private void recursiveBuild() throws ChemicalException {
		if(_paramType == null || _solution == null || _rrSubSolField == null)
			throw new ChemicalException("The param type, sub solution and sub solution field name need to be given to build the IndexProvider.");

		try{
			//This cast can fail
			_paramType = (ParameterizedType)_paramType.getActualTypeArguments()[0];

			//If the cast does not fail, we found another SubSolution object,
			//and another recursion is needed

			//Then we generate the List of List for the SubIndexProviderSolution
			List<List<SubIndexProvider>> firstLevelList = new ArrayList<List<SubIndexProvider>>();
			List<SubIndexProvider> secondLevelList = new ArrayList<SubIndexProvider>();
			SubIndexProviderSolution sipSolAccumulation = null;
			for(Object o : _solution.getSubSolutions()){
				Solution subSubSol = (Solution) o;

				BuilderSubIndexProviderSolution sipSolBuilder = new BuilderSubIndexProviderSolution();

				sipSolBuilder.setReactionRule(_rr);
				sipSolBuilder.setParamType(_paramType);
				sipSolBuilder.setSolution(subSubSol);
				sipSolBuilder.setSubSolutionField(_rrSubSolField);
				sipSolBuilder.build();

				SubIndexProviderSolution sipSol = sipSolBuilder.getSubIndexProvider();
				if(sipSol != null) {
					if(sipSolAccumulation == null)
						sipSolAccumulation = sipSol;
					else
						sipSolAccumulation.merge(sipSol);
				}

				/*if(sipSol != null)
					firstLevelList.add(sipSol.get_listElements());*/
				//System.out.print("Le SOUS sipSol est: "+sipSol+" pour la solution "+subSubSol);
			}
			
			//As we are in the recursion, this _sipSol is only a option among others
			//Thus, the response CAN be null (it is just a wrong way, and we can "backtrack"-sort of)
			if(sipSolAccumulation == null) {
				_sipSol = null;
			} else {
				secondLevelList.add(sipSolAccumulation);
				firstLevelList.add(secondLevelList);
	
				//At this point we have a SubSolution<Subsolution<...>> so there can 
				//not be dependent indexes
				List<List<Integer>> dependentIndexesList = new ArrayList<List<Integer>>();
				_sipSol = new SubIndexProviderSolution(firstLevelList, dependentIndexesList);
			}
		}catch(ClassCastException e){
			//This exception is caught when the cast fails. It means that we are on an SubSolutionElements.
			//We need to get the type list to try to create the IndexProvider.
			//At this point, this._subSol is the Solution in which we are going to try to find our reactives.
			//In other words, we are at the end of the recursion
			Method getter = Utils.getMethodFromReactionRule(_rr, "get", _rrSubSolField);
			try {
				//The getter allows us to generate SubSolution element to access the type list
				SubSolution<SubSolutionReactivesAccessor> subSolObject = (SubSolution<SubSolutionReactivesAccessor>) getter.invoke(_rr, null);

				//For each type in the SubSolutionReactivesAccessor type list, create
				//a SubIndexProviderElement object.
				List<List<SubIndexProvider>> firstLevelList = new ArrayList<List<SubIndexProvider>>();
				List<SubIndexProvider> secondLevelList = new ArrayList<SubIndexProvider>();
				List<String> typeList = new ArrayList<String>();
				for(Class<?> c : subSolObject.getTypeList()){
					typeList.add(c.getName());

					if(_solution.getMapElements().get(c.getName()) == null)
						throw new ChemicalException("There is no reactive of the type asked ("+c.getName()+"), aborting index provider building.");

					SubIndexProviderElement sipElmt = new SubIndexProviderElement(_solution.getMapElements().get(c.getName()).size());
					secondLevelList.add(sipElmt);
				}

				//Here the first level list contains only one sub list of index providers
				firstLevelList.add(secondLevelList);

				//Finally, generate the incompatible indexes list<list<integer>
				List<List<Integer>> dependentIndexesList = this.buildDependantIndexesListWithTypes(typeList, _solution.getMapElements());

				_sipSol = new SubIndexProviderSolution(firstLevelList, dependentIndexesList);
			} catch(ChemicalException e1) {
				throw e1;
			} catch (IllegalArgumentException e2) {
				throw new ChemicalException("Error while building the index provider : invocation of the getter of a reaction rule SubSolution field failed.");
			} catch (IllegalAccessException e3) {
				throw new ChemicalException("Error while building the index provider : invocation of the getter of a reaction rule SubSolution field failed.");
			} catch (InvocationTargetException e4) {
				throw new ChemicalException("Error while building the index provider : invocation of the getter of a reaction rule SubSolution field failed.");
			}
		}
	}


	private void rootBuild() throws ChemicalException {
		if(_rrFields == null)
			throw new ChemicalException("The reaction rule's fields parameters need to be given to build the IndexProvider.");

		//The two level of list of the index provider being built right now
		//In this case, the first level list only contains sub-list 
		//(particular case of the root SubIndexProviderSolution in an IndexProvider object)
		List<List<SubIndexProvider>> firstLevelList = new ArrayList<List<SubIndexProvider>>();
		List<SubIndexProvider> secondLevelList = new ArrayList<SubIndexProvider>();
		for(Field f : _rrFields){
			//Always consider the reaction rule field as a reactive 
			//Unless the user specified @Dontreact above it
			if(f.getAnnotation(Dontreact.class) == null){
				SubIndexProvider sip = null;

				if(f.getType().getName().contains(SubSolution.class.getSimpleName())){
					//If the reaction rule attribute is a SubSolution... difficult
					//We have to consider ALL the sub-solution in the mother solution
					//Each one of them is an alternative to find the desired reactives
					SubIndexProviderSolution sipSolAccumulation = null;
					ParameterizedType p = (ParameterizedType)f.getGenericType();

					for(Object o : _solution.getMapElements().get(Solution.class.getName())){
						Solution s = (Solution) o;

						//Recursion : create sub sub index providers
						BuilderSubIndexProviderSolution sipSolBuilder = new BuilderSubIndexProviderSolution();
						sipSolBuilder.setSolution(s);
						sipSolBuilder.setReactionRule(_rr);
						sipSolBuilder.setParamType(p);
						sipSolBuilder.setSubSolutionField(f);
						sipSolBuilder.build();

						SubIndexProviderSolution sipSol = sipSolBuilder.getSubIndexProvider();
						if(sipSol != null) {
							if(sipSolAccumulation == null)
								sipSolAccumulation = sipSol;
							else
								sipSolAccumulation.merge(sipSol);
						}
						//System.out.print("Le sipSol est: "+sipSol+" pour la solution "+s);
						//System.out.println(", son accumulation est: "+sipSolAccumulation);
					}
					
					//The accumulation can not be null. 
					//If it is, this mean the reactives will never be matched anyway
					if(sipSolAccumulation == null) {
						throw new ChemicalException("There is not enough solution nesting, impossible to match reactives, aborting index provider building.");
					}
					
					sip = sipSolAccumulation;
				}else{
					//Else, the reaction rule attribute is a simple Java object,
					//and a SubIndexProviderElement is sufficient
					if(_solution.getMapElements().get(f.getType().getName()) == null)
						throw new ChemicalException("There is no reactive of the type asked ("+f.getType().getName()+"), aborting index provider building.");

					sip = new SubIndexProviderElement(_solution.getMapElements().get(f.getType().getName()).size());
				}

				secondLevelList.add(sip);
			}
		}
		
		//The first level list only contains one sub-list (see why in the comment up there in this function)
		firstLevelList.add(secondLevelList);

		//Finally, get the dependent indexes list for this level of nesting
		List<List<Integer>> dependantIndexesList = buildDependantIndexesListWithFields(_rrFields, _solution.getMapElements());

		_sipSol = new SubIndexProviderSolution(firstLevelList, dependantIndexesList);

		/*if(_rr.getClass().getSimpleName().equals("MaxIntSubSolRR") || _rr.getClass().getSimpleName().equals("DeleteRuleMax")){
			System.out.println("Le _sipSol final root construit est : "+_sipSol);
			System.out.println("\n\n\n\n\n");
		}*/

	}



	private List<List<Integer>> buildDependantIndexesListWithFields(Field[] rrFields, Map<String, List<Object>> mapElements) throws ChemicalException {

		Map<String, List<Integer>> dependantIndexesMap = new HashMap<String, List<Integer>>();
		String reactiveTypeName;
		for(int i = 0; i < rrFields.length; i++){
			if(rrFields[i].getAnnotation(Dontreact.class) == null) {
				if(rrFields[i].getType().getName().contains(SubSolution.class.getName())){
					reactiveTypeName = Solution.class.getName();
				}else{
					reactiveTypeName = rrFields[i].getType().getName();
				}

				//If the type isn't even an entry of the hash map, don't bother
				if(mapElements.get(reactiveTypeName) == null)
					throw new ChemicalException("rrFields There is no reactive of this type, aborting IndexProvider instanciation.");

				if(dependantIndexesMap.containsKey(reactiveTypeName)){
					dependantIndexesMap.get(reactiveTypeName).add(i);
				}else{
					List<Integer> l = new ArrayList<Integer>();
					l.add(i);
					dependantIndexesMap.put(reactiveTypeName, l);
				}
			}
		}

		//We have to provide the IndexProvider a list of a list of int, so
		//we need to transform the map of list in a list of list
		List<List<Integer>> dependantIndexesList = new ArrayList<List<Integer>>();
		for(String s : dependantIndexesMap.keySet()){
			if(dependantIndexesMap.get(s).size() > 1){
				dependantIndexesList.add(dependantIndexesMap.get(s));
			}
		}

		return dependantIndexesList;
	}

	private List<List<Integer>> buildDependantIndexesListWithTypes(List<String> typeList, Map<String, List<Object>> mapElements) throws ChemicalException {

		Map<String, List<Integer>> dependantIndexesMap = new HashMap<String, List<Integer>>();
		String reactiveTypeName;
		for(int i = 0; i < typeList.size(); i++){
			reactiveTypeName = typeList.get(i);
			if(dependantIndexesMap.containsKey(reactiveTypeName)){
				dependantIndexesMap.get(reactiveTypeName).add(i);
			}else{
				List<Integer> l = new ArrayList<Integer>();
				l.add(i);
				dependantIndexesMap.put(reactiveTypeName, l);
			}

			//If the type isn't even an entry of the hash map, return false (didn't find any reactive)
			if(mapElements.get(typeList.get(i))== null)
				throw new ChemicalException("There is no reactive of this type, aborting IndexProvider instanciation.");
		}

		//We have to provide the IndexProvider a list of a list of int, so
		//we need to transform the map of list in a list of list
		List<List<Integer>> dependantIndexesList = new ArrayList<List<Integer>>();
		for(String s : dependantIndexesMap.keySet()){
			if(dependantIndexesMap.get(s).size() > 1){
				dependantIndexesList.add(dependantIndexesMap.get(s));
			}
		}

		return dependantIndexesList;
	}
}
