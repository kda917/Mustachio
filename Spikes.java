package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;

public class Spikes extends Entity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4275488541482993286L;
	
	private int xstart;
	private int ystart;
	private int width;
	private int height;
	private int offset;
	
	public int getX() {
		return xstart;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setX(int x) {
		xstart = x;
	}

	public int getY() {
		return ystart;
	}

	public Spikes(int x, int y, int w, int h){
		xstart = x;
		ystart = y;
		height = h;
		width = w;
	}
	
	public void draw(Graphics g){

		
		Color c = g.getColor();
		g.setColor(Color.RED);
		
		if(height > 10){
			g.fillRect(xstart + offset, ystart + 10, width, height - 10);
			g.setColor(c);
		}
		
		if(width >= 10){
			for(int i = 0; i < width;){
				g.drawImage(MustachioGraphics.spikeUp, xstart + offset + i, ystart, null);
				i = i + 10;
			}
		}
		
		if(height >= 10){
			if(width >= 10){
				for(int j = 0; j < width;){
					g.drawImage(MustachioGraphics.spikeDown, xstart + offset + j, ystart + height, null);
					j = j + 10;
				}
				for(int k = 0; k < height - 10;){
					g.drawImage(MustachioGraphics.spikeLeft, xstart + offset - 5, ystart + 10 + k, null);
					k = k + 10;
				}
			
				for(int h = 0; h < height - 10;){
					g.drawImage(MustachioGraphics.spikeRight, xstart + offset + width - 5, ystart + 10 + h, null);
					h = h + 10;
				}
			}
		}
	}

	@Override
	public void setY(int y) {
		ystart = y;
		
	}

	@Override
	public void setOffset(int o) {
		this.offset = o;
	}
}
