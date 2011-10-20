package fr.insa.rennes.info.chemical.example.chemorphing.frontend;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;

import fr.insa.rennes.info.chemical.example.chemorphing.backend.Globals;

public class TreatmentPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5692640131194865714L;
	
	public TreatmentPanel(){
		super();
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		this.add(Box.createRigidArea(new Dimension(0, 15)));
		JLabel jl = new JLabel(Globals.IMG_NUMBER_PROMPT);
		jl.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(jl);
		
		//TODO tell where the pool is and get its size, then fill tab with consecutive integers
		Integer[] integers = new Integer[]{2, 3};
		SpinnerListModel integersModel = new SpinnerListModel(integers);
		JSpinner spinner = new JSpinner(integersModel);
		spinner.setPreferredSize(new Dimension(40, 30));
		spinner.setMaximumSize(new Dimension(40, 30));

		this.add(Box.createRigidArea(new Dimension(0, 15)));
		this.add(spinner);

		this.add(Box.createRigidArea(new Dimension(0, 15)));
		JButton jb = new JButton(Globals.MORPH_BUTTON);
		jb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Launch morphing
			}
		});
		jb.setAlignmentX(Component.CENTER_ALIGNMENT);
		jb.setPreferredSize(new Dimension(120, 30));
		jb.setMaximumSize(new Dimension(120, 30));
		this.add(jb);
	}

}
