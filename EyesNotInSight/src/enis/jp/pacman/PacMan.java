package penis.jp.pacman;

import penis.engine.*;
import penis.jp.pacman.Ghost.GhostMode;

import static org.lwjgl.glfw.GLFW.*;

/**
 * 
 * @author Saris, Jan-Philipp
 * @since 1.0
 * @date 13.06.2022
 */
public class PacMan extends PacmanActor {
	
	private int[] dirBuffer;
	

	public PacMan(PacManState _parent) {
		super(_parent);
		
		setPosition(50, 50);
		
		setSpriteSheet("sprites.png", 16, 16);
		addAnimation(new int[] { 8, 9, 2, 9 }, 16, true, "walk_up");
		addAnimation(new int[] { 3, 4, 2, 4 }, 16, true, "walk_down");
		addAnimation(new int[] { 5, 6, 2, 6 }, 16, true, "walk_left");
		addAnimation(new int[] { 0, 1, 2, 1 }, 16, true, "walk_right");
		addAnimation(new int[] {2}, 1, false, "idle");
		addAnimation(new int[] { 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26 }, 6, false, "die");
		
		play("idle");
		
		dirBuffer = new int[] { -1, -1, -1, -1 };
		
		tileX = 1;
		tileY = 1;
	}
	
	public void updateDead(float delta) {
		super.updateDeadPacman(delta);
	}
	
	@Override
	public void update(float delta) {
		Input input = parent.getInput();
		
		
		if(input.isDown(GLFW_KEY_W)) {
			enqueueDir(0);
		} else {
			removeDir(0);
		}

		if(input.isDown(GLFW_KEY_S)) {
			enqueueDir(1);
		} else {
			removeDir(1);
		}

		if(input.isDown(GLFW_KEY_A)) {
			enqueueDir(2);
		} else {
			removeDir(2);
		}

		if(input.isDown(GLFW_KEY_D)) {
			enqueueDir(3);
		} else {
			removeDir(3);
		}
		
		/*if(dirBuffer[0] == -1) {
			//play("idle");
		} else {
			play("walk_" + directions[dirBuffer[0]], true);
		}
		
		switch(dirBuffer[0]) {
		case 0:
			tileY -= 1; //position.y -= speed * delta;
			break;
			
		case 1:
			tileY += 1; //position.y += speed * delta;
			break;
			
		case 2:
			tileX -= 1; //position.x -= speed * delta;
			break;
			
		case 3:
			tileX += 1; //position.x += speed * delta;
			break;
			
		default:
			break;
		}
		
		int mw = state.getMap().getWidth();
		int mh = state.getMap().getHeight();
		
		if(tileX < 0) tileX += mw;
		if(tileX >= mw) tileX -= mw;
		
		if(tileY < 0) tileY += mh;
		if(tileY >= mh) tileY -= mh;
		
		position.x = 8 * tileX - 4;
		position.y = 8 * tileY - 4;*/
		
		super.update(delta);
		
		state.getMap().setPacman(tileX, tileY);
	}
	
	private void enqueueDir(int dir) {
		if(dir < 0 || dir >= directions.length) return;
		
		for(int i = 0; i < dirBuffer.length; i++) {
			if(dirBuffer[i] == dir) return;
			
			if(dirBuffer[i] == -1) {
				dirBuffer[i] = dir;
				return;
			}
		}
	}
	
	private void removeDir(int dir) {
		boolean found = false;
		
		for(int i = 0; i < dirBuffer.length; i++) {
			if(found) {
				dirBuffer[i - 1] = dirBuffer[i];
				dirBuffer[i] = -1;
			}
			
			if(dirBuffer[i] == dir) {
				dirBuffer[i] = -1;
				found = true;
			}
		}
	}
	
	public boolean dies(Ghost[] ghosts) {
		for(Ghost g : ghosts) {
			if(g.getMode() != GhostMode.CHASE && g.getMode() != GhostMode.SCATTER) continue;
			
			if(tileX == g.getX() && tileY == g.getY()) return true;
		}
		
		return false;
	}

	@Override
	protected int getDirection() {
		return dirBuffer[0];
	}
	
	@Override
	public void reset() {
		super.reset();
		play("idle");
	}

}
