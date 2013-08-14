package com.game.source.engine;

import org.lwjgl.input.Mouse;

public class GameTick { // Calculate all that happens in the game
	// Delta is 1000 / 60, about 17. Gravity is 9.8m/s/s, a block is 0.5m. That's 19.6 blocks/s/s.
	// There are 60 frames per second, so move (19.6 / 60) blocks/s/s.
	// (20 / 60) / (1000 / 60) = 0.02 blocks/s/s
	public static final float gravity = 0.08f;
	public static float yvel = 0;

	public static void run(int delta) {
		Input.update();

		if (Input.getMouse(0)) {
			Camera.rotateCam((float) (Mouse.getDX() * 0.2), (float) (Mouse.getDY() * -0.2));
			Mouse.setGrabbed(true);
		} else {
			Mouse.setGrabbed(false);
		}

		float speed = (Input.getKey(42) ? 0.1f : 0.02f);

		if (Input.getKey(17)) {
			Camera.move(speed * delta, 0f, 0f);
		}
		if (Input.getKey(30)) {
			Camera.move(0f, -speed * delta, 0f);
		}
		if (Input.getKey(31)) {
			Camera.move(-speed * delta, 0f, 0f);
		}
		if (Input.getKey(32)) {
			Camera.move(0f, speed * delta, 0f);
		}
		/*
		 * if (Input.getKey(16) && Camera.camPos.y < -4) { Camera.move(0f, 0f,
		 * speed * delta); } if (Input.getKey(18) && Camera.camPos.y > -27) {
		 * Camera.move(0f, 0f, -speed * delta); }
		 */
		if (Input.getKeyDown(57) && Camera.camPos.y >= -3) {
			yvel = 0.8f;
		}
		
		if (Input.getKeyDown(34)) {
			RenderTick.lightx = -Camera.camPos.x;
			RenderTick.lighty = Camera.camPos.y + 6;
			RenderTick.lightz = -Camera.camPos.z;
		}

		Camera.camPos.y -= yvel;
		yvel -= gravity;
		if (Camera.camPos.y >= -3) {
			yvel = 0;
			Camera.camPos.y = -3;
		}
	}
}
