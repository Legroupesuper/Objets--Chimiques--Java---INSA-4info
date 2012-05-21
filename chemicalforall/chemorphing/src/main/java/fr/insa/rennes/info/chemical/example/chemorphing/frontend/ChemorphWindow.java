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
/**
 * 
 */
package fr.insa.rennes.info.chemical.example.chemorphing.frontend;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.insa.rennes.info.chemical.example.chemorphing.backend.Globals;
import fr.insa.rennes.info.chemical.example.chemorphing.middleend.PoolManager;

/**
 * @author ArthurTemple
 *
 */
public class ChemorphWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2868970750863786114L;
	
	private TreatmentPanel tp = new TreatmentPanel();
	private PoolPanel pp = new PoolPanel();
	private ResultPanel rp = new ResultPanel();
	private AddPanel ap = new AddPanel();
	
	public ChemorphWindow(){
		super();
		
		this.setLocation(50, 50);

		this.setSize(Globals.WINDOW_WIDTH, Globals.WINDOW_HEIGHT);
		
		this.setTitle(Globals.getSetting("TITLE"));

		this.setResizable(false);
		
		this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
            	if(PoolManager.savePool()){
            		System.exit(0);
            	} else {
            		System.exit(2);
            	}
            }
        });
		
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(2, 2));
		
		tp.setBorder(BorderFactory.createTitledBorder(Globals.getSetting("TREATMENT_TITLE")));
		
		pp.setBorder(BorderFactory.createTitledBorder(Globals.getSetting("POOL_TITLE")));
		
		rp.setBorder(BorderFactory.createTitledBorder(Globals.getSetting("RESULT_TITLE")));
		
		ap.setBorder(BorderFactory.createTitledBorder(Globals.getSetting("ADD_TITLE")));
		
		// Add panels to the main window : beware ! the order defines how panels are positioned
		container.add(tp);
		container.add(rp);
		container.add(pp);
		container.add(ap);

		this.getContentPane().add(container);
	}
	public PoolPanel getPoolPanel() {
		return pp;
	}
	public ResultPanel getResultPanel() {
		return rp;
	}
	public TreatmentPanel getTreatmentPanel() {
		return tp;
	}
	public AddPanel getAddPanel() {
		return ap;
	}

}