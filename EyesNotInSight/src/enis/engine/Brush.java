package penis.engine;

import java.util.Objects;

import org.lwjgl.util.vector.Vector4f;

import static org.lwjgl.opengl.GL31.*;

/**
 * Enthaelt grundlegende Render-Funktionen.
 * 
 * @author Saris, Jan-Philipp
 * @since 1.0
 * @date 30.05.2022
 */
public class Brush {
	
	private Window window;
	
	/**
	 * Speichert lediglich das zugehoerige Fenster ab.
	 * @param _window Das zugehoerige Fenster (nicht 'null')
	 */
	public Brush(Window _window) {
		Objects.requireNonNull(_window);
		
		window = _window;
	}
	
	/**
	 * Rendert den Umriss eines Rechtecks.
	 * Anders als geschrieben koennen auch andere Ecke des Rechtecks (oder diese in anderer Reihenfolge)
	 * angegeben werden, sofern es sich um zwei gegenueberliegende Ecke handelt.
	 * @since 1.2
	 * @date 09.06.2022
	 * @param x1 x-Komponente der Position der oberen linken Ecke
	 * @param y1 y-Komponente der Position der oberen linken Ecke
	 * @param x2 x-Komponente der Position der unteren rechten Ecke
	 * @param y2 y-Komponente der Position der unteren rechten Ecke
	 * @param color Die Farbe, mit der das Rechteck gefuellt werden soll
	 */
	public void drawRect(float x1, float y1, float x2, float y2, Vector4f color) {
		if(color == null) {
			drawRect(x1, y1, x2, y2, 1.f, 1.f, 1.f, 1.f);
		} else {
			drawRect(x1, y1, x2, y2, color.x, color.y, color.z, color.w);
		}
	}
	
	/**
	 * Rendert ein ausgefuelltes Rechteck.
	 * Anders als geschrieben koennen auch andere Ecke des Rechtecks (oder diese in anderer Reihenfolge)
	 * angegeben werden, sofern es sich um zwei gegenueberliegende Ecke handelt.
	 * @since 1.2
	 * @date 09.06.2022
	 * @param x1 x-Komponente der Position der oberen linken Ecke
	 * @param y1 y-Komponente der Position der oberen linken Ecke
	 * @param x2 x-Komponente der Position der unteren rechten Ecke
	 * @param y2 y-Komponente der Position der unteren rechten Ecke
	 * @param r R-Komponente der Fuellfarbe (Rot-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param g G-Komponente der Fuellfarbe (Gruen-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param b B-Komponente der Fuellfarbe (Blau-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param a A-Komponente der Fuellfarbe, Angabe über Transparenz (Alpha-Kanal, Wert zwischen 0.0 und 1.0)
	 */
	public void drawRect(float x1, float y1, float x2, float y2, float r, float g, float b, float a) {
		glColor4f(r, g, b, a);
		
		glBegin(GL_LINE_LOOP);
		
		glVertex2f(x1, y1);
		glVertex2f(x2, y1);
		glVertex2f(x2, y2);
		glVertex2f(x1, y2);
		glVertex2f(x1, y1);
		
		glEnd();
	}
	
	/**
	 * Rendert ein ausgefuelltes Rechteck.
	 * Anders als geschrieben koennen auch andere Ecke des Rechtecks (oder diese in anderer Reihenfolge)
	 * angegeben werden, sofern es sich um zwei gegenueberliegende Ecke handelt.
	 * @param x1 x-Komponente der Position der oberen linken Ecke
	 * @param y1 y-Komponente der Position der oberen linken Ecke
	 * @param x2 x-Komponente der Position der unteren rechten Ecke
	 * @param y2 y-Komponente der Position der unteren rechten Ecke
	 * @param color Die Farbe, mit der das Rechteck gefuellt werden soll
	 */
	public void fillRect(float x1, float y1, float x2, float y2, Vector4f color) {
		if(color == null) {
			fillRect(x1, y1, x2, y2, 1.f, 1.f, 1.f, 1.f);
		} else {
			fillRect(x1, y1, x2, y2, color.x, color.y, color.z, color.w);
		}
	}
	
	/**
	 * Rendert ein ausgefuelltes Rechteck.
	 * Anders als geschrieben koennen auch andere Ecke des Rechtecks (oder diese in anderer Reihenfolge)
	 * angegeben werden, sofern es sich um zwei gegenueberliegende Ecke handelt.
	 * @param x1 x-Komponente der Position der oberen linken Ecke
	 * @param y1 y-Komponente der Position der oberen linken Ecke
	 * @param x2 x-Komponente der Position der unteren rechten Ecke
	 * @param y2 y-Komponente der Position der unteren rechten Ecke
	 * @param r R-Komponente der Fuellfarbe (Rot-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param g G-Komponente der Fuellfarbe (Gruen-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param b B-Komponente der Fuellfarbe (Blau-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param a A-Komponente der Fuellfarbe, Angabe über Transparenz (Alpha-Kanal, Wert zwischen 0.0 und 1.0)
	 */
	public void fillRect(float x1, float y1, float x2, float y2,
			float r, float g, float b, float a) {
		
		glColor4f(r, g, b, a);
		
		glBegin(GL_QUADS);

		glVertex2f(x1, y1);
		glVertex2f(x2, y1);
		glVertex2f(x2, y2);
		glVertex2f(x1, y2);
		
		glEnd();
	}
	
	/**
	 * Rendert eine gesamte Textur an der gegebenen Position.
	 * @param x x-Komponente der Position der oberen linken Ecke
	 * @param y y-Komponente der Position der oberen linken Ecke
	 * @param texture Die zu rendernde Textur
	 * @since 1.1
	 * @date 02.06.2022
	 */
	public void drawTexture(float x, float y, Texture texture) {
		if(texture == null) return;
		
		drawTexture(x, y, x + texture.getWidth(), y + texture.getHeight(), 0.f, 0.f, 1.f, 1.f, texture, 1.f, 1.f, 1.f, 1.f);
	}
	
	/**
	 * Rendert eine gesamte Textur an der gegebenen Position.
	 * @param x x-Komponente der Position der oberen linken Ecke
	 * @param y y-Komponente der Position der oberen linken Ecke
	 * @param texture Die zu rendernde Textur
	 * @param r R-Komponente der Tint-Farbe (Rot-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param g G-Komponente der Tint-Farbe (Gruen-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param b B-Komponente der Tint-Farbe (Blau-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param a A-Komponente der Tint-Farbe, Angabe über Transparenz (Alpha-Kanal, Wert zwischen 0.0 und 1.0)
	 * @since 1.1
	 * @date 02.06.2022
	 */
	public void drawTexture(float x, float y, Texture texture, float r, float g, float b, float a) {
		if(texture == null) return;
		
		drawTexture(x, y, x + texture.getWidth(), y + texture.getHeight(), 0.f, 0.f, 1.f, 1.f, texture, r, g, b, a);
	}
	
	/**
	 * Rendert eine gesamte Textur an der gegebenen Position.
	 * @param x x-Komponente der Position der oberen linken Ecke
	 * @param y y-Komponente der Position der oberen linken Ecke
	 * @param texture Die zu rendernde Textur
	 * @param flipX Angabe darüber, ob der Texturausschnitt an der mittleren y-Achse gespiegelt werden soll
	 * @param flipY Angabe darüber, ob der Texturausschnitt an der mittleren x-Achse gespiegelt werden soll
	 * @param r R-Komponente der Tint-Farbe (Rot-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param g G-Komponente der Tint-Farbe (Gruen-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param b B-Komponente der Tint-Farbe (Blau-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param a A-Komponente der Tint-Farbe, Angabe über Transparenz (Alpha-Kanal, Wert zwischen 0.0 und 1.0)
	 * @since 1.2
	 * @date 26.06.2022
	 */
	public void drawTexture(float x, float y, Texture texture, boolean flipX, boolean flipY, float r, float g, float b, float a) {
		if(texture == null) return;
		
		drawTexture(x, y, x + texture.getWidth(), y + texture.getHeight(), 0.f, 0.f, 1.f, 1.f, texture, flipX, flipY, r, g, b, a);
	}
	
	/**
	 * Rendert eine Textur im angegebenen Rechteck.
	 * @param x1 x-Komponente der Position der oberen linken Ecke
	 * @param y1 y-Komponente der Position der oberen linken Ecke
	 * @param x2 x-Komponente der Position der unteren rechten Ecke
	 * @param y2 y-Komponente der Position der unteren rechten Ecke
	 * @param texture Die zu rendernde Textur
	 */
	public void drawTexture(float x1, float y1, float x2, float y2, Texture texture) {
		drawTexture(x1, y1, x2, y2, 0.f, 0.f, 1.f, 1.f, texture);
	}
	
	/**
	 * Rendert eine Textur im angegebenen Rechteck und farrbt sie dabei.
	 * @param x1 x-Komponente der Position der oberen linken Ecke
	 * @param y1 y-Komponente der Position der oberen linken Ecke
	 * @param x2 x-Komponente der Position der unteren rechten Ecke
	 * @param y2 y-Komponente der Position der unteren rechten Ecke
	 * @param texture Die zu rendernde Textur
	 * @param r R-Komponente der Tint-Farbe (Rot-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param g G-Komponente der Tint-Farbe (Gruen-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param b B-Komponente der Tint-Farbe (Blau-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param a A-Komponente der Tint-Farbe, Angabe über Transparenz (Alpha-Kanal, Wert zwischen 0.0 und 1.0)
	 * @since 1.1
	 * @date 02.06.2022
	 */
	public void drawTexture(float x1, float y1, float x2, float y2, Texture texture, float r, float g, float b, float a) {
		drawTexture(x1, y1, x2, y2, 0.f, 0.f, 1.f, 1.f, texture, r, g, b, a);
	}
	
	/**
	 * Rendert den durch die uv-Koordinaten festgelegten Bereich der Textur im angegebenen Rechteck.
	 * Anders als geschrieben koennen auch andere Ecke des Rechtecks (oder diese in anderer Reihenfolge)
	 * angegeben werden, sofern es sich um zwei gegenueberliegende Ecken handelt; in diesem Fall
	 * muessen aber auch die uv-Koordinaten mit denen der jeweiligen Ecken uebereinstimmen.
	 * @param x1 x-Komponente der Position der oberen linken Ecke
	 * @param y1 y-Komponente der Position der oberen linken Ecke
	 * @param x2 x-Komponente der Position der unteren rechten Ecke
	 * @param y2 y-Komponente der Position der unteren rechten Ecke
	 * @param u1 u-Komponente der Texturkoordinate der oberen linken Ecke (quasi x1 fuer Texturen, Wert zwischen 0.0 und 1.0)
	 * @param v1 v-Komponente der Texturkoordinate der oberen linken Ecke (quasi y1 fuer Texturen, Wert zwischen 0.0 und 1.0)
	 * @param u2 u-Komponente der Texturkoordinate der unteren rechten Ecke (quasi x2 fuer Texturen, Wert zwischen 0.0 und 1.0)
	 * @param v2 u-Komponente der Texturkoordinate der unteren rechten Ecke (quasi y2 fuer Texturen, Wert zwischen 0.0 und 1.0)
	 * @param texture Die zu rendernde Textur
	 */
	public void drawTexture(float x1, float y1, float x2, float y2, float u1, float v1, float u2, float v2, Texture texture) {
		drawTexture(x1, y1, x2, y2, u1, v1, u2, v2, texture, 1.f, 1.f, 1.f, 1.f);
	}
	
	/**
	 * Rendert den durch die uv-Koordinaten festgelegten Bereich der Textur im angegebenen Rechteck.
	 * Die uv-Koordinaten sind hierbei nicht in den Bereich 0.0-1.0 skaliert, sondern entsprechen
	 * den Pixelkoordinaten in der Textur (also u zwischen 0 und Breite, v zwischen 0 und Hoehe)
	 * Anders als geschrieben koennen auch andere Ecke des Rechtecks (oder diese in anderer Reihenfolge)
	 * angegeben werden, sofern es sich um zwei gegenueberliegende Ecken handelt; in diesem Fall
	 * muessen aber auch die uv-Koordinaten mit denen der jeweiligen Ecken uebereinstimmen.
	 * @param x1 x-Komponente der Position der oberen linken Ecke
	 * @param y1 y-Komponente der Position der oberen linken Ecke
	 * @param x2 x-Komponente der Position der unteren rechten Ecke
	 * @param y2 y-Komponente der Position der unteren rechten Ecke
	 * @param u1 u-Komponente der Texturkoordinate der oberen linken Ecke (quasi x1 für Texturen)
	 * @param v1 v-Komponente der Texturkoordinate der oberen linken Ecke (quasi y1 für Texturen)
	 * @param u2 u-Komponente der Texturkoordinate der unteren rechten Ecke (quasi x2 für Texturen)
	 * @param v2 u-Komponente der Texturkoordinate der unteren rechten Ecke (quasi y2 für Texturen)
	 * @param texture Die zu rendernde Textur
	 */
	public void drawTexture(float x1, float y1, float x2, float y2, int u1, int v1, int u2, int v2, Texture texture) {
		drawTexture(x1, y1, x2, y2, u1, v1, u2, v2, texture, 1.f, 1.f, 1.f, 1.f);
	}
	
	/**
	 * Rendert den durch die uv-Koordinaten festgelegten Bereich der Textur im angegebenen Rechteck.
	 * Anders als geschrieben koennen auch andere Ecke des Rechtecks (oder diese in anderer Reihenfolge)
	 * angegeben werden, sofern es sich um zwei gegenueberliegende Ecken handelt; in diesem Fall
	 * muessen aber auch die uv-Koordinaten mit denen der jeweiligen Ecken uebereinstimmen.
	 * @param x1 x-Komponente der Position der oberen linken Ecke
	 * @param y1 y-Komponente der Position der oberen linken Ecke
	 * @param x2 x-Komponente der Position der unteren rechten Ecke
	 * @param y2 y-Komponente der Position der unteren rechten Ecke
	 * @param u1 u-Komponente der Texturkoordinate der oberen linken Ecke (quasi x1 für Texturen, Wert zwischen 0.0 und 1.0)
	 * @param v1 v-Komponente der Texturkoordinate der oberen linken Ecke (quasi y1 für Texturen, Wert zwischen 0.0 und 1.0)
	 * @param u2 u-Komponente der Texturkoordinate der unteren rechten Ecke (quasi x2 für Texturen, Wert zwischen 0.0 und 1.0)
	 * @param v2 u-Komponente der Texturkoordinate der unteren rechten Ecke (quasi y2 für Texturen, Wert zwischen 0.0 und 1.0)
	 * @param texture Die zu rendernde Textur
	 * @param flipX Angabe darueber, ob der Texturausschnitt an der mittleren y-Achse gespiegelt werden soll
	 * @param flipY Angabe darueber, ob der Texturausschnitt an der mittleren x-Achse gespiegelt werden soll
	 * @since 1.2
	 * @date 26.06.2022
	 */
	public void drawTexture(float x1, float y1, float x2, float y2, float u1, float v1, float u2, float v2, Texture texture, boolean flipX, boolean flipY) {
		drawTexture(x1, y1, x2, y2, u1, v1, u2, v2, texture, flipX, flipY, 1.f, 1.f, 1.f, 1.f);
	}
	
	/**
	 * Rendert den durch die uv-Koordinaten festgelegten Bereich der Textur im angegebenen Rechteck.
	 * Die uv-Koordinaten sind hierbei nicht in den Bereich 0.0-1.0 skaliert, sondern entsprechen
	 * den Pixelkoordinaten in der Textur (also u zwischen 0 und Breite, v zwischen 0 und Hoehe)
	 * Anders als geschrieben koennen auch andere Ecke des Rechtecks (oder diese in anderer Reihenfolge)
	 * angegeben werden, sofern es sich um zwei gegenueberliegende Ecken handelt; in diesem Fall
	 * muessen aber auch die uv-Koordinaten mit denen der jeweiligen Ecken uebereinstimmen.
	 * @param x1 x-Komponente der Position der oberen linken Ecke
	 * @param y1 y-Komponente der Position der oberen linken Ecke
	 * @param x2 x-Komponente der Position der unteren rechten Ecke
	 * @param y2 y-Komponente der Position der unteren rechten Ecke
	 * @param u1 u-Komponente der Texturkoordinate der oberen linken Ecke (quasi x1 für Texturen)
	 * @param v1 v-Komponente der Texturkoordinate der oberen linken Ecke (quasi y1 für Texturen)
	 * @param u2 u-Komponente der Texturkoordinate der unteren rechten Ecke (quasi x2 für Texturen)
	 * @param v2 u-Komponente der Texturkoordinate der unteren rechten Ecke (quasi y2 für Texturen)
	 * @param texture Die zu rendernde Textur
	 * @param flipX Angabe darueber, ob der Texturausschnitt an der mittleren y-Achse gespiegelt werden soll
	 * @param flipY Angabe darueber, ob der Texturausschnitt an der mittleren x-Achse gespiegelt werden soll
	 * @since 1.2
	 * @date 26.06.2022
	 */
	public void drawTexture(float x1, float y1, float x2, float y2, int u1, int v1, int u2, int v2, Texture texture, boolean flipX, boolean flipY) {
		drawTexture(x1, y1, x2, y2, u1, v1, u2, v2, texture, flipX, flipY, 1.f, 1.f, 1.f, 1.f);
	}
	
	/**
	 * Rendert den durch die uv-Koordinaten festgelegten Bereich der Textur im angegebenen Rechteck und faerbt sie dabei.
	 * Anders als geschrieben koennen auch andere Ecke des Rechtecks (oder diese in anderer Reihenfolge)
	 * angegeben werden, sofern es sich um zwei gegenueberliegende Ecken handelt; in diesem Fall
	 * muessen aber auch die uv-Koordinaten mit denen der jeweiligen Ecken uebereinstimmen.
	 * @param x1 x-Komponente der Position der oberen linken Ecke
	 * @param y1 y-Komponente der Position der oberen linken Ecke
	 * @param x2 x-Komponente der Position der unteren rechten Ecke
	 * @param y2 y-Komponente der Position der unteren rechten Ecke
	 * @param u1 u-Komponente der Texturkoordinate der oberen linken Ecke (quasi x1 für Texturen, Wert zwischen 0.0 und 1.0)
	 * @param v1 v-Komponente der Texturkoordinate der oberen linken Ecke (quasi y1 für Texturen, Wert zwischen 0.0 und 1.0)
	 * @param u2 u-Komponente der Texturkoordinate der unteren rechten Ecke (quasi x2 für Texturen, Wert zwischen 0.0 und 1.0)
	 * @param v2 u-Komponente der Texturkoordinate der unteren rechten Ecke (quasi y2 für Texturen, Wert zwischen 0.0 und 1.0)
	 * @param texture Die zu rendernde Textur
	 * @param r R-Komponente der Tint-Farbe (Rot-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param g G-Komponente der Tint-Farbe (Gruen-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param b B-Komponente der Tint-Farbe (Blau-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param a A-Komponente der Tint-Farbe, Angabe über Transparenz (Alpha-Kanal, Wert zwischen 0.0 und 1.0)
	 * @since 1.1
	 * @date 02.06.2022
	 */
	public void drawTexture(float x1, float y1, float x2, float y2, float u1, float v1, float u2, float v2, Texture texture,
			float r, float g, float b, float a) {
		drawTexture(x1, y1, x2, y2, u1, v1, u2, v2, texture, false, false, r, g, b, a);
	}
	
	/**
	 * Rendert den durch die uv-Koordinaten festgelegten Bereich der Textur im angegebenen Rechteck und faerbt sie dabei.
	 * Die uv-Koordinaten sind hierbei nicht in den Bereich 0.0-1.0 skaliert, sondern entsprechen
	 * den Pixelkoordinaten in der Textur (also u zwischen 0 und Breite, v zwischen 0 und Hoehe)
	 * Anders als geschrieben koennen auch andere Ecke des Rechtecks (oder diese in anderer Reihenfolge)
	 * angegeben werden, sofern es sich um zwei gegenueberliegende Ecken handelt; in diesem Fall
	 * muessen aber auch die uv-Koordinaten mit denen der jeweiligen Ecken uebereinstimmen.
	 * @param x1 x-Komponente der Position der oberen linken Ecke
	 * @param y1 y-Komponente der Position der oberen linken Ecke
	 * @param x2 x-Komponente der Position der unteren rechten Ecke
	 * @param y2 y-Komponente der Position der unteren rechten Ecke
	 * @param u1 u-Komponente der Texturkoordinate der oberen linken Ecke (quasi x1 für Texturen)
	 * @param v1 v-Komponente der Texturkoordinate der oberen linken Ecke (quasi y1 für Texturen)
	 * @param u2 u-Komponente der Texturkoordinate der unteren rechten Ecke (quasi x2 für Texturen)
	 * @param v2 u-Komponente der Texturkoordinate der unteren rechten Ecke (quasi y2 für Texturen)
	 * @param texture Die zu rendernde Textur
	 * @param r R-Komponente der Tint-Farbe (Rot-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param g G-Komponente der Tint-Farbe (Gruen-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param b B-Komponente der Tint-Farbe (Blau-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param a A-Komponente der Tint-Farbe, Angabe über Transparenz (Alpha-Kanal, Wert zwischen 0.0 und 1.0)
	 * @since 1.1
	 * @date 02.06.2022
	 */
	public void drawTexture(float x1, float y1, float x2, float y2, int u1, int v1, int u2, int v2, Texture texture,
			float r, float g, float b, float a) {
		drawTexture(x1, y1, x2, y2, u1, v1, u2, v2, texture, false, false, r, g, b, a);
	}
	
	/**
	 * Rendert den durch die uv-Koordinaten festgelegten Bereich der Textur im angegebenen Rechteck und faerbt sie dabei.
	 * Anders als geschrieben koennen auch andere Ecke des Rechtecks (oder diese in anderer Reihenfolge)
	 * angegeben werden, sofern es sich um zwei gegenueberliegende Ecke handelt; in diesem Fall
	 * muessen aber auch die uv-Koordinaten mit denen der jeweiligen Ecken uebereinstimmen.
	 * @param x1 x-Komponente der Position der oberen linken Ecke
	 * @param y1 y-Komponente der Position der oberen linken Ecke
	 * @param x2 x-Komponente der Position der unteren rechten Ecke
	 * @param y2 y-Komponente der Position der unteren rechten Ecke
	 * @param u1 u-Komponente der Texturkoordinate der oberen linken Ecke (quasi x1 für Texturen, Wert zwischen 0.0 und 1.0)
	 * @param v1 v-Komponente der Texturkoordinate der oberen linken Ecke (quasi y1 für Texturen, Wert zwischen 0.0 und 1.0)
	 * @param u2 u-Komponente der Texturkoordinate der unteren rechten Ecke (quasi x2 für Texturen, Wert zwischen 0.0 und 1.0)
	 * @param v2 u-Komponente der Texturkoordinate der unteren rechten Ecke (quasi y2 für Texturen, Wert zwischen 0.0 und 1.0)
	 * @param texture Die zu rendernde Textur
	 * @param flipX Angabe darueber, ob der Texturausschnitt an der mittleren y-Achse gespiegelt werden soll
	 * @param flipY Angabe darueber, ob der Texturausschnitt an der mittleren x-Achse gespiegelt werden soll
	 * @param r R-Komponente der Tint-Farbe (Rot-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param g G-Komponente der Tint-Farbe (Gruen-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param b B-Komponente der Tint-Farbe (Blau-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param a A-Komponente der Tint-Farbe, Angabe über Transparenz (Alpha-Kanal, Wert zwischen 0.0 und 1.0)
	 * @since 1.2
	 * @date 26.06.2022
	 */
	public void drawTexture(float x1, float y1, float x2, float y2, float u1, float v1, float u2, float v2, Texture texture,
			boolean flipX, boolean flipY, float r, float g, float b, float a) {
		if(texture == null) return;
		
		if(flipX) {
			float tmp = u1;
			u1 = u2;
			u2 = tmp;
		}
		
		if(flipY) {
			float tmp = v1;
			v1 = v2;
			v2 = tmp;
		}
		
		glColor4f(r, g, b, a);
		texture.bind();
		
		glBegin(GL_QUADS);

		glTexCoord2f(u1, v1);
		glVertex2f(x1, y1);

		glTexCoord2f(u2,v1);
		glVertex2f(x2, y1);

		glTexCoord2f(u2, v2);
		glVertex2f(x2, y2);

		glTexCoord2f(u1, v2);
		glVertex2f(x1, y2);
		
		glEnd();
		
		texture.unbind();
	}
	
	/**
	 * Rendert den durch die uv-Koordinaten festgelegten Bereich der Textur im angegebenen Rechteck und faerbt sie dabei.
	 * Die uv-Koordinaten sind hierbei nicht in den Bereich 0.0-1.0 skaliert, sondern entsprechen
	 * den Pixelkoordinaten in der Textur (also u zwischen 0 und Breite, v zwischen 0 und Hoehe)
	 * Anders als geschrieben koennen auch andere Ecke des Rechtecks (oder diese in anderer Reihenfolge)
	 * angegeben werden, sofern es sich um zwei gegenueberliegende Ecke handelt; in diesem Fall
	 * muessen aber auch die uv-Koordinaten mit denen der jeweiligen Ecken uebereinstimmen.
	 * @param x1 x-Komponente der Position der oberen linken Ecke
	 * @param y1 y-Komponente der Position der oberen linken Ecke
	 * @param x2 x-Komponente der Position der unteren rechten Ecke
	 * @param y2 y-Komponente der Position der unteren rechten Ecke
	 * @param u1 u-Komponente der Texturkoordinate der oberen linken Ecke (quasi x1 für Texturen)
	 * @param v1 v-Komponente der Texturkoordinate der oberen linken Ecke (quasi y1 für Texturen)
	 * @param u2 u-Komponente der Texturkoordinate der unteren rechten Ecke (quasi x2 für Texturen)
	 * @param v2 u-Komponente der Texturkoordinate der unteren rechten Ecke (quasi y2 für Texturen)
	 * @param texture Die zu rendernde Textur
	 * @param flipX Angabe darueber, ob der Texturausschnitt an der mittleren y-Achse gespiegelt werden soll
	 * @param flipY Angabe darueber, ob der Texturausschnitt an der mittleren x-Achse gespiegelt werden soll
	 * @param r R-Komponente der Tint-Farbe (Rot-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param g G-Komponente der Tint-Farbe (Gruen-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param b B-Komponente der Tint-Farbe (Blau-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param a A-Komponente der Tint-Farbe, Angabe über Transparenz (Alpha-Kanal, Wert zwischen 0.0 und 1.0)
	 * @since 1.2
	 * @date 26.06.2022
	 */
	public void drawTexture(float x1, float y1, float x2, float y2, int u1, int v1, int u2, int v2, Texture texture,
			boolean flipX, boolean flipY, float r, float g, float b, float a) {
		if(texture == null) return;
		
		if(flipX) {
			int tmp = u1;
			u1 = u2;
			u2 = tmp;
		}
		
		if(flipY) {
			int tmp = v1;
			v1 = v2;
			v2 = tmp;
		}
		
		glColor4f(r, g, b, a);
		texture.bind();
		
		glMatrixMode(GL_TEXTURE);
		glPushMatrix();
		glScalef(1.f / texture.getWidth(), 1.f / texture.getHeight(), 1.f);
		
		glBegin(GL_QUADS);

		glTexCoord2i(u1, v1);
		glVertex2f(x1, y1);

		glTexCoord2i(u2,v1);
		glVertex2f(x2, y1);

		glTexCoord2i(u2, v2);
		glVertex2f(x2, y2);

		glTexCoord2i(u1, v2);
		glVertex2f(x1, y2);
		
		glEnd();
		
		glPopMatrix();
		
		texture.unbind();
	}
	
	/**
	 * Rendert den gegebenen Text in weiß (Originalfarbe) an der spezifierten Position mit der gegebenen Bitmap-Font.
	 * @since 1.2
	 * @date 09.06.2022
	 * @param x x-Komponente der oberen linken Ecke des Textes
	 * @param y y-Komponente der oberen linken Ecke des Textes
	 * @param text Der Text, der gerendert werden soll
	 * @param font Die Bitmap-Font, mit der der Text gerendert wird
	 */
	public void drawString(float x, float y, String text, BitmapFont font) {
		drawString(x, y, text, font, -1, -1, 1.f, 1.f, 1.f, 1.f);
	}
	
	/**
	 * Rendert den gegebenen Text an der spezifierten Position mit der Bitmap-Font in der gegebenen Färbung
	 * innerhalb des durch Breite und Höhe beschriebenen Maximalrechtecks.
	 * @since 1.2
	 * @date 09.06.2022
	 * @param x x-Komponente der oberen linken Ecke des Textes
	 * @param y y-Komponente der oberen linken Ecke des Textes
	 * @param text Der Text, der gerendert werden soll
	 * @param font Die Bitmap-Font, mit der der Text gerendert wird
	 * @param maxWidth Maximale Breite des gerenderten Textes. Wird sie überschritten, wird in der nächsten Zeile weitergemacht. (Wert von -1 = kein Maximum)
	 * @param maxHeight Maximale Höhe des gerenderten Textes. Ist sie erreicht, bricht das Rendering ab. (Wert von -1 = kein Maximum)
	 * @param r R-Komponente der Tint-Farbe (Rot-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param g G-Komponente der Tint-Farbe (Grün-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param b B-Komponente der Tint-Farbe (Blau-Kanal, Wert zwischen 0.0 und 1.0)
	 * @param a A-Komponente der Tint-Farbe, Angabe über Transparenz (Alpha-Kanal, Wert zwischen 0.0 und 1.0)
	 */
	public void drawString(float x, float y, String text, BitmapFont font, int maxWidth, int maxHeight, float r, float g, float b, float a) {
		if(font == null) return;
		
		Texture tex = font.getTexture();
		int fontHeight = font.getFontHeight();
		
		float currentX = x, currentY = y;
		
		glColor4f(r, g, b, a);
		tex.bind();
		
		glMatrixMode(GL_TEXTURE);
		glPushMatrix();
		glScalef(1.f / tex.getWidth(), 1.f / tex.getHeight(), 1.f);
		
		glBegin(GL_QUADS);
		
		for(int i = 0; i < text.length(); i++) {
			FontChar fc = font.getChar((int)text.charAt(i));
			if(fc == null) continue;
			
			if(maxWidth > 0 && currentX + fc.width >= x + maxWidth) {
				currentX = x;
				currentY += fontHeight;
				
				if(maxHeight > 0 && currentY + fontHeight >= y + maxHeight) break;
			}
			
			glTexCoord2i(fc.texX, fc.texY);
			glVertex2f(currentX + fc.xOffset, currentY + fc.yOffset);

			glTexCoord2i(fc.texX + fc.width, fc.texY);
			glVertex2f(currentX + fc.xOffset + fc.width, currentY + fc.yOffset);

			glTexCoord2i(fc.texX + fc.width, fc.texY + fc.height);
			glVertex2f(currentX + fc.xOffset + fc.width, currentY + fc.yOffset + fc.height);

			glTexCoord2i(fc.texX, fc.texY + fc.height);
			glVertex2f(currentX + fc.xOffset, currentY + fc.yOffset + fc.height);
			
			currentX += fc.xAdvance;
		}
		
		glEnd();
		
		glPopMatrix();
		
		tex.unbind();
	}

}
