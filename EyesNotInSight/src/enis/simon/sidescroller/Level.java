package penis.simon.sidescroller;

import penis.engine.*;

/*
@author: Seelmeyer, Simon
@date: 
*/

public class Level extends Image{
	
	Texture topcliff;				//textur obere klippe
	Texture wave;			//textur welle
	Texture bottomcliff;				//textur untere klippe
	Texture ocean;				//textur ozean
	Texture sky;				//textur himmel
	
	public static final float ground = 500;				//boden wird auf bestimmten wert gesetzt
	
	public Level (GameState _parent) {
		
		super(_parent);
		
		topcliff = parent.loadTexture("Grafik/Topcliff 1.0.png");
		
		bottomcliff = parent.loadTexture("Grafik/Bottomcliff 1.0.png");
		
		wave = parent.loadTexture("Grafik/Welle 1.1.png");
		
		ocean = parent.loadTexture("Grafik/Ocean 1.0.png");
		
		sky = parent.loadTexture("Grafik/Sky 1.0.png");
		
	}

	@Override
	public void draw(Brush brush) {
		
		float bx = 0;
		float by = 562;
		
		float tx = 0;
		float ty = 530;
		
		float wx = 0;
		float wy = 280;
		
		float ox = 0;
		float oy = 288;
		
		float sx = 0;
		float sy = 0;
		
		float vz = 32;
		
		
		setTexture(ocean);
		position.x = ox;
		position.y = oy;
		
		for (int j = 0; j <= 15; j++) {
			
			for (int i = 0; i < 1185; i+= 32) {
				position.x = ox + i;
				super.draw(brush);
			}
			position.x = ox;
			oy += 32;
			position.y = oy;
		}
		
		
		setTexture(sky);
		position.x = sx;
		position.y = sy;
			
		for (int j = 0; j <= 8; j++) {
			
			for (int i = 0; i < 1185; i+= 32) {
				position.x = sx + i;
				super.draw(brush);
			}
			position.x = sx;
			sy += 32;
			position.y = sy;
		}
		
		
		setTexture(wave);
		position.x = wx;
		position.y = wy;
		
		for (int j = 0; j <= 15; j++) {
			
			for (int i = 0; i < 1500; i+= 32) {
				position.x = wx + i;
				super.draw(brush);
			}
			position.x = wx -= 16;
			wy += 25;
			position.y = wy;
			
			if (vz == 16) {
				vz += 16;
			} else {
				vz -= 16;
			}
		}
		
		
		setTexture(topcliff);
		position.x = tx;
		position.y = ty;
		
		for (int i = 0; i < 1185; i+= 32) {
			position.x = tx + i;
			super.draw(brush);
		}
		
		
		setTexture(bottomcliff);
		position.x = bx;
		position.y = by;
		
		for (int j = 0; j <= 5; j++) {
			
			for (int i = 0; i < 1185; i+= 32) {
				position.x = bx + i;
				super.draw(brush);
			}
			position.x = bx;
			by += 32;
			position.y = by;
		}
		//position.x = bx;
		//position.y = by;
	}
	
}