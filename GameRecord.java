package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;




class Node{
	int x;
	int y;
	
	public Node(int x, int y){
		this.x = x;
		this.y = y;
	}
}

public class GameRecord {
	
//	public static ArrayList<Hero> hero = new ArrayList<Hero>();
	
//	private static String myPosition;
	
	static Hero hero = new Hero();
	
	private static int myX = hero.getX();
	private static int myY = hero.getY();
	
	public static ArrayList<Node> nodes = new ArrayList<Node>();
	
	//function that recording the game
	public static void writeRecord(){
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		//if we hit the check point
		try{
			fw = new FileWriter("GameState/gamestate.txt");
			bw = new BufferedWriter(fw);
		
			String recorder = getMyX() + " " + getMyY();
			bw.write(recorder + "\r\n");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				bw.close();
				fw.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	//function that loading the game
	public static void readerRecord(){
		FileReader fr = null;
		BufferedReader br = null;
		try{
			fr = new FileReader("GameState/gamestate.txt");
			br = new BufferedReader(fr);
			
			String n = "";

			//get the place of the player
			while((n = br.readLine())!=null){
				//seperate it with SPACE
				String []xyd = n.split(" ");
				Node node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]));
			
				setMyX(Integer.parseInt(xyd[0]));
				setMyY(Integer.parseInt(xyd[1]));
				
				nodes.add(node);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				br.close();
				fr.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	
	public static int getMyX(){
		return myX;
	}
	
	public static int getMyY(){
		return myY;
	}
	
	public static void setMyX(int myX){
		GameRecord.myX = myX;
	}
	
	public static void setMyY(int myY){
		GameRecord.myY = myY;
	}
	
	public static ArrayList<Node> getNodes(){
		return nodes;
	}
	
	public static void setNodes(ArrayList<Node> nodes){
		GameRecord.nodes = nodes;
	}
	

}
