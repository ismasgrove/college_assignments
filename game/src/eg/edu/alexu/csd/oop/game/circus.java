package eg.edu.alexu.csd.oop.game;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;





public class circus implements World{

	private static int MAX_TIME = 1 * 60 * 1000;	// 1 minute
	private int score = 0;
	private long startTime = System.currentTimeMillis();
	private final int width;
	private final int height;
	private final List<GameObject> constant = new LinkedList<GameObject>();
	private final List<GameObject> moving = new LinkedList<GameObject>();
	private final List<GameObject> control = new LinkedList<GameObject>();
	
	public circus(int screenWidth, int screenHeight) {
		width = screenWidth;
		height = screenHeight;
	/*	// control objects (hero)
		for(int i=1; i<5; i++)*/
			//control.add(new BarObject(screenWidth*i*2/11, screenHeight*7/8, 40, false, Color.BLACK));
		// moving objects (enemy)
	for(int i=0; i<20; i++)
		moving.add(Flyweight.getPlates()[i]);
	
			
		//*/// constants objects (enemy)
	
			
	//	constant.add(Flyweight.getPlates()[i]);
			
		
		}
	//*/
	private boolean intersect(GameObject o1, GameObject o2){
		return (Math.abs((o1.getX()+o1.getWidth()/2) - (o2.getX()+o2.getWidth()/2)) <= o1.getWidth()) && (Math.abs((o1.getY()+o1.getHeight()/2) - (o2.getY()+o2.getHeight()/2)) <= o1.getHeight());
	}
	@Override
	public boolean refresh() {
		boolean timeout = System.currentTimeMillis() - startTime > MAX_TIME; // time end and game over
		//GameObject spaceShip = control.get(0);
		// moving starts
		for(GameObject m : moving){
			m.setY((m.getY() + 1));
			if(m.getY()==getHeight()){
				// reuse the star in another position
				m.setY(-1 * (int)(Math.random() * getHeight()));
				m.setX((int)(Math.random() * getWidth()));	
			}
			m.setX(m.getX() + (Math.random() > 0.5 ? 1 : -1));
			//if(!timeout & intersect(m, spaceShip))
			//	score = Math.max(0, score-10);	// lose score
		}//*/
		// collecting astronauts
		for(GameObject c : constant){
			if(c.isVisible()){
				/*if(intersect(c, spaceShip)){
					score++;	// get score
					((ImageObject)c).setVisible(false);
				}else if(Math.random() > 0.999)
					((ImageObject)c).setVisible(false);	// lost the astronauts
			*/}else{
				((ImageObject)c).setVisible(true);
				// reuse the astronaut in another position
				c.setX((int)(getWidth()*0.9*Math.random()));
				c.setY((int)(getHeight()*0.9*Math.random()));
			}
		}
		return !timeout;
	}
	@Override
	public int getSpeed() {	
		return 60; 
	}
	@Override
	public int getControlSpeed() {	
		return 10;  
	}
	@Override
	public List<GameObject> getConstantObjects() {	
		return constant;	
	}
	@Override
	public List<GameObject> getMovableObjects() {	
		return moving;	
	}
	@Override
	public List<GameObject> getControlableObjects() {	
		return control;	
	}
	@Override
	public int getWidth() {	
		return width; 
	}
	@Override
	public int getHeight() { 
		return height; 
	}
	@Override
	public String getStatus() {
		return null;	// update status
	}

}
