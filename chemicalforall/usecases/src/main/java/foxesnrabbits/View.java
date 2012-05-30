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
package foxesnrabbits;

import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import fr.insa.rennes.info.chemical.backend.Solution;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Color;

public class View extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Field field;
	private Solution solution;
	private JTable table;
	int nbUpdates;
	JLabel info;
	JLabel chemProcessing;
	
	public View(Field f, Solution s){
		super();
		nbUpdates = 0;
		solution = s;
		field = f;
		DefaultTableModel model = new DefaultTableModel(field.getNbRows(), field.getNbColumns());	
		DefaultTableCellRenderer renderer = new CustomCellRenderer();
		renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		table = new JTable(model);
		table.setDefaultRenderer(Object.class, renderer);
		table.setRowHeight(25);
		initialize();
		this.setVisible(true);
	}
	
	public void initialize(){
		getContentPane().add(table, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel lblOrangeCells = new JLabel("orange cells      ");
		lblOrangeCells.setBackground(Color.ORANGE);
		lblOrangeCells.setForeground(Color.ORANGE);
		panel.add(lblOrangeCells);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(View.class.getResource("/foxesnrabbits/fox.png")));
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("          Foxes'n Rabbits          ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(View.class.getResource("/foxesnrabbits/rabbit.png")));
		panel.add(lblNewLabel_2);
		
		JLabel lblBlueCells = new JLabel("      blue cells");
		lblBlueCells.setForeground(Color.BLUE);
		panel.add(lblBlueCells);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		chemProcessing = new JLabel("Chemical processing...");
		panel_1.add(chemProcessing);
		
		info = new JLabel("infos");
		panel_1.add(info);
		setPreferredSize(new Dimension(800, 500));
		pack();
	    setLocationRelativeTo(null);
	}

	public void update() {
		synchronized(solution){
			for (int i=0;i<field.getNbRows();i++){
				for (int j=0;j<field.getNbColumns();j++){
					table.getModel().setValueAt("", i, j);
				}
			}
			List<Object> rabbits = solution.getReactivesFromType(Rabbit.class);
			List<Object> foxes = solution.getReactivesFromType(Fox.class);
			int nbrabbits = 0;
			int nbfoxes = 0;
			for (Object rabbit : rabbits){
				Cell loc = ((Rabbit)rabbit).getLocation();
				table.getModel().setValueAt("r", loc.getX(), loc.getY());
				nbrabbits++;
			}
			for (Object fox : foxes){
				Cell loc = ((Fox)fox).getLocation();
				table.getModel().setValueAt("f", loc.getX(), loc.getY());				
				nbfoxes++;
			}
		
			info.setText("      View updates:"+(nbUpdates++)+"          Rabbits count:"+nbrabbits+"           Foxes count:"+nbfoxes);
		}		
	}
	
	public void finalUpdate(){
		chemProcessing.setText("Chemical processing... done !");
	}
	

	
}
