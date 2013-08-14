package com.game.source.engine;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Input { // Get all mouse and keyboard inputs
	private static ArrayList<Integer> currentKeys = new ArrayList<Integer>(); // List of pressed keys
	private static ArrayList<Integer> frameKeyDowns = new ArrayList<Integer>();
	private static ArrayList<Integer> frameKeyUps = new ArrayList<Integer>();
	private static ArrayList<Integer> currentMouse = new ArrayList<Integer>();
	private static ArrayList<Integer> frameMouseDowns = new ArrayList<Integer>();
	private static ArrayList<Integer> frameMouseUps = new ArrayList<Integer>();
	private static int mouseX;
	private static int mouseY;

	public static void update() {
		int keyLimit = 255;
		int mouseLimit = 5;

		frameKeyUps.clear();
		frameMouseUps.clear();
		for (int i = 0; i < keyLimit; i++) {
			if (currentKeys.contains(i) && !getKey(i)) {
				frameKeyUps.add(i);
			}
			if (currentMouse.contains(i) && !getMouse(i) && i < mouseLimit) {
				frameMouseUps.add(i);
			}
		}

		frameKeyDowns.clear();
		frameMouseDowns.clear();
		for (int i = 0; i < keyLimit; i++) {
			if (!currentKeys.contains(i) && getKey(i)) {
				frameKeyDowns.add(i);
			}
			if (!currentMouse.contains(i) && getMouse(i) && i < mouseLimit) {
				frameMouseDowns.add(i);
			}
		}

		currentKeys.clear();
		currentMouse.clear();
		for (int i = 0; i < keyLimit; i++) {
			if (getKey(i)) {
				currentKeys.add(i);
			}
			if (getMouse(i) && i < mouseLimit) {
				currentMouse.add(i);
			}
		}
	}

	public static boolean getKey(int key) {
		return Keyboard.isKeyDown(key);
	}

	public static boolean getMouse(int button) {
		return Mouse.isButtonDown(button);
	}

	public static boolean getKeyDown(int key) {
		return frameKeyDowns.contains(key);
	}

	public static boolean getMouseDown(int key) {
		return frameMouseDowns.contains(key);
	}

	public static boolean getKeyUp(int key) {
		return frameKeyUps.contains(key);
	}

	public static boolean getMouseUp(int key) {
		return frameMouseUps.contains(key);
	}
}
