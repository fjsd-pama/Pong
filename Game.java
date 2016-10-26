//package com.edu4java.minitennis8;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

//For keyboard reading essentials
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//end

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.lang.Math;

//AWT Engine starts several threads; each thread is as if it was an independent program running at the same time

@SuppressWarnings("serial")
//Extend the JPanel class to be able to overwrite the paint method called by the AWT Engine to paint what appears in the screen
public class Game extends JPanel {

	private JFrame frame;
	private int randNum;
	private int randNum2;
	private int randNum3;

	int speed = 1;
	Ball ball = new Ball(this);
	Racquet racquet = new Racquet(this, 0, 0, 330);
	Racquet racquet2 = new Racquet(this, 0, 0, 40){
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_A)
				//VK_A represents key A
				xa = -game.speed; //moves the Racquet to the left
			if (e.getKeyCode() == KeyEvent.VK_D)
				//VK_D represents key D
				xa = game.speed; //moves the Racquet to the right
		}
	};

	/*SOME THINGS TO TAKE NOTE:
	The moving objects of the game are the ball and the racquet. Changing the speed of these
	two objects, we will modify the speed of the game. We are going to include a
	property called "speed" in the Game class to keep the speed of the game.
	The property "speed" will be 1 initially, and it will increase each time we hit the ball with the racquet.*/

	// private int getScore() {
	// 	return speed - 1;
	// } *Gibalhin nako's Racquet

	public Game() {
		randNum = 1 + (int)(Math.random() * 200);
		randNum2 = 1 + (int)(Math.random() * 200);
		randNum3 = 1 + (int)(Math.random() * 200);
		frame = new JFrame("Mini Tennis");
		frame.add(this); //add "this" game
		frame.setSize(300, 400); //Creates a window of 300 pixels by 400 pixels
		setBackground(new Color(randNum, randNum2, randNum3));
		frame.setVisible(true); //Makes the window visible
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Without this, the program won't finish even if we click exit

		//we create the Listener and we register it
		//The Listener is in charge to listen if a key is presses

		//addKeyListener registers Listener in the JPanel
		addKeyListener(new KeyListener() {

			//KeyListener() is an interface which has the keyTyped(), keyPressed() and keyReleased() methods which receive a KeyEvent as a parameter

			//Will be called each time the key is pressed (and several times if the key is maintained pressed)
			//Contains information on which key has been pressed or released
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				racquet.keyReleased(e);
				racquet2.keyReleased(e);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				racquet.keyPressed(e);
				racquet2.keyPressed(e);
			}
		});
		setFocusable(true); //necessary so that the JPanel object receives the keyboard notifications
		Sound.BACK.loop(); //will initiate the playing of our background music and will play repeatedly till it gets to the gameOver() method, where we stop the background music with Sound.BACK.stop()

		//TRIVIA: Using e.getKeyCode() we can obtain the key and if we pass a key code to KeyEvent.getKeyText(...), we can obtain the text which is associated to the key.
		//If we want to register an object to listen to the events of the mouse we can use addMouseListener(MouseListener listener).
	}

	//Move the Sprites
	private void move() {
		ball.move(); //move ball
		racquet.move(); //move racquet
		racquet2.move(); //move racquet2
	}

	@Override //AWT Engine calls the paint method every time the operative system reports that the canvas has to be painted
	//Called also when we maximize or minimize our app
	public void paint(Graphics g) {
		//Graphics is an old class, now replaced with the even-better Graphics2D
		super.paint(g); //cleans the screen
		Graphics2D g2d = (Graphics2D) g; //TODO: Read about Graphics2D library
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON); //makes the borders of the figures smoother
		ball.paint(g2d);
		racquet.paint(g2d);
		racquet2.paint(g2d);

		g2d.setColor(Color.BLUE); //set the color that we use to draw the score
		g2d.setFont(new Font("Verdana", Font.BOLD, 25));
		g2d.drawString("R1: " + String.valueOf(racquet2.getScore()), 10, 30);
		g2d.drawString("R2: " + String.valueOf(racquet.getScore()), 10, 370);
	}

	//launches a popup using JOptionPane.showMessageDialog with the message "Game Over" and an "Accept" button.
	//will be called from the sprite "Ball" when it detects that it has got to the lower border of the canvas
	public void gameOver() {
		Sound.BACK.stop(); //will stop the background music "BACK" before we inform the user "game over"
		Sound.GAMEOVER.play();

		/*JOptionPane.showMessageDialog(this, "your score is: " + racquet.getScore(),
				"Game Over", JOptionPane.YES_NO_OPTION);
		System.exit(ABORT); //makes the program finish*/

		//displays it's game over
		if ( racquet.getScore() > racquet2.getScore() ){
			JOptionPane.showMessageDialog(this, "Racquet 2 wins!!",
				"Game Over", JOptionPane.YES_NO_OPTION); //Racquet 2 means the racquet na naa's ubos
		} else{
			JOptionPane.showMessageDialog(this, "Racquet 1 wins!!",
				"Game Over", JOptionPane.YES_NO_OPTION); //Racquet 1 means racquet na naa's taas
		}

		//prompts users if they want to play again, stops if they don't
		if (JOptionPane.showConfirmDialog(null, "Do you want to play again?", "Play again",
		        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
		    // yes option
		  speed = 1;
			ball = new Ball(this);
			racquet.setScore(0);
			racquet2.setScore(0);
			randNum = 1 + (int)(Math.random() * 200);
			randNum2 = 1 + (int)(Math.random() * 200);
			randNum3 = 1 + (int)(Math.random() * 200);
			setBackground(new Color(randNum, randNum2, randNum3));
			setVisible(true);
		  pong();

		} else {
		    // no option
		    System.exit(ABORT);
		}
	}

	public void pong(){

		/*//The "old" game loop
		while (true) {
			game.move(); //updates where the Sprites are in the coordinates [x, y]
			game.repaint(); //forces de AWT engine to call the paint method to paint again the canvas, together with the changes in the positions etc.
			Thread.sleep(10); //Thread allows a program to start several executions at the same time
			//it tells the processor that the thread which is being run must sleep for 10 milliseconds, which allows the processor to execute other threads and in particular the AWT-EventQueue thread which calls the paint method.
		}*/

		//The "new" game loop
		while ( (racquet.getScore() != 3) && (racquet2.getScore() != 3) ) {
			move(); //updates where the Sprites are in the coordinates [x, y]
			repaint(); //forces de AWT engine to call the paint method to paint again the canvas, together with the changes in the positions etc.

			try {
			    Thread.sleep(10); //Thread allows a program to start several executions at the same time
				//it tells the processor that the thread which is being run must sleep for 10 milliseconds, which allows the processor to execute other threads and in particular the AWT-EventQueue thread which calls the paint method.
			} catch(InterruptedException e) {
			     // this part is executed when an exception (in this example InterruptedException) occurs
			}
		}

		gameOver();
	}

	public static void main(String[] args) throws InterruptedException {
		Game game = new Game();
		game.pong();
	}
}
