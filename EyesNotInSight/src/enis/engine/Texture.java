package penis.engine;

import static org.lwjgl.opengl.GL31.*;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.stb.STBImage;
import org.lwjgl.util.vector.Vector4f;

/**
 * Uebernimmt das Laden einer Textur und die Einbindung dieser in OpenGL.
 * 
 * @author Saris, Jan-Philipp
 * @since 1.0
 * @date 30.05.2022
 */
public class Texture {
	
	private int textureHandle;
	private int width, height;
	
	/**
	 * Laedt mit dem angegebenen Dateipfad die dahinterliegende Textur ueber die STB-Bibliothek
	 * und setzt diese zur Nutzung mit OpenGL auf.
	 * @param filename Vollstaendiger Dateipfad der Textur (innerhalb von Eclipse reicht der Dateipfad ab Projektverzeichnis)
	 */
	public Texture(String filename) {
		ByteBuffer imageBuffer = null;
		
		try {
			int[] w = new int[1], h = new int[1], numChannels = new int[1];
			imageBuffer = STBImage.stbi_load(filename, w, h, numChannels, 4);
			
			if(imageBuffer == null) {
				throw new IOException("Couldn't load texture '" + filename + "'");
			}
			
			width = w[0];
			height = h[0];
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		textureHandle = glGenTextures();

		glBindTexture(GL_TEXTURE_2D, textureHandle);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, imageBuffer);

		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	/**
	 * Bindet die geladene Textur zum Rendern. Erlaubt zudem die Nutzung von Texturen in den naechsten Render-Aufrufen.
	 * Die Funktion wird intern durch die 'Brush'-Klasse aufgerufen, nach aussen hin sonst nicht noetig.
	 */
	public void bind() {
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, textureHandle);
	}
	/**
	 * Entbindet die geladene Textur vom Render-Prozess. Schaltet zudem die Textur-Funktionalitaet von OpenGL wieder ab.
	 * Die Funktion wird intern durch die 'Brush'-Klasse aufgerufen, nach aussen hin sonst nicht noetig.
	 */
	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
		glDisable(GL_TEXTURE_2D);
	}
	
	/**
	 * 
	 * @return Breite der Textur
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * 
	 * @return Hoehe der Textur
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * @since 1.1
	 * @date 02.06.2022
	 * @return Die interne ID, die OpenGL mit der jeweiligen Textur verknuepft
	 */
	public int getHandle() {
		return textureHandle;
	}
	
	/**
	 * Holt die Pixeldaten der Textur aus OpenGL und überfuehrt diese ins Vector4f-Format.
	 * @since 1.2
	 * @date 14.06.2022
	 * @return Die Pixeldaten
	 */
	public Vector4f[][] getTextureData() {
		Vector4f[][] pixels = new Vector4f[width][height];
		float[] pixelData = new float[width * height * 4];
		
		glBindTexture(GL_TEXTURE_2D, textureHandle);
		glGetTexImage(GL_TEXTURE_2D, 0, GL_RGBA, GL_FLOAT, pixelData);
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				int i = y * width + x;
				
				pixels[x][y] = new Vector4f(pixelData[4 * i + 0],
						pixelData[4 * i + 1],
						pixelData[4 * i + 2],
						pixelData[4 * i + 3]);
			}
		}
		
		return pixels;
	}

}
