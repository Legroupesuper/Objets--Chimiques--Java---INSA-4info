package fr.insa.rennes.info.chemical.example.chemorphing.frontend;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import fr.insa.rennes.info.chemical.example.chemorphing.backend.Globals;
import fr.insa.rennes.info.chemical.example.chemorphing.backend.MarkedImage;
import fr.insa.rennes.info.chemical.example.chemorphing.middleend.PoolManager;

public class AddPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2698146579174565773L;
	
	private Image addImg = null;
	private JPanel container = new JPanel();
	private AddSubPanel asp = null;
	
	public AddPanel(){
		super();
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		this.add(Box.createRigidArea(new Dimension(0, 5)));
		
		JButton browseButton = new JButton(Globals.SELECT_BUTTON);
		browseButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(AddPanel.this);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						asp = new AddSubPanel(ImageIO.read((chooser.getSelectedFile())), chooser.getSelectedFile().getPath());
						container.removeAll();
						container.add(asp);
						container.repaint();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		this.add(browseButton);
		
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		
		this.add(container);
		
		this.add(Box.createRigidArea(new Dimension(0, 5)));
		
		JButton addButton = new JButton(Globals.ADD_BUTTON);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				AddPanel.this.asp.
			}
		});
		this.add(addButton);
		
	}
	
	private class AddSubPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = -1148985532077660127L;
		private MarkedImage mi;
		
		public AddSubPanel(Image img, String addr){
			super();
			this.mi = new MarkedImage(img, addr);
		}
		
		public void validateImage(){
			if(mi.get_point1() != null && mi.get_point2() != null && mi.get_point3() != null){
				PoolManager.getPool().add(mi);
				AddPanel.this.asp = null;
				AddPanel.this.repaint();
			}
		}
	}

}
