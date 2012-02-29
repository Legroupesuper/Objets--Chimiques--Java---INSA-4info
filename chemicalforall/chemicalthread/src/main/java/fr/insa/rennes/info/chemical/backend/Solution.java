package fr.insa.rennes.info.chemical.backend;




import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;

import fr.insa.rennes.info.chemical.user.Dontreact;
import fr.insa.rennes.info.chemical.user.InertEvent;
import fr.insa.rennes.info.chemical.user.InertEventListener;
import fr.insa.rennes.info.chemical.user.ReactionRule;


/**
 * The solution is a set which contains reactives and ReactionRules.
 * A reactive can be any kind of object or primitive type. It can also be an other Solution.
 * The ReactionRules are objects that implements the interface ReactiveRule.
 * 
 * @author
 *
 */
public final class Solution implements Collection<Object>{

	/**
	 * A list of different strategies available for reactive selection sequence
	 */
	public static enum Strategy{RANDOM, ORDERED};

	/**
	 * The map containing all the reactives. The keys of the map are the type (for example java.lang.String)
	 * and the values are a list of object containing all the reactives of this type.
	 */
	private Map<String, List<Object>> _mapElements;

	/**
	 * The map that remembers all the reaction rules setters so that we have to search for
	 * them only once. The keys are the concatenation of the class name and the attribute name, and
	 * the values are the name of the setter method.
	 */
	private Map<String, Integer> _mapReactionRulesSetters;

	/**
	 * The boolean that indicates whether or not the solution is inert (can not react anymore)
	 */
	private boolean _inert = false;

	/**
	 * This boolean is useful for the reaction rules threads. If it is set at false,
	 * all the thread stop.
	 */
	private boolean _reactionInProgress = false;

	/**
	 * The list containing all the threads of the solution's reactions rules threads
	 */
	private Map<ReactionRule, ChemicalThread> _threadTable = Collections.synchronizedMap(new HashMap<ReactionRule, ChemicalThread>());

	/**
	 * The thread group, common to all the threads
	 */
	private ThreadGroup _threadGroup = new ThreadGroup("Group");

	/**
	 * The chosen reactive permutation strategy
	 */
	private Strategy _strategy;

	private InertEventListener _listener = null;
	/**
	 * Default constructor for a Chemical Programming-powered Solution
	 * Takes random strategy as default behavior
	 * @see Solution.Strategy
	 */
	public Solution() {
		this(Strategy.RANDOM);
		_inert = false;
	}

	/**
	 * Constructor for a Chemical Programming-powered Solution
	 * @param s the Strategy to use in the solution
	 * @see Solution.Strategy
	 */
	public Solution(Strategy s) {
		super();
		_mapElements = new HashMap<String, List<Object>>();
		_mapElements = Collections.synchronizedMap(_mapElements);
		_mapReactionRulesSetters = new HashMap<String, Integer>();
		_strategy = s;
		_inert = false;
	}

	/**
	 * This method allow the user to add any type of Object to the solution.
	 * @param newReactive The new reactive you want to add
	 * @return The reactive has been added
	 */
	public boolean add(Object newReactive) {
		//The map (container of our elements) must NOT be accessed concurrently
		synchronized (_mapElements) {

			//At first we determine whether it is a ReactionRule or not
			String className = getReactiveType(newReactive);
			boolean addElement = true;
			//It is a ReactionRule, hence special treatment
			if(className.equals(ReactionRule.class.getName())){

				Class<? extends Object> clazz = newReactive.getClass();
				String errorMsg = "";

				//We check that every field has an appropriate setter
				boolean classOK = true;
				int numberOfMethods = clazz.getDeclaredMethods().length;
				boolean setterOK;
				for(Field f : clazz.getDeclaredFields()){
					setterOK = false;

					for(int  i = 0; i < numberOfMethods; i++){
						Method m = clazz.getDeclaredMethods()[i];
						if(m.getName().toLowerCase().equals("set"+f.getName().toLowerCase())){
							//Remember the setter name by putting it in the reaction rules' setters map
							_mapReactionRulesSetters.put(clazz.getName()+"."+f.getName(), i);

							setterOK = true;
						}
					}

					if(!(setterOK)){
						classOK = false;
						if(!setterOK)
							errorMsg+="class "+clazz.getName()+" lacks a setter for attribute "+f+"\n";
					}
				}
				if(!classOK){
					try {
						throw new ChemicalException(errorMsg);
					} catch (ChemicalException e) {
						e.printStackTrace();
					}
				}else{
					//If the reaction rule doesn't exist, we add it
					if(!_threadTable.containsKey(newReactive)){
						launchThread((ReactionRule) newReactive);
					}else
						addElement = false;
				}
			}
			
			if(className.equals(Solution.class.getName())){
				Solution sol = (Solution) newReactive;
				sol.addInertEventListener(new InertEventListener() {

					public void isInert(InertEvent e) {
						Solution.this.wakeAll();
					}
				});
				if(_reactionInProgress)
					sol.react();
			}
			
			if(!className.equals(SubSolutionReactivesAccessor.class.getName()) && addElement){
				boolean result;
				//When you add an element in the solution, the solution is no more inert
				_inert = false;
				//There is already an entry in the map for this reactive, so we just add the element
				if(_mapElements.get(className) != null){
					result = _mapElements.get(className).add(newReactive);
					//There is no entry for the moment : we init the list
				}else{
					List<Object> l =new ArrayList<Object>();
					result = l.add(newReactive);
					_mapElements.put(className, l);
				}
				return result;
			}else{
				return false;
			}
		}
	}

	//Add every element from the collection into the map
	public boolean addAll(Collection<?> arg0) {
		synchronized (_mapElements) {
			for(Object obj : arg0){
				this.add(obj);
			}
		}
		return true;
	}

	public void addInertEventListener(InertEventListener listener){
		_listener = listener;
	}

	public void clear() {
		_mapElements.clear();
	}

	public boolean contains(Object reactive) {
		String reactiveType = getReactiveType(reactive);

		//If the hash map doesn't even know the type, return false
		if(_mapElements.get(reactiveType) == null) {
			return false;
		}

		return _mapElements.get(reactiveType).contains(reactive);
	}

	public boolean containsAll(Collection<?> arg0) {
		for(Object obj : arg0) {
			if(!this.contains(obj))
				return false;
		}
		return true;
	}

	protected void deleteReaction(ReactionRule r){
		synchronized (_mapElements) {
			List<Object> l = _mapElements.get(ReactionRule.class.getName());
			l.remove(r);
			synchronized (_threadTable) {
				_threadTable.get(r).stopTheThread();
				_threadTable.remove(r);
			}
			int nbAwake = getNumberOfActiveThreads();
			if(nbAwake==0){
				endOfReaction();
			}

		}
	}

	private synchronized void endOfReaction(){
		_reactionInProgress = false;

		notifyAll();
		_inert = true;
		fireInertEvent(new InertEvent(new Object()));
	}

	public void fireInertEvent(InertEvent e){
		if(_listener != null)
			_listener.isInert(e);
	}

	protected synchronized boolean get_reactionInProgress() {
		return _reactionInProgress;
	}

	private int getNumberOfActiveThreads(){
		int nb=0;
		synchronized (_threadTable) {
			//Count the number of thread that are awaken right nowcollection synchron
			for(Thread t : _threadTable.values()){
				if(!t.getState().equals(Thread.State.WAITING)){
					nb++;
				}
			}
		}
		return nb;
	}
	
	
	private String getReactiveType(Object reactive) {
		String interfaceS = " ";
		for(Class<?> s : reactive.getClass().getInterfaces()){
			interfaceS += s.getName()+" ";
		}

		if(interfaceS.contains(" "+ReactionRule.class.getName()+" ")) {
			return ReactionRule.class.getName();
		}else if(interfaceS.contains(" "+SubSolutionReactivesAccessor.class.getName()+" ")){
			return SubSolutionReactivesAccessor.class.getName();
		}else {
			return reactive.getClass().getName();
		}
	}

	/**
	 * The Solution is inert when no more reaction is possible. This means that the ReactionRules can't
	 * react anymore. To be inert, all the inner solutions must be inert too.
	 * @return The solution is inert
	 */
	public synchronized boolean is_inert() {
		return _inert;
	}


	public boolean isEmpty() {
		return _mapElements.isEmpty();
	}

	public Iterator<Object> iterator() {
		List<Object> reactivesCopy = new LinkedList<Object>();
		for(List<Object> reactiveList : _mapElements.values()) {
			reactivesCopy.addAll(reactiveList);
		}
		return Collections.unmodifiableList(reactivesCopy).iterator();
	}

	/**
	 * Creates and launches a thread that will make the specified reaction rule react.
	 * All the reaction rules threads will be in the same thread group and have
	 * a pointer to the solution (this)
	 */
	private void launchThread(ReactionRule r){
		ChemicalThread t = new ChemicalThread(r, this, _threadGroup);
		_threadTable.put(r, t);
		t.start();
	}

	/**
	 * When a reaction rule did not find any reactives, it calls this function that will "make" it sleep.
	 * It checks if this reaction rule's thread is the last standing. If it is,
	 * it stops all the reaction and declares the solution inert.
	 * This function is called by the main function of a chemical thread
	 */
	protected synchronized void makeSleep(){
		int nbAwaken = getNumberOfActiveThreads();

		//If there is more than one thread alive (including the current one)
		//it means other reaction rules can react, so just make this thread wait
		//This function ever deals with inner solutions. Awaken threads in relation with 
		//ReactionRules of inner solutions are detected in nbAwaken.
		if(nbAwaken > 1){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//If the current thread is the last one standing, kill all the threads by switching the
			//boolean _keepOnReacting to false (all thread are in a loop on this boolean).
		}else{
			endOfReaction();
		}
	}

	/**
	 * The main function that begins the reaction of the solution. It does not stop until the function
	 * is_inert sends true.
	 * Internally, it creates a thread for each reaction rule so that their execution be parallel.
	 * This function also starts the reactions in the inner solutions. Basically, a solution must
	 * launch endOfReaction() when no more reaction is possible. This also include the case where the Solution
	 * does not contain any ReactionRule. 
	 * 
	 */
	public void react() {
		synchronized (this) {
			_reactionInProgress = true;
			//We try to launch all the inner solutions
			if(_mapElements.get(Solution.class.getName()) != null){//Go through all the inner solutions
				for(Object o : _mapElements.get(Solution.class.getName())){
					Solution s = (Solution) o;
					if(!s.is_inert()){
						/**
						 * If the solution is not inert, we add an InertEvenListener that will notify this
						 * when s will become inert. The notification will awake all the ReactionRules which are
						 * included in _mapElements.
						 */
						s.addInertEventListener(new InertEventListener() {
							public void isInert(InertEvent e) {
								Solution.this.wakeAll();
							}
						});
						s.react();
					}
				}
			}else if(_mapElements.get(ReactionRule.class.getName()) == null){
				/**
				 * If there is no ReactionRule in the _mapElements, the solution can not react.
				 */
				endOfReaction();
			}
			notifyAll();
		}
	}

	private void reactMe(){

	}

	//To remove the object, we have to find its type
	//and check that it is present in the hash map
	public boolean remove(Object reactive) {
		String reactiveType = getReactiveType(reactive);
		boolean res = false;
		//For the same reason as in add, synchronized have to be declared on the hash map
		synchronized(_mapElements) {
			//If the hash map doesn't even know the type, return false
			if(_mapElements.get(reactiveType) == null) {
				return false;
			}

			res = _mapElements.get(reactiveType).remove(reactive);
			if(_mapElements.get(reactiveType).size() == 0){
				_mapElements.remove(reactiveType);
			}
			return res;
		}
	}

	public boolean removeAll(Collection<? extends Object> arg0) {
		boolean res = false;

		synchronized (_mapElements) {
			for(Object obj : arg0) {
				res = res || this.remove(obj);
			}
		}

		return res;
	}

	IndexProviderElement generateListIndexProviderElement(Field f, ReactionRule r, ParameterizedType p, Solution s, int pos){
		if(p.getActualTypeArguments().length > 0){
			try{//Try to match a SubSolution
				ParameterizedType oldParam = p;
				p = (ParameterizedType) p.getActualTypeArguments()[0];
				List<List<IndexProviderElement>> l = new ArrayList<List<IndexProviderElement>>();
				int i=0;
				for(Object solu : s._mapElements.get(Solution.class.getName())){
					Solution sol = (Solution) solu;
					IndexProviderElement elem = generateListIndexProviderElement(f, r, p, sol, i);
					List<IndexProviderElement> ltemp = new ArrayList<IndexProviderElement>();
					ltemp.add(elem);
					l.add(ltemp);
					i++;
				}
				List<List<Integer>> lincomp = new ArrayList<List<Integer>>();
				List<Integer> lbis = new ArrayList<Integer>();
				lincomp.add(lbis);
				IndexProviderSubSolution subSol = new IndexProviderSubSolution(l, lincomp);
				return subSol;
			}catch(Exception e){//Try to match a ListElement
				IndexProviderElement elem;
				try{
					Method getter = null;
					for(Method get : r.getClass().getDeclaredMethods()){
						if(get.getName().toLowerCase().contains("get"+f.getName().toLowerCase())){
							getter = get;
							break;
						}
					}
					SubSolutionReactivesAccessor result = (SubSolutionReactivesAccessor) getter.invoke(r, new Object[0]);
					List<List<IndexProviderElement>> finalList = new ArrayList<List<IndexProviderElement>>();
					List<IndexProviderElement> tempList = new ArrayList<IndexProviderElement>();
					for(Class<? extends Object> o : result.getTypeList()){
						tempList.add(new IndexProviderSimpleElement(s._mapElements.get(o.getName()).size()));
					}
					tempList.add(new IndexProviderSimpleElement(0));
					finalList.add(tempList);
					//TODO gerer les incompatibilités ici
					List<List<Integer>> lincomp = new ArrayList<List<Integer>>();
					List<Integer> lbis = new ArrayList<Integer>();
					lincomp.add(lbis);
					elem = new IndexProviderSubSolution(finalList, lincomp);
				}catch(Exception e2){
					List<List<IndexProviderElement>> finalList = new ArrayList<List<IndexProviderElement>>();
					List<IndexProviderElement> tempList = new ArrayList<IndexProviderElement>();
					tempList.add(new IndexProviderSimpleElement(0));
					tempList.add(new IndexProviderSimpleElement(0));
					finalList.add(tempList);
					List<List<Integer>> lincomp = new ArrayList<List<Integer>>();
					List<Integer> lbis = new ArrayList<Integer>();
					lincomp.add(lbis);
					elem = new IndexProviderSubSolution(finalList, lincomp);
				}
				return elem;
			}
		}else{
			return null;
		}

	}

	/*
	 * This method is called by every thread-reaction rule to get its reactives.
	 * It returns true if a set of parameter have been found, else false. The reactives are set directly
	 * in the reaction rule instance by reflexivity so no need to return them.
	 * This function is synchronized on the atoms' hash map
	 */
	public boolean requestForParameters(ReactionRule r) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException{
		synchronized (this) {
			if(!_reactionInProgress){
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		//Build the type table : the types of the reaction rules reactives
		Field[] fields = r.getClass().getDeclaredFields();
		String table[] = new String[fields.length];
		List<String> tableS = new ArrayList<String>();
		List<IndexProviderElement> listElements = new ArrayList<IndexProviderElement>();
		for(int i=0; i< fields.length; i++){
			if(fields[i].getAnnotation(Dontreact.class) == null){
				if(fields[i].getType().getName().contains("SubSolution")){
					table[i] = Solution.class.getName();
					tableS.add(Solution.class.getName());
				}else{
					table[i] = fields[i].getType().getName();
					tableS.add(fields[i].getType().getName());
					System.out.println("==>Annotations : "+fields[i].getAnnotation(Dontreact.class));
				}
			}
		}

		//The access to the main atom map is restricted to 1 thread at a time
		synchronized(_mapElements) {
			//Initialize the index provider to try every possible combination

			//Construct the map of the dependent indexes (two dependent indexes can not have the same value
			//as they refer to the same element)
			Map<String, List<Integer>> mapIndexProvider = new HashMap<String, List<Integer>>();
			for(int i = 0; i < tableS.size(); i++){
				if(mapIndexProvider.containsKey(tableS.get(i))){
					List<Integer> l = mapIndexProvider.get(tableS.get(i));
					l.add(i);
					mapIndexProvider.put(tableS.get(i), l);
				}else{
					List<Integer> l = new ArrayList<Integer>();
					l.add(i);
					mapIndexProvider.put(tableS.get(i), l);
				}

				//If the type isn't even an entry of the hash map, return false (didn't find any reactive)
				if(_mapElements.get(tableS.get(i))== null)
					return false;
			}

			//We have to provide the IndexProvider a list of a list of int, so
			//we need to transform the map of list in a list of list
			List<List<Integer>> listProvider = new ArrayList<List<Integer>>();
			for(String s : mapIndexProvider.keySet()){
				if(mapIndexProvider.get(s).size()>1){
					listProvider.add(mapIndexProvider.get(s));
				}else{
				}
			}

			IndexProviderSubSolution sol = generateIndexProviderSubSolution(fields, listProvider, r);
			if(sol == null || sol.getNumberOfElements().equals(BigInteger.ZERO))
				return false;

			//Instantiate the IndexProvider object
			IndexProvider indexProvider = null;


			try {
				indexProvider = new IndexProvider(sol, _strategy);
			} catch (ChemicalException e1) {
				return false;
			}
			System.out.println(indexProvider);
			//Effectively research a valid set of reactives for the reaction rule
			List<Pair<Solution, Object>> reactives = new ArrayList<Pair<Solution,Object>>();
			int setterNumber;
			Method setter;
			boolean hasMatched = false;
			//Loop until the reactives has been found OR all combination have been tested
			while(!indexProvider.is_overflowReached()){
				reactives.clear();
				IndexProviderSubSolution solution = indexProvider.increment();
				if(solution == null)
					return false;
				int i=0;
				Pair<Solution, Object> obj = null;
				for(Field f : fields){
					if(f.getAnnotation(Dontreact.class) == null){
						//Find the setter for this field
						setterNumber = _mapReactionRulesSetters.get(r.getClass().getName()+"."+f.getName());
						setter = r.getClass().getDeclaredMethods()[setterNumber];
	
						//and invoke the setter
						try{
							obj = instanciateField(f, this, solution.get_listElements().get(i), r);
							if(obj == null)
								return false;
						}catch(Exception e){
							return false;
						}
						setter.invoke(r, new Object[]{obj.get_second()});
	
						reactives.add(obj);
						i++;
					}
				}
				//The right types have been found, now test the selection rule
				if(r.computeSelect()){

					//remove the selected reactives from the solution
					for(Pair<Solution, Object> react : reactives){
						if(!react.get_first().is_inert() && react.get_first() != this){
							System.err.println("On ne peut pas instancier des éléments d'une sous-solution non inerte");
							System.err.println(react.get_first());
							return false;
						}
						try{
							if(react.get_second().getClass().getName().equals(SubSolution.class.getName())){
								SubSolution<SubSolutionReactivesAccessor> s = (SubSolution<SubSolutionReactivesAccessor>) react.get_second();
								System.err.println("Solution temp = "+react.get_first()._mapElements);
								for(Object o : s.getElements()){
									try{
										System.err.println("elem - "+o);
										react.get_first()._mapElements.get(o.getClass().getName()).remove(o);
									}catch(Exception ex){
										
									}
								}
							}else{
								if(react.get_second().getClass().getName().equals(Solution.class.getName())){
									Solution stemp = (Solution) react.get_second();
									System.err.println("!!!!!!!!!!!!!!!!!\n!!!!!!!!!!!!!!!!!!!!\n!!!!!!!!!!!!!!!!!\nOn a matché un solution "+stemp);
									if(!stemp._inert){
										System.err.println("?????????????\n????????????????????\n?????????????????\nOn a matché un solution");
										return false;
									}
								}
								System.err.println("On supprime "+react.get_second());
								react.get_first()._mapElements.get(react.get_second().getClass().getName()).remove(react.get_second());

							}
						}catch(Exception e){
							System.err.println(e.getMessage());
							e.printStackTrace();
						}
					}
					hasMatched = true;
					break;
				}

			}
			//All the possibilities have been tested here
			//Return false if nothing matched
			if(!hasMatched){
				return false;
			}
		}
		return true;
	}

	/**
	 * This function is used to instanciate a field of a reaction rule.
	 * To succeed this operation, it needs to use an IndexProviderElement which gives the indexe of the element
	 * to use in the solution s.
	 * @param f the field
	 * @param s the solution
	 * @param ipe the IndexProviderElement
	 * @return the instanciated object or null
	 */
	private Pair<Solution, Object> instanciateField(Field f, Solution s, IndexProviderElement ipe, ReactionRule r){
		if(ipe instanceof IndexProviderSimpleElement){//This is a simple element, so it it direct
			return new Pair<Solution, Object>(s, s._mapElements.get(f.getType().getName()).get(ipe.getValue()));
		}else{//This is a Solution, it will be more complex
			Method getter = Utils.getMethodFromReactionRule(r, "get", f);
			try {
				SubSolution<SubSolutionReactivesAccessor> el = (SubSolution<SubSolutionReactivesAccessor>) getter.invoke(r, null);
				//We must genetrate the List<Object> of el
				//First we must get the good subsolution
				Solution nextS = (Solution)s._mapElements.get(Solution.class.getName()).get(ipe.getValue());
				//Then we get le List<Object>
				Pair<Solution, List<Object>> lo = generateListObject(((IndexProviderSubSolution)ipe).get_listElements(), nextS, el);
				//finally, we use the setter
				el.setElements(lo.get_second());
				return new Pair<Solution, Object>(lo.get_first(), el);
			}catch(Exception e){
				return null;
			}
		}
	}

	private Pair<Solution, List<Object>> generateListObject(List<IndexProviderElement> lipe, Solution s, SubSolution<SubSolutionReactivesAccessor> el){
		try{
			if(lipe.size()==1 && lipe.get(0) instanceof IndexProviderSubSolution){
				IndexProviderSubSolution ipss = (IndexProviderSubSolution) lipe.get(0);
				Solution snext = (Solution) s._mapElements.get(Solution.class.getName()).get(lipe.get(0).getValue());
				return generateListObject(ipss.get_listElements(), snext, el);
			}else{
				List<Object> l = new ArrayList<Object>();
				int i=0;
				l.add(s);
				for(Class<?> c : el.getTypeList()){
					l.add(s._mapElements.get(c.getName()).get(lipe.get(i).getValue()));
					i++;
				}
				return new Pair<Solution, List<Object>>(s, l);
			}
		}catch(Exception e){
			return null;
		}
	}

	private IndexProviderSubSolution generateIndexProviderSubSolution(Field[] fieldTable, List<List<Integer>> incompatiblesIndexes, ReactionRule r){
		List<IndexProviderElement> secondLevelList = new ArrayList<IndexProviderElement>();
		System.out.println("INCOMPATIBLES 1st LEVEL : "+incompatiblesIndexes);
		for(Field f : fieldTable){
			if(f.getAnnotation(Dontreact.class)==null){
				if(f.getType().getName().contains("SubSolution")){//This is a subsolution
					List<List<IndexProviderElement>> ltemp = new ArrayList<List<IndexProviderElement>>();
					IndexProviderSubSolution subsol = null;
					IndexProviderSubSolution lastSubSolution = null;
					System.out.println(_mapElements);
					
					for(Object o : _mapElements.get(Solution.class.getName())){
						Solution s = (Solution) o;
						ParameterizedType p = (ParameterizedType)f.getGenericType();
						subsol = s.generateIndexProviderSubSolution(p, f, r, s);
						if(subsol != null){
							if(lastSubSolution == null)
								lastSubSolution = subsol;
							else
								lastSubSolution.merge(subsol);
	
							/*if(!subsol.getNumberOfElements().equals(BigInteger.ZERO)){
								List<IndexProviderElement> l = new ArrayList<IndexProviderElement>();
								l.add(subsol);
								ltemp.add(l);//We only have 1 element in the subsolution first level list
								
							}
							*/
						}
					}
					secondLevelList.add(lastSubSolution);
					//if(lastSubSolution != null)
					//	secondLevelList.add(new IndexProviderSubSolution(ltemp, new ArrayList<List<Integer>>()));//We get the dependant indexes
					//from the computed IndexProviderSubSolution
				}else{//It's an element
					secondLevelList.add(new IndexProviderSimpleElement(_mapElements.get(f.getType().getName()).size()));
				}
			}
		}
		List<List<IndexProviderElement>> firstLevelList = new ArrayList<List<IndexProviderElement>>();
		firstLevelList.add(secondLevelList);
		return new IndexProviderSubSolution(firstLevelList, incompatiblesIndexes);
	}

	
	/**
	 * Generate a List<List<IndexProviderElement>> from different parameters
	 * @param p A parametrized type which must be a SubSolution<ChemicalElement>
	 * @param f The starting field of the ReactionRule
	 * @param r The reaction rules which contains the SubSolution
	 * @param s The solution where we have to look for p
	 * @return A well generated List<List<IndexProviderElement>>
	 */
	private List<List<IndexProviderElement>>  generateListListForIndexProviderSubSolution(ParameterizedType p, Field f, ReactionRule r, Solution s){
		List<List<IndexProviderElement>> ltemp = new ArrayList<List<IndexProviderElement>>();
		for(Object o : s._mapElements.get(Solution.class.getName())){
			Solution stemp = (Solution) o;
			IndexProviderSubSolution subsoltemp = generateIndexProviderSubSolution(p, f, r, stemp);
			if(subsoltemp != null)
				ltemp.add(subsoltemp.get_listElements());
		}
		System.err.println("LA TAILLE DE LA LISTE AU PREMIER NIVEAU EST DE :"+ltemp.size());
		return ltemp;
	}
	
	
	/**
	 * This function create a IndexProviderSubSolution from a set of parameters
	 * @param p A parametrized type which must be a SubSolution<ChemicalElement>
	 * @param f The starting field of the ReactionRule
	 * @param r The reaction rules which contains the SubSolution
	 * @param s The solution where we have to look for p
	 * @return An IndexProviderSubSolution or null when it's not possible to generate it
	 */
	private IndexProviderSubSolution generateIndexProviderSubSolution(ParameterizedType p, Field f, ReactionRule r, Solution s){
		try{
			p = (ParameterizedType)p.getActualTypeArguments()[0];//This cast can fail
			//Then we generate the List<List<...>>
			List<List<IndexProviderElement>> ltemp = generateListListForIndexProviderSubSolution(p, f, r, s);
			//At this point we have a Subsolution<Subsolution<...>> so there is no incompatible indexes
			//to generate
			List<List<Integer>> incompat = new ArrayList<List<Integer>>();
			System.out.println("Ca n'a pas fail");
			return new IndexProviderSubSolution(ltemp, incompat);
		}catch(Exception e){
			/*
			 * This exception is catched when the cast fails. It means that we are on an ElementList.
			 * We need to get the type list to try to create the IndexProvider.
			 * At this point, s is the Solution in which we are going to try to find our elements list.
			 */
			System.out.println("Ca a fail");
			Method getter = Utils.getMethodFromReactionRule(r, "get", f);
			try {
				//The getter allows us to generate SubSolution element to acces the type list
				SubSolution<SubSolutionReactivesAccessor> el = (SubSolution<SubSolutionReactivesAccessor>) getter.invoke(r, null);

				List<List<IndexProviderElement>> l = new ArrayList<List<IndexProviderElement>>();
				List<IndexProviderElement> ll = new ArrayList<IndexProviderElement>();
				List<String> table = new ArrayList<String>();
				for(Class<?> c : el.getTypeList()){
					table.add(c.getName());
					try{
						System.err.println("on ajoute un "+c.getName());
						ll.add(new IndexProviderSimpleElement(s._mapElements.get(c.getName()).size()));
					}catch(Exception ex){
					}
				}
				l.add(ll);
				//We need to generate the incompatible indexes list<list<integer>
				Map<String, List<Integer>> mapIndexProvider = new HashMap<String, List<Integer>>();
				for(int i = 0; i < table.size(); i++){
					if(mapIndexProvider.containsKey(table.get(i))){
						List<Integer> lll = mapIndexProvider.get(table.get(i));
						lll.add(i);
						mapIndexProvider.put(table.get(i), lll);
					}else{
						List<Integer> lll = new ArrayList<Integer>();
						lll.add(i);
						mapIndexProvider.put(table.get(i), lll);
					}

					//If the type isn't even an entry of the hash map, return false (didn't find any reactive)
					if(s._mapElements.get(table.get(i))== null){
						return null;
					}
				}
				//We have to provide the IndexProvider a list of a list of int, so
				//we need to transform the map of list in a list of list
				List<List<Integer>> listProvider = new ArrayList<List<Integer>>();
				for(String sname : mapIndexProvider.keySet()){
					System.err.println("Pour le type "+sname+" on a "+mapIndexProvider.get(sname).size()+" éléments dans la map");
					if(mapIndexProvider.get(sname).size()>1){
						System.err.println("On ajoute donc bien les éléments");
						listProvider.add(mapIndexProvider.get(sname));
					}else{
					}
				}
				System.err.println("On a enfin notre liste incompatible : "+listProvider);
				return new IndexProviderSubSolution(l, listProvider);
			} catch (Exception e1) {
				//e1.printStackTrace();
			}
		}
		return null;
	}


	public boolean retainAll(Collection<?> arg0) {
		boolean res = false;
		String reactiveName;

		//The write/remove operations on the hash map have to be atomic
		synchronized (_mapElements) {
			for(Object obj : arg0) {
				reactiveName = getReactiveType(obj);

				if(_mapElements.get(reactiveName) == null || !_mapElements.get(reactiveName).contains(obj)) {
					_mapElements.get(reactiveName).remove(obj);
					res = true;
				}
			}
		}

		return res;
	}

	public int size() {
		int res = 0;

		for(List<Object> reactiveList : _mapElements.values()) {
			res += reactiveList.size();
		}

		return res;
	}

	public Object[] toArray() {
		List<Object> res = new LinkedList<Object>();

		for(List<Object> reactiveList : _mapElements.values()) {
			res.addAll(reactiveList);

		}

		return res.toArray();
	}

	public Object[] toArray(Object[] arg0) {
		return this.toArray();
	}

	@Override
	public String toString(){
		String res = "Solution is :\n";

		for(Map.Entry<String, List<Object>> entry : _mapElements.entrySet()) {
			res += entry.getKey()+" -> "+entry.getValue()+"\n";
		}

		return res;
	}

	/*
	 * This function is called by a reaction rule's thread when it successfully
	 * reacted, to wake every other thread that was asleep
	 */
	public synchronized void wakeAll() {
		notifyAll();
	}

	/**
	 * This method set the log file of the chemical library.
	 * For information, logs are set not to be bigger than 10,000 bytes long
	 * and rotate over 5 files
	 * Anyhow, System.err is default log outputstream whether this
	 * method is called or not
	 * @param fileName File name of the log file (a number from 0 to 4
	 * will be appended during logs rotation)
	 * @throws IOException
	 */
	public static void setLogFile(String fileName) throws IOException{
		Utils.logger.addHandler(new FileHandler(fileName, 10000, 5, false));

	}

}
