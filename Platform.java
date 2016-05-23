package main;

import java.awt.Graphics;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Platform extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2023021306134402233L;
	
	private int length;
	private int xstart;
	private int ystart;
	private int xinc;
	private int yinc;
	
	private int offset;

	public int getLength() {
		return length;
	}

	public int getXstart() {
		return xstart;
	}
	
	public void setXstart(int x) {
		xstart = x;
	}

	public int getYstart() {
		return ystart;
	}

	public int getXinc() {
		return xinc;
	}

	public int getYinc() {
		return yinc;
	}
	
	public int getX() {
		return xstart;
	}
	
	public int getY() {
		return ystart;
	}
	
	public void draw(Graphics g) {
		/**g.drawLine(xstart + offset, 
				ystart, 
				xstart + offset + (xinc * length), 
				ystart + (yinc * length));
				*/
		for(int i = 0; i < length;){
			g.drawImage(MustachioGraphics.platform, xstart + offset + i, ystart, null);
			i = i + 20;
		}
		
	}

	public Platform(int x, int y, int xi, int yi, int iter){
		length = iter;
		xstart = x;
		ystart = y;
		xinc = xi;
		yinc = yi;
		offset = 0;
	}

	@Override
	public void setX(int x) {
		xstart = x;
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
