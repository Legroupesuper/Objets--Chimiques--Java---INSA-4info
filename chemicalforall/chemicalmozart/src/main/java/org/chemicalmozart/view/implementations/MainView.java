/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of chemicalmozart.

    chemicalmozart is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    chemicalmozart is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with chemicalmozart.  If not, see <http://www.gnu.org/licenses/>
*/

package org.chemicalmozart.view.implementations;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MainView extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTextField speed;
	private JTextField scale;
	private JTextField number;


	/**
	 * Create the dialog.
	 */
	public MainView() {
		setContentPane(new JPanel(){
			private static final long serialVersionUID = 1L;
			Image bck = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/img/bck.png"));
			Image img1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/img/becher.png"));
			Image img2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/img/becher2.png"));
			@Override
			public void paint(Graphics g) {
				// TODO Auto-generated method stub
				//super.paint(g);
				

				g.drawImage(bck, 0, 0, getWidth(), getHeight(), this);
				g.drawImage(img2, getWidth()/2 + 10, 10, (getWidth()/2)-20, getHeight()-20, this);
				g.drawImage(img1, 10, 10, (getWidth()/2)-20, getHeight()-20, this);
				paintChildren(g);
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainView.class.getResource("/javax/swing/plaf/metal/icons/ocean/info.png")));
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel leftPanel = new JPanel();
		leftPanel.setOpaque(false);
		leftPanel.setBackground(Color.WHITE);
		getContentPane().add(leftPanel);
		leftPanel.setLayout(new GridLayout(3, 0, 0, 0));
		
		JPanel mesurePanel = new JPanel();
		mesurePanel.setOpaque(false);
		mesurePanel.setBorder(new EmptyBorder(30, 10, 30, 10));
		leftPanel.add(mesurePanel);
		mesurePanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Measure speed");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Ubuntu Light", Font.BOLD, 11));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mesurePanel.add(lblNewLabel);
		
		speed = new JTextField();
		mesurePanel.add(speed);
		speed.setColumns(10);
		
		JPanel scalePanel = new JPanel();
		scalePanel.setOpaque(false);
		scalePanel.setBorder(new EmptyBorder(30, 10, 30, 10));
		leftPanel.add(scalePanel);
		scalePanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblScale = new JLabel("Scale");
		lblScale.setFont(new Font("Ubuntu Light", Font.BOLD, 11));
		lblScale.setForeground(Color.RED);
		lblScale.setHorizontalAlignment(SwingConstants.CENTER);
		scalePanel.add(lblScale);
		
		scale = new JTextField();
		scalePanel.add(scale);
		scale.setColumns(10);
		
		JPanel measureNumber = new JPanel();
		measureNumber.setOpaque(false);
		measureNumber.setBorder(new EmptyBorder(30, 10, 30, 10));
		leftPanel.add(measureNumber);
		measureNumber.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblMeasureNum = new JLabel("Measure number");
		lblMeasureNum.setFont(new Font("Ubuntu Light", Font.BOLD, 11));
		lblMeasureNum.setForeground(Color.RED);
		lblMeasureNum.setHorizontalAlignment(SwingConstants.CENTER);
		measureNumber.add(lblMeasureNum);
		
		number = new JTextField();
		measureNumber.add(number);
		number.setColumns(10);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setOpaque(false);
		rightPanel.setBackground(Color.WHITE);
		getContentPane().add(rightPanel);
		rightPanel.setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel runPanel = new JPanel();
		runPanel.setOpaque(false);
		runPanel.setBorder(new EmptyBorder(35, 10, 35, 10));
		rightPanel.add(runPanel);
		runPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnRun = new JButtonImg("boutongenerer");
		runPanel.add(btnRun);
		
		JPanel closePanel = new JPanel();
		closePanel.setOpaque(false);
		closePanel.setBorder(new EmptyBorder(35, 10, 35, 10));
		rightPanel.add(closePanel);
		closePanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnClose = new JButtonImg("boutonquitter");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		closePanel.add(btnClose);
	}
	
	public static void main(String[] args){
		try {
			MainView dialog = new MainView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}