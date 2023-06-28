package penis.jp.pacman;

import org.lwjgl.util.vector.Vector4f;

import penis.engine.*;
import penis.jp.pacman.Ghost.GhostMode;

/**
 * 
 * @author Saris, Jan-Philipp
 * @since 1.0
 * @date 14.06.2022
 */
public class PacmanMap extends GameObject {
	
	private static enum FloorTile {
		FLOOR,
		PILLET,
		POWER_PILLET,
		GHOST_GROUND,
		GHOST_ENTER_ONLY,
		COLLISION
	}
	
	private Texture mapTex, spritesTex;
	private PacManState state;
	private int width, height;
	private FloorTile[][] map;
	private int amountPillets;
	
	private int eatenTargetX, eatenTargetY;
	private int ghostEnterX, ghostEnterY;

	private int pacX, pacY;
	private int blinkyX, blinkyY;
	private int pinkyX, pinkyY;
	private int inkyX, inkyY;
	private int clydeX, clydeY;
	
	private boolean initialized;
	
	private static final Vector4f floorColor = new Vector4f(1, 1, 1, 1),
			pilletColor = new Vector4f(1, 1, 0, 1),
			powerColor = new Vector4f(1, 0, 0, 1),
			ghostGroundColor = new Vector4f(0, 1, 0, 1),
			ghostEnterColor = new Vector4f(0, 1, 1, 1),
			ghostEatenTargetColor = new Vector4f(0, 128.f/255, 128.f/255, 1),
			pacmanInitColor = new Vector4f(1, 0, 1, 1),
			blinkyInitColor = new Vector4f(0x40/255.f, 0, 0, 1),
			pinkyInitColor = new Vector4f(0x40/255.f, 0, 0x40/255.f, 1),
			inkyInitColor = new Vector4f(0, 0, 0x40/255.f, 1),
			clydeInitColor = new Vector4f(0x80/255.f, 0x40/255.f, 0, 1);
	
	public PacmanMap(PacManState _parent) {
		super(_parent);
		state = _parent;
		
		spritesTex = parent.loadTexture("sprites.png");
		
		initialized = false;
	}
	
	public PacmanMap(PacManState _parent, String mapId) {
		this(_parent);
		
		setMap(mapId);
	}
	
	public void setMap(String mapId) {
		mapTex = parent.loadTexture(mapId + ".png");
		
		Texture miniMap = parent.loadTexture(mapId + "_mini.png");
		width = miniMap.getWidth();
		height = miniMap.getHeight();
		
		Vector4f[][] pixels = miniMap.getTextureData();
		
		map = new FloorTile[width][height];
		amountPillets = 0;
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				Vector4f p = pixels[x][y];
				
				if(p.equals(floorColor)) {
					map[x][y] = FloorTile.FLOOR;
				} else if(p.equals(pilletColor)) {
					map[x][y] = FloorTile.PILLET;
					amountPillets++;
				} else if(p.equals(powerColor)) {
					map[x][y] = FloorTile.POWER_PILLET;
				} else if(p.equals(ghostGroundColor)) {
					map[x][y] = FloorTile.GHOST_GROUND;
				} else if(p.equals(ghostEnterColor)) {
					map[x][y] = FloorTile.GHOST_ENTER_ONLY;
					ghostEnterX = x;
					ghostEnterY = y;
				} else if(p.equals(pacmanInitColor)) {
					map[x][y] = FloorTile.FLOOR;
					pacX = x;
					pacY = y;
				} else if(p.equals(ghostEatenTargetColor)) {
					map[x][y] = FloorTile.FLOOR;
					eatenTargetX = x;
					eatenTargetY = y;
				} else if(p.equals(blinkyInitColor)) {
					map[x][y] = FloorTile.PILLET;
					blinkyX = x;
					blinkyY = y;
				} else if(p.equals(pinkyInitColor)) {
					map[x][y] = FloorTile.PILLET;
					pinkyX = x;
					pinkyY = y;
				} else if(p.equals(inkyInitColor)) {
					map[x][y] = FloorTile.PILLET;
					inkyX = x;
					inkyY = y;
				} else if(p.equals(clydeInitColor)) {
					map[x][y] = FloorTile.PILLET;
					clydeX = x;
					clydeY = y;
				} else {
					map[x][y] = FloorTile.COLLISION;
				}
			}
		}
		
		initialized = true;
	}
	
	public void resetPositions(PacMan pacman, Ghost[] ghosts) {
		if(!initialized) return;
		
		pacman.setTilePos(pacX, pacY);
		pacman.reset();

		ghosts[0].setTilePos(blinkyX, blinkyY);
		ghosts[0].reset();

		ghosts[1].setTilePos(pinkyX, pinkyY);
		ghosts[1].reset();

		ghosts[2].setTilePos(inkyX, inkyY);
		ghosts[2].reset();

		ghosts[3].setTilePos(clydeX, clydeY);
		ghosts[3].reset();
		
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Brush brush) {
		if(!initialized) return;
		
		brush.drawTexture(position.x, position.y, mapTex);
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				float posX = position.x + 8 * x;
				float posY = position.y + 8 * y;
				
				if(map[x][y] == FloorTile.PILLET) {
					brush.drawTexture(posX, posY, posX + 8, posY + 8, 16 * 10 + 4, 4, 16 * 10 + 12, 12, spritesTex);
				} else if(map[x][y] == FloorTile.POWER_PILLET) {
					brush.drawTexture(posX, posY, posX + 8, posY + 8, 16 * 11 + 4, 4, 16 * 11 + 12, 12, spritesTex);
				}
			}
		}
	}
	
	public boolean isCollision(PacmanActor actor, int x, int y) {
		if(!initialized) return false;
		
		if(x < 0 || y < 0 || x >= width || y >= height) return false;
		
		if(map[x][y] == FloorTile.COLLISION) return true;
		
		if(actor instanceof PacMan && map[x][y] == FloorTile.GHOST_ENTER_ONLY) return true;
		
		if(actor instanceof Ghost) {
			if(map[x][y] == FloorTile.GHOST_ENTER_ONLY) {
				Ghost g = (Ghost)actor;
				if(g.getMode() == GhostMode.EATEN || g.getMode() == GhostMode.EATEN_ENTER || g.getMode() == GhostMode.EATEN_LEAVE) {
					return false;
				}
				
				return true;
			}
		}
		
		return false;
	}
	
	public void setPacman(int x, int y) {
		if(!initialized) return;
		
		if(x < 0 || y < 0 || x >= width || y >= height) return;
		
		if(map[x][y] == FloorTile.PILLET) {
			amountPillets--;
		} else if(map[x][y] == FloorTile.POWER_PILLET) {
			state.setGhostMode(GhostMode.FRIGHTENED);
		}
		
		map[x][y] = FloorTile.FLOOR;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getPilletsLeft() {
		return amountPillets;
	}
	
	public int getEatenX() {
		return eatenTargetX;
	}
	
	public int getEatenY() {
		return eatenTargetY;
	}
	
	public int getGhostEntryX() {
		return ghostEnterX;
	}
	
	public int getGhostEntryY() {
		return ghostEnterY;
	}

}
