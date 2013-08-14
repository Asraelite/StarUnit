package com.game.source;

import com.game.source.engine.GameTick;
import com.game.source.engine.RenderTick;
import com.game.source.engine.Window;
import com.game.source.engine.Frame;

public class Main { // Executes other classes in correct order
	public static void main(String[] args) {
		startGame();
	}

	public static final int WINDOW_W = 1000;
	public static final int WINDOW_H = 650;

	public static void startGame() {
		Window window = new Window();
		window.createWindow(WINDOW_W, WINDOW_H, "Star Unit");
		Frame.start();
	}
}
