package fr.insa.rennes.info.chemical.example.chemorphing.frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import fr.insa.rennes.info.chemical.example.chemorphing.middleend.WindowManager;

public class AddPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2698146579174565773L;

	private JPanel container = new JPanel();
	private AddSubPanel asp = null;

	public AddPanel(){
		super();

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		this.add(Box.createRigidArea(new Dimension(0, 5)));

		JButton browseButton = new JButton(Globals.getSetting("SELECT_BUTTON"));
		browseButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(AddPanel.this);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						asp = new AddSubPanel(ImageIO.read((chooser.getSelectedFile())), chooser.getSelectedFile().getPath());
						container.removeAll();
						container.add(asp);
						AddPanel.this.validate();
					} catch (IOException e1) {
						Globals.logger.warning(e1.getLocalizedMessage());
					}
				}
			}
		});

		this.add(browseButton);

		this.add(Box.createRigidArea(new Dimension(0, 5)));

		this.add(container);

		this.add(Box.createRigidArea(new Dimension(0, 5)));

		JButton addButton = new JButton(Globals.getSetting("ADD_BUTTON"));
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddPanel.this.asp.validateImage();
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
		private int counter;
		private JPanel picPanel, pointPanel;
		private Point[] points;

		public AddSubPanel(Image img, String addr){
			super();
			this.mi = new MarkedImage(img, addr);
			this.counter = 0;
			this.points = new Point[3];
			Dimension usefulDim = new Dimension(Globals.WINDOW_WIDTH *2/5 +10, Globals.WINDOW_HEIGHT /3 +10);
			this.setPreferredSize(usefulDim);
			this.setMaximumSize(usefulDim);
			this.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 0));


			picPanel = new JPanel(){
				private static final long serialVersionUID = 1L;
				public void paintComponent(Graphics g){
					g.drawImage(mi.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
					Point p;
					g.setColor(Color.RED);
					for(int i = 0 ; i < points.length ; i++){
						p = points[i];
						if(p != null){
							g.fillOval(p.x-3, p.y-3, 6, 6);
							g.drawString(String.valueOf(i+1), p.x+1, p.y-2);
						}
					}
				}
			};
			picPanel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					points[counter++] = e.getPoint();
					counter %= 3;
					AddSubPanel.this.repaint();
				}
			});
			usefulDim = new Dimension(Globals.WINDOW_WIDTH *2/5 -45, Globals.WINDOW_HEIGHT /3);
			picPanel.setPreferredSize(usefulDim);
			picPanel.setMaximumSize(usefulDim);


			pointPanel = new JPanel(){
				private static final long serialVersionUID = 1L;
				public void paintComponent(Graphics g){
					Point p;
					String x = "", y = "";
					for(int i = 0, j = 13 ; i<points.length ; i++, j+=26){
						p = points[i];
						if(p != null){
							x = String.valueOf(p.x);
							y = String.valueOf(p.y);
						} else {
							x = "";
							y = "";
						}
						g.drawString("X"+(i+1)+" = "+x, 2, j);
						g.drawString("Y"+(i+1)+" = "+y, 2, j+13);
					}
				}
			};
			usefulDim = new Dimension(50, Globals.WINDOW_HEIGHT /3);
			pointPanel.setPreferredSize(usefulDim);
			pointPanel.setMaximumSize(usefulDim);

			this.add(picPanel);
			this.add(pointPanel);
		}

		public void validateImage(){
			
			if(PoolManager.addToPool(mi, points, picPanel.getSize())){
				//Set the subpanel off the panel
				AddPanel.this.container.removeAll();
				AddPanel.this.container.repaint();
				WindowManager.repaintPool();
			}
		}

	}

}
