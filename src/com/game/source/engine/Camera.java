package com.game.source.engine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	public static float yaw = 0;
	public static float pitch = 0;
	public static Vector3f camPos = null;

	public static void rotateCam(float y, float p) {
		yaw += y;
		pitch += p;
		if (pitch > 90) { // Prevent flipping upside down
			pitch = 90;
		}
		if (pitch < -90) { // Prevent flipping downside up
			pitch = -90;
		}
	}

	public static void setCamPos(float x, float y, float z) {
		camPos = new Vector3f(x, y, z);
	}

	public static void move(float forward, float side, float up) {
		camPos.x -= forward * (float) Math.sin(Math.toRadians(yaw)); // Forward and backwards
		camPos.z += forward * (float) Math.cos(Math.toRadians(yaw));

		camPos.x -= Math.abs(side) * (float) Math.sin(Math.toRadians(yaw + (side > 0 ? 90 : -90)));
		camPos.z += Math.abs(side) * (float) Math.cos(Math.toRadians(yaw + (side > 0 ? 90 : -90)));

		camPos.y += up;
	}

	public static void camLookThrough() {
		GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);
		GL11.glTranslatef(camPos.x, camPos.y, camPos.z);
	}

}
