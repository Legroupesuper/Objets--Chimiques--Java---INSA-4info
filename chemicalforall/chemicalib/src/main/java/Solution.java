import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;


public class Solution implements Collection<Object>{
	private HashMap<String, ArrayList<Object>> _mapElements;
	private boolean _nouvelElementCree = false;
	public Solution() {
		super();
		_mapElements = new HashMap<String, ArrayList<Object>>();
	}

	@Override
	public boolean add(Object arg0) {
	//	_nouvelElementCree = true;
		String interfaceS = "";
		for(Class s : arg0.getClass().getInterfaces()){
			interfaceS += s.getName();
		}
		if(!interfaceS.contains("ReactionRule")){
			//System.out.println("Ajout d'un objet de type : "+arg0.getClass().getName()+" | "+interfaceS);
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
			//System.out.println("Ajout d'un objet de type : "+arg0.getClass().getName()+" | "+interfaceS);

			Class c = arg0.getClass();
			String messageErreur = "";
			//On vérifie que les fields ont leur setteur et getteur
			boolean classeOK = true;
			for(Field f : c.getDeclaredFields()){
			//	System.out.println("Field : "+f.getName());
				boolean getteurOK = false;
				boolean setteurOK = false;
				for(Method m : c.getDeclaredMethods()){
					if(m.getName().toLowerCase().equals("get"+f.getName())){
						getteurOK = true;
					}
					if(m.getName().toLowerCase().equals("set"+f.getName())){
						setteurOK = true;
					}
				}
				if(!(setteurOK && getteurOK)){
					classeOK = false;
					if(!setteurOK)
						messageErreur+="Il manque le setteur associé à "+f+" dans la classe "+arg0.getClass()+"\n";
					if(!getteurOK)
						messageErreur+="Il manque le getteur associé à "+f+" dans la classe "+arg0.getClass()+"\n";
				}
			}
			if(!classeOK){
				try {
					throw new ChimiqueException(messageErreur);
				} catch (ChimiqueException e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
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

	@Override
	public boolean addAll(Collection arg0) {
		// TODO FAIRE LES VERIFICATIONS
		return false;
	}

	@Override
	public void clear() {
		_mapElements.clear();
	}

	@Override
	public boolean contains(Object arg0) {
		return _mapElements.containsValue(arg0);
	}

	@Override
	public boolean containsAll(Collection arg0) {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return _mapElements.isEmpty();
	}

	@Override
	public Iterator iterator() {
		return _mapElements.values().iterator();
	}

	@Override
	public boolean remove(Object arg0) {
		return false;
	}

	@Override
	public boolean removeAll(Collection arg0) {
		return false;
	}

	@Override
	public boolean retainAll(Collection arg0) {
		return false;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public Object[] toArray() {
		return null;
	}

	@Override
	public Object[] toArray(Object[] arg0) {
		return null;
	}
	
	void run() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		//Tant qu'un nouvel élément est créé on reteste si on peut faire la réaction.
		_nouvelElementCree = true;
		while(_nouvelElementCree){
			_nouvelElementCree = false;
			//On parcours toutes les ReactionRules
			for(Object r : _mapElements.get("ReactionRule")){//r est une ReactionRule
				Class c = r.getClass();//On récupère la classe
				/*
				 *Il est nécéssaire de sortir tous les paramètres dont on a besoin de la hashmap
				 *afin d'éviter qu'une autre RR les utilise.
				 *Si jamais les conditions nécessaires à l'éxécution de la RR ne sont pas satisfaites,
				 *on se servira du tableau suivant pour réinjecter les objets dans la solution. 
				 */
				ArrayList<Object> objetsEnCours = new ArrayList<Object>();
				boolean reactionPossible = true;//Sert à évaluer si la réaction est possible
				for(Field f : c.getDeclaredFields()){//On parcours tous les réactifs de la RR
					//System.out.println("On a un champ : "+f.getType().getName());
					/*
					 * Si la solution contient ce type d'éléments et que la liste n'est pas vide
					 */
					if(_mapElements.containsKey(f.getType().getName()) && _mapElements.get(f.getType().getName()).size()>0){
						ArrayList<Object> l = _mapElements.get(f.getType().getName());
						/*
						 * On cherche le setteur associé au champ en question
						 */
						for(Method m : c.getDeclaredMethods()){
							if(m.getName().toLowerCase().equals(("set"+f.getName()))){
								//On invoque la méthode
								m.invoke(r, new Object[]{l.get(0)});
							}
						}
						//On ajoute l'objet aux objets en cours de traitement
						objetsEnCours.add(l.get(0));
						//On le sort du multi-ensemble
						l.remove(0);
					//	System.out.println("Size : "+l.size());
		
					}else{
						//La réaction n'est plus possible
						reactionPossible = false;
						//On réinjecte les objets en cours
						for(Object t : objetsEnCours){
							//System.out.println("FIN - On ajoute "+t);
							this.add(t);
						}
						objetsEnCours.clear();
						break;
					}
				}
				if(reactionPossible){
					if(((ReactionRule)r).computeSelect()){
						Object tab[] = ((ReactionRule)r).computeResult();
						for(Object t : tab){
							this.add(t);
						}
						_nouvelElementCree = true;
					}else{
						
						for(Object t : objetsEnCours){
							System.out.println("On réajoute "+t);
							this.add(t);
						}
						_nouvelElementCree = (_nouvelElementCree || false);
						objetsEnCours.clear();
					}
					
				}else{
					_nouvelElementCree = (_nouvelElementCree || false);
				}
			}
		}
		System.out.println("Fin de réaction");
	}
}
