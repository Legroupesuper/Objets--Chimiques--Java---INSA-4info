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
package org.chemicalmozart.view.implementations;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JProgressBar;

public class SecondView extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SecondView dialog = new SecondView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SecondView() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel leftPanel = new JPanel();
		getContentPane().add(leftPanel);
		leftPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel progressBarPanel = new JPanel();
		progressBarPanel.setBorder(new EmptyBorder(40, 10, 40, 10));
		leftPanel.add(progressBarPanel);
		progressBarPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JProgressBar progressBar = new JProgressBar();
		progressBarPanel.add(progressBar);
		
		JPanel btnPlayPanel = new JPanel();
		btnPlayPanel.setBorder(new EmptyBorder(30, 10, 30, 10));
		leftPanel.add(btnPlayPanel);
		btnPlayPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnPlay = new JButton("Play");
		btnPlayPanel.add(btnPlay);
		
		JPanel rightPanel = new JPanel();
		getContentPane().add(rightPanel);
		rightPanel.setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel btnSavePanel = new JPanel();
		btnSavePanel.setBorder(new EmptyBorder(30, 10, 30, 10));
		rightPanel.add(btnSavePanel);
		btnSavePanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnSave = new JButton("Save");
		btnSavePanel.add(btnSave);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(30, 10, 30, 10));
		rightPanel.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnClose = new JButton("Close");
		panel.add(btnClose);
	}

}