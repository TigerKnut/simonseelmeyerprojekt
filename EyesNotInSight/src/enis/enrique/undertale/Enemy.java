package penis.enrique.undertale;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;
import penis.engine.*;

public class Enemy extends Image {

	public Rectangle s = new Rectangle(0, 0, 24, 40);
	private float speed = 0.25f;
	
	
	public Enemy(GameState _parent) {
        super(_parent);
		
        setTexture(parent.loadTexture("Sprites/Chara Schwert.png"));
        position.x = 500;
        position.y = 525;
       
	}
	
	@Override
	public void update(float delta) {
		
		position.y -= speed;
		
		
		s.setX((int) position.x + 22);
	    s.setY((int) position.y + 24);
	}
    
	@Override
	public void draw(Brush brush) {
		super.draw(brush);
		
		brush.drawRect(s.getX(), s.getY(), s.getX() + s.getWidth(), s.getY() + s.getHeight(), 1, 1, 1, 1);
	}
	
	public void EnemyHitbox() {
		
		System.out.println(s.intersects(new Rectangle (0, 0, 32, 32)));
	}

}

