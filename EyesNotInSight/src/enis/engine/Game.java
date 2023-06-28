package penis.engine;

import static org.lwjgl.opengl.GL31.*;

import java.util.HashMap;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector4f;

/**
 * Verwaltet zentral alle zum Game und dessen Lauf zugehoerigen Komponenten sowie die State-Machine,
 * auf der die Struktur aufbaut (s. {@link GameState}).
 * 
 * @author Saris, Jan-Philipp
 * @since 1.0
 * @date 30.05.2022
 */
public class Game implements Runnable {
	
	private Window window;
	private Brush brush;
	private Input input;
	private Loader loader;
	
	private int _w, _h;
	private String _t;
	
	private Thread thread;
	
	private long currentTime, prevTime;
	
	private HashMap<String, GameState> gameStates;
	private String defaultGameState;
	private GameState currentGameState;
	
	private int lastFps;
	private int fpsCount;
	private float fpsTimer;
	private BitmapFont fpsFont;
	private boolean showFps;
	
	/**
	 * Erzeugt alle noetigen Komponenten und speichert für die spaetere Erstellung im eigenen Thread
	 * die Erstellungsdaten fuer das Window ab (s. {@link Window#create})
	 * @param width Breite des spaeter zu erstellenden Fensters
	 * @param height Hoehe des spaeter zu erstellenden Fensters
	 * @param title Titel des spaeter zu erstellenden Fensters
	 */
	public Game(int width, int height, String title) {
		window = new Window();
		brush = new Brush(window);
		input = new Input();
		loader = new Loader();
		
		_w = width;
		_h = height;
		_t = title;
		
		gameStates = new HashMap<>();
		showFps = false;
	}
	
	/**
	 * Startet den Thread, in dem das Game laeuft.
	 * Die Funktion ist notwendig, da zwischen Erstellung des Game's und dem Starten des Threads zuvor
	 * der grundlegende GameState gesetzt werden muss ({@link #setDefaultGameState(GameState)})
	 */
	public void start() {
		if(thread != null) return;
		
		thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * Aus dem 'Runnable'-Interface implementierte Funktion.
	 * Sie ist der Inhalt des neu erstellten Threads ({@link #start()})
	 */
	@Override
	public void run() {
		input.setWindow(window.create(_w, _h, _t));
		
		currentGameState = gameStates.get(defaultGameState);
		if(currentGameState == null) {
			throw new IllegalStateException("A default game state must be specified in order for the game to run");
		}
		
		fpsFont = loader.loadBitmapFont("res/main_menu/Calibri.fnt", "res/main_menu/Calibri.png");
		
		currentGameState.load();
		
		currentGameState.init();
		
		currentTime = System.currentTimeMillis();
		
		while(window.isActive()) {
			prevTime = currentTime;
			currentTime = System.currentTimeMillis();
			float delta = (currentTime - prevTime) / 1000.f;
			
			fpsTimer += delta;
			if(fpsTimer >= 1.f) {
				fpsTimer -= 1.f;
				lastFps = fpsCount;
				fpsCount = 0;
			}
			fpsCount++;
			
			input.update();
			
			//Escape soll ins Hauptmenü zurückführen
			if(input.justPressed(GLFW.GLFW_KEY_ESCAPE)) {
				changeGameState();
			}
			
			currentGameState.update(delta);
			
			glMatrixMode(GL_PROJECTION);
			glPushMatrix();
			
			window.beginRender();
			
			currentGameState.render(brush);
			
			if(showFps) brush.drawString(0, 0, ""+lastFps, fpsFont);
			
			window.endRender();
			
			glMatrixMode(GL_PROJECTION);
			glPopMatrix();
		}
		
		currentGameState.destroy();
	}
	
	/**
	 * Gibt Zugriff auf {@link Window#setClearColor(float, float, float, float)}
	 * @param color Die Farbe, mit der der Hintergrund gefuellt werden soll
	 */
	public final void setClearColor(Vector4f color) {
		if(color == null) {
			setClearColor(0.f, 0.f, 0.f, 1.f);
		} else {
			setClearColor(color.x, color.y, color.z, color.w);
		}
	}
	
	/**
	 * Gibt Zugriff auf {@link Window#setClearColor(float, float, float, float)}
	 * @param r R-Komponente der Fuellfarbe (Rot-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param g G-Komponente der Fuellfarbe (Gruen-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param b B-Komponente der Fuellfarbe (Blau-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param a A-Komponente der Fuellfarbe, Angabe über Transparenz (Alpha-Kanal, Wert zwischen 0.0 und 1.0)
	 */
	public final void setClearColor(float r, float g, float b, float a) {
		window.setClearColor(r, g, b, a);
	}
	
	/**
	 * Gibt das zum Game gehoerige Window-Objekt zurück, noetig für GameState
	 * @return Das Fenster des Game's
	 */
	public final Window getWindow() {
		return window;
	}
	
	/**
	 * Gibt den zum Game gehoerigen Input-Manager zurück, noetig für GameState
	 * @return Der Input-Manager des Game's
	 */
	public final Input getInput() {
		return input;
	}
	
	/**
	 * Gibt den zum Game gehoerigen Loader zurück, noetig für GameState
	 * @return Der Loader des Game's
	 */
	public final Loader getLoader() {
		return loader;
	}
	
	/**
	 * Weist das Game an, in dem grundlegenden Zustand zurueckzukehren.
	 */
	public final void changeGameState() {
		changeGameState(defaultGameState);
	}
	
	/**
	 * Geht in einen neuen State über, falls ein State mit der gegebenen ID registriert ist (s. @link {@link #addGameState(GameState)})
	 * Bleibt andernfalls im aktuellen State.
	 * @param newGameState Die ID des gewuenschten neuen GameState's (s. {@link GameState#getStateID()})
	 */
	public final void changeGameState(String newGameState) {
		GameState newState = gameStates.get(newGameState);
		if(newState == null) return;
		
		if(currentGameState != null) {
			currentGameState.destroy();
		}
		
		currentGameState = newState;
		currentGameState.onInit();
	}
	
	/**
	 * Speichert den gegebenen GameState als Grundzustand ab und fuegt registriert, falls noetig.
	 * Die Funktion muss vor {@link #start()} aufgerufen werden.
	 * @param state Der gewuenschte Grundzustand (nicht 'null')
	 */
	public final void setDefaultGameState(GameState state) {
		if(state == null) return;
		
		if(!gameStates.containsKey(state.getStateID())) {
			gameStates.put(state.getStateID(), state);
		}
		
		defaultGameState = state.getStateID();
	}
	
	/**
	 * @since 1.3
	 * @date 03.07.2022
	 * @return
	 */
	public final String getCurrentGameStateID() {
		return currentGameState.getStateID();
	}
	
	/**
	 * Registriert einen GameState, falls noch keiner mit der ID ({@link GameState#getStateID()}) registriert ist.
	 * @param state Der zu registrierende Zustand (nicht 'null')
	 */
	public final void addGameState(GameState state) {
		if(state == null) return;
		
		if(!gameStates.containsKey(state.getStateID())) {
			gameStates.put(state.getStateID(), state);
		}
	}
	
	/**
	 * Weist das Game an, die Fps-Anzahl (nicht) zu rendern
	 * @since 1.2
	 * @date 16.06.2022
	 * @param show Entsprechende Angabe ueber das Rendern
	 */
	public final void setShowFps(boolean show) {
		showFps = show;
	}

}
