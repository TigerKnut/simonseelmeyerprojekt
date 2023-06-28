package penis.jp.pacman;

/**
 * 
 * @author Saris, Jan-Philipp
 * @since 1.0
 * @date 16.06.2022
 *
 */
public class Blinky extends Ghost {
	
	private PacmanMap map;

	public Blinky(PacManState _parent, PacmanMap _map) {
		super(_parent);
		
		map = _map;
		
		addAnimation(new int[] { 38, 39 }, 8, true, "walk_up");
		addAnimation(new int[] { 36, 37 }, 8, true, "walk_down");
		addAnimation(new int[] { 34, 35 }, 8, true, "walk_left");
		addAnimation(new int[] { 32, 33 }, 8, true, "walk_right");
		addAnimation(new int[] { 33, 37, 35, 39 }, 2, false, "idle");
		
		scatterX = state.getMap().getWidth();
		scatterY = -1;
	}

	@Override
	protected void setTarget() {
		targetX = state.getPacman().getX();
		targetY = state.getPacman().getY();
	}
	
	@Override
	public void setMode(GhostMode _mode) {
		if(_mode == GhostMode.SCATTER && map.getPilletsLeft() <= 20) return;
		
		super.setMode(_mode);
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
	}

}
