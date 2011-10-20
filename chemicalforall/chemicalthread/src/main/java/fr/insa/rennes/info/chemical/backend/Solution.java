package fr.insa.rennes.info.chemical.backend;




import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * The solution is a set wich contains reactives and ReactionRules.
 * A reactive can be any kind of object or primitive type. It can also be an other Solution.
 * The ReactionRules are objects that implements the interface ReactiveRule.
 * 
 * @author
 *
 */
public class Solution implements Collection<Object>{
	private Map<String, ArrayList<Object>> _mapElements;
	private boolean _innert = false;

	private boolean _continuerReaction = true;
	private List<Thread> _threadTable = new ArrayList<Thread>();

	private ThreadGroup _threadGroup = new ThreadGroup("Group");

	public Solution() {
		super();
		_mapElements = new HashMap<String, ArrayList<Object>>();
		_mapElements = Collections.synchronizedMap(_mapElements);
	}

	public boolean add(Object arg0) {
		synchronized (_mapElements) {
			String interfaceS = " ";
			for(Class s : arg0.getClass().getInterfaces()){
				interfaceS += s.getName()+" ";
			}
			//System.out.println("AAAAAAAAAAHHH "+interfaceS);
			if(!interfaceS.contains("backend.ReactionRule ")){
				if(_mapElements.get(arg0.getClass().getName()) != null){
					ArrayList<Object> l = _mapElements.get(arg0.getClass().getName());
					boolean result = l.add(arg0);
					_mapElements.put(arg0.getClass().getName(), l);
					return result;
				}else{
					ArrayList<Object> l =new ArrayList<Object>();
					boolean result = l.add(arg0);
					_mapElements.put(arg0.getClass().getName(), l);
					return result;
				}
			}else{
				Class c = arg0.getClass();
				String messageErreur = "";
				//We check that every field has an appropriate setter
				boolean classeOK = true;
				for(Field f : c.getDeclaredFields()){
					boolean getterOK = false;
					boolean setterOK = false;
					for(Method m : c.getDeclaredMethods()){
						if(m.getName().toLowerCase().equals("get"+f.getName().toLowerCase())){
							getterOK = true;
						}
						if(m.getName().toLowerCase().equals("set"+f.getName().toLowerCase())){
							setterOK = true;
						}
					}
					if(!(setterOK && getterOK)){
						classeOK = false;
						if(!setterOK)
							messageErreur+="Il manque le setter associé à "+f+" dans la classe "+arg0.getClass()+"\n";
						if(!getterOK)
							messageErreur+="Il manque le getter associé à "+f+" dans la classe "+arg0.getClass()+"\n";
					}
				}
				if(!classeOK){
					try {
						throw new ChimiqueException(messageErreur);
					} catch (ChimiqueException e) {
						e.printStackTrace();
					}
				}
				if(_mapElements.get("ReactionRule") != null){
					ArrayList<Object> l = _mapElements.get("ReactionRule");
					boolean result = l.add(arg0);
					_mapElements.put("ReactionRule", l);
					return result;
				}else{
					ArrayList<Object> l =new ArrayList<Object>();
					boolean result = l.add(arg0);
					_mapElements.put("ReactionRule", l);
					return result;
				}
			}
		}
	}

	public boolean addAll(Collection arg0) {
		synchronized (_mapElements) {
			for(Object obj : arg0){
				this.add(obj);
			}
		}
		return false;
	}

	public void clear() {
		_mapElements.clear();
	}

	public boolean contains(Object arg0) {
		return _mapElements.containsValue(arg0);
	}

	public boolean containsAll(Collection arg0) {
		return _mapElements.values().containsAll(arg0);
	}

	public synchronized void firstSleep(String s){

	}

	protected synchronized boolean getContinuerReaction() {
		return _continuerReaction;
	}

	public boolean is_innert() {
		synchronized (this) {
			return _innert;
		}
	}

	public boolean isEmpty() {
		return _mapElements.isEmpty();
	}
	public Iterator iterator() {
		return _mapElements.values().iterator();
	}
	private void launchThread(ReactionRule r){
		ChemicalThread t = new ChemicalThread(r, this, _threadGroup);
		_threadTable.add(t);
		t.start();
	}


	/*
	 * When a reaction rule did not find any reactives, it calls this function.
	 * It checks if this reaction rule's thread is the last standing. If it is,
	 * it stops all the reaction and declares the solution inert.
	 */
	public synchronized void makeSleep(String s){
		int nbAwake = 0;
		for(Thread t : _threadTable){
			if(!t.getState().equals(Thread.State.WAITING)){
				nbAwake++;
			}
		}
		if(nbAwake>1){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else{
			synchronized (this) {
				_continuerReaction = false;
			}
			notifyAll();
			synchronized (this) {
				_innert = true;
			}
		}
	}

	public boolean remove(Object arg0) {

		return false;
	}

	public boolean removeAll(Collection arg0) {
		return false;
	}
	/*
	 * This method is called by every thread-reaction rule to get its reactives.
	 * It returns true if a set of parameter have been found, else false. The reactives are set directly
	 * in the reaction rule instance by reflexivity so no need to return them.
	 * This function is synchronized on the atoms' hash map
	 */
	public boolean requestForParameters(ReactionRule r) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
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
			HashMap<String, List<Integer>> mapIndexProvider = new HashMap<String, List<Integer>>();
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
				if(_mapElements.get(table[i])== null)// || _mapElements.get(table[i]).size()==0)
					return false;
				tabMaxIndex[i] = _mapElements.get(table[i]).size();
			}
			List<List<Integer>> listProvider = new ArrayList<List<Integer>>();
			for(String s : mapIndexProvider.keySet()){
				if(mapIndexProvider.get(s).size()>1){
					listProvider.add(mapIndexProvider.get(s));
				}
			}
			IndexProvider indexPovider = null;
			try{
				indexPovider = new IndexProvider(listProvider, tabMaxIndex);
			}catch(ChimiqueException e){
				System.out.println("Exception levée et gérée magnifiquement");
				return false;
			}

			//Effectively research a valid set of reactives for the reaction rule
			Object reactives[] = new Object[fields.length];
			boolean hasMatched = false;
			boolean b;
			//Loop until the reactives has been found OR all combination have been tested
			while(!indexPovider.is_overflowReached()){
				int i=0;
				Object obj = null;
				for(Field f : fields){
					for(Method m : r.getClass().getDeclaredMethods()){
						if(m.getName().toLowerCase().equals(("set"+f.getName().toLowerCase()))){
							//On invoque la méthode
							obj = _mapElements.get(f.getType().getName()).get(indexPovider.get(i));
							m.invoke(r, new Object[]{obj});

						}
					}
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
				indexPovider.increment();
			}
			//All the possibilities have been tested here
			//Return false if nothing matched
			if(!hasMatched){
				return false;
			}
		}

		return true;
	}

	public boolean retainAll(Collection arg0) {
		return false;
	}

	public void run() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		synchronized (this) {
			for(Object r : _mapElements.get("ReactionRule")){//r est une ReactionRule
				launchThread((ReactionRule)r);
			}
			notifyAll();
		}
	}

	public int size() {
		return _mapElements.values().size();
	}

	public Object[] toArray() {
		return _mapElements.values().toArray();
	}

	public Object[] toArray(Object[] arg0) {
		return _mapElements.values().toArray(arg0);
	}

	@Override
	public String toString(){
		return _mapElements.toString();
	}

	/*
	 * This function is called by a reaction rule's thread when it successfully
	 * reacted, to wake every other thread that was asleep
	 */
	public synchronized void wakeAll() {
		notifyAll();
	}
}
