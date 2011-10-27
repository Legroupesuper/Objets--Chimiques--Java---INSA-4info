package fr.insa.rennes.info.chemical.example.chemorphing.frontend;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class AnimationDisplayPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7173332119516627449L;
	
	private Timer t;
	private Image backgroundImg;
	private Image[] list;
	private int index;
	
	
	public Image getBackgroundImg() {
		return backgroundImg;
	}

	public void setBackgroundImg(Image backgroundImg) {
		this.backgroundImg = backgroundImg;
	}

	public AnimationDisplayPanel(Image[] ai) {
		super();
		this.list = ai;
		this.index=0;
		this.backgroundImg=this.list[index];
	    t = new Timer();
	    t.schedule(new Refresher(), 0, 100);
	}
	public Image nextImage(){
		index = (index+1)%this.list.length;
		return this.list[index];
	}

	 private class Refresher extends TimerTask {

	    public void run() {
	      AnimationDisplayPanel.this.setBackgroundImg(AnimationDisplayPanel.this.nextImage());
	      AnimationDisplayPanel.this.repaint();
	    }

	 }
	 
	 public void paintComponent(Graphics g){
		 g.drawImage(this.backgroundImg, 0, 0, this.getWidth(), this.getHeight(), null);
	 }
}
