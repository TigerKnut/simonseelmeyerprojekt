package penis.engine;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Verwaltet alle in den einzelnen GameState's genutzten Ressourcen.
 * Vermeidet zudem ein mehrfaches Laden von Daten durch internes Abspeichern.
 * 
 * @author Saris, Jan-Philipp
 * @since 1.0
 * @date 30.05.2022
 */
public class Loader {
	
	/**
	 * Stellt eine Ressource dar. Die Arten von Ressourcen, die der Loader bereitstellen kann, ist durch
	 * die Werte von 'ResourceType' begrenzt.
	 * 
	 * @author Saris, Jan-Philipp
	 * @since 1.0
	 * @date 30.05.2022
	 */
	private static class Resource {
		
		public enum ResourceType {
			RT_NONE,
			RT_TEXTURE,
			RT_TEXT,
			RT_BITMAPFONT
		}
		
		public ResourceType type;
		public Object resource;
		public String id;
		
		public Resource(Object res, ResourceType _type, String _id) {
			resource = res;
			_type = type;
			id = _id;
		}
		
	}
	
	private static class StringResource {
		
		public String text;
		public String[] lines;
		
		public StringResource(String _text, String[] _lines) {
			text = _text;
			lines = _lines;
		}
		
	}
	
	private List<Resource> resources;
	
	/**
	 * Setzt die interne Datenstruktur zum Verwalten der Ressourcen auf.
	 */
	public Loader() {
		resources = new ArrayList<>();
	}
	
	/**
	 * Laedt die Textur am angegebenen Dateipfad oder, falls diese bereits einmal geladen wurde,
	 * wird sie aus dem internen Speicher zurueckgegeben (vermeidet mehrfaches Laden und unnoetige Speichernutzung).
	 * @param filename Vollstaendiger Dateipfad (innerhalb von Eclipse reicht der Pfad ab Projektverzeichnis)
	 * @return Die Textur am angegebenen Dateipfad
	 */
	public Texture loadTexture(String filename) {
		for(Resource r : resources) {
			if(r.type == Resource.ResourceType.RT_TEXTURE && r.id.equals(filename)) {
				return (Texture)r.resource;
			}
		}
		
		Texture tex = new Texture(filename);
		
		resources.add(new Resource(tex, Resource.ResourceType.RT_TEXTURE, filename));
		
		return tex;
	}
	
	/**
	 * Laedt die Datei am angegebenen Dateipfad als Text oder, falls diese bereits einmal geladen wurde,
	 * wird ihr Inhalt aus dem internen Speicher zurueckgegeben (vermeidet mehrfaches Laden und unnoetige Speichernutzung).
	 * @param filename Vollstaendiger Dateipfad (innerhalb von Eclipse reicht der Pfad ab Projektverzeichnis)
	 * @return Den Inhalt der Datei am angegebenen Dateipfad als Text
	 * @since 1.1
	 * @date 02.06.2022
	 */
	public String loadText(String filename) {
		for(Resource r : resources) {
			if(r.type == Resource.ResourceType.RT_TEXT && r.id.equals(filename)) {
				return ((StringResource)r.resource).text;
			}
		}
		
		String result = "";
		ArrayList<String> lines = new ArrayList<>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			
			while(line != null) {
				result += line + "\n";
				lines.add(line);
			}
			
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String[] linesArray = lines.toArray(new String[lines.size()]);
		
		resources.add(new Resource(new StringResource(result, linesArray), Resource.ResourceType.RT_TEXT, filename));
		
		return result;
	}
	
	/**
	 * Laedt die Datei am angegebenen Dateipfad als Array von Strings oder, falls diese bereits einmal geladen wurde,
	 * wird ihr Inhalt aus dem internen Speicher zurueckgegeben (vermeidet mehrfaches Laden und unnoetige Speichernutzung).
	 * @param filename Vollstaendiger Dateipfad (innerhalb von Eclipse reicht der Pfad ab Projektverzeichnis)
	 * @return Inhalt der Datei am angegebenen Dateipfad als Array, das die einzelnen Zeilen als Strings enthält.
	 * @since 1.2
	 * @date 09.06.2022
	 */
	public String[] loadTextLines(String filename) {
		for(Resource r : resources) {
			if(r.type == Resource.ResourceType.RT_TEXT && r.id.equals(filename)) {
				return ((StringResource)r.resource).lines;
			}
		}
		
		String result = "";
		ArrayList<String> lines = new ArrayList<>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			
			while(line != null) {
				result += line + System.lineSeparator();
				lines.add(line);
				line = reader.readLine();
			}
			
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String[] linesArray = lines.toArray(new String[lines.size()]);
		
		resources.add(new Resource(new StringResource(result, linesArray), Resource.ResourceType.RT_TEXT, filename));
		
		return linesArray;
	}
	
	/**
	 * Laedt eine Bitmap-Font bestehend aus einer Bilddatei sowie einer Textdatei, in der im fnt-Format
	 * die Textur-UV-Informationen ueber die einzelne Chars enthalten sind.
	 * @since 1.2
	 * @date 09.06.2022
	 * @param fntFilename Vollstaendiger Dateipfad der fnt-Datei (innerhalb von Eclipse reicht der Pfad ab Projektverzeichnis)
	 * @param texFilename Vollstaendiger Dateipfad der Bilddatei (innerhalb von Eclipse reicht der Pfad ab Projektverzeichnis)
	 * @return
	 */
	public BitmapFont loadBitmapFont(String fntFilename, String texFilename) {
		String resId = fntFilename + texFilename;
		for(Resource r : resources) {
			if(r.type == Resource.ResourceType.RT_TEXT && r.id.equals(resId)) {
				return (BitmapFont)r.resource;
			}
		}
		
		BitmapFont font = new BitmapFont(this, fntFilename, texFilename);
		
		resources.add(new Resource(font, Resource.ResourceType.RT_BITMAPFONT, resId));
		
		return font;
	}

}
