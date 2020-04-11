 package eg.edu.alexu.csd.oop.game;

import java.awt.BorderLayout;
import java.awt.List;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Flyweight {


	
	private static final String colors[] = {"yellow","red","green","pink","blue"};
	
	public static GameObject [] getPlates(){
		LinkedList<GameObject> plates =new LinkedList<GameObject>();
		GameObject []platez = new GameObject[20];
		for(int i =0 ; i< 20; i++){
			
			platez[i] = Factory.createplateimage();
			platez[i].setX(getRandomX());
			
		}
		return platez;
		
		
	} 
	
	private static String getRandomColor(){
		return colors[(int)(Math.random()*colors.length)];
	}
	
	
	private static int getRandomX(){
		return (int)(Math.random()*Circuss.width);
	}
	
}
