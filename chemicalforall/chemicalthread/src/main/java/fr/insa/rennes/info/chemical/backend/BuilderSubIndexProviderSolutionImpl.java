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

/**
 * Standard implementation of {@link BuilderSubIndexProviderSolution}. Another implementation can be created
 * by implementing {@link BuilderSubIndexProviderSolution}.
 * @author Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 */
class BuilderSubIndexProviderSolutionImpl implements BuilderSubIndexProviderSolution {
	/**
	 * The product built, here a {@link SubIndexProviderSolution}.
	 */
	private SubIndexProviderSolution _sipSol;
	/**
	 * The reaction rule parameter.
	 */
	private ReactionRule _rr;
	/**
	 * The reaction rule's fields parameter.
	 */
	private Field[] _rrFields;
	/**
	 * The parameterized type parameter.
	 */
	private ParameterizedType _paramType;
	/**
	 * The solution parameter.
	 */
	private Solution _solution;
	/**
	 * The {@link SubSolution} field of the reaction rule;
	 */
	private Field _rrSubSolField;
	/**
	 * Indicates if the product's building is finished. 
	 * Allows to perform a check before sending the product when the user 
	 * calls {@link #getProduct()} although it is not built yet.  
	 */
	private boolean _complete;
	
	/**
	 * Constructs a standard builder for a {@link SubIndexProviderSolution}.
	 * Initializes all the builder's parameters to <code>null</code> and the {@link #_complete} field
	 * to false.
	 */
	public BuilderSubIndexProviderSolutionImpl() {
		_rr = null;
		_rrFields = null;
		_paramType = null;
		_solution = null;
		_rrSubSolField = null;
		_complete = false;
	}
	
	public SubIndexProviderSolution getProduct() throws ChemicalException {
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

	/**
	 * Processes to the building of the index provider.
	 * This functions first checks if the solution and reaction rule parameters are instantiated, otherwise a ChemicalException
	 * explaining the issue is thrown.<br />
	 * Depending on the other parameters instantiation, this function will then call 
	 * a root build (see {@link #rootBuild()}) or a recursive build ({@link #recursiveBuild()}).
	 * Root build is used only once, for the root SubIndexProviderSolution of 
	 * a IndexProvider object, then the recursive function is always called (see the {@link SubIndexProviderSolution} structure).
	 * At the end, the product is declared as complete ({@link #_complete} is set to <code>true</code>).<br />
	 * Note: the implementation used to build every {@link SubIndexProviderSolution} is the standard implementation.
	 * @see IndexProvider
	 * @see SubIndexProviderSolution
	 * @see BuilderIndexProvider
	 */
	public void build() throws ChemicalException {
		if(_solution == null || _rr == null)
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


	/**
	 * This building version needs the following parameters to be correctly instantiated : 
	 * solution, reaction rule, parameterized type, {@link SubSolution} field of the reaction rule. If at least
	 * one of these parameters is not instantiated, a ChemicalException is thrown.
	 * Depending on the parameterized type ( {@link SubSolution} or {@link SubSolutionElements} )
	 * the processing differs. In the first case, a recursion is done in order to reach
	 * the desired solution overlapping level; in the second this precise overlapping level is reached,
	 * the recursion ends and a {@link SubIndexProviderElement} is created for each desired 
	 * reactive given in the type list (see {@link SubSolutionReactivesAccessor} and 
	 * the {@link SubIndexProviderSolution} structure).
	 * @throws ChemicalException
	 * @see SubSolution
	 * @see SubIndexProviderSolution
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void recursiveBuild() throws ChemicalException {
		if(_paramType == null || _solution == null || _rr == null || _rrSubSolField == null)
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

				BuilderSubIndexProviderSolution sipSolBuilder = new BuilderSubIndexProviderSolutionImpl();

				sipSolBuilder.setReactionRule(_rr);
				sipSolBuilder.setParamType(_paramType);
				sipSolBuilder.setSolution(subSubSol);
				sipSolBuilder.setSubSolutionField(_rrSubSolField);
				sipSolBuilder.build();

				SubIndexProviderSolution sipSol = sipSolBuilder.getProduct();
				if(sipSol != null) {
					if(sipSolAccumulation == null)
						sipSolAccumulation = sipSol;
					else
						sipSolAccumulation.merge(sipSol);
				}
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
				SubSolution<SubSolutionReactivesAccessor> subSolObject = (SubSolution<SubSolutionReactivesAccessor>) getter.invoke(_rr, new Object[0]);

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

	
	/**
	 * Builds a root {@link SubIndexProviderSolution} for a {@link IndexProvider}; normally called only once.<br />
	 * This building version needs the following parameters to be correctly instantiated : 
	 * solution, reaction rule and reaction rule's fields. If at least
	 * one of these parameters is not instantiated, a ChemicalException is thrown.<br />
	 * This function basically goes through the reaction rule (given in parameter of the builder)
	 * and for each field/reactive, it creates a {@link SubIndexProviderSolution} (if the field is a {@link SubSolution} object)
	 * or a {@link SubIndexProviderElement} (if the field is a simple java object).
	 * All sub {@link SubIndexProviderSolution} will be built with the {@link #recursiveBuild()} function.
	 * @throws ChemicalException
	 * @see IndexProvider
	 * @see BuilderIndexProvider
	 */
	private void rootBuild() throws ChemicalException {
		if(_rrFields == null || _solution == null || _rr == null)
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
						BuilderSubIndexProviderSolution sipSolBuilder = new BuilderSubIndexProviderSolutionImpl();
						sipSolBuilder.setSolution(s);
						sipSolBuilder.setReactionRule(_rr);
						sipSolBuilder.setParamType(p);
						sipSolBuilder.setSubSolutionField(f);
						sipSolBuilder.build();

						SubIndexProviderSolution sipSol = sipSolBuilder.getProduct();
						if(sipSol != null) {
							if(sipSolAccumulation == null)
								sipSolAccumulation = sipSol;
							else
								sipSolAccumulation.merge(sipSol);
						}
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
	}


	/**
	 * Builds the list of dependent indexes for a the specified reaction rule fields.
	 * Basically two indexes are dependent if they correspond to reactives that have the same type.
	 * And when two indexes are dependent, their HAVE to be different in the index provider, else
	 * that would mean a solution element is selected twice for the same reaction
	 * This function builds a list containing list of integers; a sub-list represents a 
	 * set of dependent indexes, and there can be several sets of dependent indexes.
	 * @param rrFields The reaction rule fields (that do not specify the {@link Dontreact} annotation).
	 * @param mapElements The solution elements.
	 * @return The list of sets of dependent indexes.
	 * @throws ChemicalException
	 */
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
	
	/**
	 * Builds the list of dependent indexes for the specified list of reactive type.
	 * Basically two indexes are dependent if they correspond to reactives that have the same type.
	 * And when two indexes are dependent, their HAVE to be different in the index provider, else
	 * that would mean a solution element is selected twice for the same reaction.
	 * This function builds a list containing list of integers; a sub-list represents a 
	 * set of dependent indexes, and there can be several sets of dependent indexes.
	 * @param typeList The list of reactive type.
	 * @param mapElements The solution elements.
	 * @return The list of sets of dependent indexes.
	 * @throws ChemicalException
	 */
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
