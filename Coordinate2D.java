package main;

public class Coordinate2D {
	
	private int x;
	private int y;
	
	public Coordinate2D(int xcoor, int ycoor){
		x = xcoor;
		y = ycoor;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setX(int xcoor){
		x = xcoor;
	}
	
	public void setY(int ycoor){
		y = ycoor;
	}

}