package penis.jp.pacman;

/**
 * 
 * @author Saris, Jan-Philipp
 * @since 1.0
 * @date 16.06.2022
 *
 */
public class Pinky extends Ghost {

	public Pinky(PacManState _parent) {
		super(_parent);

		addAnimation(new int[] { 54, 55 }, 8, true, "walk_up");
		addAnimation(new int[] { 52, 53 }, 8, true, "walk_down");
		addAnimation(new int[] { 50, 51 }, 8, true, "walk_left");
		addAnimation(new int[] { 48, 49 }, 8, true, "walk_right");
		addAnimation(new int[] { 49, 53, 51, 55 }, 2, false, "idle");
		
		scatterX = -1;
		scatterY = -1;
	}

	@Override
	protected void setTarget() {
		PacMan pac = state.getPacman();
		
		final int[][] dirOffsets = new int[][] {
			{ -4, -4 },
			{ 0, 4 },
			{ -4, 0 },
			{ 4, 0 }
		};

		targetX = pac.getX() + dirOffsets[pac.getViewDirection()][0];
		targetY = pac.getY() + dirOffsets[pac.getViewDirection()][1];
	}

}
