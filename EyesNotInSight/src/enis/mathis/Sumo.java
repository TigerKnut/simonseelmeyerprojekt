package penis.mathis;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import org.lwjgl.glfw.GLFW;

import penis.engine.*;


public class Sumo extends GameState {

	private Konflikt konflikt;
	private Player p1, p2;
	
	public Sumo(Game _game) {
		super(_game);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		input.registerInputButton(GLFW_KEY_W);
		input.registerInputButton(GLFW_KEY_A);
		input.registerInputButton(GLFW_KEY_S);
		input.registerInputButton(GLFW_KEY_D);
	}

	@Override
	protected void onInit() {
		// TODO Auto-generated method stub
		
		new Image(this, loadTexture("Arena.PNG"));
		
		konflikt = new Konflikt(this);
		konflikt.setPaused(true);
		konflikt.setHidden(true);
		p1 = new Player(this, true, konflikt);
		p2 = new Player(this, false, konflikt);
		p1.setSpriteSheet("SumoGruen.png", 64, 64);
		p2.setSpriteSheet("SumoBlau.png", 64, 64);
		p1.setOther(p2);
		p2.setOther(p1);
		konflikt.setPlayer(p1);
	}

	@Override
	protected void onUpdate(float delta) {
		// TODO Auto-generated method stub
		
		konflikt.setPaused(!p1.isPaused());
		
		//p1.clickUpdate();
		//p2.clickUpdate();
		
	}

	@Override
	protected void onRender(Brush brush) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		konflikt = null;
		p1 = null;
		p2 = null;
	}

	@Override
	public String getStateID() {
		// TODO Auto-generated method stub
		return "Sumo";
	}

	@Override
	public String getStateDisplayName() {
		return "Sumo";
	}
	
	public Game getGame() {
		return game;
	}

}
