package eg.edu.alexu.csd.oop.game;
import java.io.File;
import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedHashMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class DynamicBinder {
	private LinkedHashMap<String, Constructor<GameObject>> objectsMap;
	private LinkedHashMap<String, Constructor<World>> worldMap;
	private static DynamicBinder binder;
	private JFileChooser fileChooser;
	private JOptionPane optionPane;
	private ClassLoader cLoader;
	
	private DynamicBinder(){
		
	}
	
	public static DynamicBinder getInstance(){
		if (binder==null){
			binder = new DynamicBinder();
		}
		return binder;
	}
	
	public Constructor<GameObject> loadGameObject(String name, String type) throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, SecurityException{
		if (fileChooser==null){fileChooser = new JFileChooser();}
		if (objectsMap==null){objectsMap = new LinkedHashMap<>();}
		fileChooser.showOpenDialog(null);
		File f = fileChooser.getSelectedFile();
		@SuppressWarnings("deprecation")
		URL url = f.toURL();
		URL[] urls = {url};
		cLoader = new URLClassLoader(urls);
		Class<?> gameObject = cLoader.loadClass(name);
		if (type=="characer")
			objectsMap.put(name, (Constructor<GameObject>) gameObject.getDeclaredConstructor(int.class, int.class, String.class));
		
		return (Constructor<GameObject>) objectsMap.get(name);
	}
	
	public Constructor<World> loadWorld(String name, int screenWidth, int screenHeight) throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, SecurityException{
		if (fileChooser==null){fileChooser = new JFileChooser();}
		if (objectsMap==null){worldMap = new LinkedHashMap<>();}
		fileChooser.showOpenDialog(null);
		File f = fileChooser.getSelectedFile();
		@SuppressWarnings("deprecation")
		URL url = f.toURL();
		URL[] urls = {url};
		cLoader = new URLClassLoader(urls);
		Class<?> worldObject = World.class.forName(name, true, cLoader);
		worldMap.put(name, (Constructor<World>) worldObject.getDeclaredConstructor(int.class, int.class));
		
		return (Constructor<World>) worldMap.get(name);
	}
	
	
	
}
