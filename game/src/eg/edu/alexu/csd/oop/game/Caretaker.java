package eg.edu.alexu.csd.oop.game;

import java.util.ArrayList;

class Caretaker {
	
	
	private static Caretaker caretaker;  
	
	public static synchronized Caretaker getInstance(){   
		if(caretaker == null)  
			caretaker = new Caretaker();  
		return caretaker;  
		}
	
	private ArrayList<Snapshot> snapshots = new ArrayList<>();
	
	  public void addMemento(Snapshot s) {
	        snapshots.add(s);
	    	System.out.println(s.time);
	    }
	

    public Snapshot getMemento() {
        return snapshots.get(snapshots.size());
    }
    public Snapshot getinitialMemento() {
        return snapshots.get(0);
    
}
}
