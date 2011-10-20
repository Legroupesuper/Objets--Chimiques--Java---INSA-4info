/**
 * 
 */
package fr.insa.rennes.info.chemical.example.chemorphing.frontend;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.insa.rennes.info.chemical.example.chemorphing.backend.Globals;

/**
 * @author ArthurTemple
 *
 */
public class ChemorphWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2868970750863786114L;
	
	protected TreatmentPanel tp = new TreatmentPanel();
	protected PoolPanel pp = new PoolPanel();
	protected ResultPanel rp = new ResultPanel();
	protected AddPanel ap = new AddPanel();
	
	public ChemorphWindow(){
		super();
		
		this.setLocationRelativeTo(null);
		
		this.setSize(500, 400);
		
		this.setTitle(Globals.TITLE);
		
		this.setResizable(false);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(2, 2));
		
		tp.setBorder(BorderFactory.createTitledBorder(Globals.TREATMENT_TITLE));
		
		pp.setBorder(BorderFactory.createTitledBorder(Globals.POOL_TITLE));
		
		rp.setBorder(BorderFactory.createTitledBorder(Globals.RESULT_TITLE));
		
		ap.setBorder(BorderFactory.createTitledBorder(Globals.ADD_TITLE));
		
		// Add panels to the main window : beware ! the order defines how panels are positioned
		container.add(tp);
		container.add(rp);
		container.add(pp);
		container.add(ap);

		this.getContentPane().add(container);
	}

}
