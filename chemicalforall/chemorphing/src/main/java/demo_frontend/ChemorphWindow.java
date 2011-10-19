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
	
	public ChemorphWindow(){
		super();
		
		this.setLocationRelativeTo(null);
		
		this.setSize(500, 400);
		
		this.setTitle("Chemical Image Morpher");
		
		JPanel container = new JPanel();
		
		this.getContentPane().add(container);
	}

}
