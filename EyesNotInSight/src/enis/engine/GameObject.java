package penis.engine;

import java.util.Objects;

import org.lwjgl.util.vector.Vector2f;

/**
 * Oberklasse fuer alle Objekte, die direkt in die Verwaltung eines GameState's eingefuegt werden.
 * Klassen, die von 'GameObject' erben werden vom zugehoerigen GameState automatisch geupdatet und gerendert.
 * Siehe auch {@link GameState#add(GameObject)}.
 * 
 * @author Saris, Jan-Philipp
 * @since 1.0
 * @date 30.05.2022
 */
public abstract class GameObject {
	
	protected GameState parent;
	protected Vector2f position;
	
	private boolean objectPaused, objectHidden;
	
	/**
	 * Uebernimmt die grundlegende Initialisierung, die fuer alle 'GameObject'-Unterklassen notwendig ist.
	 * Fuegt zudem das GameObject automatisch zum gegebenen GameState hinzu (s. s. {@link GameState#add(GameObject)}).
	 * @param _parent Der GameState, zu dem das GameObject hinzugefuegt werden soll (nicht 'null').
	 */
	public GameObject(GameState _parent) {
		Objects.requireNonNull(_parent);
		
		parent = _parent;
		position = new Vector2f();
		
		parent.add(this);
	}
	
	/**
	 * Enthaelt die update-Funktionalitaet eines GameObject's. Muss in erbender Klasse überschrieben werden.
	 * Die Ausgestaltung ist frei.
	 * @param delta Zeit in Sekunden, die seit dem letzten Frame vergangenen ist.
	 */
	public abstract void update(float delta);
	
	/**
	 * Enthaelt die render-Funktionalitaet eines GameObject's. Muss in erbender Klasse überschrieben werden.
	 * Die Ausgestaltung ist frei.
	 * @param brush Instanz einer Hilfsklasse, die grundlegende Render-Aufrufe bereitstellt.
	 */
	public abstract void draw(Brush brush);
	
	/**
	 * Setzt die Position des GameObject's auf die angegebene Position oder auf (0,0), falls 'pos' 'null' ist.
	 * @param pos Die neue Position des GameObject's.
	 */
	public void setPosition(Vector2f pos) {
		if(pos == null) {
			setPosition(0, 0);
			return;
		}
		
		setPosition(pos.x, pos.y);
	}
	
	/**
	 * Setzt die Position des GameObject's auf die angegebene Position.
	 * @param x x-Komponente der Position.
	 * @param y y-Komponente der Position.
	 */
	public void setPosition(float x, float y) {
		position.x = x;
		position.y = y;
	}
	
	/**
	 * 
	 * @param paused Soll das Object weiter automatisch geupdatet werden?
	 * @since 1.2
	 * @date 16.06.2022
	 */
	public final void setPaused(boolean paused) {
		objectPaused = paused;
	}

	/**
	 * 
	 * @since 1.2
	 * @date 16.06.2022
	 * @return Wird das Object aktuell automatisch geupdatet?
	 */
	public final boolean isPaused() {
		return objectPaused;
	}

	/**
	 * 
	 * @param hidden Soll das Object weiter automatisch gerendert werden?
	 * @since 1.2
	 * @date 16.06.2022
	 */
	public final void setHidden(boolean hidden) {
		objectHidden = hidden;
	}

	/**
	 * 
	 * @since 1.2
	 * @date 16.06.2022
	 * @return Wird das Object aktuell automatisch gerendert?
	 */
	public final boolean isHidden() {
		return objectHidden;
	}

}
