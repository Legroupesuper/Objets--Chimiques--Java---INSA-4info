/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChemicalLibSuper.

    ChemicalLibSuper is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChemicalLibSuper is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with ChemicalLibSuper.  If not, see <http://www.gnu.org/licenses/>
 */
package fr.insa.rennes.info.chemical.backend;




import java.io.File;
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
 * Central class of the library. The solution is a set of reagents and ReactionRules.
 * A reagent can be any kind of object or primitive type. It can also be an other Solution.
 * The ReactionRules are objects that implements the interface ReactionRule.
 * 
 * @see ReactionRule
 * 
 * @author Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 *
 */
public final class Solution implements Collection<Object>{
	/**
	 * Different strategies available for reagent selection sequence.
	 * <p>
	 * ORDERED Iterates on the elements of the solution and tries to use reagents in order of appearance. Each execution will give the same result.<br />
	 * RANDOM The iteration is randomized and each execution is different (indeterminism).
	 *</p>
	 */
	public static enum Strategy{RANDOM, ORDERED};

	/**
	 * The map containing all the reagents. The keys of the map are the type (i.e java.lang.String)
	 * and the values are a list of object containing all the reagents of this type.
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
	 * Indicates that the reaction is still in progress. Useful for the reaction rules threads. If it is set at false,
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
	private ThreadGroup _threadGroup;

	/**
	 * The reagents iteration strategy, chosen by the user. The chosen strategy will apply 
	 * to every reaction rule added in this solution. 
	 * @see Solution.Strategy
	 */
	private Strategy _strategy;

	/**
	 * The inert event listener of this solution. Might be an other solution
	 * or a listener defined by the user. ONly one inert listener can be used a a time.
	 * @see InertEvent
	 * @see InertEventListener
	 */
	private InertEventListener _listener = null;


	/**
	 * Default constructor for a Chemical Programming-powered Solution.<br />
	 * Uses random strategy as default behavior
	 * @see Solution.Strategy
	 */
	public Solution() {
		this(Strategy.RANDOM);
	}

	/**
	 * Constructor for a Chemical Programming-powered Solution, with a Strategy parameter.
	 * @param s The desired strategy to iterate over the solution's reagents.
	 * @see Solution.Strategy
	 */
	public Solution(Strategy s) {
		super();
		_mapElements = new HashMap<String, List<Object>>();
		_mapElements = Collections.synchronizedMap(_mapElements);
		_mapReactionRulesSetters = new HashMap<String, Integer>();
		_threadGroup = new ThreadGroup("ChemicalGroup");
		_threadTable = Collections.synchronizedMap(new HashMap<ReactionRule, ChemicalThread>());
		_strategy = s;
		_inert = false;
	}

	/**
	 * Used in add function when a ReactionRule object is added as a reagent. Checks if the ReactionRule class is valid.<br />
	 * A valid ReactionRule class implements setters for its attributes that do not specify the Dontreact annotation.
	 * @param reactionRuleObject The reaction rule object added in the solution.
	 * @return <code>true</code> if the reaction rule object is valid
	 * @see Dontreact
	 * @see ReactionRule
	 */
	private boolean checkReactionRuleReagent(Object reactionRuleObject) {
		Class<? extends Object> rrClass = reactionRuleObject.getClass();

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

			if(!setterOK){
				throw new IllegalArgumentException("The specified reaction rule hasn't got all the necessary setters: missing setter for "+rrClass.getSimpleName()+"::"+f.getName());
			}
		}

		if(!classOK){
			return false;
		} 


		//If the reaction rule doesn't exist, we add it in the table
		if(!_threadTable.containsKey(reactionRuleObject)){
			ReactionRule r = (ReactionRule)reactionRuleObject;

			ChemicalThread t = new ChemicalThread(r, this, _threadGroup);
			_threadTable.put(r, t);

			//Only if the reaction is in progress, we start the thread
			if(_reactionInProgress)
				t.start();
		} else 
			return false;

		return true;
	}

	/**
	 * Used in add function to add a sub-solution as a reagent in the solution.<br />
	 * Applies a special treatment to the solution reagent : sets a InertEventListener on the sub-solution,
	 * and begin the reaction of the sub-solution.
	 * @param solutionObject The sub-solution added.
	 * @see InertEventListener
	 */
	private void processAddSubSolution(Object solutionObject) {
		Solution sol = (Solution) solutionObject;
		sol.addInertEventListener(new InertEventListener() {
			public void isInert(InertEvent e) {
				Solution.this.tryTrivialEndOfReaction();
			}
		});
		if(_reactionInProgress)
			sol.react();
	}

	/**
	 * Adds any type of object to this solution.<br />
	 * Every object inserted in the solution will be considered as a reagent, 
	 * included Solution objects and ReactionRule objects, and can be used by any ReactionRule object.
	 * @param newReagent The new reagent to add to the solution.
	 * @return <code>true</code> (as specified by Collection.add(E))
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	public boolean add(Object newReagent) {
		//The map (container of our elements) must NOT be accessed concurrently
		synchronized (_mapElements) {

			//At first we determine whether it is a ReactionRule or not
			String className = getReagentType(newReagent);
			String rawClassName = newReagent.getClass().getName();
			boolean addElement = true;

			//It is a ReactionRule, hence special treatment
			if(className.equals(ReactionRule.class.getName())) {
				addElement = checkReactionRuleReagent(newReagent);
			} else if(className.equals(Solution.class.getName())){
				processAddSubSolution(newReagent);
			}

			if(!className.equals(SubSolutionReagentsAccessor.class.getName()) && addElement){
				boolean result;
				//When you add an element in the solution, the solution is no more inert
				_inert = false;

				//There is already an entry in the map for this reagent, so we just add the element
				if(_mapElements.get(rawClassName) != null){
					result = _mapElements.get(rawClassName).add(newReagent);
				}
				//There is no entry for the moment : we init the list
				else{
					List<Object> l =new ArrayList<Object>();
					result = l.add(newReagent);
					_mapElements.put(rawClassName, l);
				}
				if(getNumberOfActiveThreads() == 1 && !containsNonInertSubSol())
					endOfReaction();
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
	 * Removes all of the reagents from this solution.
	 */
	public void clear() {
		_mapElements.clear();
	}

	/**
	 * Returns true if this solution contains the specified element/reagent.
	 * @param reagent Reagent whose presence in this list is to be tested 
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	public boolean contains(Object reagent) {
		String reagentType = reagent.getClass().getName();

		//If the hash map doesn't even know the type, return false
		if(_mapElements.get(reagentType) == null) {
			return false;
		}

		return _mapElements.get(reagentType).contains(reagent);
	}

	/**
	 * Returns <code>true</code> if this solution all of the elements/reagents in the specified collection.
	 * @return <code>true</code> if this solution all of the elements/reagents in the specified collection.
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
			if(_mapElements.remove(r.getClass().getName()) != null) {
				synchronized (_threadTable) {
					_threadTable.get(r).stopTheThread();
					_threadTable.remove(r);
				}

				tryTrivialEndOfReaction();
			}
		}
	}

	/**
	 * Checks if the end of the reaction is reached, and if it is, call {@link #endOfReaction()}.
	 * The end of the reaction is reached when there is no longer any active thread and 
	 * that there is no non inert inner solution. In the contrary, 
	 * if the end of the reaction isn't reached, this function wakes all the reaction
	 * rule threads. 
	 */
	private void tryTrivialEndOfReaction() {
		if(_threadTable.size() == 0 && !containsNonInertSubSol())
			endOfReaction();
		else {
			wakeAll();
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
		_inert = true;

		notifyAll();
		fireInertEvent(new InertEvent(this));
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
			//Count the number of thread that are awaken right now, 
			//apart from the one running this function
			for(Thread t : _threadTable.values()){
				if(!t.getState().equals(Thread.State.WAITING)){
					nb++;
				}
			}
		}
		return nb;
	}

	/**
	 * Returns <code>true</code> if this solution contains at least one non inert inner solution.
	 * @return <code>true</code> if this solution contains at least one non inert inner solution.
	 */
	private boolean containsNonInertSubSol() {
		List<Object> subSols = _mapElements.get(Solution.class.getName());
		if(subSols == null)
			return false;

		for(Object o : subSols) {
			Solution s = (Solution)o;
			if(!s.is_inert()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gives the type of the specified reagent.
	 * For most of the object, the value returned is it's raw name (<code>object.getClass().getName()</code>), 
	 * but for the Solution and ReactionRule object, a special name is returned.
	 * @param reagent Object whose type is to be determined.
	 * @return The java type of the specified reagent.
	 */
	private String getReagentType(Object reagent) {
		for(Class<?> s : reagent.getClass().getInterfaces()){
			if(s.getName().equals(ReactionRule.class.getName())) {
				return ReactionRule.class.getName();
			}else if(s.getName().equals(SubSolutionReagentsAccessor.class.getName())){
				return SubSolutionReagentsAccessor.class.getName();
			}
		}

		return reagent.getClass().getName();
	}

	/**
	 * Returns <code>true</code> if this solution is inert. A Solution is inert when no more reaction are possible. 
	 * This means that there is no more ReactionRules that can react; the solution reached a stable state.
	 * Note: to be inert, all the solution's inner solutions must be inert as well.
	 * @return <code>true</code> if this solution is inert.
	 */
	public boolean is_inert() {
		return _inert;
	}

	/**
	 * Returns <code>true</code> if this solution contains no elements/reagents.
	 * @return <code>true</code> if this solution contains no elements/reagents.
	 */
	public boolean isEmpty() {
		return _mapElements.isEmpty();
	}

	/**
	 * Returns an iterator over the elements/reagents in this solution. 
	 * As this solution can contain any type of element, the function returns an iterator of Object objects.
	 * There are no guarantees concerning the order in which the elements are returned
	 * @return an <code>Iterator</code> over the elements in this solution.
	 * @see Iterator
	 */
	public Iterator<Object> iterator() {
		List<Object> reagentsCopy = new LinkedList<Object>();
		for(List<Object> reagentList : _mapElements.values()) {
			reagentsCopy.addAll(reagentList);
		}
		return Collections.unmodifiableList(reagentsCopy).iterator();
	}

	/**
	 * Returns a list containing the elements/reagents in this solution that have the specified type. 
	 * As this solution can contain any type of element, the function returns a list of Object objects.
	 * There are no guarantees concerning the order in which the elements are returned
	 * @param type The type of the objects needed.
	 * @return a <code>List</code> containing the elements of the specified type in this solution.
	 */
	public List<Object> getReactivesFromType(Class<?> type) {
		List<Object> result = _mapElements.get(type.getName());
		if(result == null)
			return null;
		else
			return Collections.unmodifiableList(result);
	}

	/**
	 * Called by a thread, makes the thread wait until a <code>notify</code> is called.
	 * When a reaction rule did not find any reagents, it calls this function that will "make" it sleep.
	 * It checks if this reaction rule's thread is the last standing. If it is,
	 * it stops all the reaction and declares the solution inert.
	 * This function is called by the main function of a chemical thread.
	 * @see ChemicalThread
	 * @see Solution#_inert
	 */
	synchronized void makeSleep(){
		int nbThreadAwaken = getNumberOfActiveThreads();
		boolean containsNonInertSubSolutions = containsNonInertSubSol();

		//If there is more than one thread alive (including the current one)
		//it means other reaction rules may still be reacting, so just make this thread wait.
		//Same thing with the number of inert solution: a solution can't be inert if one or more
		//of its inner solutions isn't inert.
		if(nbThreadAwaken > 1 || containsNonInertSubSolutions){
			//Loop on the wait (always)
			boolean interrupted;
			do {
				interrupted = false;
				try {
					wait();
				} catch (InterruptedException e) {
					interrupted = true;
				}
			} while(interrupted);

		} else {
			//If the current thread is the last one standing, kill all the threads by switching the
			//boolean _keepOnReacting to false (all thread are in a loop on this boolean).
			endOfReaction();
		}
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
			_inert = false;
			//We try to launch all the inner solutions
			if(_mapElements.get(Solution.class.getName()) != null){
				for(Object o : _mapElements.get(Solution.class.getName())){
					Solution s = (Solution) o;
					/*
					 * We add an InertEvenListener that will notify this
					 * when s will become inert. The notification will awake all the ReactionRules which are
					 * included in _mapElements.
					 */
					s.addInertEventListener(new InertEventListener() {
						public void isInert(InertEvent e) {
							Solution.this.tryTrivialEndOfReaction();
						}
					});
					s.react();
				}
			}else if(_threadTable.size() == 0){
				//If there is no ReactionRule (thus no thread) in the solution, 
				//the solution can not react.
				endOfReaction();
				return;
			}

			//Finally, launch all the reaction rule threads
			for(ChemicalThread t : _threadTable.values())
				t.start();
		}
	}

	/**
	 * Removes a single instance of the specified element/reagent from this solution, if it is present.
	 * Returns <code>true</code> if this solution contained the specified element/reagent (or equivalently, if this solution changed as a result of the call). 
	 * @param reagent Element to be removed from this solution, if present. 
	 * @return <code>true</code> if an element was removed as a result of this call 
	 */
	public boolean remove(Object reagent) {
		//To remove the object, we have to find its type
		//and check that it is present in the hash map

		String reagentType = reagent.getClass().getName();
		boolean res = false;
		//For the same reason as in add, synchronized have to be declared on the hash map
		synchronized(_mapElements) {
			//If the hash map doesn't even know the type, return false
			if(_mapElements.get(reagentType) == null) {
				return false;
			}

			res = _mapElements.get(reagentType).remove(reagent);
			if(_mapElements.get(reagentType).size() == 0){
				_mapElements.remove(reagentType);
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
	 * Called by every thread-reaction rule to get its reagents.
	 * It returns true if a set of parameter has been found, else false. The reagents are set directly
	 * in the reaction rule instance by reflexivity, there is no need to return them.
	 * This function is synchronized on the _mapElements attribute.
	 * @param r The reaction rule requesting for parameters;
	 * @return <code>true</code> if parameters/reagents where found.
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
			} catch(NullPointerException e) { 
				throw e;
			} catch(Exception e) {
				//Just in case there is any other exception, and in order to avoid 
				//to annoy the user with a stack trace, just return false 
				return false;
			}

			//Once the index provider is created, we have to search for reagents matching,
			//Meanwhile, we have to catch every Exception before the user get them
			try {
				List<Pair<Solution, Object>> reagents = searchForReagents(r, rrFields, indexProvider);

				//If a valid set of reagent was found, erase the reagents from the solutions
				//and return true
				if(reagents != null) {
					extractReagentsFromSolutions(reagents);
					return true;
				} else {
					//Else, no reagents where found, return false
					return false;
				}

			} catch (IllegalArgumentException e) {
				return false;
			} catch (IllegalAccessException e) {
				return false;
			} catch (InvocationTargetException e) {
				return false;
			} catch(NullPointerException e) { 
				throw e;
			} catch(Exception e) {
				//Just in case there is any other exception, and in order to avoid 
				//to annoy the user with a stack trace, just return false 
				return false;
			}
		}
	}

	/**
	 * Searches for reagents for the specified reaction rule.
	 * The difference with {@link Solution#requestForParameters(ReactionRule)} is that this functions is given
	 * a fully instanciated index provider.
	 * If a set of reagents is found following the types, the {@link ReactionRule#computeSelect()}
	 * is tested. In case of success, the reaction rule's fields are instantiated.
	 * Returns the set of reagents one was found that passes the {@link ReactionRule#computeSelect()} function.
	 * If no valid set of reagents was found, returns null.
	 * @param rr The reaction rule asking for reagents
	 * @param rrFields The fields of the reaction rule
	 * @param indexProvider The index provider, 
	 * @return The set of reagents if a valid one was found, else null.
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @see IndexProvider
	 */
	private List<Pair<Solution, Object>> searchForReagents(ReactionRule rr, Field[] rrFields, IndexProvider indexProvider) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		//Effectively research a valid set of reagents for the reaction rule
		//given all the tools
		List<Pair<Solution, Object>> reagents = new ArrayList<Pair<Solution,Object>>();
		int setterNumber;
		Method setter;
		Pair<Solution, Object> reagentObject = null;
		int i;
		boolean tryComputeSelect;

		//Loop until the reagents has been found OR all combination have been tested
		while(!indexProvider.is_overflowReached()) {
			SubIndexProviderSolution sipSol = indexProvider.getSubIndexProvider();

			i = 0;
			tryComputeSelect = true;
			for(Field f : rrFields) {
				if(f.getAnnotation(Dontreact.class) == null){
					//Find the setter for this field...
					setterNumber = _mapReactionRulesSetters.get(rr.getClass().getName()+"."+f.getName());
					setter = rr.getClass().getDeclaredMethods()[setterNumber];

					//..instanciate the (value) of the field...
					reagentObject = instanciateField(f, sipSol.get_listSubIP().get(i), rr);
					System.out.println(f.getName()+" : "+reagentObject);
					//If instanciateField returned false, an error occured, go on the the next increment of the index provider
					if(reagentObject == null) {
						tryComputeSelect = false;
						break;
					}

					//...and invoke the setter
					setter.invoke(rr, new Object[]{reagentObject.get_second()});

					reagents.add(reagentObject);
					i++;
				}
			}

			//When we get here, the right types have been found, now test the selection rule
			if(tryComputeSelect && rr.computeSelect()) {
				//If the computeSelect says OK, return the found set of reagents
				return reagents;
			}

			//If we did not leave the function, it means something is 
			//wrong with the set of reagents
			reagents.clear();

			//Increment the index provider and loop
			indexProvider.increment();
		}

		//If we reach this line, it means we tried every combination and all failed
		return null;
	}

	/**
	 * Extracts the given reagents from this solution (and inner solutions).
	 * This function is called right after finding reagents for a reaction rule that matches
	 * the types and the {@link ReactionRule#computeSelect()}. They have to be deleted from this solution, just like 
	 * chemical reagents in real chemistry.
	 * reagents can possibly be in a non inert inner solution (using sch a reagent is not allowed by chemical programming). 
	 * @param reagents The list of reagents to be deleted. Reagents can possibly be in inner solutions.
	 */
	@SuppressWarnings("unchecked")
	private void extractReagentsFromSolutions(List<Pair<Solution, Object>> reagents) {
		//Remove the selected reagents from the solution
		for(Pair<Solution, Object> react : reagents){
			//If the reagent is a SubSolution object, a different treatment is needed
			if(react.get_second().getClass().getName().equals(SubSolution.class.getName())){
				SubSolution<SubSolutionReagentsAccessor> s = (SubSolution<SubSolutionReagentsAccessor>) react.get_second();

				//Delete each object in the sub-solution
				for(Object o : s.getElements()){;
				react.get_first()._mapElements.get(o.getClass().getName()).remove(o);
				}
			} else if(getReagentType(react.get_second()).equals(ReactionRule.class.getName())) {
				//For a reaction rule, use the dedicated function
				react.get_first().deleteReaction((ReactionRule)react.get_second());
			} else{
				//For a simple reagent (not a SubSolution object), just delete it from its solution
				react.get_first()._mapElements.get(react.get_second().getClass().getName()).remove(react.get_second());
			}
		}
	}

	/**
	 * This function is used to instantiate a field of a reaction rule in order to perform a reaction.
	 * When a set of reagents matching the right types for the specified reaction rule are found, this function is
	 * called (by {@link Solution#searchForReagents(ReactionRule, Field[], IndexProvider)} to instantiate the reaction rule's fields one by one.
	 * To succeed this operation needs to use an SubIndexProvider which gives the index of the element/reagent
	 * to use in this solution or in its inner solutions.
	 * @param f The ReactionRule field
	 * @param sip The sub index provider
	 * @param r The reaction rule that is concerned by the instanciation.
	 * @return A pair of object containing the value to be set to the field, and a reference to the solution ('possibly an inner solution) in which this reagent is contained.
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
			SubSolution<SubSolutionReagentsAccessor> subSolObject = (SubSolution<SubSolutionReagentsAccessor>) getter.invoke(r, new Object[0]);

			//In order to instanciate a SubSolution object, we have to go down the 
			//nested solutions... (recursion)
			SubIndexProviderSolution sipSol = (SubIndexProviderSolution)sip;
			Solution nextSol = (Solution)this._mapElements.get(Solution.class.getName()).get(sip.getValue());
			while(sipSol.get_listSubIP().size() == 1 && sipSol.get_listSubIP().get(0) instanceof SubIndexProviderSolution){
				nextSol = (Solution) nextSol._mapElements.get(Solution.class.getName()).get(sipSol.get_listSubIP().get(0).getValue());
				sipSol = (SubIndexProviderSolution) sipSol.get_listSubIP().get(0);
			}

			//If the solution in which the reagents are going to be selected
			//isn't inert, then return null to mean an error occured
			if(!nextSol.is_inert())
				return null;

			//...until we reach the level in which the reagents have to be found,
			//so that we can instanciate the elementList of the SubSolution object
			List<Object> l = new ArrayList<Object>();
			int i = 0;
			for(Class<?> c : subSolObject.getTypeList()){
				l.add(nextSol._mapElements.get(c.getName()).get(sipSol.get_listSubIP().get(i).getValue()));
				i++;
			}

			//Finally, we use the setter of SubSolution
			subSolObject.setElements(l);
			subSolObject.setSolution(nextSol);


			return new Pair<Solution, Object>(nextSol, subSolObject);
		}
	}



	/**
	 * Retains only the elements/reagents in this solution that are contained in the specified collection. 
	 * In other words, removes from this solution all of its elements that are not contained in the specified collection.
	 * @param c Collection containing elements to be retained in this solution.
	 * @return <code>true</code> if this collection changed as a result of the call. 
	 */
	public boolean retainAll(Collection<?> c) {
		boolean res = false;
		String reagentName;

		//The write/remove operations on the hash map have to be atomic
		synchronized (_mapElements) {
			Iterator<Object> it = this.iterator();
			Object obj = null;
			while(it.hasNext()) {
				obj = it.next();

				if(!c.contains(obj)) {
					reagentName = getReagentType(obj);
					_mapElements.get(reagentName).remove(obj);
					res = true;
				}
			}
		}

		return res;
	}

	/**
	 * Returns the number of elements/reagents in this solution. 
	 * If this collection contains more than <code>Integer.MAX_VALUE</code> elements, returns <code>Integer.MAX_VALUE</code>.
	 * @return The number of elements in this solution. 
	 */
	public int size() {
		int res = 0;

		for(List<Object> reagentList : _mapElements.values()) {
			res += reagentList.size();
		}

		return res;
	}

	/**
	 * Returns an array containing all of the elements in this solution.
	 * @return An array containing all of the elements in this solution in any order.
	 */
	public Object[] toArray() {
		List<Object> res = new LinkedList<Object>();

		for(List<Object> reagentList : _mapElements.values()) {
			res.addAll(reagentList);

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
		String solutionBeginning = "Solution\n{\n";
		String solutionEnd ="}\n";
		String res = solutionBeginning+this.prettyPrint(0)+solutionEnd;
		return res;
	}
	
	/**
	 * A somewhat useful pretty printer, which works recursively
	 * @param level the depth level
	 */
	private String prettyPrint(int level){
		String solutionEnd ="}\n";

		String res = "";
		String alinea = "";
		// Prepare indentation
		for(int i = 0 ; i < level+1 ; i++){
			alinea += "\t";
		}

		for(Map.Entry<String, List<Object>> entry : _mapElements.entrySet()) {
			String type = entry.getKey();
			if(type.equals(Solution.class.getName())){
				res += alinea+"Solution\n" + alinea + "{\n";
				for(Object sol : entry.getValue()){
					res += ((Solution)sol).prettyPrint(level+1);
				}
				res += alinea+solutionEnd;
			} else {
				res += alinea+type+" -> "+entry.getValue()+"\n";				
			}
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
			res = new LinkedList<Object>();
		return res;
	}

	/**
	 * Returns the map of elements containing all this solution's elements/reagents.
	 * @return The map of elements containing all this solution's elements/reagents.
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
