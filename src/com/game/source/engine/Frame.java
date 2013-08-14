package com.game.source.engine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;

import com.game.source.engine.Window;

public class Frame { // Handles frame rate management (not faster than 60 etc.)
	public static final double FRAME_CAP = 60.0;
	public static int fps = 0;
	public static long thisModTime = 0;
	public static long lastModTime = 0;

	public static void start() {
		Window window = new Window();

		Camera.setCamPos(10, -4, -10);
		Camera.rotateCam(90f, 0f);
		RenderTick.setup();

		while (!window.isCloseRequested() && !Input.getKey(1)) {
			long thisTime = Clock.getTime();
			thisModTime = thisTime % 1000000000;
			int delta = (int) ((thisTime - Clock.getLastTime()) / 1000000);
			Clock.setLastTime(thisTime);
			fps++;
			if(thisModTime < lastModTime){
				Window.setTitle("Star Unit - FPS: " + fps);
				fps = 0;
			}
			lastModTime = thisModTime;
			GameTick.run(delta);
			RenderTick.run();
			GL11.glLoadIdentity();
			window.updateDisplay();
			Display.sync(60);
		}

		RenderTick.end();
		window.endDisplay();
	}
}
