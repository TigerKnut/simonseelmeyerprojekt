package penis.enrique.undertale;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;
import penis.engine.*;

public class Player extends Image {

	private float speed = 240;
	public Rectangle r = new Rectangle(0, 0, 22, 22);
	int health = 99;

	public Player(GameState _parent) {
		super(_parent);

		setTexture(parent.loadTexture("Sprites/Herz (cyan).png"));
		position.x = 609;
		position.y = 380;
	}
	
	@Override
	public void update(float delta) {
		Input input = parent.getInput();
		
		Vector2f movement = new Vector2f();
		
		if (input.isDown(GLFW.GLFW_KEY_UP)) {
			movement.y -= 1;
		}
		if (input.isDown(GLFW.GLFW_KEY_DOWN)) {
			movement.y += 1;
		}
		if (input.isDown(GLFW.GLFW_KEY_LEFT)) {
			movement.x -= 1;
		}
		if (input.isDown(GLFW.GLFW_KEY_RIGHT)) {
			movement.x += 1;
		}
		
		if(position.y <= 267 && movement.y < 0) {
			movement.y = 0; 
		}	
			
		if(position.y >= 493 && movement.y > 0) {
			movement.y = 0;
		}		
		
		if(position.x <= 495 && movement.x < 0) {
			movement.x = 0;
		}	
		
		if(position.x >= 723 && movement.x > 0) {
			movement.x = 0;	
			
		}
		
		if(movement.x != 0 || movement.y != 0) movement.normalise();
		movement.scale(speed * delta);
		
		position.x += movement.x;
		position.y += movement.y;
		
		r.setX((int) position.x + 20);
		r.setY((int) position.y + 14);
	}
		
	@Override	
	public void draw(Brush brush) {
		super.draw(brush);
		
		brush.drawRect(r.getX(), r.getY(), r.getX() + r.getWidth(), r.getY() + r.getHeight(), 1, 1, 1, 1);
	}
	
	
	
    public void Hitbox() {
		
	    System.out.println(r.intersects(new Rectangle (0, 0, 32, 32)));
	    
	}
    
    public void Health() {
       
      /*if (r.intersects(s) == true) {
    	
    	  health -= 10;
    	  
      }*/
    		
    }
    
}
