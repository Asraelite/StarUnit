package com.game.source.engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Window { // Create and manage window
	public void createWindow(int width, int height, String title) {
		try { // Don't know why it would fail, but apparently it can
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create(); // Create window (?)
			Display.setTitle(title);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void updateDisplay() {
		Display.update(); // Refresh window
	}

	public void endDisplay() {
		Display.destroy(); // Close window
	}

	public boolean isCloseRequested() { // Check if "X" button is clicked on window
		return Display.isCloseRequested();
	}
	
	public static void setTitle(String title){
		Display.setTitle(title);
	}
}
