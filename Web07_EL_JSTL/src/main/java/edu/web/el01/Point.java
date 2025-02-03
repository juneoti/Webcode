package edu.web.el01;

public class Point {
	private double x;
	private double y;
	
	public Point() {
		System.out.println("Point()");
	}

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}
	
	public double distance(double x, double y) {
		double disX = this.x - x;
		double disY = this.y - y;
		return Math.sqrt(disX * disX + disY * disY);
	}
	
}
