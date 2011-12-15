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
	}

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
			if(!className.equals(ChemicalElement.class.getName()) && addElement){
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
			List l = _mapElements.get(ReactionRule.class.getName());
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

	//This void synchronised method acts as a lock
	//	protected synchronized void firstSleep(String s){
	//
	//	}

	private synchronized void endOfReaction(){
		_reactionInProgress = false;

		notifyAll();
		_inert = true;
		fireInertEvent(new InertEvent(new Object()));
	}

	public void fireInertEvent(InertEvent e){
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
		}else if(interfaceS.contains(" "+ChemicalElement.class.getName()+" ")){
			return ChemicalElement.class.getName();
		}else {
			return reactive.getClass().getName();
		}
	}

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

	/*
	 * Creates and launches a thread that will make the specified reaction rule react.
	 * All the reaction rules threads will be in the same thread group and have
	 * a pointer to the solution (this)
	 */
	private void launchThread(ReactionRule r){
		ChemicalThread t = new ChemicalThread(r, this, _threadGroup);
		_threadTable.put(r, t);
		t.start();
	}

	/*
	 * When a reaction rule did not find any reactives, it calls this function that will "make" it sleep.
	 * It checks if this reaction rule's thread is the last standing. If it is,
	 * it stops all the reaction and declares the solution inert.
	 * This function is called by the main function of a chemical thread
	 */
	protected synchronized void makeSleep(){
		int nbAwaken = getNumberOfActiveThreads();

		//If there is more than one thread alive (including the current one)
		//it means other reaction rules can react, so just make this thread wait
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
	 */
	public void react() {
		synchronized (this) {
			_reactionInProgress = true;
			notifyAll();
		}
	}

	private void reactMe(){

	}

	//To remove the object, we have to find its type
	//and check that it is present in the hash map
	public boolean remove(Object reactive) {
		String reactiveType = getReactiveType(reactive);

		//For the same reason as in add, synchronized have to be declared on the hash map
		synchronized(_mapElements) {
			//If the hash map doesn't even know the type, return false
			if(_mapElements.get(reactiveType) == null) {
				return false;
			}

			return _mapElements.get(reactiveType).remove(reactive);
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
		for(int i=0; i< fields.length; i++){
			table[i] = fields[i].getType().getName();
		}

		//The access to the main atom map is restricted to 1 thread at a time
		synchronized(_mapElements) {
			//Initialize the index provider to try every possible combination
			int tabMaxIndex[] = new int[table.length];

			//Construct the map of the dependent indexes (two dependent indexes can not have the same value
			//as they refer to the same element)
			Map<String, List<Integer>> mapIndexProvider = new HashMap<String, List<Integer>>();
			for(int i = 0; i < table.length; i++){
				if(mapIndexProvider.containsKey(table[i])){
					List<Integer> l = mapIndexProvider.get(table[i]);
					l.add(i);
					mapIndexProvider.put(table[i], l);
				}else{
					List<Integer> l = new ArrayList<Integer>();
					l.add(i);
					mapIndexProvider.put(table[i], l);
				}

				//If the type isn't even an entry of the hash map, return false (didn't find any reactive)
				if(_mapElements.get(table[i])== null)
					return false;

				tabMaxIndex[i] = _mapElements.get(table[i]).size();
			}

			//We have to provide the IndexProvider a list of a list of int, so
			//we need to transform the map of list in a list of list
			List<List<Integer>> listProvider = new ArrayList<List<Integer>>();
			for(String s : mapIndexProvider.keySet()){
				if(mapIndexProvider.get(s).size()>1){
					listProvider.add(mapIndexProvider.get(s));
				}
			}

			//Instantiate the IndexProvider object
			IndexProvider indexProvider = null;
			try{
				indexProvider = new IndexProvider(listProvider, tabMaxIndex, _strategy);
			}catch(ChemicalException e){
				System.out.println("Exception levée et gérée magnifiquement");
				return false;
			}

			//Effectively research a valid set of reactives for the reaction rule
			Object reactives[] = new Object[fields.length];
			int setterNumber;
			Method setter;
			boolean hasMatched = false;
			//Loop until the reactives has been found OR all combination have been tested
			while(!indexProvider.is_overflowReached()){
				int i=0;
				Object obj = null;
				for(Field f : fields){
					//Find the setter for this field
					setterNumber = _mapReactionRulesSetters.get(r.getClass().getName()+"."+f.getName());
					setter = r.getClass().getDeclaredMethods()[setterNumber];

					//and invoke the setter
					obj = _mapElements.get(f.getType().getName()).get(indexProvider.get(i));
					setter.invoke(r, new Object[]{obj});

					reactives[i] = obj;
					i++;
				}
				//The right types have been found, now test the selection rule
				if(r.computeSelect()){

					//remove the selected reactives from the solution
					for(Object react : reactives){
						_mapElements.get(react.getClass().getName()).remove(react);
					}
					hasMatched = true;
					break;
				}
				indexProvider.increment();
			}
			//All the possibilities have been tested here
			//Return false if nothing matched
			if(!hasMatched){
				return false;
			}
		}
		return true;
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
