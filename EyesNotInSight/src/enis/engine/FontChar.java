package penis.engine;

/**
 * Enthaelt Informationen, die innerhalb einer Textur den Ausschnitt für einen Char definiert.
 * 
 * @author Saris, Jan-Philipp
 * @since 1.2
 * @date 09.06.2022
 *
 */
public class FontChar {
	
	public int texX, texY, width, height, xOffset, yOffset, xAdvance;
	
	public FontChar(int _texX, int _texY, int _width, int _height, int _xOffset, int _yOffset, int _xAdvance) {
		texX = _texX;
		texY = _texY;
		width = _width;
		height = _height;
		xOffset = _xOffset;
		yOffset = _yOffset;
		xAdvance = _xAdvance;
	}

}
