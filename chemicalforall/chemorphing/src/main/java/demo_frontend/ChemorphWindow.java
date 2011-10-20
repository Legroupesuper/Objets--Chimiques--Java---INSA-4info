/**
 * 
 */
package demo_frontend;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author ArthurTemple
 *
 */
public class ChemorphWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2868970750863786114L;
	
	private JPanel container;
	
	public ChemorphWindow(JPanel jp){
		super();
		
		this.setLocationRelativeTo(null);
		
		this.setSize(500, 400);
		
		this.setTitle("Chemical Image Morpher");
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.container = jp;
		
		this.getContentPane().add(container);
	}

}
