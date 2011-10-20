package fr.insa.rennes.info.chemical.example.chemorphing.frontend;

import java.awt.Dimension;

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
		
		this.add(new JLabel(Globals.IMG_NUMBER_PROMPT));
		
		//TODO tell where the pool is and get its size, then fill tab with consecutive integers
		Integer[] integers = new Integer[]{2, 3};
		SpinnerListModel integersModel = new SpinnerListModel(integers);
		JSpinner spinner = new JSpinner(integersModel);
		spinner.setMaximumSize(new Dimension(100, 20));
		this.add(spinner);
		
		this.add(new JButton(Globals.MORPH_BUTTON));
	}

}
