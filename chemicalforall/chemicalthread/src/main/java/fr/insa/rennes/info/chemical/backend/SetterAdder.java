package fr.insa.rennes.info.chemical.backend;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import fr.insa.rennes.info.chemical.user.ReactionRule;

public class SetterAdder {

	private Object rule;	// The modified rule
	private Field[] fieldsToSet;	// The fields which setters will be created
	private ClassPool pool;

	public SetterAdder(ReactionRule rr, List<Field> fieldsToSet) {
		super();
		this.rule = rr;
		this.fieldsToSet = new Field[fieldsToSet.size()];
		for(int i = 0 ; i < fieldsToSet.size() ; i++){
			this.fieldsToSet[i] = fieldsToSet.get(i);
		}
		this.pool = ClassPool.getDefault();
	}

	/**
	 * 
	 * @return The modified ReactionRule
	 */
	public ReactionRule addSetters(){
		CtClass cc = null;
		try {
			cc = pool.get(rule.getClass().getName());
		} catch (NotFoundException e) {
			e.printStackTrace();
		}

		for(Field f : fieldsToSet){
			CtClass classType = null;
			try {
				classType = pool.get(f.getType().getName());
				CtField newField = new CtField(classType, f.getName(), cc);
				CtMethod setter = CtNewMethod.setter("set"+f.getName(), newField);
				cc.addMethod(setter);
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CannotCompileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		cc.setName(cc.getName()+"autogen");
		Class<? extends ReactionRule> clazz;
		try {
			clazz = cc.toClass();
			return clazz.newInstance();
		} catch (CannotCompileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param mod The mod of a Class
	 * @return A String defining the mod
	 */
	private String identifier(int mod){
		return "abstract : "+Modifier.isAbstract(mod)+
		"\nfinal : "+Modifier.isFinal(mod)+
		"\ninterface : "+Modifier.isInterface(mod)+
		"\nnative : "+Modifier.isNative(mod)+
		"\nprivate : "+Modifier.isPrivate(mod)+
		"\nprotected : "+Modifier.isProtected(mod)+
		"\npublic : "+Modifier.isPublic(mod)+
		"\nstatic : "+Modifier.isStatic(mod)+
		"\nstrict : "+Modifier.isStrict(mod)+
		"\nsynchronized : "+Modifier.isSynchronized(mod)+
		"\ntransient : "+Modifier.isTransient(mod)+
		"\nvolatile : "+Modifier.isVolatile(mod);
	}

}
