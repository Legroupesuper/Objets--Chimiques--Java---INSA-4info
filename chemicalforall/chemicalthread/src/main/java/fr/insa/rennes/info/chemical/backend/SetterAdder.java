package fr.insa.rennes.info.chemical.backend;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import fr.insa.rennes.info.chemical.user.ReactionRule;

public class SetterAdder {

	private Object rule;	// The modified rule
	private Field[] fieldsToSet;	// The fields which setters will be created
	private ClassPool pool;

	public SetterAdder(ReactionRule rr, Field[] fieldsToSet) {
		super();
		this.rule = rr;
		this.fieldsToSet = fieldsToSet;
		this.pool = ClassPool.getDefault();
	}

	/**
	 * 
	 * @return The modified ReactionRule
	 */
	public Object addSetters(){
		CtClass cc = null;
		try {
			cc = pool.get(rule.getClass().getName());
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		cc.setName(cc.getName()+"autogen");
		cc.setModifiers(Modifier.PUBLIC);
		for(CtField f : cc.getFields()){
			if(f.getModifiers() != Modifier.PUBLIC){
				f.setModifiers(Modifier.PUBLIC);
			}
		}
//		try {
//			cc.addConstructor(CtNewConstructor.make(new CtClass[]{}, new CtClass[]{}, CtNewConstructor.PASS_NONE, null, null, cc));
//		} catch (CannotCompileException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		cc.getClassInitializer().setModifiers(Modifier.PUBLIC);

		for(Field f : fieldsToSet){

			CtMethod meth = null;
			try {
				meth = new CtMethod(CtClass.voidType, "set"+f.getName(), new CtClass[] { pool.get(f.getType().getName()) }, cc);
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				meth.setBody("this."+f.getName()+" = $1;");
				cc.addMethod(meth);
			} catch (CannotCompileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
