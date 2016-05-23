package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Writer {

	String filepath;
	ArrayList<String> enemies;
	ArrayList<String> plats;
	ArrayList<String> spikes;
	ArrayList<String> checkpoints;
	String spawn;
	
	Writer(String x){
		filepath = x;
		enemies = new ArrayList<String>();
		plats = new ArrayList<String>();
		spikes = new ArrayList<String>();
		checkpoints = new ArrayList<String>();
		spawn = "";
	}
	
	public void setEnemies(ArrayList<String> enemies) {
		this.enemies = enemies;
	}

	public void setPlats(ArrayList<String> plats) {
		this.plats = plats;
	}

	public void setSpikes(ArrayList<String> spikes) {
		this.spikes = spikes;
	}

	public void Write(){
		try {

			File file = new File(filepath);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), false));
			
			
			for (String code : plats){
				bw.write(code + "\n");
			}
			
			for (String code : spikes){
				bw.write(code + "\n");
			}
			
			for (String code : enemies){
				bw.write(code + "\n");
			}
			
			for (String code : checkpoints){
				bw.write(code + "\n");
			}
			
			bw.write(spawn);
			
			
			
			
			
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setCheckpoints(ArrayList<String> checkpoints) {
		this.checkpoints = checkpoints;
	}

	public void setSpawn(String spawn) {
		this.spawn = spawn;
	}
}