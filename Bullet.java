package main;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.UUID;

/**
 * 
 * The slashed stuffs just for future game building, such as bullets power and direction
 *
 */
public class Bullet implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9061235962302493052L;
	
	private final UUID guid;
	
	private int id;
	private Hero hero_id;
	private int style; //bullet style, 0 means enemy's bullet, 1 means yours
//	private int direction; // 1,2,3,4 means up, down, left, right
	private int speed = 5; // bullet speed
//	private int power; // bullet power;
	private int bullet_x;
	private int bullet_y;
	
	private static final int WIDTH = 5;
	private static final int HEIGHT = 5;
//	private boolean live = true;	// bullet exist or not

	
	public Bullet(Hero hero_id, int bullet_x, int bullet_y){ //constructor of client
		this.hero_id = hero_id;
		this.bullet_x = bullet_x;
		this.bullet_y = bullet_y;
		this.guid = UUID.randomUUID();
	}
	
//	public Bullet(int id, Hero hero_id, int style, int direction, int speed, int power, int bullet_x, int bullet_y){ // constructor of host
//		
//		this.id = id;
//		this.hero_id = hero_id;
//		this.style = style;
//		this.direction = direction;
//		this.speed = speed;
//		this.power = power;
//		this.bullet_x = bullet_x;
//		this.bullet_y = bullet_y;
//	}
	
	public void move(){
		
//		if(direction == 1){
//			this.bullet_y -= speed;
//		}else if(direction == 2){
//			this.bullet_y += speed;
//		}else if(direction == 3){
//			this.bullet_x -= speed;
//		}else if(direction == 4){
//			this.bullet_x += speed;
//		}
		this.bullet_x += speed;

	}
	
	
	public int getId(){
		return this.id;
	}
	
	public Hero getHeroId(){
		return this.hero_id;
	}
	
	public int getStyle(){
		return this.style;
	}
	
//	public int getDirection(){
//		return this.direction;
//	}
//	
//	public int getPower(){
//		return this.power;
//	}
	
	public int getX(){
		return this.bullet_x;
	}
	
	public int getY(){
		return this.bullet_y;
	}
	
	public int getWidth() {
		return WIDTH;
	}
	
	public int getHeight() {
		return HEIGHT;
	}
	
	public void setX(int x){
		this.bullet_x = x;
	}
	
	public void setY(int y){
		this.bullet_y = y;
	}
	
	
	public void drawBullet(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(bullet_x, bullet_y, WIDTH, HEIGHT);
		g.setColor(c);
		
		//move();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Bullet) {
			Bullet b = (Bullet) o;
			return guid.equals(b.guid);
		}
		return false;
	}
	
//	public Rectangle getRec(){
//		return new Rectangle(bullet_x, bullet_y, 5, 5);
//	}
	
}
