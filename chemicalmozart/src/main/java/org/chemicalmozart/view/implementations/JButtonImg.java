package org.chemicalmozart.view.implementations;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class JButtonImg extends JButton{
	Image _img;
	Image _imgn;
	Image _imgp;
	Image _imgs;
	Dimension _size;
	boolean _isMouseOver;
	String _imgPath;
	
	public JButtonImg(String imgPath) {
		_imgn = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/img/"+imgPath+".png"));
		_img = _imgn;
		_imgp = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/img/"+imgPath+"p.png"));
		_imgs = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/img/"+imgPath+"s.png"));
		_isMouseOver = false;
		_imgPath = imgPath;
		_size = new Dimension(_img.getWidth(null), _img.getHeight(null));
	    setPreferredSize(_size);
	    setMinimumSize(_size);
	    setMaximumSize(_size);
	    setSize(_size);
	    setLayout(null);
	    this.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				if(_isMouseOver)
					_img = _imgs;
				else
					_img = _imgn;
			}
			
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				_img = _imgp;
			}
			
			public void mouseExited(MouseEvent e) {
				_isMouseOver = false;
				_img = _imgn;
			}
			
			public void mouseEntered(MouseEvent e) {
				_isMouseOver = true;
				_img = _imgs;
			}
			
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	@Override
	public void paint(Graphics g) {
		Dimension d = getSize();
		g.drawImage(_img, 0, 0, d.width, d.height, null);
	}
}
