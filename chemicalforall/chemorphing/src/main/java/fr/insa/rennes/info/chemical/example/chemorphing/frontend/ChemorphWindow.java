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
	
	private TreatmentPanel tp = new TreatmentPanel();
	private PoolPanel pp = new PoolPanel();
	private ResultPanel rp = new ResultPanel();
	private AddPanel ap = new AddPanel();
	
	public ChemorphWindow(){
		super();
		
		this.setLocation(50, 50);

		this.setSize(Globals.WINDOW_WIDTH, Globals.WINDOW_HEIGHT);
		
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
	public PoolPanel get_poolPanel() {
		return pp;
	}

}
