package eg.edu.alexu.csd.oop.game;

import java.util.LinkedList;
import java.util.List;

public class Originator {
	private int state;

	    public void setState(int state) {
	        System.out.println("Originator: Setting state to " + state);
	        this.state = state;
	    }

	    public void save(Circuss w) {
	    	List<GameObject> c = new LinkedList<>();
			 for(int i =0;i<w.getControlableObjects().size();i++)
				 c.add(w.getControlableObjects().get(i));
	    	
	        w.snap(w.time(),c);
	    }
	    public void restore(Snapshot s,Circuss w) {
	      w.setsnap(s);
			
	    }
}
