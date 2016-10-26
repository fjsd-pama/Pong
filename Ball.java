//package com.edu4java.minitennis8;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;

//Isolates everything that has to do with the ball
public class Ball {
	//Putting the code of the Ball into a Sprite type class becomes more clear when we include the racquet with a new Sprite
	private static final int DIAMETER = 30;

	int x = 0;
	int y = 0;

	//xa and ya represent the speed in which the ball is moving
	int xa = 1; //makes the ball move to the right; initial value
	int ya = 1; //makes the ball move down; initial value
	private Game game; //The Ball sprite needs the reference to the Game object to obtain the borders of the canvas and in this way know when to change the direction.
	//e.g. in move(), game.getWidth() and game.getHeight() are called.

	public Ball(Game game) {
		this.game = game;
	}

	//To make the circle move, we have to modify the position (x,y) each time and paint the circle in the new position.
	void move() {
		boolean changeDirection = true;

		//these  if-statements limit a border of the canvas.
		if (x + xa < 0){
			xa = game.speed; //move to the right
		}

		else if (x + xa > game.getWidth() - DIAMETER){
			xa = -game.speed; //move to the left
		}

		else if (y + ya < 0){
			//NAKADAOG NA DIRI ANG RACQUET 1!!
			Sound.SMASH.play();
			game.racquet.score++;
			//game.gameOver(); //when nalapas na siya sa top
			ya = game.speed; //move down
		}
		else if (y + ya > game.getHeight() - DIAMETER){
			//NAKADAOG NA DIRI ANG RACQUET 2!!
			Sound.SMASH.play();
			game.racquet2.score++;
			ya = -game.speed; //move up
			//game.gameOver(); //when nalapas na siya sa height (sa bottom)
		} else if (collision(game.racquet)){
			//If the collision takes place, we will change the direction and the position of the ball.
			ya = -game.speed; //move up
			y = game.racquet.getTopY() - DIAMETER;
			//The "Racquet" getTopY() method gives us the position in the "y" axis of the upper part of the racquet, and if we discount the DIAMETER, we obtain the exact position where to put the ball so that it is on top of the racquet.
			game.speed++;
			//game.racquet.score++;
		} else if (collision(game.racquet2)){
			//If the collision takes place, we will change the direction and the position of the ball.
			ya = game.speed; //move down
			y = game.racquet2.getTopY() + DIAMETER;
			//The "Racquet" getTopY() method gives us the position in the "y" axis of the upper part of the racquet, and if we discount the DIAMETER, we obtain the exact position where to put the ball so that it is on top of the racquet.
			game.speed++;
			//game.racquet2.score++;
		} else{
			changeDirection = false;
		}


		if (changeDirection){
			Sound.BALL.play(); //plays "BALL" sound, when the ball bounces
		}

		x = x + xa;
		y = y + ya;
	}

	// returns true, if the rectangle occupied by the racquet "game.racquet.getBounds()" intersects with the rectangle occupied by the ball "getBounds()"
	private boolean collision(Racquet given) {
		//return game.racquet.getBounds().intersects(getBounds());
		return given.getBounds().intersects(getBounds());
	}

	public void paint(Graphics2D g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.GREEN); //set the color used to draw
		g.fillOval(x, y, DIAMETER, DIAMETER); //accepts coordinates "x, y", height, and width
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, DIAMETER, DIAMETER);
	}
}
