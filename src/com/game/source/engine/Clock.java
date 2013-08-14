package com.game.source.engine;

public class Clock { // Get and manage time and FPS
	public static long lastTime;
	public static int fps;
	public static int speed;
	
	public static long getTime(){
		return System.nanoTime();
	}
	
	public static void setLastTime(long time){
		lastTime = time;
	}
	
	public static long getLastTime(){
		return lastTime;
	}
}
