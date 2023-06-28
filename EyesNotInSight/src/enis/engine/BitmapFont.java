package penis.engine;

import java.util.HashMap;

import org.lwjgl.util.vector.Vector2f;

/**
 * Haelt eine Bitmap-Font, mit der Text gerendert werden kann.
 * Aufgrund des Wesens einer Bitmap-Font ist eine Skalierung (vorerst) nicht moeglich,
 * da der Text dabei unscharf/unschoen wird.
 * @author Saris, Jan-Philipp
 * @since 1.2
 * @date 09.06.2022
 *
 */
public class BitmapFont {
	
	private Texture fontTexture;
	private FontChar[] chars;
	private HashMap<Integer, Integer> charIndexMap;
	private int fontHeight;
	
	/**
	 * Laedt die Bestandteile der Bitmap-Font und parsed die Informationen der fnt-Datei
	 * @param loader Der Loader des Game's (nicht 'null')
	 * @param fontFile Vollstaendiger Dateipfad der fnt-Datei (innerhalb von Eclipse reicht der Pfad ab Projektverzeichnis)
	 * @param textureFile Vollstaendiger Dateipfad der Bilddatei (innerhalb von Eclipse reicht der Pfad ab Projektverzeichnis)
	 */
	public BitmapFont(Loader loader, String fontFile, String textureFile) {
		String[] fntContent = loader.loadTextLines(fontFile);
		fontTexture = loader.loadTexture(textureFile);
		chars = null;
		
		int currentCharIndex = 0;
		
		for(String line : fntContent) {
			if(chars != null && line.startsWith("char id")) {
				String[] lineParts = line.split("\\s+");
				for(int i = 1; i < lineParts.length; i++) {
					lineParts[i] = lineParts[i].split("=")[1];
				}
				
				int id = Integer.parseInt(lineParts[1]);
				int texX = Integer.parseInt(lineParts[2]);
				int texY = Integer.parseInt(lineParts[3]);
				int width = Integer.parseInt(lineParts[4]);
				int height = Integer.parseInt(lineParts[5]);
				int xOffset = Integer.parseInt(lineParts[6]);
				int yOffset = Integer.parseInt(lineParts[7]);
				int xAdvance = Integer.parseInt(lineParts[8]);
				
				chars[currentCharIndex] = new FontChar(texX, texY, width, height, xOffset, yOffset, xAdvance);
				charIndexMap.put(id, currentCharIndex);
				currentCharIndex++;
			} else if(line.startsWith("chars count")) {
				int charCount = Integer.parseInt(line.split("=")[1]);
				chars = new FontChar[charCount];
				charIndexMap = new HashMap<>();
			} else if(line.startsWith("info")) {
				fontHeight = Integer.parseInt(line.split("\\s+")[2].split("=")[1]);
			}
		}
	}
	
	/**
	 * 
	 * @return Die Textur, die die Bilddatei der Bitmap-Font haelt.
	 */
	public Texture getTexture() {
		return fontTexture;
	}
	
	/**
	 * 
	 * @param charId Der Integer-Wert des Chars gemaess Java-Standard.
	 * @return Die Informationen, die der fnt-Datei über den gegebenen char entnommen wurden od. 'null', falls diese nicht existieren.
	 */
	public FontChar getChar(int charId) {
		if(!charIndexMap.containsKey(charId)) return null;
		
		return chars[charIndexMap.get(charId)];
	}
	
	/**
	 * 
	 * @return Die absolut maximale Hoehe der Chars (alias die Font-Size)
	 */
	public int getFontHeight() {
		return fontHeight;
	}
	
	/**
	 * Berechnet die Groesse des Textes, wenn dieser mit dieser Font gerendert wuerde.
	 * Keine maximale Breite, also einzeilig.
	 * @param text Der Text, dessen Rendergroesse gefragt ist
	 * @return Rendergroesse des Textes. Die y-Komponente entspricht {@link #getFontHeight()}.
	 */
	public Vector2f getStringSize(String text) {
		return getStringSize(text, -1, -1);
	}
	
	/**
	 * Berechnet die Groesse des Textes, wenn dieser mit dieser Font gerendert wuerde.
	 * Bei Ueberschreitung von 'maxWidth' wird eine neue Zeile angefangen.
	 * @param text Der Text, dessen Rendergroesse gefragt ist
	 * @param maxWidth Maximale Breite des Textes (-1 = kein Maximum)
	 * @return Rendergroesse des Textes
	 */
	public Vector2f getStringSize(String text, int maxWidth) {
		return getStringSize(text, maxWidth, -1);
	}
	
	/**
	 * Berechnet die Groesse des Textes, wenn er mit dieser Font gerendert wuerde.
	 * Bei Ueberschreitung von 'maxWidth' wird eine neue Zeile angefangen.
	 * Bei Ueberschreitung von 'maxHeight' wird abgebrochen;
	 * der Text wuerde dann auch nicht mehr gerendert.
	 * @param text Der Text, dessen Rendergroesse gefragt ist
	 * @param maxWidth Maximale Breite des Textes (-1 = kein Maximum)
	 * @param maxHeight Maximale Hoehe des Textes (-1 = kein Maximum)
	 * @return Rendergroesse des Textes
	 */
	public Vector2f getStringSize(String text, int maxWidth, int maxHeight) {
		Vector2f size = new Vector2f();
		
		float currentX = 0, currentY = 0;
		
		for(int i = 0; i < text.length(); i++) {
			FontChar fc = getChar((int)text.charAt(i));
			if(fc == null) continue;
			
			if(maxWidth > 0 && currentX + fc.width >= maxWidth) {
				currentX = 0;
				currentY += fontHeight;
				
				if(maxHeight > 0 && currentY + fontHeight >= maxHeight) break;
			}
			
			currentX += fc.xAdvance;
			
			if(currentX > size.x) size.x = currentX;
			if(currentY + fontHeight > size.y) size.y = currentY + fontHeight;
		}
		
		return size;
	}

}
