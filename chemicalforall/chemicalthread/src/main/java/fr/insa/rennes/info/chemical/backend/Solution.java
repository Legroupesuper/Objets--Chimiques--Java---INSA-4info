package fr.insa.rennes.info.chemical.backend;




import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
 * Central class of the library. The solution is a set of reactives and ReactionRules.
 * A reactive can be any kind of object or primitive type. It can also be an other Solution.
 * The ReactionRules are objects that implements the interface ReactiveRule.
 * 
 * @see ReactionRule
 * 
 * @author Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 *
 */
public final class Solution implements Collection<Object>{

	/**
	 * Different strategies available for reactive selection sequence.
	 * <p>
	 * ORDERED Iterates on the elements of the solution and tries to use reactives in order of appearance. Each execution will give the same result.<br />
	 * RANDOM The iteration is randomized and each execution is different (indeterminism).
	 *</p>
	 */
	public static enum Strategy{RANDOM, ORDERED};

	/**
	 * The map containing all the reactives. The keys of the map are the type (i.e java.lang.String)
	 * and the values are a list of object containing all the reactives of this type.
	 */
	private Map<String, List<Object>> _mapElements;

	/**
	 * The map that remembers all the reaction rules setters so that we have to search for
	 * them only once. The keys are the concatenation of the class name and the attribute name, and
	 * the values are the name of the setter method. This table is constructed in the add function.
	 */
	private Map<String, Integer> _mapReactionRulesSetters;

	/**
	 * Indicates whether or not the solution is inert. An inert solution is a solution that finished 
	 * all its possible reaction and reached a stable state.
	 */
	private boolean _inert = false;

	/**
	 * Indicates tha the reaction is still in progress. Useful for the reaction rules threads. If it is set at false,
	 * all the thread stop. Difference with _inert is that _inert is used for the user, and _reactionInProgress is 
	 * used internally and is not always synchronized with _inert.
	 */
	private boolean _reactionInProgress = false;

	/**
	 * The list containing all the threads of the solution's reactions rules threads (each reaction rule has its own thread).
	 */
	private Map<ReactionRule, ChemicalThread> _threadTable;

	/**
	 * The thread group, common to all the threads
	 */
	private ThreadGroup _threadGroup = new ThreadGroup("ChemicalGroup");

	/**
	 * The reactive iteration strategy, chosen by the user.
	 * @see Solution.Strategy
	 */
	private Strategy _strategy;

	private InertEventListener _listener = null;
	
	/**
	 * Default constructor for a Chemical Programming-powered Solution.<br />
	 * Uses random strategy as default behavior
	 * @see Solution.Strategy
	 */
	public Solution() {
		this(Strategy.RANDOM);
		_inert = false;
	}

	/**
	 * Constructor for a Chemical Programming-powered Solution, with a Strategy parameter.
	 * @param s The desired strategy to iterate over the solution's reactives.
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

	/**
	 * Used in add function when a ReactionRule object is added as a reactive. Checks if the ReactionRule class is valid.<br />
	 * A valid ReactionRule class implements setters for its attributes that do not specify the Dontreact annotation.
	 * @param reactionRuleObject The reaction rule object added in the solution.
	 * @return <code>true</code> if the reaction rule object is valid
	 * @see Dontreact
	 * @see ReactionRule
	 */
	private boolean checkReactionRuleReactive(Object reactionRuleObject) {
		Class<? extends Object> rrClass = reactionRuleObject.getClass();
		String errorMsg = "";

		//We check that every field has an appropriate setter
		boolean classOK = true;
		int numberOfMethods = rrClass.getDeclaredMethods().length;
		boolean setterOK;
		for(Field f : rrClass.getDeclaredFields()){
			setterOK = false;
			
			if(f.getAnnotation(Dontreact.class) != null) 
				continue;
			
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
			return false;
		}else{
			//If the reaction rule doesn't exist, we add it
			if(!_threadTable.containsKey(reactionRuleObject)){
				launchThread((ReactionRule) reactionRuleObject);
			}else
				return false;
		}
		return true;
	}

	/**
	 * Used in add function to add a sub-solution as a reactive in the solution.<br />
	 * Applies a special treatment to the solution reactive : sets a InertEventListener on the sub-solution,
	 * and begin the reaction of the sub-solution.
	 * @param solutionObject The sub-solution added.
	 * @see InertEventListener
	 */
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
	 * Adds any type of object to this solution.<br />
	 * Every object inserted in the solution will be considered as a reactive, 
	 * included Solution objects and ReactionRule objects, and can be used by any ReactionRule object.
	 * @param newReactive The new reactive to add to the solution.
	 * @return <code>true</code> (as specified by Collection.add(E))
	 * @see java.util.Collection#add(java.lang.Object)
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

			//TODO: (Message de Antoine) Pas la peine le premier test, de toute facon l'utilisateur peut pas acc�der � SubSolutionReactivesAccessor
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

	/**
	 * Adds all of the elements in the specified collection to the end of this solution, 
	 * in the order that they are returned by the specified collection's iterator.
	 * The behavior of this operation is undefined if the specified collection is modified while the operation is in progress.
	 * @param c Collection containing elements to be added to this solution.
	 * @return <code>true</code> (as specified by Collection.addAll(Collection<? extends E))
	 * @see Solution#add(Object)
	 * @see Collection#addAll(Collection)
	 */
	public boolean addAll(Collection<?> c) {
		synchronized (_mapElements) {
			for(Object obj : c){
				this.add(obj);
			}
		}
		return true;
	}
	
	/**
	 * Adds the specified inert event listener to receive inert events from this solution.<br />
	 * Note: only one listener can be set at once.
	 * @param listener The inert event listener
	 * @see InertEvent
	 * @see InertEventListener
	 */
	public void addInertEventListener(InertEventListener listener){
		_listener = listener;
	}

	/**
	 * Removes all of the reactives from this solution.
	 */
	public void clear() {
		_mapElements.clear();
	}
	
	/**
	 * Returns true if this solution contains the specified element/reactive.
	 * @param reactive Reactive whose presence in this list is to be tested 
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	public boolean contains(Object reactive) {
		String reactiveType = getReactiveType(reactive);

		//If the hash map doesn't even know the type, return false
		if(_mapElements.get(reactiveType) == null) {
			return false;
		}

		return _mapElements.get(reactiveType).contains(reactive);
	}

	/**
	 * Returns <code>true</code> if this solution all of the elements/reactives in the specified collection.
	 * @return <code>true</code> if this solution all of the elements/reactives in the specified collection.
	 * @see java.util.Collection#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection<?> c) {
		for(Object obj : c) {
			if(!this.contains(obj))
				return false;
		}
		return true;
	}
	
	/**
	 * Deletes the specified reaction rule from this solution.
	 * If this solution does not contain the specified reaction rule, nothing is done. 
	 * Else, the thread associated to the reaction rule is also stopped. 
	 * In case the reaction rule removed is the last one in this solution, 
	 * the end of the reaction is detected. 
	 * @param r The reaction rule to be removed from this solution.
	 * @see ReactionRule
	 * @see Solution#endOfReaction()
	 */
	void deleteReaction(ReactionRule r){
		synchronized (_mapElements) {
			List<Object> l = _mapElements.get(ReactionRule.class.getName());
			if(l.remove(r)) {
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
	}
	
	/**
	 * Ends and stops the reaction.
	 * The _inert and _reactionInProgress booleans are switched, and the InertEvent is fired.
	 * @see Solution#_inert
	 * @see Solution#_reactionInProgress
	 * @see InertEvent
	 * @see InertEventListener
	 */
	private synchronized void endOfReaction(){
		_reactionInProgress = false;

		notifyAll();
		_inert = true;
		fireInertEvent(new InertEvent(new Object()));
	}
	
	/**
	 * Fires the inert event notifying this solution's inert event listeners
	 * that this solution is now inert.
	 * @param e The inert event to be fired.
	 * @see InertEvent
	 * @see InertEventListener
	 */
	public void fireInertEvent(InertEvent e){
		if(_listener != null)
			_listener.isInert(e);
	}
	
	/**
	 * Returns <code>true</code> if this solution is still reacting (similar to <code>! _inert</code>).
	 * @return <code>true</code> if the reaction is in progress.
	 */
	synchronized boolean get_reactionInProgress() {
		return _reactionInProgress;
	}
	
	/**
	 * Gives the number of thread that are awaken in this solution.
	 * Each thread corresponds to a reaction rule.
	 * @return The number of active threads of this solution.
	 * @see ChemicalThread
	 */
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

	/**
	 * Gives the type of the specified reactive.
	 * For most of the object, the value returned is it's raw name (<code>object.getClass().getName()</code>), 
	 * but for the Solution and ReactionRule object, a special name is returned.
	 * @param reactive Object whose type is to be determined.
	 * @return The java type of the specified reactive.
	 */
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
	 * Returns <code>true</code> if this solution is inert. A Solution is inert when no more reaction are possible. 
	 * This means that there is no more ReactionRules that can react; the solution reached a stable state.
	 * Note: to be inert, all the solution's inner solutions must be inert as well.
	 * @return <code>true</code> if this solution is inert.
	 */
	public synchronized boolean is_inert() {
		return _inert;
	}

	/**
	 * Returns <code>true</code> if this solution contains no elements/reactives.
	 * @return <code>true</code> if this solution contains no elements/reactives.
	 */
	public boolean isEmpty() {
		return _mapElements.isEmpty();
	}
	
	/**
	 * Returns an iterator over the elements/reactives in this solution. 
	 * As this solution can contain any type of element, the function returns an iterator of Object objects.
	 * There are no guarantees concerning the order in which the elements are returned
	 * @return an <code>Iterator</code> over the elements in this solution.
	 * @see Iterator
	 */
	public Iterator<Object> iterator() {
		List<Object> reactivesCopy = new LinkedList<Object>();
		for(List<Object> reactiveList : _mapElements.values()) {
			reactivesCopy.addAll(reactiveList);
		}
		return Collections.unmodifiableList(reactivesCopy).iterator();
	}

	/**
	 * Creates and launches a thread that will run the specified reaction rule.
	 * Every reaction rule has its own thread, and all the threads are in the same thread group.
	 * @param r The reaction rule to be launched.
	 */
	private void launchThread(ReactionRule r){
		ChemicalThread t = new ChemicalThread(r, this, _threadGroup);
		_threadTable.put(r, t);
		t.start();
	}

	/**
	 * Called by a thread, makes the thread wait until a <code>notify</code> is called.
	 * When a reaction rule did not find any reactives, it calls this function that will "make" it sleep.
	 * It checks if this reaction rule's thread is the last standing. If it is,
	 * it stops all the reaction and declares the solution inert.
	 * This function is called by the main function of a chemical thread.
	 * @see ChemicalThread
	 * @see Solution#_inert
	 */
	synchronized void makeSleep(){
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
	 * Launches the reaction of this solution.
	 * This is the main function that begins the reaction of the solution. 
	 * After that, the reaction does not stop until the function <code>is_inert</code> returns true or until an InertEvent is fired. 
	 * <br />
	 * This function begins by launching the reactions in this solution's inner solutions and declares itself as non inert.
	 * All the threads of the reaction rules from this solutions and its inner solutions are started all together.
	 * In case this solution does not contain any inner solution nor reaction rule, nothing happens.
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

	/**
	 * Removes a single instance of the specified element/reactive from this solution, if it is present.
	 * Returns <code>true</code> if this solution contained the specified element/reactive (or equivalently, if this solution changed as a result of the call). 
	 * @param reactive Element to be removed from this solution, if present. 
	 * @return <code>true</code> if an element was removed as a result of this call 
	 */
	public boolean remove(Object reactive) {
		//To remove the object, we have to find its type
		//and check that it is present in the hash map
		
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
	
	/**
	 * Removes all of this solution's elements that are also contained in the specified collection. 
	 * @param c Collection containing elements to be removed from this solution. 
	 * @return <code>true</code> if this solution changed as a result of the call. 
	 */
	public boolean removeAll(Collection<? extends Object> c) {
		boolean res = false;

		synchronized (_mapElements) {
			for(Object obj : c) {
				res = res || this.remove(obj);
			}
		}

		return res;
	}




	/**
	 * Called by every thread-reaction rule to get its reactives.
	 * It returns true if a set of parameter has been found, else false. The reactives are set directly
	 * in the reaction rule instance by reflexivity, there is no need to return them.
	 * This function is synchronized on the _mapElements attribute.
	 * @param r The reaction rule requesting for parameters;
	 * @return <code>true</code> if parameters/reactives where found.
	 * @see ReactionRule
	 * @see Solution#_mapElements
	 */
	boolean requestForParameters(ReactionRule r) {
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
				BuilderIndexProvider ipBuilder = new BuilderIndexProviderImpl();
				ipBuilder.setSolution(this);
				ipBuilder.setReactionRule(r);
				ipBuilder.setReactionRuleFields(rrFields);
				ipBuilder.setStrategy(_strategy);
				ipBuilder.build();
				indexProvider = ipBuilder.getProduct();

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
	
	/**
	 * Searches for reactives for the specified reaction rule.
	 * The difference with {@link Solution#requestForParameters(ReactionRule)} is that this functions is given
	 * a fully instanciated index provider.
	 * If a set of reactives is found following the types only, the {@link ReactionRule#computeSelect()}
	 * is tested. In case of success, the reaction rule's fields are instantiated
	 * @param rr The reaction rule asking for reactives
	 * @param rrFields The fields of the reaction rule
	 * @param indexProvider The index provider, 
	 * @return <code>true</code> if reactives where found and instantiated.
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @see IndexProvider
	 */
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
	
	/**
	 * Extracts the given reactives from this solution (and inner solutions).
	 * This function is called right after finding reactives for a reaction rule that match
	 * the types and the {@link ReactionRule#computeSelect()}. They have to be deleted from this solution, just like 
	 * chemical reactives.
	 * Returns <code>true</code> if the reactives where deleted. This operation can fail because some
	 * reactives can possibly be in a non inert inner solution (using sch a reactive is not allowed by chemical programming). 
	 * @param reactives The list of reactives to be deleted. Reactives can possibly be in inner solutions.
	 * @return <code>true</code> if the reactives where deleted. 
	 */
	@SuppressWarnings("unchecked")
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
					//System.err.println("!!!!!!!!!!!!!!!!!\n!!!!!!!!!!!!!!!!!!!!\n!!!!!!!!!!!!!!!!!\nOn a match� un solution "+stemp);
					
					//If among the reactives we try to use a solution object
					//and that this solution is not inert, the matching fails (it is impossible to do that)
					if(!stemp._inert){
						//System.err.println("?????????????\n????????????????????\n?????????????????\nOn a match� un solution");
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
	 * This function is used to instantiate a field of a reaction rule in order to perform a reaction.
	 * When a set of reactives matching the right types for the specified reaction rule are found, this function is
	 * called (by {@link Solution#searchForReactives(ReactionRule, Field[], IndexProvider)} to instantiate the reaction rule's fields one by one.
	 * To succeed this operation needs to use an SubIndexProvider which gives the index of the element/reactive
	 * to use in this solution or in its inner solutions.
	 * @param f The ReactionRule field
	 * @param sip The sub index provider
	 * @param r The reaction rule that is concerned by the instanciation.
	 * @return A pair of object containing the value to be set to the field, and a reference to the solution ('possibly an inner solution) in which this reactive is contained.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@SuppressWarnings("unchecked")
	private Pair<Solution, Object> instanciateField(Field f, SubIndexProvider sip, ReactionRule r) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		//If the field is a simple element,it is direct
		if(sip instanceof SubIndexProviderElement){
			return new Pair<Solution, Object>(this, _mapElements.get(f.getType().getName()).get(sip.getValue()));
		}
		//TIf the field is a set of elements in a subsolution, it will be more complex
		else{
			Method getter = Utils.getMethodFromReactionRule(r, "get", f);
			SubSolution<SubSolutionReactivesAccessor> subSolObject = (SubSolution<SubSolutionReactivesAccessor>) getter.invoke(r, new Object[0]);
			
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



	/**
	 * Retains only the elements/reactives in this solution that are contained in the specified collection. 
	 * In other words, removes from this solution all of its elements that are not contained in the specified collection.
	 * @param c Collection containing elements to be retained in this solution.
	 * @return <code>true</code> if this collection changed as a result of the call. 
	 */
	public boolean retainAll(Collection<?> c) {
		boolean res = false;
		String reactiveName;

		//The write/remove operations on the hash map have to be atomic
		synchronized (_mapElements) {
			Iterator<Object> it = this.iterator();
			Object obj = null;
			while(it.hasNext()) {
				obj = it.next();

				if(!c.contains(obj)) {
					reactiveName = getReactiveType(obj);
					_mapElements.get(reactiveName).remove(obj);
					res = true;
				}
			}
		}

		return res;
	}
	
	/**
	 * Returns the number of elements/reactives in this solution. 
	 * If this collection contains more than <code>Integer.MAX_VALUE</code> elements, returns <code>Integer.MAX_VALUE</code>.
	 * @return The number of elements in this solution. 
	 */
	public int size() {
		int res = 0;

		for(List<Object> reactiveList : _mapElements.values()) {
			res += reactiveList.size();
		}

		return res;
	}
	
	/**
	 * Returns an array containing all of the elements in this solution.
	 * @return An array containing all of the elements in this solution in any order.
	 */
	public Object[] toArray() {
		List<Object> res = new LinkedList<Object>();

		for(List<Object> reactiveList : _mapElements.values()) {
			res.addAll(reactiveList);

		}

		return res.toArray();
	}
	
	/**
	 * Returns an array containing all of the elements in this solution; the runtime type of the returned array is that of the specified array. 
	 * @param a the array into which the elements of this solution are to be stored, if it is big enough; otherwise, a new array of the same runtime type is allocated for this purpose.  
	 * @return An array containing all of the elements in this solution in any order.
	 */
	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a) {
		if(a instanceof Object)
			return (T[]) this.toArray();
		else
			return null;
	}
	
	/**
	 * Returns a string representation of this solution.
	 * The string representation consists of a list of the elements indexed by their type. Here is an example:<br />
	 * <div style="font-style: italic">
	 * Solution is:<br />
	 * java.lang.String -> [string1, string2, string3]<br />
	 * java.lang.Integer -> [int1, int2, int3, int4, int5]
	 * </div>
	 */
	@Override
	public String toString(){
		String res = "Solution is :\n";

		for(Map.Entry<String, List<Object>> entry : _mapElements.entrySet()) {
			res += entry.getKey()+" -> "+entry.getValue()+"\n";
		}

		return res;
	}

	/**
	 * Notifies all the threads waiting on this solution's instance.
	 * This function is called by a reaction rule's thread when it successfully
	 * reacted, to wake every thread that was waiting.
	 */
	public synchronized void wakeAll() {
		notifyAll();
	}
	
	/**
	 * Returns a list containing all the innner solutions of this solution
	 * @return A list containing all the innner solutions of this solution
	 */
	List<Object> getSubSolutions() {
		List<Object> res = _mapElements.get(Solution.class.getName());
		if(res == null)
			res = new ArrayList<Object>();
		return res;
	}
	
	/**
	 * Returns the map of elements containing all this solution's elements/reactives.
	 * @return The map of elements containing all this solution's elements/reactives.
	 */
	Map<String, List<Object>> getMapElements() {
		return _mapElements;
	}

	/**
	 * Sets the log file of the chemical library.
	 * For information, logs are set not to be bigger than 10,000 bytes long
	 * and rotate over 5 files
	 * Anyhow, System.err is default log output stream whether this
	 * method is called or not
	 * @param fileName File name of the log file (a number from 0 to 4
	 * will be appended during logs rotation)
	 * @throws IOException
	 */
	public static void setLogFile(String fileName) throws IOException{
		Utils.logger.addHandler(new FileHandler(fileName, 10000, 5, false));

	}

}
