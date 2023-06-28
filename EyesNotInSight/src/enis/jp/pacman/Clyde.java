package penis.jp.pacman;

/**
 * 
 * @author Saris, Jan-Philipp
 * @since 1.0
 * @date 16.06.2022
 *
 */
public class Clyde extends Ghost {

	public Clyde(PacManState _parent) {
		super(_parent);

		addAnimation(new int[] { 86, 87 }, 8, true, "walk_up");
		addAnimation(new int[] { 84, 85 }, 8, true, "walk_down");
		addAnimation(new int[] { 82, 83 }, 8, true, "walk_left");
		addAnimation(new int[] { 80, 81 }, 8, true, "walk_right");
		addAnimation(new int[] { 81, 85, 83, 87 }, 2, false, "idle");
		
		scatterX = -1;
		scatterY = state.getMap().getHeight();
	}

	@Override
	protected void setTarget() {
		PacMan pac = state.getPacman();
		
		int dx = pac.getX() - tileX;
		int dy = pac.getY() - tileY;
		
		int sqrDist = dx * dx + dy * dy;
		
		if(sqrDist > 64) {
			targetX = pac.getX();
			targetY = pac.getY();
		} else {
			targetX = scatterX;
			targetY = scatterY;
		}
	}

}
