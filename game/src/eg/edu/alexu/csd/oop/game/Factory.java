package eg.edu.alexu.csd.oop.game;

import java.util.HashMap;

public class Factory {

	private static final String colors[] = {"yellow","red","green","pink","blue"};
	private static final HashMap<String,GameObject> plates= new HashMap<String, GameObject>();

	public static 	 GameObject createplateimage(){
		String color = getRandomColor();
		GameObject plate = null;
		
		
			plate =  new ImageObject(0,0,"/"+color+".png");
		
		plates.put(color, plate);
		plate.setX(getRandomX());
			return plate;
		
	}
	public static int getRandomX(){
		return (int)(Math.random()*Circuss.width);
	}
	
	private static String getRandomColor(){
		return colors[(int)(Math.random()*colors.length)];
	}	
}