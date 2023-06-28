package penis.example;

import penis.engine.*;

/**
 * @author Saris, Jan-Philipp
 * @date 30.05.2022
 */
public class ExampleMain {
	
	public static void main(String[] args) {
		//Erzeugt ein Game, das intern zudem ein Fenster mit der gegebenen Breite und Höhe (Parameter Nr. 1 & 2)
		//und dem Fenstertitel (Parameter Nr. 3) erstellt.
		Game game = new Game(720, 480, "Example");
		
		//Weist dem Game seinen ersten GameState zu, in den es übergehen wird.
		//Zudem besteht von überall aus die Möglichkeit, jederzeit in diesen Grundzustand zurückzukehren
		//durch die Funktion 'changeGameState()' (nur empfohlen, wenn der aktuelle GameState
		//sein Arbeit beendet hat und beispielsweise ins Hauptmenü zurückgekehrt werden soll).
		game.setDefaultGameState(new ExampleState(game));
		
		//Startet die eigentliche Game-Verwaltung in einem eigenen Thread
		game.start();
	}

}
