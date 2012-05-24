/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

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
package fr.insa.rennes.info.chemical.example.chemorphing.frontend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;

import fr.insa.rennes.info.chemical.example.chemorphing.backend.Globals;
import fr.insa.rennes.info.chemical.example.chemorphing.middleend.ExportManager;
import fr.insa.rennes.info.chemical.example.chemorphing.middleend.WindowManager;

public class ResultPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3825088398508571358L;

	private JButton fullScreenButton;
	private JButton exportButton;
	private AnimationDisplayPanel adPanel;
	private JWindow fullScreenWindow;

	public ResultPanel(){
		super();

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		Dimension dim = new Dimension(Globals.WINDOW_WIDTH *3/8, Globals.WINDOW_HEIGHT *3/8);
		this.setPreferredSize(dim);
		this.setMaximumSize(dim);


		fullScreenButton = new JButton(Globals.getSetting("FULLSCREEN_BUTTON"));
		fullScreenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResultPanel.this.goFullScreen();
			}
		});

		exportButton = new JButton(Globals.getSetting("EXPORT_BUTTON"));
		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResultPanel.this.export();
			}
		});

	}

	protected void export() {
		JFileChooser chooser = new JFileChooser();
		int returnVal = chooser.showSaveDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File f = chooser.getSelectedFile();
			if(!f.getName().endsWith(".gif")){
				f = new File(f.getAbsolutePath()+".gif");
			}
			ExportManager.export(f);
		}
	}

	protected void goFullScreen() {
		// Determine if full-screen mode is supported directly
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gs = ge.getDefaultScreenDevice();
		if (gs.isFullScreenSupported()) {
			// Full-screen mode is supported
			Globals.logger.info("OK for fullscreen");
		} else {
			// Full-screen mode will be simulated
			Globals.logger.config("Unable to fullscreen your face, emulating instead");
		}

		// Create a button that leaves full-screen mode
		JButton btn = new JButton(Globals.getSetting("BACK_BUTTON"));
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// Return to normal windowed mode
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				GraphicsDevice gs = ge.getDefaultScreenDevice();
				gs.setFullScreenWindow(null);
				WindowManager.wake();
				ResultPanel.this.fullScreenWindow.dispose();
				ResultPanel.this.init();
			}
		});

		// Create a window for full-screen mode; add a button to leave full-screen mode
		JFrame frame = new JFrame(gs.getDefaultConfiguration());
		fullScreenWindow = new JWindow(frame);
		fullScreenWindow.add(adPanel, BorderLayout.CENTER);
		fullScreenWindow.add(btn, BorderLayout.NORTH);

		// Enter full-screen mode
		gs.setFullScreenWindow(fullScreenWindow);
		fullScreenWindow.validate();
		WindowManager.dispose();
	}

	private void init(){
		this.removeAll();

		this.add(fullScreenButton);

		this.add(Box.createRigidArea(new Dimension(0, 5)));

		this.add(adPanel);

		this.add(Box.createRigidArea(new Dimension(0, 5)));

		this.add(exportButton);
	}

	public void addAnimationPanel(Image[] imgTab) {
		Image[] tempTab = new Image[imgTab.length];
		System.arraycopy(imgTab, 0, tempTab, 0, imgTab.length);
		adPanel = new AnimationDisplayPanel(tempTab);
		Dimension dim = new Dimension(Globals.WINDOW_WIDTH *1/4, Globals.WINDOW_HEIGHT *1/4);
		adPanel.setPreferredSize(dim);
		adPanel.setMinimumSize(dim);
		init();
	}

}