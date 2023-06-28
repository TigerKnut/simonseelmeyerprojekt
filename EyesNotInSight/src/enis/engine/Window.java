package penis.engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL31.*;

import org.lwjgl.opengl.GL;
import org.lwjgl.util.vector.Vector4f;

/**
 * Uebernimmt die Erstellung und Verwaltung des Fensters.
 * 
 * @author Saris, Jan-Philipp
 * @since 1.0
 * @date 30.05.2022
 */
public class Window {
	
	private long windowHandle;
	private static int numActiveWindows = 0;
	
	private int width, height;
	private String title;
	
	private Vector4f clearColor;
	
	/**
	 * Stellt die Grundlagen für ein Fenster bereit (simpler default constructor mit minimaler Initialisierung)
	 */
	public Window() {
		windowHandle = 0;
		clearColor = new Vector4f(0.f, 0.f, 0.f, 1.f);
	}
	
	/**
	 * Erstellt ein Fenster aus den gegebenen Informationen mithilfe der GLFW-Bibliothek.
	 * Setzt zude, die OpenGL-Integration des Fensters auf.
	 * Die Funktion muss zwingend nach Starten des Threads im Game aufgerufen werden!
	 * @param _width Die Breite des zu erstellenden Fensters
	 * @param _height Die Hoehe des zu erstellenden Fensters
	 * @param _title Der Fenstertitel, der dem Fenster zugewiesen werden soll
	 * @return Die ID, die dem erstellten Fenster zugewiesen wird (nur nötig für Input-Klasse).
	 */
	public long create(int _width, int _height, String _title) {
		if(windowHandle != 0) return windowHandle;
		
		width = _width;
		height = _height;
		title = _title;
		
		if(numActiveWindows == 0) {
			if(!glfwInit()) {
				throw new RuntimeException("Failed initializing GLFW");
			}
		}
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		
		windowHandle = glfwCreateWindow(width, height, title, 0, 0);
		
		glfwMakeContextCurrent(windowHandle);
		GL.createCapabilities();
		
		numActiveWindows++;
		
		return windowHandle;
	}
	
	/**
	 * Überprueft, ob der Nutzer den X-Knopf zum Schließen des Fensters gedrueckt hat.
	 * @return Ergebnis der Abfrage, zugleich Information über Schließungswunsch, das Game beendet dabei automatisch.
	 */
	public boolean isActive() {
		if(windowHandle == 0) return false;
		
		return !glfwWindowShouldClose(windowHandle);
	}
	
	/**
	 * Setzt OpenGL fuer das Rendern eines neuen Frames auf.
	 * Die 'clearColor', also die Farbe, mit der OpenGL den gesamten Bildschirm zunaechst fuellt,
	 * wird ebenso in jedem Frame neu angegeben, sodass diese zwischendurch veraendert werden kann.
	 */
	public void beginRender() {
		glClearColor(clearColor.x, clearColor.y, clearColor.z, clearColor.w);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	/**
	 * Beendet das Rendern des aktuellen Frames.
	 * Dabei wird mithilfe von GLFW der Double-Buffer getauscht, sodass auf einem internen Frame nachher
	 * ein weiteres gerendert werden kann.
	 * Zudem lässt die Funktion die waehrend des letzten Frames eingegangenen Informationen (z. B. Tasteneingaben)
	 * verarbeiten.
	 */
	public void endRender() {
		glDisable(GL_BLEND);
		glfwSwapBuffers(windowHandle);
		glfwPollEvents();
	}
	
	/**
	 * Setzt die Farbe, mit der OpenGL ein neues Frame anfangs fuellt.
	 * @param r R-Komponente der Farbe (Rot-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param g G-Komponente der Farbe (Gruen-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param b B-Komponente der Farbe (Blau-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param a A-Komponente der Farbe, Angabe über Transparenz (Alpha-Kanal, Wert zwischen 0.0 und 1.0)
	 */
	public void setClearColor(float r, float g, float b, float a) {
		clearColor.x = r;
		clearColor.y = g;
		clearColor.z = b;
		clearColor.w = a;
	}
	
	/**
	 * @since 1.2
	 * @date 09.06.2022
	 * @return Breite des Fensters
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * @since 1.2
	 * @date 09.06.2022
	 * @return Hoehe des Fensters
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Veraendert die Groesse des Fensters.
	 * @since 1.2
	 * @date 09.06.2022
	 * @param width Neue Breite des Fensters (>0)
	 * @param height Neue Hoehe des Fensters (>0)
	 */
	public void setSize(int _width, int _height) {
		if(windowHandle == 0 || _width <= 0 || _height <= 0) return;
		
		width = _width;
		height = _height;
		
		glfwSetWindowSize(windowHandle, width, height);
		glViewport(0, 0, width, height);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width, height, 0, -1, 1);
	}

}
