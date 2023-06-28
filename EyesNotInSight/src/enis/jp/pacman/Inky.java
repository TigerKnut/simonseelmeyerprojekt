package penis.jp.pacman;

/**
 * 
 * @author Saris, Jan-Philipp
 * @since 1.0
 * @date 16.06.2022
 *
 */
public class Inky extends Ghost {

	public Inky(PacManState _parent) {
		super(_parent);

		addAnimation(new int[] { 70, 71 }, 8, true, "walk_up");
		addAnimation(new int[] { 68, 69 }, 8, true, "walk_down");
		addAnimation(new int[] { 66, 67 }, 8, true, "walk_left");
		addAnimation(new int[] { 64, 65 }, 8, true, "walk_right");
		addAnimation(new int[] { 65, 69, 67, 71 }, 2, false, "idle");
		
		scatterX = state.getMap().getWidth();
		scatterY = state.getMap().getHeight();
	}

	@Override
	protected void setTarget() {
		PacMan pac = state.getPacman();
		Ghost blinky = state.getBlinky();
		
		final int[][] dirOffsets = new int[][] {
			{ -2, -2 },
			{ 0, 2 },
			{ -2, 0 },
			{ 2, 0 }
		};

		int ix = pac.getX() + dirOffsets[pac.getViewDirection()][0];
		int iy = pac.getY() + dirOffsets[pac.getViewDirection()][1];
		
		int dx = ix - blinky.getX();
		int dy = iy - blinky.getY();
		
		targetX = ix + dx;
		targetY = iy + dy;
	}

}
