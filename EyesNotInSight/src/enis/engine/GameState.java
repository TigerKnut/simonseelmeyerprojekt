package penis.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.lwjgl.util.vector.Vector4f;

import penis.mathis.Konflikt;

/**
 * Beschreibt einen Zustand der State-Machine, die den Kern der {@link Game}-Struktur bildet.
 * 
 * @author Saris, Jan-Philipp
 * @since 1.0
 * @date 30.05.2022
 */
public abstract class GameState {

	protected Game game;
	protected Window window;
	protected Loader loader;
	protected Input input;
	
	private List<GameObject> gameObjects;
	
	private String folderPath;
	
	/**
	 * Setzt alle noetigen Komponenten des GameState's auf und registriert den Zustand im Game (s. {@link Game#addGameState(GameState)})
	 * @param _game Das 'Game', zu dem der Zustand gehoeren soll (nicht 'null').
	 */
	public GameState(Game _game) {
		Objects.requireNonNull(_game);
		game = _game;
		window = game.getWindow();
		input = game.getInput();
		loader = game.getLoader();
		
		folderPath = "res/" + getStateID() + "/";
		
		gameObjects = new ArrayList<>();
		
		game.addGameState(this);
	}
	
	/**
	 * Wrapper fuer die in Unterklassen ueberschriebene Funktion {@link #onLoad}
	 * @since 1.2
	 * @date 09.06.2022
	 */
	public final void load() {
		onLoad();
	}
	
	/**
	 * Wrapper fuer die in Unterklassen ueberschriebene Funktion {@link #onInit()}
	 */
	public final void init() {
		onInit();
	}
	
	/**
	 * Ruft {@link #onUpdate(float)} der erbenden Klasse auf und updatet dann alle hinzugefuegten GameObject's.
	 * @param delta Die seit dem letzten Frame vergangene Zeit in Sekunden
	 */
	public final void update(float delta) {
		onUpdate(delta);
		
		//Objekte dürfen nicht geupdatet werden, wenn der GameState nicht mehr aktuell ist.
		if(game.getCurrentGameStateID() != this.getStateID()) return;
		
		for(int i = 0; i < gameObjects.size(); i++) {
			if(game.getCurrentGameStateID() != this.getStateID()) return;
			
			GameObject obj = gameObjects.get(i);
			
			if(!obj.isPaused()) obj.update(delta);
		}
	}
	
	/**
	 * Rendert alle hinzugefuegten GameObject's und ruft dann {@link #onRender(Brush)} der erbenden Klasse auf.
	 * @param brush Instanz einer Hilfsklasse, die Render-Funktionalitaeten enthaelt.
	 */
	public final void render(Brush brush) {
		for(GameObject obj : gameObjects) {
			if(!obj.isHidden()) obj.draw(brush);
		}
		
		onRender(brush);
	}
	
	/**
	 * Wrapper fuer die in Unterklassen ueberschriebene Funktion {@link #onDestroy()}
	 */
	public final void destroy() {
		onDestroy();
		gameObjects.clear();
	}
	
	/**
	 * Enthaelt den Prae-Initialisierungscode in Unterklassen, in welchem Daten vorab geladen werden sollen,
	 * um eine Mehrbelastung im Spiel zu vermeiden.
	 * Wird nur ein einziges Mal aufgerufen.
	 */
	protected abstract void onLoad();
	
	/**
	 * Enthaelt den Initialisierungs-Code in Unterklassen
	 */
	protected abstract void onInit();
	
	/**
	 * Enthaelt die Update-Routine in Unterklassen
	 * @param delta Die seit dem letzten Frame vergangene Zeit in Sekunden
	 */
	protected abstract void onUpdate(float delta);
	
	/**
	 * Enthaelt die Render-Routine in Unterklassen
	 * @param brush Instanz einer Hilfsklasse, die Render-Funktionalitäten bietet
	 */
	protected abstract void onRender(Brush brush);
	
	/**
	 * Enthaelt den SHutdown-Code in Unterklassen.
	 * Im Regelfall reicht es hier aus, alle eigens erstellten Variablen
	 * auf Grundzustaende zurueckzusetzen (z. B. 'null' etc.)
	 */
	protected abstract void onDestroy();
	
	/**
	 * Gibt die interne ID des GameState's zurueck, sie sollte für jeden GameState einzigartig sein.
	 * Mit der ID werden GameState's im Game Referenziert (s. {@link Game#changeGameState(String)}).
	 * Außerdem wird die ID zur Vereinfachung der load-Aufrufe genutzt; dafuer muss im 'res'-Ordner
	 * ein Unterordner angelegt werden mit exakt dem Namen, der durch die ID vorgegeben wird.
	 * Beispiel: Funktion gibt 'example' zurueck, Unterordner heisst 'example' ("res/example/")
	 * @return Die ID des GameState's
	 */
	public abstract String getStateID();
	
	/**
	 * Gibt den Namen zurueck, mit dem der GameState im Hauptmenue angezeigt werden soll.
	 * Z. B. für ein Pacman-Spiel koennte man "Pacman" zurueckgeben etc.
	 * @since 1.2
	 * @date 09.06.2022
	 * @return Der Anzeigename des GameState's
	 */
	public abstract String getStateDisplayName();
	
	/**
	 * Fuegt ein GameObject der internen Verwaltung hinzu, sofern es nicht 'null' ist
	 * @param obj Das hinzuzufuegende GameObject
	 */
	public final void add(GameObject obj) {
		if(obj == null) return;
		
		gameObjects.add(obj);
	}
	
	/**
	 * Gibt den Input-Manager des uebergeordneten Game's zurueck
	 * @return Der Input-Manager
	 */
	public final Input getInput() {
		return input;
	}
	
	/**
	 * Laedt eine Textur über den internen Loader (@link Loader#loadTexture(String)}).
	 * Durch interne Vereinfachung muss nicht der vollstaendige Pfad angegeben werden, sondern lediglich
	 * der Pfad ab dem eigenen Unterordner im 'res'-Verzeichnis (s. auch {@link #getStateID()})
	 * @param filename Dateipfad der Textur
	 * @return Geladene Textur
	 */
	public final Texture loadTexture(String filename) {
		return loader.loadTexture(folderPath + filename);
	}
	
	/**
	 * Laedt den Inhalt einer Datei als Text ueber den internen Loader ({@link Loader#loadText(String)}).
	 * Durch interne Vereinfachung muss nicht der vollstaendige Pfad angegeben werden, sondern lediglich
	 * der Pfad ab dem eigenen Unterordner im 'res'-Verzeichnis (s. auch {@link #getStateID()})
	 * @param filename Dateipfad der Datei
	 * @return Inhalt der Datei als Text
	 */
	public final String loadText(String filename) {
		return loader.loadText(folderPath + filename);
	}
	
	/**
	 * Laedt eine Bitmap-Font ueber den internen Loader ({@link Loader#loadBitmapFont(String, String)}).
	 * Durch interne Vereinfachung muss nicht der vollstaendige Pfad angegeben werden, sondern lediglich
	 * der Pfad ab dem eigenen Unterordner im 'res'-Verzeichnis (s. auch {@link #getStateID()})
	 * @since 1.2
	 * @date 09.06.2022
	 * @param fntFilename
	 * @param texFilename
	 * @return
	 */
	public final BitmapFont loadBitmapFont(String fntFilename, String texFilename) {
		return loader.loadBitmapFont(folderPath + fntFilename, folderPath + texFilename);
	}
	
	/**
	 * Gibt Zugriff auf {@link Window#setClearColor(float, float, float, float)}
	 * @date 31.05.2022
	 * @param color Die Farbe, mit der der Hintergrund gefuellt werden soll (XYZW-Komponenten als RGBA)
	 */
	protected final void setClearColor(Vector4f color) {
		if(color == null) {
			setClearColor(0.f, 0.f, 0.f, 1.f);
		} else {
			setClearColor(color.x, color.y, color.z, color.w);
		}
	}
	
	/**
	 * Gibt Zugriff auf {@link Window#setClearColor(float, float, float, float)}
	 * @date 31.05.2022
	 * @param r R-Komponente der Fuellfarbe (Rot-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param g G-Komponente der Fuellfarbe (Gruen-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param b B-Komponente der Fuellfarbe (Blau-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param a A-Komponente der Fuellfarbe, Angabe über Transparenz (Alpha-Kanal, Wert zwischen 0.0 und 1.0)
	 */
	protected final void setClearColor(float r, float g, float b, float a) {
		game.setClearColor(r, g, b, a);
	}
	
}
