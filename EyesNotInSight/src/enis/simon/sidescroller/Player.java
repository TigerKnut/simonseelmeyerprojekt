package penis.simon.sidescroller;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import penis.engine.*;

/*
@author: Seelmeyer, Simon
@date: 
*/

public class Player extends Image{
	
	private float normspeed = 150;				//das normale bewegungstempo des spielers
	private float speed = normspeed;				//die momentane bewegungsgeschwindigkeit
	private float upspeed = 0;				//die bewegungsgeschwindigkeit nach oben und unten
	private float jumptimer = 0;				//legt fest, nach welcher zeit man wieder springen kann
	private float indashtimer = 0;				//legt fest wie lange der dash andauert
	private float dashtimer = 0;				//legt fest, nach welcher zeit man wieder dashen kann
	private float hittimer = 0.5f;				//legt fest, nach welcher zeit man wieder schlagen kann
	private float inhittimer = 0;				//legt fest, wie lange der schlag andauert
	
	Texture player;				//textur des spielers
	Texture playerattack;				//textur beim ausholen des schlags des spielers
	
	Rectangle ph = new Rectangle((int)position.x, (int)position.y, 10, 25);				//trefferbox, wo der spieler verletzt wird
	Rectangle pd = new Rectangle ((int)position.x + 10, (int)position.y, 5, 10);				//trefferbox, wo der spieler den gegner verletzt
	
	
	public Player(GameState _parent) {
		
		super(_parent);
		
		player = parent.loadTexture("Grafik/Spieler 1.1.png");
		position.x = 200;
		position.y = 490;
		playerattack = parent.loadTexture("Grafik/Player 1.1 schwert hoch.png");
		
	}
	

	@Override
	public void update(float delta) {
		Input input = parent.getInput();
		
		Vector2f movement = new Vector2f();
		
		movement.y -= upspeed;
		
		if (input.isDown(GLFW_KEY_A)) {
			movement.x -= 1;
			
		}
		
		if (input.isDown(GLFW_KEY_D)) {
			movement.x += 1;
		}
		
		if (input.isDown(GLFW_KEY_SPACE) && jumptimer <= 0) {
			jumptimer = 1;
			upspeed = 5;
			
		}
		
		if (position.y >= Level.ground && upspeed < 0) {
			upspeed = 0;
		}
		
		if (input.isDown(GLFW_KEY_LEFT_SHIFT) && dashtimer <= 0) {
			dashtimer = 0.5f;
			indashtimer = 0.1f;
			speed += 1500;
			
		}
		
		if (indashtimer <= 0) {
			speed = normspeed;
		}
		
		if (input.isDown(GLFW_KEY_ENTER)) {
		
			if (hittimer <= 0) {
				setTexture(playerattack);
				inhittimer = 0.5f;
				pd.setY((int)position.y + 20);
				pd.setHeight(10);
				pd.setWidth(20);
				hittimer = 0.7f;
			}
			
		}
		
		if (inhittimer <= 0) {
			setTexture(player);
		}
		
		if(movement.x != 0 || movement.y != 0) movement.normalise();
		movement.scale(speed * delta);
		
		if (position.y < Level.ground) {
			upspeed -= 9*delta;
		}
		
		position.x += movement.x;
		position.y += movement.y;
		
		if (hittimer <= 0) {
			pd.setY((int)position.y + 10);
			pd.setHeight(10);
			pd.setWidth(5);
		}
		
		ph.setX((int)position.x + 10);
		ph.setY((int)position.y);
		pd.setX((int)position.x + 20);
		pd.setY((int)position.y);
		
		jumptimer -= delta;
		indashtimer -= delta;
		dashtimer -= delta;
		hittimer -= delta;
		inhittimer -= delta;
		
		SideScroller.px = position.x;
		SideScroller.py = position.y;
		
	}
	
	
	@Override
	public void draw(Brush brush) {
		super.draw(brush);
		
		//brush.drawRect(ph.getX(), ph.getY(), ph.getX() + ph.getWidth(), ph.getY() + ph.getHeight(), 1, 1, 1, 1);
		//brush.drawRect(pd.getX(), pd.getY(), pd.getX() + pd.getWidth(), pd.getY() + pd.getHeight(), 1, 1, 1, 1);
		
	}
	
}