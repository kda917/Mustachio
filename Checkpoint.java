package main;

public class Checkpoint {
	private int x;
	private int y;
	
	private int threshold;
	
	public Checkpoint(int x, int y, int threshold) {
		this.x = x;
		this.y = y;
		this.threshold = threshold;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public int getThreshold() {
		return threshold;
	}
}
