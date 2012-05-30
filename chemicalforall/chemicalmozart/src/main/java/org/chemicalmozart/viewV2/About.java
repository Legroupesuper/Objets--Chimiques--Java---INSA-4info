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
package org.chemicalmozart.viewV2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class About extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton okButton;
	private JLabel lblLogo;
	private JLabel lblTitle;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			About dialog = new About();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public About() {
		setBounds(100, 100, 450, 335);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			lblLogo = new JLabel("");
			lblLogo.setIcon(new ImageIcon(About.class.getResource("/img/chemistry.png")));
		}
		{
			lblTitle = new JLabel("Chemical Music Generator - About");
			lblTitle.setForeground(SystemColor.controlDkShadow);
			lblTitle.setFont(new Font("Verdana", Font.PLAIN, 16));
		}
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(19)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 386, Short.MAX_VALUE)
					.addGap(19))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(29)
					.addComponent(lblLogo)
					.addGap(18)
					.addComponent(lblTitle)
					.addContainerGap(61, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblLogo))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(26)
							.addComponent(lblTitle)))
					.addGap(18)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 166, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JTextArea txtpnThisProgramGenerates = new JTextArea();
		txtpnThisProgramGenerates.setBackground(Color.WHITE);
		txtpnThisProgramGenerates.setWrapStyleWord(true);
		txtpnThisProgramGenerates.setFont(new Font("Tahoma", Font.PLAIN, 10));
		txtpnThisProgramGenerates.setLineWrap(true);
		txtpnThisProgramGenerates.setEditable(false);
		txtpnThisProgramGenerates.setText("This program generates random midi music, corresponding to a few parameters you should specify: tempo, number of bars, and scale. The .midi file is then created accordingly to the \"Output midi file\" field.\r\nThe randomness of the generated song is due to a specific chemical programming Java library we created. Therefore the entire process is a demonstration of this library's capabilities.\r\n\r\nCreated by six french students from 4th year in Computer Science Department, INSA de Rennes: Cédric Andreolli, Chloé Boulanger, Olivier Cléro, Antoine Guellier, Sébastien Guilloux & Arthur Templé. Under the direction of Jean-Louis Pazat.\r\n\r\n© INSA Rennes 2012 - LGPL license.");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addComponent(txtpnThisProgramGenerates, GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(txtpnThisProgramGenerates, GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
		);
		panel.setLayout(gl_panel);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				getRootPane().setDefaultButton(okButton);
			}
			GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
			gl_buttonPane.setHorizontalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addGap(169)
						.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(170, Short.MAX_VALUE))
			);
			gl_buttonPane.setVerticalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addComponent(okButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
			);
			buttonPane.setLayout(gl_buttonPane);
		}
	}
}