package penis.jp.pacman;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import penis.engine.*;

/**
 * 
 * @author Saris, Jan-Philipp
 * @since 1.0
 * @date 16.06.2022
 *
 */
public abstract class Ghost extends PacmanActor {
	
	public enum GhostMode {
		CHASE,
		SCATTER,
		FRIGHTENED,
		EATEN,
		EATEN_ENTER,
		EATEN_LEAVE,
		TRAPPED
	}
	
	protected int targetX, targetY;
	protected int scatterX, scatterY;
	protected PacManState state;
	protected float timer;
	
	protected final static Random rnd = new Random(System.currentTimeMillis());
	
	protected final static float scaredTime = 25.f, trappedTime = 12.f;
	
	private GhostMode mode, bufferedMode;
	private boolean useBufferedMode;

	public Ghost(PacManState _parent) {
		super(_parent);
		state = _parent;
		setSpriteSheet("sprites.png", 16, 16);

		addAnimation(new int[] { 40, 41 }, 8, true, "frightened");
		addAnimation(new int[] { 40, 41, 56, 57 }, 8, true, "frightened_blink");
		addAnimation(new int[] { 89 }, 1, true, "eaten_walk_up");
		addAnimation(new int[] { 88 }, 1, true, "eaten_walk_down");
		addAnimation(new int[] { 73 }, 1, true, "eaten_walk_left");
		addAnimation(new int[] { 72 }, 1, true, "eaten_walk_right");

		mode = GhostMode.CHASE;
		useBufferedMode = false;
	}
	
	@Override
	public void update(float delta) {
		if(useBufferedMode) {
			setMode(bufferedMode);
			
			if(mode == bufferedMode) {
				useBufferedMode = false;
			}
		}
		
		if(mode == GhostMode.EATEN && nextX == state.getMap().getEatenX() && nextY == state.getMap().getEatenY()) {
			mode = GhostMode.EATEN_ENTER;
		}
		
		if(mode == GhostMode.EATEN_ENTER && nextX == state.getMap().getGhostEntryX() && nextY == state.getMap().getGhostEntryY()) {
			mode = GhostMode.TRAPPED;
			timer = 0.f;
		}
		
		if(mode == GhostMode.EATEN_LEAVE && tileX == state.getMap().getEatenX() && tileY == state.getMap().getEatenY()) {
			mode = GhostMode.CHASE;
		}
		
		if(mode == GhostMode.FRIGHTENED && tileX == state.getPacman().getX() && tileY == state.getPacman().getY()) {
			setMode(GhostMode.EATEN);
		}
		
		if(mode == GhostMode.TRAPPED) {
			timer += delta;
			if(timer >= trappedTime) {
				mode = GhostMode.EATEN_LEAVE;
			}
		}
		
		if(mode == GhostMode.FRIGHTENED) {
			timer += delta;
			if(timer >= scaredTime) {
				mode = GhostMode.CHASE;
			}
		}

		overridingAnims = true;
		
		if(mode == GhostMode.FRIGHTENED) {
			play(timer / scaredTime >= .8f ? "frightened_blink" : "frightened", true);
			
			
		} else if(mode == GhostMode.EATEN || mode == GhostMode.EATEN_ENTER || mode == GhostMode.TRAPPED) {
			play("eaten_walk_" + directions[currentDirection], true);
		} else {
			overridingAnims = false;
		}
		
		super.update(delta);
	}
	
	@Override
	protected int getDirection() {
		//Richtung in Reihenfolge: oben, links, unten, rechts
		//denn Pacman hat diese Priorisierungsreihenfolge
		//bei gleicher Distanz zum Target festgesetzt
		final int[][] deltas = new int[][] {
			{0, -1},
			{-1, 0},
			{0, 1},
			{1, 0}
		};
		
		//Hilfesarray, um Pacmans Priorisierungsmapping
		//in internes Mapping umzuwandeln
		final int[] dirMapping = new int[] { 0, 2, 1, 3 };
		
		final int[] forbiddenDirs = new int[] {
			2, 0, 3, 1	
		};
		
		if(mode == GhostMode.FRIGHTENED) {
			List<Integer> dirs = new ArrayList<>();
			
			for(int i = 0; i < 4; i++) {
				if(i == forbiddenDirs[currentDirection]) continue;
				
				int x = tileX + deltas[i][0];
				int y = tileY + deltas[i][1];
				
				if(state.getMap().isCollision(this, x, y)) continue;
				
				dirs.add(i);
			}
			
			return dirMapping[dirs.get(rnd.nextInt(dirs.size()))];
		}
		
		setTarget();
		if(mode == GhostMode.SCATTER) {
			targetX = scatterX;
			targetY = scatterY;
		}else if(mode == GhostMode.EATEN) {
			targetX = state.getMap().getEatenX();
			targetY = state.getMap().getEatenY();
		}else if(mode == GhostMode.EATEN_ENTER || mode == GhostMode.EATEN_LEAVE) {
			targetX = state.getMap().getGhostEntryX();
			targetY = state.getMap().getGhostEntryY();
		}
		
		int direction = -1;
		int minDist = Integer.MAX_VALUE;
		
		for(int i = 0; i < 4; i++) {
			if(i == forbiddenDirs[currentDirection]) continue;
			
			int x = tileX + deltas[i][0];
			int y = tileY + deltas[i][1];
			
			if(state.getMap().isCollision(this, x, y)) continue;
			
			int dx = targetX - x;
			int dy = targetY - y;
			
			int sqrDist = dx * dx + dy * dy;
			
			if(sqrDist < minDist) {
				minDist = sqrDist;
				direction = i;
			}
		}
		
		return dirMapping[direction];
	}
	
	protected abstract void setTarget();
	
	public GhostMode getMode() {
		return mode;
	}
	
	public void setMode(GhostMode _mode) {
		if(_mode == GhostMode.FRIGHTENED) {
			if(mode == GhostMode.EATEN || mode == GhostMode.EATEN_ENTER || mode == GhostMode.EATEN_LEAVE || mode == GhostMode.TRAPPED) return;
			
			timer = 0.f;
		}
		
		if(mode == _mode) return;
		
		if(_mode == GhostMode.CHASE || _mode == GhostMode.SCATTER) {
			if(mode == GhostMode.EATEN || mode == GhostMode.EATEN_ENTER || mode == GhostMode.EATEN_LEAVE || mode == GhostMode.TRAPPED || mode == GhostMode.FRIGHTENED) {
				bufferedMode = _mode;
				useBufferedMode = true;
				return;
			}
		}
		
		if(_mode != GhostMode.EATEN) {
			int tmp = nextX;
			nextX = tileX;
			tileX = tmp;
			
			tmp = nextY;
			nextY = tileY;
			tileY = tmp;
			
			tweenTimer = 8.f/speed - tweenTimer;
		}
		
		final int[] invertDirectionMapping = new int[] {
			1, 0, 3, 2	
		};
		
		currentDirection = invertDirectionMapping[currentDirection];
		
		mode = _mode;
	}
	
	@Override
	public void reset() {
		super.reset();
		
		setMode(GhostMode.CHASE);
	}

}
