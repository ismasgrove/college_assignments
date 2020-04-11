package eg.edu.alexu.csd.oop.game;

import java.awt.image.BufferedImage;


public class Clown extends ImageObject {
	
	

	public static interface Deletefornoobs{
		void delete();
	}
	
	private Deletefornoobs deletefornoobs;	
	
	public Clown(int posX, int posY, String path,Deletefornoobs deletefornoobs) {
		super(posX,posY, path);
		this.deletefornoobs = deletefornoobs;
	
	}
	
	@Override
	public void setY(int mY) {
		deletefornoobs.delete();
	}


}