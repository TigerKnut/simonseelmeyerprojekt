package penis.jp.pacman;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector2f;

import penis.engine.*;
import penis.jp.pacman.Ghost.GhostMode;

/**
 * 
 * @author Saris, Jan-Philipp
 * @since 1.0
 * @date 13.06.2022
 */
@ApplyState
public class PacManState extends GameState {
	
	private PacmanMap map;
	private PacMan pacman;
	private BitmapFont font;
	private Ghost[] ghosts;
	
	private int lives;
	private Texture sprites;
	private float timer;
	
	private float scatterTimer;
	private int modeCounter;
	protected static final GhostMode[] timedModes = {
		GhostMode.SCATTER,
		GhostMode.CHASE,
		GhostMode.SCATTER,
		GhostMode.CHASE,
		GhostMode.SCATTER,
		GhostMode.CHASE,
		GhostMode.SCATTER
	};
	
	protected static final float[] waitTimes = {
		10.f,
		10.f,
		15.f,
		8.f,
		25.f,
		5.f,
		Float.POSITIVE_INFINITY
	};

	public PacManState(Game _game) {
		super(_game);
	}
	
	@Override
	protected void onLoad() {
		input.registerInputButton(GLFW.GLFW_KEY_ESCAPE);
		input.registerInputButton(GLFW.GLFW_KEY_W);
		input.registerInputButton(GLFW.GLFW_KEY_A);
		input.registerInputButton(GLFW.GLFW_KEY_S);
		input.registerInputButton(GLFW.GLFW_KEY_D);
		
		loadTexture("sprites.png");
		loadBitmapFont("Alagard.fnt", "Alagard.png");
		loadTexture("map01.png");
		loadTexture("map01_mini.png");
	}

	@Override
	protected void onInit() {
		map = new PacmanMap(this);
		map.setMap("map01");
		
		sprites = loadTexture("sprites.png");
		
		ghosts = new Ghost[4];
		ghosts[0] = new Blinky(this, map);
		ghosts[1] = new Pinky(this);
		ghosts[2] = new Inky(this);
		ghosts[3] = new Clyde(this);
		
		pacman = new PacMan(this);
		lives = 3;
		
		font = loadBitmapFont("Alagard.fnt", "Alagard.png");
		
		map.resetPositions(pacman, ghosts);

		window.setSize(8 * map.getWidth(), 8 * map.getHeight() + 16);
		
		timer = 0.f;
		
		modeCounter = 0;
		scatterTimer = waitTimes[modeCounter];
		
		game.setShowFps(false);
	}

	@Override
	protected void onUpdate(float delta) {
		if(map.getPilletsLeft() == 0) {
			map.setPaused(true);
			pacman.setPaused(true);
			
			for(Ghost g : ghosts) {
				g.setPaused(true);
			}
			
			return;
		}
		
		if(pacman.dies(ghosts)) {
			pacman.play("die", true);
			pacman.updateDead(delta);
			
			map.setPaused(true);
			pacman.setPaused(true);
			
			for(Ghost g : ghosts) {
				g.setPaused(true);
			}
			
			timer += delta;
			if(timer >= 3.f) {
				timer = 0.f;
				lives--;
				if(lives < 0) lives = 0;
				if(lives == 0) return;
				
				map.resetPositions(pacman, ghosts);
				
				map.setPaused(false);
				pacman.setPaused(false);
				
				for(Ghost g : ghosts) {
					g.setPaused(false);
				}
				
				return;
			}
		}
		
		scatterTimer -= delta;
		if(scatterTimer <= 0.f) {
			modeCounter++;
			scatterTimer = waitTimes[modeCounter];
			
			for(Ghost g : ghosts) {
				g.setMode(timedModes[modeCounter]);
			}
		}
	}

	@Override
	protected void onRender(Brush brush) {
		if(map.getPilletsLeft() == 0) {
			Vector2f size = font.getStringSize("You win!");
			
			brush.drawString((8 * map.getWidth() - size.x) / 2.f, (8 * map.getHeight() - size.y) / 2.f, "You win!", font);
		} else if(lives == 0) {
			Vector2f size = font.getStringSize("You lose!");
			
			brush.drawString((8 * map.getWidth() - size.x) / 2.f, (8 * map.getHeight() - size.y) / 2.f, "You lose!", font);
		}
		
		for(int i = 0; i < lives; i++) {
			brush.drawTexture(16 * i, window.getHeight() - 16, 16 * i + 15, window.getHeight() - 1, 16, 0, 31, 15, sprites, 1, 1, 1, 1);
		}
	}

	@Override
	protected void onDestroy() {
		map = null;
		pacman = null;
		font = null;
		
		for(int i = 0; i < ghosts.length; i++) {
			ghosts[i] = null;
		}
		
		ghosts = null;
	}

	@Override
	public String getStateID() {
		return "pacman";
	}

	@Override
	public String getStateDisplayName() {
		return "Pacman";
	}
	
	/**
	 * @return
	 */
	public PacmanMap getMap() {
		return map;
	}
	
	public PacMan getPacman() {
		return pacman;
	}
	
	public Ghost getBlinky() {
		return ghosts[0];
	}
	
	public Ghost getPinky() {
		return ghosts[1];
	}
	
	public Ghost getInky() {
		return ghosts[2];
	}
	
	public Ghost getClyde() {
		return ghosts[3];
	}
	
	public void setGhostMode(GhostMode mode) {
		for(Ghost g : ghosts) {
			g.setMode(mode);
		}
	}

}
