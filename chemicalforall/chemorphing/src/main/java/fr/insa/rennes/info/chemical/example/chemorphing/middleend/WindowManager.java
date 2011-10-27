package fr.insa.rennes.info.chemical.example.chemorphing.middleend;

import java.awt.Image;

import javax.swing.SwingUtilities;

import fr.insa.rennes.info.chemical.example.chemorphing.frontend.ChemorphWindow;

public class WindowManager {

	static private ChemorphWindow window = null;


	public static void initView(){

		window = new ChemorphWindow();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				window.setVisible(true);
			}
		});

	}

	private static void checkWindow(){
		if(window == null){
			initView();
		} 
	}

	public static void repaintPool(){
		checkWindow();
		window.get_poolPanel().refreshPanel();
		window.get_treatmentPanel().refreshPanel();
		window.validate();
	}
	
	public static void addAnimation(Image[] imgTab){
		checkWindow();
		window.get_resultPanel().addAnimationPanel(imgTab);
		window.validate();
	}

	public static void dispose() {
		window.dispose();
	}

	public static void wake() {
		window.setVisible(true);
	}

}
