package penis.engine;

import org.lwjgl.util.vector.Vector4f;

/**
 * Stellt eine Textur als {@link GameObject} dar und automatisiert dadurch den Render-Aufruf.
 * 
 * @author Saris, Jan-Philipp
 * @since 1.0
 * @date 30.05.2022
 */
public class Image extends GameObject {
	
	private Texture texture;
	private Vector4f tint;
	private boolean flipX, flipY;

	/**
	 * Noetiger Konstruktor fuer ein GameObject. Setzt zudem die gespeicherte Textur auf 'null'.
	 * @param _parent Der GameState, dem das Image zugeordnet werden soll (s. {@link GameObject#GameObject(GameState)}).
	 */
	public Image(GameState _parent) {
		this(_parent, null);
	}
	
	/**
	 * Uebernimmt die Weitergabe von '_parent' an die Oberklasse 'GameObject' und setzt die Textur auf die angegebene.
	 * @param _parent Der GameState, dem das Image zugeordnet werden soll (s. {@link GameObject#GameObject(GameState)}).
	 * @param _texture Die anfaenglich gespeicherte Textur ('null' ist erlaubt).
	 */
	public Image(GameState _parent, Texture _texture) {
		super(_parent);
		texture = _texture;
		tint = new Vector4f(1.f, 1.f, 1.f, 1.f);
	}

	/**
	 * Macht nichts, ist aber als GameObject notwendig.
	 * @param delta Die seit dem letzten Frame vergangene Zeit in Sekunden.
	 */
	@Override
	public void update(float delta) {
		
	}

	/**
	 * Rendert die Textur (falls vorhanden) an der intern gespeicherten Position (s. {@link GameObject#position}).
	 * @param brush Instanz einer Hilfsklasse, die grundlegende Render-Funktionalitaet enthaelt.
	 */
	@Override
	public void draw(Brush brush) {
		if(texture == null) return;
		
		brush.drawTexture(position.x, position.y, texture, flipX, flipY, tint.x, tint.y, tint.z, tint.w);
	}
	
	/**
	 * Setzt die Textur, die ab sofort durch das Image dargestellt wird.
	 * @param _texture Die neue Textur ('null' ist erlaubt, schaltet effektiv das Rendern des Image's ab).
	 */
	public void setTexture(Texture _texture) {
		texture = _texture;
	}
	
	/**
	 * Setzt die Einfaerbung der Textur für'S Rendern auf die gegebene Farbe oder auf Weiß (Identity), falls _tint 'null' ist.
	 * @param _tint Farbe mit XYZW-Komponenten als RGBA
	 * @since 1.1
	 * @date 02.06.2022
	 */
	public void setTint(Vector4f _tint) {
		if(_tint == null) {
			setTint(1.f, 1.f, 1.f, 1.f);
		} else {
			setTint(_tint.x, _tint.y, _tint.z, _tint.w);
		}
	}
	
	/**
	 * Setzt die Einfaerbung der Textur für'S Rendern auf die gegebene Farbe oder auf Weiß (Identity), falls _tint 'null' ist.
	 * @param r R-Komponente der Tint-Farbe (Rot-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param g G-Komponente der Tint-Farbe (Gruen-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param b B-Komponente der Tint-Farbe (Blau-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param a A-Komponente der Tint-Farbe, Angabe über Transparenz (Alpha-Kanal, Wert zwischen 0.0 und 1.0)
	 * @since 1.1
	 * @date 02.06.2022
	 */
	public void setTint(float r, float g, float b, float a) {
		tint.x = r;
		tint.y = g;
		tint.z = b;
		tint.w = a;
	}

}
