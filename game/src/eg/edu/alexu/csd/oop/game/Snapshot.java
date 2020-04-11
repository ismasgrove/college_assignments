package eg.edu.alexu.csd.oop.game;

import java.util.List;

public class Snapshot {
	long time;
	int score ;
	List<GameObject> control;

	    public Snapshot(long time,int score ,List<GameObject> control ) {
	     this.time = time;
	     this.score = score;
	     this.control = control;
	    }


}
