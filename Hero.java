package main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;

import javax.imageio.ImageIO;

public class Hero implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8354282577150095754L;
	
	private Coordinate2D coord;
	private final UUID guid = UUID.randomUUID();


	private static final int diameter = 30;
	private final int height;
	private final int width;
	
	public static final int STILL = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int UP = 3;
	public static final int DOWN = 4;
	
	private int jumpcount = 0;
	private int direction;
	
	public int index = -1;
	
	private boolean hasShot = false;
	
	public Hero(int xcord, int ycord) {
		if (ycord % 2 == 0) {
			ycord--;
		}
		coord = new Coordinate2D(xcord, ycord);
		height = diameter + 60;
		width = diameter;
		jumpcount = 0;
		direction = STILL;
	}

	public Hero() {
		coord = new Coordinate2D(0, 0);
		height = diameter + 60;
		width = diameter;
		jumpcount = 0;
		direction = STILL;
	}
	
	public Hero(Hero h) {
		this(h.coord.getX(), h.coord.getY());
	}

	public void drawHero(Graphics g, int offset) {
		
		g.drawImage(MustachioGraphics.mustaches[index], coord.getX() + offset, coord.getY(), null);
		
		/**int xOffset = x + offset;
		g.drawOval(xOffset, y, (int) diameter, (int) diameter); // head
		g.drawLine((int) (xOffset + diameter / 2), (int) (y + diameter), (int) (xOffset + diameter / 2),
				(int) (y + diameter + 35)); // torso
		g.drawLine((int) (xOffset + diameter / 2), (int) (y + diameter + 35), (int) (xOffset), (int) (y
				+ diameter + 35 + 25)); // left leg
		g.drawLine((int) (xOffset + diameter / 2), (int) (y + diameter + 35), (int) (xOffset + diameter),
				(int) (y + diameter + 35 + 25)); // right leg
		g.drawLine((int) (xOffset + diameter / 2), (int) (y + diameter + 10), (int) (xOffset + diameter),
				(int) (y + diameter + 10 + 25)); // right arm
		g.drawLine((int) (xOffset + diameter / 2), (int) (y + diameter + 10), (int) (xOffset), (int) (y
				+ diameter + 10 + 25)); // left arm*/
	}

	public void move(int d, int len) {
		if (d == LEFT) {
			coord.setX(coord.getX() - len);
		} else if (d == RIGHT) {
			coord.setX(coord.getX() + len);
		} else if (d == UP) {
			coord.setY(coord.getY() - len);
		} else if (d == DOWN) {
			coord.setY(coord.getY() + len);
		} else {
			System.out.println("Error");
		}
	}

	public int getDirection() {
		return direction;
	}
	
	public void setDirection(int dir) {
		if (dir < 0 || dir > 4) {
			return;
		}
		direction = dir;
	}
	
	public int getX() {
		return coord.getX();
	}

	public int getY() {
		return coord.getY();
	}
	
	public Coordinate2D getLoc() {
		return coord;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getJumpcount() {
		return jumpcount;
	}

	public void setJumpcount(int jc) {
		jumpcount = jc;
	}
	
	public void setY(int y) {
		coord.setY(y);
	}
	
	public void setX(int x) {
		coord.setX(x);
	}
	
	public void setLoc(Coordinate2D c) {
		coord = c;
	}
	
	public UUID getGUID() {
		return guid;
	}
	
	public boolean hasShot() {
		return hasShot;
	}
	
	public void setHasShot(boolean hS) {
		hasShot = hS;
	}
	
	@Override
	public String toString() {
		return "[" + coord.getX() + ", " + coord.getY() + "]";
	}
}
