package fr.insa.rennes.info.chemical.backend;

import java.util.logging.Logger;

public class Utils {
	
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
	
}
