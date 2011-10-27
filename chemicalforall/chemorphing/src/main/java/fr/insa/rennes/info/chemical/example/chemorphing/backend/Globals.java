package fr.insa.rennes.info.chemical.example.chemorphing.backend;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Globals {
//
//	public static final String POOL_FOLDER = "pool";
//	
//	public static final String POOL_INDEX = POOL_FOLDER + "/index.json";
//	
//	public static final String TITLE = "Chemical Image Morpher";
//	
//	public static final String TREATMENT_TITLE = "Traitement";
//	
//	public static final String RESULT_TITLE = "Résultat du morphing";
//	
//	public static final String POOL_TITLE = "Piscine d'images";
//
//	public static final String ADD_TITLE = "Ajouter une nouvelle image";
//
//	public static final String IMG_NUMBER_PROMPT = "Nombre d'images à traiter";
//	
//	public static final String MORPH_BUTTON = "Morph !";
//	
//	public static final String SELECT_BUTTON = "Sélectionner une image à ajouter";
//	
//	public static final String ADD_BUTTON = "Ajouter à la piscine !";
//	
//	public static final int WINDOW_WIDTH = 800;
//	
//	public static final int WINDOW_HEIGHT = 600;
//
//	public static final String FULLSCREEN_BUTTON = "Visualiser en plein écran";
//
//	public static final String EXPORT_BUTTON = "Exporter au format GIF";
//
//	public static final String BACK_BUTTON = "Retour en mode fenêtré";
//
//	public static final int EXPORT_SECONDS_BETWEEN_FRAMES = 1;
//	
	
	
	
	private static final String INI_FILE = "settings.ini";
	
	private static HashMap<String, String> _settings = analyseIni(INI_FILE);
	
	public static String getSetting(String setting){
		return _settings.get(setting);
	}
	
	private static HashMap<String, String> analyseIni(String iniFile){
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
			System.out.println("Unable to read file : "+iniFile);
			System.exit(1);
		}
		return hm;
	}
	
	public static final int WINDOW_WIDTH = Integer.parseInt(getSetting("WINDOW_WIDTH")); 
	public static final int WINDOW_HEIGHT = Integer.parseInt(getSetting("WINDOW_HEIGHT"));
	
}
