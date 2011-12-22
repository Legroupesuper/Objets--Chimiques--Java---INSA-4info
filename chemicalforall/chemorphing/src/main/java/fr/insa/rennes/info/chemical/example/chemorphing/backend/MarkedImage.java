package fr.insa.rennes.info.chemical.example.chemorphing.backend;

import java.awt.Image;
import java.awt.Point;

public class MarkedImage {

	private Image image;
	private Point point1;
	private Point point2;
	private Point point3;
	private String name;
	public MarkedImage(Image image, String name) {
		super();
		this.image = image;
		this.name=name;
	}
	public MarkedImage(Image image, String name, Point point1, Point point2, Point point3) {
		this(image, name);
		this.point1 = point1;
		this.point2 = point2;
		this.point3 = point3;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public Point getPoint1() {
		return point1;
	}
	public void setPoint1(Point point1) {
		this.point1 = point1;
	}
	public Point getPoint2() {
		return point2;
	}
	public void setPoint2(Point point2) {
		this.point2 = point2;
	}
	public Point getPoint3() {
		return point3;
	}
	public void setPoint3(Point point3) {
		this.point3 = point3;
	}
	public void setAddress(String name) {
		this.name = name;
	}
	public String getAddress() {
		return name;
	}
	public String getName() {
		return name.replaceAll(".*/", "").replaceAll("\\..*", "");
	}
	
}
