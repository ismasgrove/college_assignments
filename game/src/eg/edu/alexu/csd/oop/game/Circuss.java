package eg.edu.alexu.csd.oop.game;

import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import eg.edu.alexu.csd.oop.game.Clown.Deletefornoobs;
import eg.edu.alexu.csd.oop.game.ImageObject;



public class Circuss  implements World,Deletefornoobs,Strategy{
	
	private static final int MAX_TIME = 1 * 60 * 1000;	// 1 minute
	private int score = 0;
	private long startTime = System.currentTimeMillis();
	public static int width;
	private int height;
	private final List<GameObject> constant = new LinkedList<>();
	private final List<GameObject> moving = new LinkedList<>();
	public static List<GameObject> control = new LinkedList<>();
	private Subject sub = new Subject();
	private EvictingQueue<Object> queue;
	private int limit =11;
	private int speed;
	private int plateslimit;

	
	public Circuss(int screenWidth, int screenHeight,int run) {
		run(run) ;
	
		width = screenWidth;
		height = screenHeight;
		Clown clown = new Clown(screenWidth/3, (int)(screenHeight*0.5), "/clown.png",this);
		control.add(clown);
		for(int i=0; i<plateslimit; i++){ // plates
			
		moving.add( Factory.createplateimage());
	
		
			
		}
		 List<GameObject> c = new LinkedList<>();
		 for(int i =0;i<control.size();i++)
			 c.add(control.get(i));
		 
		
		snap(startTime,c);
	}
	
	public long time(){
		return  startTime;
	}
	
	private boolean imagesAreEqual(BufferedImage image1, BufferedImage image2) {
	    if (image1.getWidth() != image2.getWidth() || image1.getHeight() != image2.getHeight()) {
	         return false;
	    }
	    for (int x = 1; x < image2.getWidth(); x++) {
	        for (int y = 1; y < image2.getHeight(); y++) {
	             if (image1.getRGB(x, y) != image2.getRGB(x, y)) {
	                 return false;
	             }
	        }
	    }
	    return true;
	}
	
	private boolean colorEqual(List<GameObject> plates){
		for (int i=plates.size()-1; i>plates.size()-3; i--){
			if(!imagesAreEqual(plates.get(i).getSpriteImages()[0], plates.get(i-1).getSpriteImages()[0])){
				return false;
			}
    	}
		
		System.out.println("true");
		return true;
	}
	
	private boolean intersect(GameObject o1, GameObject o2){
		return (Math.abs((o1.getX()+o1.getWidth()/2) - (o2.getX()+o2.getWidth()/2)) <= o1.getWidth()) && (Math.abs((o1.getY()+o1.getHeight()/2) - (o2.getY()+o2.getHeight()/2)) == o1.getHeight());
	}
	@Override
	public boolean refresh() {
		queue = new EvictingQueue<>(3);
		boolean timeout = System.currentTimeMillis() - startTime > MAX_TIME; // time end and game over
		
		Subject sub = new Subject();
		PlateObserver obsv = new PlateObserver(sub);
		GameObject clown = control.get(0);
		// moving starts
		for(GameObject m : moving.toArray(new GameObject[moving.size()])){
			m.setY((m.getY() + 1));
			if(m.getY()==getHeight()){
				// reuse the star in another position
				m.setY(-1 * (int)(Math.random() * getHeight()));
				m.setX((int)(Math.random() * getWidth()));	
			}
			m.setX(m.getX() + (Math.random() > 0.5 ? 1 : -1));
			if(!timeout & intersect(m, clown) || (!timeout & intersect(m, control.get(control.size()-1)))){
			
				GameObject clone = new ImageObject((ImageObject) m);
				m.setY(-1 * (int)(Math.random() * getHeight()));
				m.setX((int)(Math.random() * getWidth()));	
				control.add(clone);
				if(control.size() > limit){
					return timeout;
				}
				queue.add(clone);
				for (int i=2; i<control.size(); i++){
					control.get(i).setY(control.get(i-1).getY()-10);
				}
				if (!timeout && control.size()>=4) 	
					if (queue.equalSequence()){
						if (colorEqual(control)){
							sub.setState(control);
							score+=3;
						}
					}
				}
			}
		
		return !timeout;
		
	}
	
	@Override
	public int getSpeed() {	
		return speed; 
	}
	@Override
	public int getControlSpeed() {	
		return 50;  
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
		
		return "Score=" + score + "   |   Time=" + Math.max(0, (MAX_TIME - (System.currentTimeMillis()-startTime))/1000);
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		int index = control.size();
		if(index != 1 ){
		control.remove(index-1);
		score --;
		}
	}
		public void setsnap(Snapshot s){
			
			control = s.control;
		score= 	s.score;
		startTime= 	System.currentTimeMillis()+(System.currentTimeMillis()-startTime)/2;
			
		}
		public void snap(long l, List<GameObject> c) {
			// TODO Auto-generated method stub
		Snapshot snap = new Snapshot( l,score,c);
		Caretaker.getInstance().addMemento(snap);
		System.out.println(snap); 
			}

		@Override
		public void run(int i) {
			// TODO Auto-generated method stub
			if(i == 1){
				speed = 10;
				plateslimit = 20;
			}else if (i == 2){
				speed = 1;
				limit = 3 ;
				plateslimit = 1;
						
			}else if(i == 3){
				speed = 10;
				limit = 5 ;
				plateslimit = 10;
				
			}
		
		
		
		}
}

