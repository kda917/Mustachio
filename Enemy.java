package main;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import java.io.Serializable;

import javax.imageio.ImageIO;

public class Enemy implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2384535512909054648L;
	
	private int enemy_x;
	private int enemy_y;
	private double speed = 0.5;
	private boolean isLive = true;
	private int width = 30;
	private int height = 30;
	private int offset;
	
	public Enemy(int enemy_x, int enemy_y){
		this.enemy_x = enemy_x;
		this.enemy_y = enemy_y;
	}
	
	public void setXstart(int x) {
		enemy_x = x;
	}
	
	public int getX(){
		return this.enemy_x;
	}
	
	public int getY(){
		return this.enemy_y;
	}
	
	public void setOffset(int o) {
		this.offset = o;
	}
	
	public void drawEnemy(Graphics g) {

		g.drawImage(MustachioGraphics.baseEnemy, enemy_x + offset, enemy_y, null);

	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void move(){
		this.enemy_x -= speed;
	}
	
	public void spawnEnermy(){
		
	}
	
	public boolean isLive(){
		return isLive;
	}


	
}
