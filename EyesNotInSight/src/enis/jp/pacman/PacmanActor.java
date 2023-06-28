package penis.jp.pacman;

import penis.engine.*;
import penis.jp.pacman.Ghost.GhostMode;

/**
 * 
 * @author Saris, Jan-Philipp
 * @since 1.0
 * @date 16.06.2022
 *
 */
public abstract class PacmanActor extends Sprite {
	
	protected float speed = 48.f;
	protected PacManState state;
	protected int tileX, tileY;

	protected static final String[] directions = new String[] { "up", "down", "left", "right" };
	
	private boolean inMovement;
	protected int currentDirection;
	protected int nextX, nextY;
	protected float tweenTimer;
	
	protected boolean overridingAnims;

	public PacmanActor(PacManState _parent) {
		super(_parent);
		state = _parent;
		
		inMovement = false;
		tweenTimer = 0.f;
		
		currentDirection = 2;
		
		overridingAnims = false;
	}
	
	public void updateDeadPacman(float delta) {
		if(!(this instanceof PacMan)) return;
		
		super.update(delta);
	}
	
	@Override
	public void update(float delta) {
		if(!inMovement) {
			int dir = getDirection();
			
			if(dir < 0 || dir > 3) {
				if(!overridingAnims) play("idle", true);
				
				position.x = 8 * tileX - 4;
				position.y = 8 * tileY - 4;
				
				super.update(delta);
				
				return;
			} else {
				currentDirection = dir;
				inMovement = true;
				
				nextX = tileX;
				nextY = tileY;
				tweenTimer = 0.f;
				
				switch(currentDirection) {
				case 0:
					nextY--;
					break;
					
				case 1:
					nextY++;
					break;
					
				case 2:
					nextX--;
					break;
					
				case 3:
					nextX++;
					break;
					
				default:
					break;
				}
				
				if(state.getMap().isCollision(this, nextX, nextY)) {
					inMovement = false;
					if(!overridingAnims) {
						play("walk_" + directions[currentDirection], true);
						super.update(delta);
						stop();
					}
					
					return;
				}
			}
		}

		if(!overridingAnims) play("walk_" + directions[currentDirection], true);
		
		float timerLength = 8.f / speed;
		tweenTimer += delta;
		if(tweenTimer >= timerLength) {
			tileX = nextX;
			tileY = nextY;
			inMovement = false;
				
			int mw = state.getMap().getWidth();
			int mh = state.getMap().getHeight();
				
			if(tileX < 0) tileX += mw;
			if(tileX >= mw) tileX -= mw;
				
			if(tileY < 0) tileY += mh;
			if(tileY >= mh) tileY -= mh;
				
			position.x = 8 * tileX - 4;
			position.y = 8 * tileY - 4;
		} else {
			float interpX = tileX + ((float)(nextX - tileX) * tweenTimer/timerLength);
			float interpY = tileY + ((float)(nextY - tileY) * tweenTimer/timerLength);
				
			position.x = (int)(8 * interpX - 4);
			position.y = (int)(8 * interpY - 4);
		}
		
		super.update(delta);
	}
	
	protected abstract int getDirection();
	
	public int getX() {
		return tileX;
	}
	
	public int getY() {
		return tileY;
	}
	
	public void setTilePos(int x, int y) {
		tileX = x;
		tileY = y;
	}
	
	public int getViewDirection() {
		return currentDirection;
	}
	
	public void reset() {
		inMovement = false;
	}

}
