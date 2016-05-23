package main;

import java.io.Serializable;
import java.util.ArrayList;

public class GameState implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9193558050552711616L;
	
	public Hero[] hero;
	
	public int index;
	
	public int read;
	
	public int prevRead;
	
	//public ArrayList<Bullet> newBullets;
	
	public boolean scrolling;
	
	public int shootCount;
	
	public GameState() {
		hero = new Hero[2];
		//newBullets = new ArrayList<Bullet>();
		read = 0;
		prevRead = 0;
	}
}
