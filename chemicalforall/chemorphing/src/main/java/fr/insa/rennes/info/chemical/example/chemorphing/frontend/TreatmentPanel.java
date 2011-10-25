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
import fr.insa.rennes.info.chemical.example.chemorphing.middleend.PoolManager;

public class TreatmentPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5692640131194865714L;
	
	public TreatmentPanel(){
		super();
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		this.add(Box.createRigidArea(new Dimension(0, 50)));
		JLabel jl = new JLabel(Globals.IMG_NUMBER_PROMPT);
		jl.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(jl);
		
		//get pool size, then fill tab with consecutive integers
		int poolSize = PoolManager.getPool().size();
		Integer[] integers = new Integer[poolSize - 1];
		for(int i=2 ; i<=poolSize ; ++i){
			integers[i-2] = i;
		}
		
		SpinnerListModel integersModel = new SpinnerListModel(integers);
		final JSpinner spinner = new JSpinner(integersModel);
		spinner.setPreferredSize(new Dimension(40, 30));
		spinner.setMaximumSize(new Dimension(40, 30));

		this.add(Box.createRigidArea(new Dimension(0, 15)));
		this.add(spinner);

		this.add(Box.createRigidArea(new Dimension(0, 15)));
		JButton jb = new JButton(Globals.MORPH_BUTTON);
		jb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launchMorphing((Integer) spinner.getValue());
			}
		});
		jb.setAlignmentX(Component.CENTER_ALIGNMENT);
		jb.setPreferredSize(new Dimension(120, 30));
		jb.setMaximumSize(new Dimension(120, 30));
		this.add(jb);
	}
	
	public void launchMorphing(int nb){
		// TODO Launch morphing
		System.out.println(nb);
	}

}
