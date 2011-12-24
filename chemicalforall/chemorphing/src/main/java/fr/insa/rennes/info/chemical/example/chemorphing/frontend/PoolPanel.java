package fr.insa.rennes.info.chemical.example.chemorphing.frontend;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import fr.insa.rennes.info.chemical.example.chemorphing.backend.Globals;
import fr.insa.rennes.info.chemical.example.chemorphing.backend.MarkedImage;
import fr.insa.rennes.info.chemical.example.chemorphing.middleend.PoolManager;

public class PoolPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8061512060922263630L;

	private JPanel poolPanel = new JPanel();
	
	public PoolPanel(){
		super();

		Dimension ppDim = new Dimension(Globals.WINDOW_WIDTH *2/5, Globals.WINDOW_HEIGHT *27/64);
		poolPanel.setPreferredSize(ppDim);
		poolPanel.setMaximumSize(ppDim);
		
		poolPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
		JScrollPane jsp = new JScrollPane(poolPanel);

		Dimension jspDim = new Dimension(Globals.WINDOW_WIDTH *2/5 +20, Globals.WINDOW_HEIGHT *27/64);
		jsp.setPreferredSize(jspDim);
		jsp.setMaximumSize(jspDim);
		
		this.add(jsp);
		
		loadPoolDisplay();
	}
	
	private void loadPoolDisplay(){
		for(MarkedImage mi : PoolManager.getPool().getPool()){
			JLabel jl = new JLabel(new ImageIcon(mi.getImage().getScaledInstance(90, 90, Image.SCALE_DEFAULT)));
			jl.setToolTipText(mi.getName());
			poolPanel.add(jl);
		}
	}
	
	public void refreshPanel(){
		poolPanel.removeAll();
		loadPoolDisplay();
	}

}
