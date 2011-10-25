package fr.insa.rennes.info.chemical.example.chemorphing.middleend;

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
	
	public static void repaintPool(){
		if(window == null){
			initView();
		} else {
			window.get_poolPanel().refreshPanel();
			window.validate();
		}
	}

}
