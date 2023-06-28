package penis.mathis;


import org.lwjgl.glfw.GLFW;

import penis.engine.*;

/**
 * 
 * @author johannes.seiters
 *
 */
public class WinP2 extends GameState {

	private BitmapFont font;
	
	private Texture WinP2Bild;
	private Texture winAnim, loseAnim;

	
	public WinP2(Game _game) {
		super(_game);
	}

	@Override
	protected void onLoad() {

	}

	@Override
	protected void onInit() {
		
	 

	 WinP2Bild = loadTexture ("WinP2Bild.png");
	 winAnim = loadTexture("SumoGruen.png");
	 loseAnim = loadTexture("SumoBlau.png");
	 
	 Sprite win = new Sprite(this);
	 win.setPosition(220, 210);
	 win.setSpriteSheet(winAnim, 64, 64);
	 win.addAnimation(new int[] {63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74}, 3, true, "win");
	 win.play("win");
	 
	 Sprite lose = new Sprite(this);
	 lose.setPosition(140, 210);
	 lose.setSpriteSheet(loseAnim, 64, 64);
	 lose.addAnimation(new int[] {81, 82, 83, 84, 85, 86, 87, 88, 89}, 3, true, "lose");
	 lose.play("lose");
	                                                                                                                                                                     
	}

	@Override
	protected void onUpdate(float delta) {
		// TODO Auto-generated method stub
		
		if (input.isDown(GLFW.GLFW_KEY_SPACE)){
			 
		    game.changeGameState("smash-intro");
			return;	
		}
		
	}
	
	
	// // if Anweisung zum dr�cken der Leertaste - r�ckf�hrung zu SmashIntro.
	

	@Override
	protected void onRender(Brush brush) {
		// TODO Auto-generated method stub
	brush.drawTexture(0, 0, WinP2Bild);
	   
	}
	
	// Einblenden der Grafik des Gewinnerbildschirms Spieler 2. 
	
	 
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getStateID() {
		// TODO Auto-generated method stub
		return "WinsP2";
	  
	    
	}

	@Override
	public String getStateDisplayName() {
		return "Sumo";
	}

}