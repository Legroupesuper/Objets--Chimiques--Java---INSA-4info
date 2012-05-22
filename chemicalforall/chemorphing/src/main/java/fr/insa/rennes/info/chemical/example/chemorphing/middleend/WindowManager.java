/* 
	Copyright (C) 2012 Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChemicalLibSuper.

    ChemicalLibSuper is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChemicalLibSuper is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ChemicalLibSuper.  If not, see <http://www.gnu.org/licenses/>
*/
package fr.insa.rennes.info.chemical.example.chemorphing.middleend;

import java.awt.Image;

import javax.swing.SwingUtilities;

import fr.insa.rennes.info.chemical.example.chemorphing.frontend.ChemorphWindow;

public class WindowManager {

	private WindowManager(){}	// Overriding/disabling default constructor in order to prevent from abusive accessibility

	private static ChemorphWindow window = null;


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
		window.getPoolPanel().refreshPanel();
		window.getTreatmentPanel().refreshPanel();
		window.validate();
	}
	
	public static void addAnimation(Image[] imgTab){
		checkWindow();
		window.getResultPanel().addAnimationPanel(imgTab);
		window.validate();
	}

	public static void dispose() {
		window.dispose();
	}

	public static void wake() {
		window.setVisible(true);
	}

}