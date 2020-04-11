package eg.edu.alexu.csd.oop.game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import eg.edu.alexu.csd.oop.game.GameEngine.GameController;

public class Main {

	private static World w;
	private static  Originator ori = new Originator();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		w = new eg.edu.alexu.csd.oop.game.Circuss(1000, 700,1);
		JMenuBar menuBar = new JMenuBar();;
		JMenu menu = new JMenu("File");
		JMenuItem newMenuItem = new JMenuItem("New");
		JMenuItem pauseMenuItem = new JMenuItem("Pause");
		JMenuItem resumeMenuItem = new JMenuItem("Resume");
		JMenuItem worldMenuItem = new JMenuItem("Load World");
		menu.add(newMenuItem);
		menu.addSeparator();
		menu.add(pauseMenuItem);
		menu.add(resumeMenuItem);
		menu.add(worldMenuItem);
		menuBar.add(menu);
		final GameController gameController = GameEngine.start("Very Simple Game in 99 Line of Code", w, menuBar, Color.WHITE);
	
		newMenuItem.addActionListener(new ActionListener() {
		@Override public void actionPerformed(ActionEvent e) {
			gameController.changeWorld(w);
			}
		});
		pauseMenuItem.addActionListener(new ActionListener() {
		@Override public void actionPerformed(ActionEvent e) {
				gameController.pause();
			}
		});
		resumeMenuItem.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				gameController.resume();
			}
		});
		
		newMenuItem.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				
				ori.restore(Caretaker.getInstance().getinitialMemento(), (Circuss) w);
	
				}
			});
		
		//LOAD WORLD BUTTON
		//USES DYNAMIC BINDER TO CHANGE WORLD
		worldMenuItem.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				try {
					gameController.changeWorld(DynamicBinder.getInstance().loadWorld("eg.edu.alexu.csd.oop.game.Circuss", 1000, 700).newInstance(400, 700));
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | MalformedURLException | ClassNotFoundException
						| NoSuchMethodException | SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	
}
