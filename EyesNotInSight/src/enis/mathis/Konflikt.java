package penis.mathis;

import org.lwjgl.glfw.GLFW;

import penis.engine.*;

public class Konflikt extends Sprite{

	private float speed = 60;
	private int cnt = 0;
	private float clicktimer;
	private Player player;
	private boolean firstPosition;
	
	public Konflikt(GameState _parent) {
		super(_parent);
		
		setPaused(true);
		
		setPosition(150, 200);
		setSpriteSheet("SumoKonflikt.png", 128, 64);
		setXFlip(true);
		addAnimation(new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 5, true, "konflikt");
		
		// TODO Auto-generated constructor stub
	}
	
	public void update (float delta) {
		if (!firstPosition) {
			setPosition(player.getPositionX() - 17, player.getPositionY());
			firstPosition = true;
		}
		
		Input input = parent.getInput();
		
		if(input.justPressed(GLFW.GLFW_KEY_D)) {
			cnt += 1;
		}

		if(input.justPressed(GLFW.GLFW_KEY_LEFT)) {
			cnt -= 1;
		}
		
		speed = cnt * 4;
		
		position.x += speed * delta;
		
		clicktimer += delta;
		
		if(clicktimer >= 1) {
			cnt = 0;
			clicktimer = 0;
		}
		
		if (position.x <= 20) {
			
			((Sumo) parent).getGame().changeGameState("WinsP1");
			
		}
		
		if (position.x >= 220) {
			
			((Sumo) parent).getGame().changeGameState("WinsP2");
			
		}
		
		super.update(delta);

	}
	
	public void setPlayer (Player p) {
		
		player = p;
		
	}
	
}
