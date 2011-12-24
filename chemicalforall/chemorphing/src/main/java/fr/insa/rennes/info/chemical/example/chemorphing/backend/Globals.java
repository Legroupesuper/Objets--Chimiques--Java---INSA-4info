package fr.insa.rennes.info.chemical.example.chemorphing.backend;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Globals {
	
	private Globals(){}	// Overriding/disabling default constructor in order to prevent from abusive accessibility

	private static final String INI_FILE = "settings.ini";
	
	public static Logger logger = Logger.getLogger("fr.insa.rennes.info.chemical");
	
	
	private static Map<String, String> settings = analyseIni(INI_FILE);
	
	public static String getSetting(String setting){
		return settings.get(setting);
	}
	
	private static Map<String, String> analyseIni(String iniFile){
		HashMap<String, String> hm = new HashMap<String, String>();
		try{
			FileInputStream fis= new FileInputStream(iniFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis, System.getProperty("file.encoding")));
			String rec;
			Pattern p = Pattern.compile("(.*?)=(.*)$");
			Matcher m;
			while ((rec = reader.readLine()) != null){
				rec = rec.replaceFirst("\\s*=\\s*", "=");
				m = p.matcher(rec);
				if(m.matches()){
					hm.put(m.group(1), m.group(2));
				}
			}
		} catch(IOException e){
			logger.warning("Unable to read file : "+iniFile);
			System.exit(1);
		}
		return hm;
	}
	
	public static final int WINDOW_WIDTH = Integer.parseInt(getSetting("WINDOW_WIDTH")); 
	public static final int WINDOW_HEIGHT = Integer.parseInt(getSetting("WINDOW_HEIGHT"));
	
}
