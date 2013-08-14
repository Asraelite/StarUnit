package com.game.source.engine;

import static org.lwjgl.opengl.GL11.*; // Import OpenGL

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class RenderTick { // Calculate all that needs to be rendered
	public static float lightx = 0;
	public static float lighty = 0;
	public static float lightz = 0;
	public static int x = 300;
	public static int y = 200;
	public static int lastTexture = 0;
	public static ArrayList<Texture> textures = new ArrayList<Texture>();

	public static FloatBuffer asFloatBuffer(float[] floatarray) {
		ByteBuffer temp = ByteBuffer.allocateDirect(16);
		temp.order(ByteOrder.nativeOrder());
		return (FloatBuffer) temp.asFloatBuffer().put(floatarray).flip();
	}

	private static void initGL() {
		GL11.glMatrixMode(GL11.GL_PROJECTION); // start OpenGL
		GL11.glLoadIdentity();
		GLU.gluPerspective(60.0f, (float) 1000 / (float) 650, 0.1f, 1000.0f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW); // Select The Model view Matrix, whatever that is
		GL11.glLoadIdentity();

		GL11.glEnable(GL11.GL_TEXTURE_2D); // Enable Texture Mapping
		GL11.glShadeModel(GL11.GL_SMOOTH); // Enable Smooth Shading
		GL11.glClearColor(0f, 0f, 0f, 0f); // Black Background
		GL11.glClearDepth(1.0); // Depth Buffer Setup
		GL11.glEnable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
		GL11.glDepthFunc(GL11.GL_LEQUAL); // The Type Of Depth Testing To Do
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST); // Perspective calculations or something

		// Lighting

		float lightAmbient[] = { 0.5f, 0.5f, 0.5f, 1.0f };
		float lightDiffuse[] = { 5.0f, 5.0f, 5.0f, 1.0f };
		float lightPosition[] = { 5f, 5f, 5f, 1.0f };

		GL11.glLightModel(GL11.GL_AMBIENT, asFloatBuffer(lightAmbient)); // Ambient light
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, asFloatBuffer(lightDiffuse)); // Diffuse lighting
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, asFloatBuffer(lightPosition)); // Position
		GL11.glEnable(GL11.GL_CULL_FACE);
		//GL11.glCullFace(GL11.GL_BACK);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_DIFFUSE);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_LIGHT0);
	}

	public static void setup() {
		glTranslatef(-1.25f, 1.25f, -10f); // Move 10 out
		loadTexture("PNG", "src/com/textures/tiles/0.png");
		loadTexture("PNG", "src/com/textures/tiles/1.png");
		loadTexture("PNG", "src/com/textures/tiles/2.png");
		loadTexture("PNG", "src/com/textures/tiles/3.png");
		loadTexture("PNG", "src/com/textures/tiles/4.png");
		loadTexture("PNG", "src/com/textures/tiles/5.png");
		initGL();
	}
	
	private static int[][] blockCols = {
		{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {}
	};

	private static float seed(int in) {
		return (float) ((((in * 467) % 43) / 69) % 1);
	}

	private static void loadTexture(String type, String name) {
		glBindTexture(GL_TEXTURE_2D, 0);
		try {
			textures.add(TextureLoader.getTexture(type, new FileInputStream(new File(name))));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		GL11.glTexParameteri(GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
	}

	private static void drawShip(int[][][] ship, float xPos, float yPos, float zPos, int rotPitch, int rotYaw, int rotRoll) {
		for (int i = 0; i < ship.length; i++) { // Loop through X axis
			for (int j = 0; j < ship[i].length; j++) { // Loop through Y axis
				for (int k = 0; k < ship[i][j].length; k++) { // Loop through Z axis
					if (ship[i][j][k] != 0) {
						drawCube(ship[i][j][k] - 1, (float) (xPos + i), (float) (yPos + j), (float) (zPos + k));
					}
				}
			}
		}
	}

	private static Vector3f v(int x, int y, int z) {
		return new Vector3f((float) x, (float) y, (float) z);
	}
	
	public static void renderLights(ArrayList<float[]> lights){
		for(int i = 0; i < lights.size(); i++){
			
		}
	}

	public static float distanceBetween(float x1, float y1, float z1, float x2, float y2, float z2){
		float deltaX = x2 - x1;
		float deltaY = y2 - y1;
		float deltaZ = z2 - z1;
		return (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
	}
	
	private static Vector3f[][] offset = new Vector3f[][] {
			{ v(1, 0, 0), v(0, 0, 0), v(0, 1, 0), v(1, 1, 0) }, // Front
			{ v(0, 0, 0), v(0, 0, 1), v(0, 1, 1), v(0, 1, 0) }, // Right
			{ v(0, 0, 1), v(0, 0, 0), v(1, 0, 0), v(1, 0, 1) }, // Bottom
			{ v(0, 0, 1), v(1, 0, 1), v(1, 1, 1), v(0, 1, 1) }, // Back
			{ v(1, 0, 1), v(1, 0, 0), v(1, 1, 0), v(1, 1, 1)}, // Left
			{ v(0, 1, 0), v(0, 1, 1), v(1, 1, 1), v(1, 1, 0) } // Top

	};
	
	

	private static int[] texCoords = new int[] { 0, 1, 0, 0, 1, 0, 1, 1 };

	private static void drawCube(int type, float x, float y, float z) { // My function I made by myself :3
		if(type != lastTexture){
			int texID = textures.get(type).getTextureID();
			glBindTexture(GL_TEXTURE_2D, texID); // Set texture as current one being used
			lastTexture = type;
		}
		
		Vector3f vec1 = new Vector3f();
		Vector3f vec2 = new Vector3f();
		Vector3f norm = new Vector3f();
		
		glBegin(GL_QUADS); // Start draw faces
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 4; j++) {
				Vector3f vector = offset[i][j];
				Vector3f.sub(offset[i][(j + 1) % 4], vector, vec1);
				Vector3f.sub(offset[i][(j + 2) % 4], vector, vec2);
				Vector3f.cross(vec1, vec2, norm);
				glNormal3f(norm.x, norm.y, norm.z); // Set normal
				
				glVertex3f(x + vector.x, y + vector.y, z + vector.z);
				glTexCoord2f(texCoords[j * 2], texCoords[(j * 2) + 1]);
			}
		}

		glBindTexture(GL_TEXTURE_2D, 0);
		glEnd(); // End draw quad
	}

	static int[][][] object = { { { 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 5 }, { 1, 1, 1, 1, 1, 1 } },
			{ { 1, 1, 0, 0, 1, 1 }, {}, {}, {}, {}, { 3, 4, 5 } },
			{}, {}, { { 0, 2, 3, 3, 2, 0 } }, {}, {}, { { 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5 }, {}, {}, {}, { 6 } }
	};

	public static void run() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Clear

		Camera.camLookThrough(); // Set camera to player position

		drawShip(object, 10f, -1f, 10f, 0, 0, 0); // Draw a ship

		for (int i = 0; i < 10000; i++) { // Draw floor
			float lenPos = 100f - (float) ((Math.floor(i / 100)));
			float widPos = 100f - (float) ((i % 100));
			drawCube(4, lenPos, -2f, widPos);
		}

		drawCube(5, lightx, lighty - 1, lightz);
		float lightPosition[] = { lightx, lighty, lightz, 1.0f };

		glLight(GL_LIGHT0, GL_POSITION, asFloatBuffer(lightPosition));
	}

	public static void end() {
		for (int i = 0; i < textures.size(); i++) {
			textures.get(i).release(); // You have served your purpose, now go! Be free!
		}
	}
}
