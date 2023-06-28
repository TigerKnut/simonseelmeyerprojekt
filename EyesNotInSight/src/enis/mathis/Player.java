package penis.mathis;

import penis.engine.*;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;


public class Player extends Sprite {

	private float speed = 60;
	private int walk1, walk2;
	private boolean touching;
	private Rectangle hitbox;
	private Player other;
	private int clickcount;
	private float clickTimer;
	private boolean p1;
	private Konflikt konflikt;
	
	public Player(GameState _parent, boolean _p1, Konflikt k) {
		super(_parent);
		p1 = _p1;
		konflikt = k;
		if(p1) {
			walk1 = GLFW.GLFW_KEY_D;
			position.x = 35;
			position.y = 150;
			hitbox = new Rectangle(10, 34, 32, 40);
			setXFlip(true);
			
		}else {
			walk2 = GLFW.GLFW_KEY_LEFT;
			position.x = 295;
			position.y = 150;
			hitbox = new Rectangle(332, 34, 32, 40);
		}
		
		addAnimation(new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 10, true, "walking");
		addAnimation(new int[] {18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44}, 10, true, "idle");
		addAnimation(new int[] {45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58}, 10, false, "prepare");
		
		play("prepare", true);
		
		if (currentFrame == 58) {
			
			play("idle",true);
			
		}

		// TODO Auto-generated constructor stub
	}

	public void clickUpdate () {
		
		Input input = parent.getInput();
		
		if(touching && (input.justPressed(walk1) || input.justPressed(walk2))) {
			
			clickcount++;
			
		}
	}
	
	@Override
	public void update (float delta) {
		
		clickTimer += delta;
		
		
		if(clickTimer >= 1) {
			clickcount = 0;
			clickTimer = 0;
		}
		
		if(hitbox.intersects(other.getHitbox())) {
			touching = true;
			
			setPaused(true);
			setHidden(true);
			
			other.setPaused(true);
			other.setHidden(true);
			
			konflikt.setPaused(false);
			konflikt.setHidden(false);
			
			konflikt.setPosition(position.x + 23, position.y);
			konflikt.play("konflikt");
			
		}
		
		Input input = parent.getInput();
		
		float dx = 0;
		
		if (input.isDown(walk2) && !touching) {
			dx -= 1;
			play("walking", true);
		}
		
		if (input.isDown(walk1) && !touching) {
			dx += 1;
			play("walking", true);
		}
		
		if(dx == 0) {
			
			play("idle", true);
			
		}
		
		if(touching) {
			
			int diff = clickcount - other.getClickcount();
			
			speed = diff * 4;
			
			dx = walk1 == 0 ? -1 : 1;
			
		}
		
		position.x += dx * speed * delta;
		hitbox.setX((int) position.x + (p1 ? 32 : 0));
		
		super.update(delta);
	}
	
	@Override
	public void draw (Brush brush) {
		
		super.draw(brush);
		
		//brush.drawRect(hitbox.getX(), hitbox.getY(), hitbox.getX()+hitbox.getWidth(), hitbox.getY()+hitbox.getHeight(), 1, 1, 1, 1);
	
	}
	
	public void setOther (Player _other) {
		
		other = _other;
		
	}
	
	public Rectangle getHitbox() {
		
		return hitbox;
		
	}
	
	public int getClickcount() {
		
		return clickcount;
		
	}
	
	public float getPositionX() {
		
		return position.x;
	}
	
	public float getPositionY() {
		
		return position.y;
	}
}

