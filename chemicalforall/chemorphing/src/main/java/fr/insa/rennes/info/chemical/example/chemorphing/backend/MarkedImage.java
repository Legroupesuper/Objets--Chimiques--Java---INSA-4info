package fr.insa.rennes.info.chemical.example.chemorphing.backend;

import java.awt.Image;
import java.awt.Point;

public class MarkedImage {

	private Image _image;
	private Point _point1;
	private Point _point2;
	private Point _point3;
	private String _name;
	public MarkedImage(Image _image, String name, Point _point1, Point _point2, Point _point3) {
		super();
		this._image = _image;
		this._point1 = _point1;
		this._point2 = _point2;
		this._point3 = _point3;
		this.set_name(name);
	}
	public Image get_image() {
		return _image;
	}
	public void set_image(Image _image) {
		this._image = _image;
	}
	public Point get_point1() {
		return _point1;
	}
	public void set_point1(Point _point1) {
		this._point1 = _point1;
	}
	public Point get_point2() {
		return _point2;
	}
	public void set_point2(Point _point2) {
		this._point2 = _point2;
	}
	public Point get_point3() {
		return _point3;
	}
	public void set_point3(Point _point3) {
		this._point3 = _point3;
	}
	public void set_name(String _name) {
		this._name = _name;
	}
	public String get_address() {
		return _name;
	}
	public String get_name() {
		return _name.replaceAll(".*/", "").replaceAll("\\..*", "");
	}
	
}
