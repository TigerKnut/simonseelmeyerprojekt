package penis.simon.sidescroller;

import org.lwjgl.util.Rectangle;
import penis.engine.*;

/*
@author: Seelmeyer, Simon
@date: 
*/

public class Enemy extends Image{
	
	private float speed = 0;				//bewegungsgeschwindigkeit des gegners
	private float hittimer = 0;				//legt fest, ab wann der gegner wieder schlagen kann
	private float inhittimer = 0;				//legt fest, wie lange der schlag andauert
	
	Rectangle eh = new Rectangle((int)position.x + 50, (int)position.y, 10, 25);				//hitbox, wo der gegner getroffen werden kann
	Rectangle ed = new Rectangle ((int)position.x + 10, (int)position.y, 5, 10);				//hitbox, wo der gegner den spieler trifft
	
	public Enemy (GameState _parent) {
		
		super(_parent);
		
		setTexture(parent.loadTexture("Grafik/Spieler 1.0.png"));
		position.x = 800;
		position.y = 500;
		eh.setX((int)position.x);
		eh.setY((int)position.y);
		ed.setX((int)position.x);
		ed.setY((int)position.y);
		
	}
	
	@Override
	public void update(float delta) {
		
		if (SideScroller.px < position.x) {
			speed = 1.2f;
		}
		
		if (SideScroller.px > position.x) {
			speed = -1.2f;
		}
		
		if (position.x - SideScroller.px <= 100) {
			
			if (hittimer <= 0) {
				
				speed = 0.7f;
				ed.setX((int)position.x - 20);
				ed.setY((int)position.y + 20);
				ed.setHeight(10);
				ed.setWidth(50);
				hittimer = 1;
				inhittimer = 0.7f;
			}	
		}
		
		if (inhittimer <= 0) {
			ed.setY((int)position.y + 10);
			ed.setHeight(10);
			ed.setWidth(5);
		}
		
		position.x -= speed;
		
		eh.setX((int)position.x + 10);
		eh.setY((int)position.y);
		
		if (hittimer > 0) {
			ed.setX((int)position.x + 20);
		}
		
		ed.setY((int)position.y);
		
		hittimer -= delta;
		inhittimer -= delta;
	}
	
	@Override
	public void draw(Brush brush) {
		super.draw(brush);
		
		//brush.drawRect(eh.getX(), eh.getY(), eh.getX() + eh.getWidth(), eh.getY() + eh.getHeight(), 1, 1, 1, 1);
		//brush.drawRect(ed.getX(), ed.getY(), ed.getX() + ed.getWidth(), ed.getY() + ed.getHeight(), 1, 1, 1, 1);
		
	}
	
}