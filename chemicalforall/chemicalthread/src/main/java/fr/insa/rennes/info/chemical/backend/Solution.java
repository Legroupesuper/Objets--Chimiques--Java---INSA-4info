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
	private Map<ReactionRule, ChemicalThread> _threadTable;

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
		_threadTable = Collections.synchronizedMap(new HashMap<ReactionRule, ChemicalThread>());
		_strategy = s;
		_inert = false;
	}

	//REFACTORING
	private boolean checkReactionRuleReactive(Object reactionRuleObject) {
		Class<? extends Object> rrClass = reactionRuleObject.getClass();
		String errorMsg = "";

		//We check that every field has an appropriate setter
		boolean classOK = true;
		int numberOfMethods = rrClass.getDeclaredMethods().length;
		boolean setterOK;
		for(Field f : rrClass.getDeclaredFields()){
			System.out.println("Bah merde."+f);
			setterOK = false;

			for(int  i = 0; i < numberOfMethods; i++){
				Method m = rrClass.getDeclaredMethods()[i];
				if(m.getName().toLowerCase().equals("set"+f.getName().toLowerCase())){
					//Remember the setter name by putting it in the reaction rules' setters map
					_mapReactionRulesSetters.put(rrClass.getName()+"."+f.getName(), i);

					setterOK = true;
				}
			}

			if(!(setterOK)){
				classOK = false;
				if(!setterOK)
					errorMsg+="class "+rrClass.getName()+" lacks a setter for attribute "+f+"\n";
			}
		}
		if(!classOK){
			try {
				throw new ChemicalException(errorMsg);
			} catch (ChemicalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			//If the reaction rule doesn't exist, we add it
			if(!_threadTable.containsKey(reactionRuleObject)){
				launchThread((ReactionRule) reactionRuleObject);
			}else
				return false;
		}
		return true;
	}

	//REFACTORING
	private void processAddSubSolution(Object solutionObject) {
		Solution sol = (Solution) solutionObject;
		sol.addInertEventListener(new InertEventListener() {

			public void isInert(InertEvent e) {
				Solution.this.wakeAll();
			}
		});
		if(_reactionInProgress)
			sol.react();
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
			if(className.equals(ReactionRule.class.getName())) {
				addElement = checkReactionRuleReactive(newReactive);
			} else if(className.equals(Solution.class.getName())){
				processAddSubSolution(newReactive);
			}

			//TODO: (Message de Antoine) Pas la peine le premier test, de toute facon l'utilisateur peut pas accéder à SubSolutionReactivesAccessor
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
			if(nbAwake == 0){
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
		int nb = 0;
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
		for(Class<?> s : reactive.getClass().getInterfaces()){
			if(s.getName().equals(ReactionRule.class.getName())) {
				return ReactionRule.class.getName();
			}else if(s.getName().equals(SubSolutionReactivesAccessor.class.getName())){
				return SubSolutionReactivesAccessor.class.getName();
			}
		}

		return reactive.getClass().getName();
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
		while(nbAwaken > 1){
			try {
				wait();
			} catch (InterruptedException e) {
				//Nothing, just loop
			}
			//If the current thread is the last one standing, kill all the threads by switching the
			//boolean _keepOnReacting to false (all thread are in a loop on this boolean).
		}
		
		endOfReaction();
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




	/*
	 * This method is called by every thread-reaction rule to get its reactives.
	 * It returns true if a set of parameter have been found, else false. The reactives are set directly
	 * in the reaction rule instance by reflexivity so no need to return them.
	 * This function is synchronized on the atoms' hash map
	 */
	public boolean requestForParameters(ReactionRule r) {
		synchronized (this) {
			while(!_reactionInProgress){
				try {
					wait();
				} catch (InterruptedException e) {
					//Nothing, just loop
				}
			}
		}

		//Get the reaction rule fields
		Field[] rrFields = r.getClass().getDeclaredFields();

		//The access to the main atom map is restricted to 1 thread at a time
		synchronized(_mapElements) {
			//Instantiate the IndexProvider object
			IndexProvider indexProvider = null;
			try {
				BuilderIndexProvider ipBuilder = new BuilderIndexProvider();
				ipBuilder.setSolution(this);
				ipBuilder.setReactionRule(r);
				ipBuilder.setReactionRuleFields(rrFields);
				ipBuilder.setStrategy(_strategy);
				ipBuilder.build();
				indexProvider = ipBuilder.getIndexProvider();

			} catch (ChemicalException e1) {
				return false;
			} catch(Exception e) {
				//Just in case there is any other exception, and in order to avoid 
				//to annoy the user with a stack trace, just return false 
				return false;
			}

			//Once the index provider is created, we have to search for reactives matching,
			//Meanwhile, we have to catch every Exception before the user get them
			try {
				boolean reactivesFound = searchForReactives(r, rrFields, indexProvider);
				
				return reactivesFound;
			
				//TODO : Gestion des exceptions
			} catch (IllegalArgumentException e) {
				return false;
			} catch (IllegalAccessException e) {
				return false;
			} catch (InvocationTargetException e) {
				return false;
			} catch(Exception e) {
				//Just in case there is any other exception, and in order to avoid 
				//to annoy the user with a stack trace, just return false 
				return false;
			}
		}
	}

	private boolean searchForReactives(ReactionRule rr, Field[] rrFields, IndexProvider indexProvider) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		//Effectively research a valid set of reactives for the reaction rule
		//given all the tools
		List<Pair<Solution, Object>> reactives = new ArrayList<Pair<Solution,Object>>();
		int setterNumber;
		Method setter;
		Pair<Solution, Object> reactiveObject = null;

		//Loop until the reactives has been found OR all combination have been tested
		while(!indexProvider.is_overflowReached()) {
			SubIndexProviderSolution sipSol = indexProvider.increment();

			//If the increment returns null, means that the overflow is reached
			//We return false to mean no reactives were found
			if(indexProvider.is_overflowReached())
				return false;

			int i = 0;
			for(Field f : rrFields) {
				if(f.getAnnotation(Dontreact.class) == null){
					//Find the setter for this field...
					setterNumber = _mapReactionRulesSetters.get(rr.getClass().getName()+"."+f.getName());
					setter = rr.getClass().getDeclaredMethods()[setterNumber];

					//...and invoke the setter
					reactiveObject = instanciateField(f, sipSol.get_listElements().get(i), rr);
					setter.invoke(rr, new Object[]{reactiveObject.get_second()});

					reactives.add(reactiveObject);
					i++;
				}
			}

			//When we get here, the right types have been found, now test the selection rule
			if(rr.computeSelect()) {
				//If the computeSelect says OK, 
				//TRY to delete the reactives from their respective solutions (this operation can fail)
				boolean reactivesDeleted = extractReactivesFromSolutions(reactives);
				if(reactivesDeleted) {
					//If all went well, the set of reactives was fitting, return ok
					return true;
				}
			}
			
			//If we did not leave the function, it means something is 
			//wrong with the set of reactives, erase it and loop
			reactives.clear();
		}
		
		//If we reach this line, it means we tried every combination and all failed
		return false;
	}

	private boolean extractReactivesFromSolutions(List<Pair<Solution, Object>> reactives) {
		//remove the selected reactives from the solution
		for(Pair<Solution, Object> react : reactives){
			//If one of the reactives is in a non inert sub-solution, reaction is forbidden
			if(!react.get_first().is_inert() && react.get_first() != this){
				return false;
			}

			if(react.get_second().getClass().getName().equals(SubSolution.class.getName())){
				SubSolution<SubSolutionReactivesAccessor> s = (SubSolution<SubSolutionReactivesAccessor>) react.get_second();
				//System.err.println("Solution temp = "+react.get_first()._mapElements);
				for(Object o : s.getElements()){
					//System.err.println("react - "+react);
					//System.err.println("react.get_first() - "+react.get_first());
					//System.err.println("elem - "+o);
					react.get_first()._mapElements.get(o.getClass().getName()).remove(o);
				}
			}else{
				//If the reactive is a solution, we have to see that this solution is inert
				if(react.get_second().getClass().getName().equals(Solution.class.getName())){
					Solution stemp = (Solution) react.get_second();
					//System.err.println("!!!!!!!!!!!!!!!!!\n!!!!!!!!!!!!!!!!!!!!\n!!!!!!!!!!!!!!!!!\nOn a matché un solution "+stemp);
					
					//If among the reactives we try to use a solution object
					//and that this solution is not inert, the matching fails (it is impossible to do that)
					if(!stemp._inert){
						//System.err.println("?????????????\n????????????????????\n?????????????????\nOn a matché un solution");
						return false;
					}
				}
				//System.err.println("On supprime "+react.get_second());
				react.get_first()._mapElements.get(react.get_second().getClass().getName()).remove(react.get_second());

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
	 * @param sip the IndexProviderElement
	 * @return the instanciated object or null
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private Pair<Solution, Object> instanciateField(Field f, SubIndexProvider sip, ReactionRule r) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		//If the field is a simple element,it is direct
		if(sip instanceof SubIndexProviderElement){
			return new Pair<Solution, Object>(this, _mapElements.get(f.getType().getName()).get(sip.getValue()));
		}
		//TIf the field is a set of elements in a subsolution, it will be more complex
		else{
			Method getter = Utils.getMethodFromReactionRule(r, "get", f);
			SubSolution<SubSolutionReactivesAccessor> subSolObject = (SubSolution<SubSolutionReactivesAccessor>) getter.invoke(r, null);
			
			//In order to instanciate a SubSolution object, we have to go down the 
			//nested solutions... (recursion)
			SubIndexProviderSolution sipSol = (SubIndexProviderSolution)sip;
			Solution nextSol = (Solution)this._mapElements.get(Solution.class.getName()).get(sip.getValue());
			while(sipSol.get_listElements().size() == 1 && sipSol.get_listElements().get(0) instanceof SubIndexProviderSolution){
				nextSol = (Solution) nextSol._mapElements.get(Solution.class.getName()).get(sipSol.get_listElements().get(0).getValue());
				sipSol = (SubIndexProviderSolution) sipSol.get_listElements().get(0);
			}
			
			//...until we reach the level in which the reactives have to be found,
			//so that we can instanciate the elementList of the SubSolution object
			List<Object> l = new ArrayList<Object>();
			int i = 0;
			for(Class<?> c : subSolObject.getTypeList()){
				l.add(nextSol._mapElements.get(c.getName()).get(sipSol.get_listElements().get(i).getValue()));
				i++;
			}
			
			//Finally, we use the setter of SubSolution
			subSolObject.setElements(l);
			subSolObject.setSolution(nextSol);
			
			
			return new Pair<Solution, Object>(nextSol, subSolObject);
		}
	}




	public boolean retainAll(Collection<?> arg0) {
		boolean res = false;
		String reactiveName;

		//The write/remove operations on the hash map have to be atomic
		synchronized (_mapElements) {
			Iterator<Object> it = this.iterator();
			Object obj = null;
			while(it.hasNext()) {
				obj = it.next();

				if(!arg0.contains(obj)) {
					reactiveName = getReactiveType(obj);
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

	List<Object> getSubSolutions() {
		List<Object> res = _mapElements.get(Solution.class.getName());
		if(res == null)
			res = new ArrayList<Object>();
		return res;
	}

	Map<String, List<Object>> getMapElements() {
		return _mapElements;
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
