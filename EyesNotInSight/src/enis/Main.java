package penis;

import penis.engine.*;

/**
 * @author Saris, Jan-Philipp
 * @date 30.05.2022
 */
public class Main {
	
	public static void main(String[] args) {
		Game game = new Game(400, 400, "Spielesammlung");
		game.setDefaultGameState(new MainMenu(game));
		game.start();
	}

}
