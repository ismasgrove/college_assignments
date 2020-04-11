package eg.edu.alexu.csd.oop.game;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PlateObserver extends Observer{

	public PlateObserver(Subject subject){
		this.subject=subject;
		this.subject.attach(this);
	}
	
	public void update(){
		int size = Circuss.control.size();
		System.out.println("what");
		for (int i=size-1; i>=size-3; i--){
			Circuss.control.remove(i);
		}
	}
}