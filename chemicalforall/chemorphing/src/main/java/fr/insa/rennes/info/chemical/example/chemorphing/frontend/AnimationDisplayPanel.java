/* 
	Copyright (C) 2012 Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChemicalLibSuper.

    ChemicalLibSuper is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChemicalLibSuper is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ChemicalLibSuper.  If not, see <http://www.gnu.org/licenses/>
*/
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
		this.list = new Image[ai.length];
		System.arraycopy(ai, 0, this.list, 0, ai.length);
		this.index=0;
		this.backgroundImg=this.list[index];
	    Timer t = new Timer();
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