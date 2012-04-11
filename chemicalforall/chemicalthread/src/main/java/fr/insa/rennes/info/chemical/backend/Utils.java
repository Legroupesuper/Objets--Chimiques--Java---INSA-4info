package fr.insa.rennes.info.chemical.backend;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import fr.insa.rennes.info.chemical.user.ReactionRule;

/**
 * Utility class,  used in all the project. This class contains functions that 
 * do not fit in any other class.
 * @author Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 */
class Utils {
	
/*
 * Logger (remplace avantageusement les classiques
 * System.out.println...)
 * 
 * Niveau	| Description
 * 
 * (ALL		| Tous les niveaux)
 * SEVERE	| Niveau le plus élevé
 * WARNING	| Avertissement
 * INFO		| Information
 * CONFIG	| Configuration
 * FINE		| Niveau faible
 * FINER	| Niveau encore plus faible
 * FINEST	| Niveau le plus faible
 * (OFF		| Aucun niveau)
 * 
 * Chaque niveau est associé à une méthode :
 * void severe(String msg)
 * void warning(String msg)
 * void info(String msg)
 * void config(String msg)
 * void fine(String msg)
 * void finer(String msg)
 * void finest(String msg)
 * 
 * ou bien plus génériquement par l'appel :
 * void log(Level level, String msg)
 * 
 * De plus la méthode :
 * void setLevel(Level newLevel)
 * permet de définir le niveau critique à partir duquel
 * on commence à transférer au fichier de log les messages
 * reçus par le journal (filtrage simple)
 * 
 * 	----
 * 
 * Le commentaire au-dessus pourra librement disparaître une
 * fois que tout un chacun sera au courant du mode de
 * fonctionnement du logger
 */
	// Scope is set to "default" on purpose : only this library should use this log
	static Logger logger = Logger.getLogger("fr.insa.rennes.info.chemical");
	
	/**
	 * This method is used to get a Method associated to a field and a ReactionRule.
	 * This method is prefixed by the string s
	 * @param r The ReactionRule
	 * @param s The prefixed string
	 * @param f The field
	 * @return The method which correspond to the previous parameters
	 */
	protected static Method getMethodFromReactionRule(ReactionRule r, String s, Field f){
		Method m = null;
		for(Method get : r.getClass().getDeclaredMethods()){
			if(get.getName().toLowerCase().contains(s+f.getName().toLowerCase())){
				m = get;
				break;
			}
		}
		return m;
	}
	
}
