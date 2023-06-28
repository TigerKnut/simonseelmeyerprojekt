package penis.example;

import penis.engine.*;

/**
 * @author Saris, Jan-Philipp
 * @date 30.05.2022
 */
public class ExampleMain {
	
	public static void main(String[] args) {
		//Erzeugt ein Game, das intern zudem ein Fenster mit der gegebenen Breite und H�he (Parameter Nr. 1 & 2)
		//und dem Fenstertitel (Parameter Nr. 3) erstellt.
		Game game = new Game(720, 480, "Example");
		
		//Weist dem Game seinen ersten GameState zu, in den es �bergehen wird.
		//Zudem besteht von �berall aus die M�glichkeit, jederzeit in diesen Grundzustand zur�ckzukehren
		//durch die Funktion 'changeGameState()' (nur empfohlen, wenn der aktuelle GameState
		//sein Arbeit beendet hat und beispielsweise ins Hauptmen� zur�ckgekehrt werden soll).
		game.setDefaultGameState(new ExampleState(game));
		
		//Startet die eigentliche Game-Verwaltung in einem eigenen Thread
		game.start();
	}

}
