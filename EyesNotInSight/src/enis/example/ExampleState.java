package penis.example;

import penis.engine.*;
import static org.lwjgl.glfw.GLFW.*;


/**
 * Durch die Annotation '@ApplyState' wird der GameState automatisch dem {@link MainMenu} hinzugef�gt.
 * 
 * @author Saris, Jan-Philipp
 * @date 30.05.2022
 */
//@ApplyState
public class ExampleState extends GameState {

	public ExampleState(Game _game) {
		super(_game);
	}
	
	@Override
	protected void onLoad() {
		//In dieser Funktion sollen vor der Initialisierung die im GameState ben�tigten
		//Ressourcen vorab geladen werden. Dadurch wird eine Mehrbelastung w�hrend des eigentlichen Spiels
		//zulasten der anf�nglichen Ladezeit vermieden.
		//Auch Input-Registry macht hier Sinn.
		
		//Diese Funktionsaufrufe sind notwendig, da die Input-Klasse aufgrund technischer Schwierigkeiten
		//nicht einfach so alle Tasten betrachten kann; die Struktur der Bibliothek LWJGL bzw. GLFW
		//erschwert dies ungemein.
		//Beim ersten Abfragen einer Taste wird diese aber notfalls registriert, falls nicht vorher geschehen
		//Dadurch w�ren aber die Tasteneingaben erst einen Frame sp�ter nutzbar,
		//die 'justPressed'- und 'justReleased'-Funktionen sogar noch einen Frame danach erst.
		input.registerInputButton(GLFW_KEY_W);
		input.registerInputButton(GLFW_KEY_A);
		input.registerInputButton(GLFW_KEY_S);
		input.registerInputButton(GLFW_KEY_D);
		input.registerInputButton(GLFW_KEY_X);
		input.registerInputButton(GLFW_KEY_Y);
		
		
		//Da diese Textur in diesem State f�r das 'ExampleSprite' ben�tigt wird,
		//macht es Sinn, auch f�r diese bereits hier einen Load-Call zu setzen,
		//denn die interne Verwaltung beh�lt die Ressourcen dauerhaft geladen,
		//sodass ein Mehrfachladen vermieden werden kann.
		loadTexture("star.png");
	}

	@Override
	protected void onInit() {
		//In dieser Funktion soll die gesamte Initialisierung geschehen (mit Ausnahme von Ressourcen-Laden).
		//Dazu geh�ren zum Beispiel das Laden von Ressourcen und
		//die Erzeugung der n�tigen Objekte.
		
		//Die Klasse ExampleSprite erbt von penis.engine.Sprite
		//Diese f�gt sich selbst der internen Verwaltung des GameStates's hinzu,
		//sodass weitere 'update'- und 'draw'-Aufrufe nicht n�tig sind.
		new ExampleSprite(this).setPosition(200, 200);
	}

	@Override
	protected void onUpdate(float delta) {
		//In dieser Funktion sollen alle update-Funktionalit�ten implementiert werden,
		//sofern diese nicht bereits durch die automatisierte 'update'-Funktion des GameState's abgedeckt werden.
		//Der �bergabeparameter 'delta' ist die Zeit in Sekunden, die seit dem letzten Frame vergangen ist.
	}

	@Override
	protected void onRender(Brush brush) {
		//In dieser Funktion findet das Rendering statt.
		//Objekte, die bereits der internen Verwaltung angeh�ren, werden automatisch gerendert,
		//sodass nur wenige Render-Aufgabe �brigbleiben.
		//Der �bergabeparameter 'brush' ist eine Instanz einer Hilfsklasse,
		//die einige grundlegende Render-Funktionalit�ten bereitstellt.
	}

	@Override
	protected void onDestroy() {
		//Diese Funktion dient lediglich dazu, den GameState aufzur�umen.
		//Dazu reicht es im Regelfall abgespeicherte Variablen zur�ckzusetzen;
		//wenn beispielsweise eine Textur geladen wurde, sollte diese Variable hier auf 'null' gesetzt werden.
	}

	@Override
	public String getStateID() {
		//Diese Funktion soll lediglich den internen Namen des GameState's zur�ckgeben.
		//Dieser Name dient in der �bergeordneten Verwaltung zur Kennzeichnung.
		//Innerhalb des GameState's erleichtert er 'load'-Aufrufe.
		//Mit der Engine-Klasse 'Loader' m�sste der komplette Dateipfad ab dem 'res'-Ordner angeben werden,
		//also z. B. "res/example/star.png"; durch diese Vereinfachung reicht als Dateipfad jedoch der Pfad
		//innerhalb von "res/example/", hier also "star.png".
		//Daf�r muss im 'res'-Ordner zwingend ein eigener Unterordner mit exakt diesem Namen, der hier
		//zur�ckgegeben wird, angelegt werden (s. "res/example/").
		return "example";
	}

	@Override
	public String getStateDisplayName() {
		//Mit dem String, der von dieser Funktion zur�ckgegeben wird, soll der GameState im Hauptmen� in der Auswahl angezeigt werden.
		//So bietet es sich an, einen Namen zu w�hlen, der das Spiel passend benennt,
		//z. B. "Pacman!, "Undertale" oder "Super Mario Bros."...
		return "Beispiel";
	}

}
