/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChLoe.

    ChLoe is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChLoe is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ChLoe.  If not, see <http://www.gnu.org/licenses/>
*/
package fr.insa.rennes.info.chemical.backend;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.insa.rennes.info.chemical.user.ReactionRule;

/**
 * Utility class,  used in all the project. This class contains functions that 
 * do not fit in any other class.
 * @author Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 */
public class Utils {
	
/*
 * Logger (replacing classic
 * System.out.println...)
 * 
 * Level	| Description
 * 
 * (ALL		| All levels)
 * SEVERE	| Highest level
 * WARNING	| Warning
 * INFO		| Information
 * CONFIG	| Configuration
 * FINE		| Low level
 * FINER	| Lower level
 * FINEST	| Lowest level
 * (OFF		| No level specified)
 * 
 * Each level may be accessed via a method :
 * void severe(String msg)
 * void warning(String msg)
 * void info(String msg)
 * void config(String msg)
 * void fine(String msg)
 * void finer(String msg)
 * void finest(String msg)
 * 
 * or by calling :
 * void log(Level level, String msg)
 * 
 * What's more, the method :
 * void setLevel(Level newLevel)
 * let you define the level from which
 * the messages are transmitted to log file
 * (simple filter)
 * 
 */
	// Scope is set to "default" on purpose : only this library should use this log
	/**
	 * The library logger
	 */
	public static Logger logger = Logger.getLogger("fr.insa.rennes.info.chemical");
	
	static {
		logger.setLevel(Level.OFF);
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
		logger.addHandler(new FileHandler(fileName, 50000, 1, false));
	}
	
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

	public static String getSeparator() {
		if(System.getProperty("os.name").toLowerCase().contains("windows")) return "\\";
		return "/";
	}
	
}