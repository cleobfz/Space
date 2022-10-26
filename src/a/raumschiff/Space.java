package a.raumschiff;

import java.awt.Color;
import java.util.Random;
import java.awt.BorderLayout;

import javax.swing.*;

public class Space {
	private JFrame frame;
	private JPanel space;
	private Raumschiff orion;
	
	public Space() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,600);
		frame.setLocation(200,100);
		
		frame.setLayout(new BorderLayout());
		
		space = new JPanel();
		space.setBackground(new Color(0x000000));
		
		space.setLayout(null);//Absolute (oder Null-)Layout erlaubt pixelgenaue Positionierung
		
		//Für Sterne
		for(int i = 0; i < 100; i++) {
			JPanel stern = new JPanel();
			stern.setSize(2,2);
			stern.setBackground(new Color(0xFFFFFF));
			Random r = new Random();
			stern.setLocation(r.nextInt(600),r.nextInt(600));
			space.add(stern);
		}
		orion = new Raumschiff();
		space.add(orion);
		space.setComponentZOrder(orion,0);//damit die Orion über den Sternen liegt und nicht dahinter
		
		frame.add(space, BorderLayout.CENTER);
		
		//Bewegung des Raumschiffs soll in einem eigenen Thread passieren
		createThread();
		
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	private void createThread() {
		// Hier wird der Thread erzeugt, der sich um die Bewegung des Raumschiffs kümmert
		Thread move = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						//Damit das Raumschiff nur 10mal pro Sekunde bewegt wird
						Thread.sleep(100);
						orion.move();
						orion.setLocation(orion.getX(),orion.getY());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		move.start();
	}

	public static void main(String[] args) {
		new Space();

	}

}
