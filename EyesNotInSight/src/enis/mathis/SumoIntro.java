package penis.mathis;

import org.lwjgl.glfw.GLFW;

import penis.engine.*;

/** 
 * 
 * @author johannes.seiters
 *
 */

@ApplyState
public class SumoIntro extends GameState {

	private BitmapFont font;
	
	private Texture Startbild;

	
	public SumoIntro(Game _game) {
		super(_game);
	
		// 
	}

	@Override
	protected void onLoad() {
	
		game.addGameState(new WinP2(game));
		game.addGameState(new WinP1(game));
		game.addGameState(new Sumo(game));
	}

	@Override
	protected void onInit() {
		
	 font = loadBitmapFont ("Alagard.fnt" , "Alagard.png");

	 Startbild = loadTexture ("Startbildschirm.png");
	                                                                                                                                                                     
	}

	@Override
	protected void onUpdate(float delta) {
		// TODO Auto-generated method stub
		
		if (input.isDown(GLFW.GLFW_KEY_D )&& input.isDown(GLFW.GLFW_KEY_LEFT)) {
			
		    game.changeGameState("Sumo");
			
		    return;	
		}
		
	}
	
	
	// if Anweisung zum Starten des Spiels, durch dr�cken und halten der Taste D und der linken Pfeiltaste
	

	@Override
	protected void onRender(Brush brush) {
		// TODO Auto-generated method stub
	brush.drawTexture(0, 0, Startbild);
	   
	}
	
	// Einblenden der Grafik des Starbildschirms 
	
	 
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getStateID() {
		// TODO Auto-generated method stub
		return "smash-intro";
	  
	    
	}

	@Override
	public String getStateDisplayName() {
		return "Sumo";
	}

}
  

