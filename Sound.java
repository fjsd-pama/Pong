//package com.edu4java.minitennis7;

/*SOME IMPORTANT NOTE:
To keep the audioclips of our game we create a Sound class, which we´ll have a constant
with an audioclip for each of the sounds we use. These constants are public so that any
object which have access to them, can play them. For example, in the Ball class we can
play the sound of the bouncing of the ball using Sound.BALL.play() at the moment
we know the ball changes its direction.*/

import java.applet.Applet;
import java.applet.AudioClip; //used to play sound archives

public class Sound {
	public static final AudioClip BALL = Applet.newAudioClip(Sound.class.getResource("ball.wav"));
	public static final AudioClip GAMEOVER = Applet.newAudioClip(Sound.class.getResource("gameover.wav"));
	public static final AudioClip BACK = Applet.newAudioClip(Sound.class.getResource("back.wav"));
	public static final AudioClip SMASH = Applet.newAudioClip(Sound.class.getResource("smash.wav"));
}
