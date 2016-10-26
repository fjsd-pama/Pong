//package com.edu4java.minitennis8;

import java.awt.Graphics2D;
import java.awt.Rectangle; //has an intersects method(Rectangle r) which returns true when the two rectangles occupy the same space
import java.awt.event.KeyEvent;
import java.awt.Color;

public class Racquet {
	//private static final int Y = 330;
	private static final int WIDTH = 60;
	private static final int HEIGHT = 10;
	int x = 0; //initially, the Racket will be in the left of the canvass
	int xa = 0;
	int Y; //the "y" coordinate
	int score;
	//private Game game;
	protected Game game;

	public Racquet(Game game, int x, int xa, int Y) {
		this.x = x;
		this.xa = xa;
		this.Y = Y;
		this.game = game;
		score = 0;
	}

	//Racquet doesn´t change its vertical position; it will only move left or right, never up or down.
	public void move() {

		//
		if (x + xa > 0 && x + xa < game.getWidth() - WIDTH)
			x = x + xa;
	}

	public void paint(Graphics2D g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.RED);
		g.fillRect(x, Y, WIDTH, HEIGHT);
		//defines a rectangle of 60 (WIDTH) by 10 (HEIGHT) pixels in the position (x,y)= e.g. (x,330)
	}

	//sets xa into 0, when no key is pressed, or when key is released
	public void keyReleased(KeyEvent e) {
		xa = 0;
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			xa = -game.speed; //moves the Racket to the left
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			xa = game.speed; //moves the Racket to the right
	} //Kailangan pa ko mag-implement ug keyPressed for racquet2

	//returns a rectangle type of object, indicating the position of the racquet
	//will be used by the sprite "Ball", to know the position of the racquet and in this way to detect the collision
	public Rectangle getBounds() {
		return new Rectangle(x, Y, WIDTH, HEIGHT);
	}

	//TODO: Dili pa ko sure ani. Let's go back to this later.
	public int getTopY() {
		return Y - HEIGHT;
	}

	public int getScore() {
		//return speed - 1;
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
