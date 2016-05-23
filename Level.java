package main;

import java.io.Serializable;
import java.util.ArrayList;

public class Level implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9182102992955258676L;
	
	private int width;
	private EntityMap map;
	private Coordinate2D heroSpawn;
	private ArrayList<Enemy> enemies;
	//background image
	//enemies?
	
	public Level() {
		map = new EntityMap();
		enemies = new ArrayList<Enemy>();
		width = 0;
	}
	
	public void addEntity(Entity e) {
		map.add(e);
	}
	
	public EntityMap getEntities() {
		return map;
	}
	
	public void addEnemy(Enemy e) {
		enemies.add(e);
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}
	
	public void setEnemies(ArrayList<Enemy> e) {
		enemies = e;
	}
	
	public void setSpawn(Coordinate2D spawn) {
		heroSpawn = spawn;
	}
	
	public Coordinate2D getSpawn() {
		return heroSpawn;
	}
	
	public void setWidth(int gWidth) {
		width = gWidth;
	}
	
	public int getWidth() {
		return width;
	}
}
