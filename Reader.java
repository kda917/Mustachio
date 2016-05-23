package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Reader {


	private ArrayList<Platform> plats;
	private ArrayList<Spikes> spikes;
	private ArrayList<Enemy> enemies;
	private ArrayList<String> plats1;
	private ArrayList<String> spikes1;
	private ArrayList<String> enemies1;
	private ArrayList<String> checkpoints1;
	private ArrayList<Checkpoint> checkpoints;
	private Coordinate2D heroSpawn;
	private String heroSpawn1;
	private String file;

	Reader(String x){
		file = x;
		plats = new ArrayList<Platform>();
		spikes = new ArrayList<Spikes>();
		enemies = new ArrayList<Enemy>();
		plats1 = new ArrayList<String>();
		spikes1 = new ArrayList<String>();
		enemies1 = new ArrayList<String>();
		checkpoints1 = new ArrayList<String>();
		checkpoints = new ArrayList<Checkpoint>();
		heroSpawn1 = "";
		Readfile();
	}

	public ArrayList<String> getCheckpoints1() {
		return checkpoints1;
	}

	public String getHeroSpawn1() {
		return heroSpawn1;
	}

	private void Readfile(){
		BufferedReader br = null;

		try {

			String Line;
			String[] tokens = null;
			br = new BufferedReader(new FileReader(file));

			while ((Line = br.readLine()) != null) {
				tokens = Line.split("[.]+");
				if(Line.charAt(0) == 'p'){
					plats1.add(tokens[1] + "." + tokens[2] + "." + tokens[3]);
					plats.add(new Platform(Integer.parseInt(tokens[1]), 400-Integer.parseInt(tokens[2]), 1, 0, Integer.parseInt(tokens[3])));
				}else if(Line.charAt(0) == 's'){
					spikes1.add(tokens[1] + "." + tokens[2] + "."  + tokens[3] + "." + tokens[4]);
					spikes.add(new Spikes(Integer.parseInt(tokens[1]), 400-Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4])));
				}else if(Line.charAt(0) == 'e'){
					enemies1.add(tokens[1] + "." + tokens[2]);
					enemies.add(new Enemy(Integer.parseInt(tokens[1]), 400-Integer.parseInt(tokens[2])));
				}else if(Line.charAt(0) == 'c') {
					checkpoints1.add(tokens[1] + "." + tokens[2] + "." + tokens[3]);
					checkpoints.add(new Checkpoint(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3])));
				}else if(Line.charAt(0) == 'x') {
					heroSpawn1 = tokens[1] + "." + tokens[2];
					heroSpawn = new Coordinate2D(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public ArrayList<String> getPlats1() {
		return plats1;
	}

	public ArrayList<String> getSpikes1() {
		return spikes1;
	}

	public ArrayList<String> getEnemies1() {
		return enemies1;
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public ArrayList<Platform> getPlats() {
		return plats;
	}

	public ArrayList<Spikes> getSpikes() {
		return spikes;
	}
	
	public ArrayList<Checkpoint> getCheckpoints() {
		return checkpoints;
	}
	
	public Coordinate2D getHeroSpawn() {
		return heroSpawn;
	}
}
