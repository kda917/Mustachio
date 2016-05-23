package main;

import java.awt.Graphics;
import java.io.Serializable;

public abstract class Entity implements Serializable { //anything in Game that isn't the hero. Must have location
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -925271611738206739L;
	
	public abstract int getX();
	public abstract int getY();
	public abstract void setX(int x);
	public abstract void setY(int y);
	public abstract void setOffset(int o);
	public abstract void draw(Graphics g);
	
}
