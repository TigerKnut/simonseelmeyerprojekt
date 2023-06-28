package penis.engine;

import java.util.HashMap;

/**
 * Uebernimmt die Aufgabe des animationsbasierten Textur-Renderings.
 * 
 * @author Saris, Jan-Philipp
 * @since 1.0
 * @date 30.05.2022
 */
public class Sprite extends GameObject {
	
	/**
	 * Hilfsklasse zur Darstellung von Animationen.
	 * 
	 * @author Saris, Jan-Philipp
	 * @since 1.0
	 * @date 30.05.2022
	 */
	public static class Animation {
		
		public int[] frames;
		public float frameLength;
		public boolean loop;
		public String name;
		
		/**
		 * @param frames Reihenfolge der Animationsframes als Array (Zaehlung ist nullbasiert von links oben nach rechts unten zeilenweise, wird bei 'null' auf das Array {0} gesetzt)
		 * @param fps Anzahl angezeigter Animationsframes pro Sekunde
		 * @param loop Angabe darueber, ob die Animation automatisch wiederholen soll.
		 * @param name Verweisname der Animation (wird bei 'null' auf "empty_anim_name" gesetzt)
		 */
		public Animation(int[] _frames, int fps, boolean _loop, String _name) {
			this(_frames, 1.f / fps, _loop, _name);
		}
		
		/**
		 * @param frames Reihenfolge der Animationsframes als Array (Zaehlung ist nullbasiert von links oben nach rechts unten zeilenweise, wird bei 'null' auf das Array {0} gesetzt)
		 * @param frameLength Laenge eines Frames in Sekunden (Kann aus FPS errechnet werden.
		 * @param loop Angabe darueber, ob die Animation automatisch wiederholen soll.
		 * @param name Verweisname der Animation (wird bei 'null' auf "empty_anim_name" gesetzt)
		 */
		public Animation(int[] _frames, float _frameLength, boolean _loop, String _name) {
			frames = _frames;
			frameLength = _frameLength;
			loop = _loop;
			name = _name;
			
			if(frames == null) frames = new int[] {0};
			
			if(name == null) name = "empty_anim_name";
		}
		
	}
	
	private Texture texture;
	private boolean flipX, flipY;
	private int frameWidth, frameHeight, framesInWidth;
	
	private HashMap<String, Animation> animations;
	
	private Animation currentAnimation;
	protected int currentFrame;
	private int currentFrameIndex;
	private float animationTimer;
	private boolean paused;

	/**
	 * Bringt das Sprite in den Grundzustand
	 * @param _parent Der GameState, zu dem das Sprite gehoert
	 */
	public Sprite(GameState _parent) {
		super(_parent);
		
		animations = new HashMap<>();
		
		currentFrame = 0;
		currentAnimation = null;
		currentFrameIndex = 0;
		animationTimer = 0.f;
		paused = false;
		
		flipX = false;
		flipY = false;
	}
	
	/**
	 * Laedt eine Textur und weist sie dem Sprite als Spritesheet zu
	 * @param textureFileName Dateipfad der Textur (Da das Sprite einem GameState angehoert, genuegt der verkuerzte Pfad, s. {@link GameState#getStateID()})
	 * @param _frameWidth Breite eines Animationsframes
	 * @param _frameHeight H�he eines Animationsframes
	 */
	public void setSpriteSheet(String textureFilename, int _frameWidth, int _frameHeight) {
		setSpriteSheet(parent.loadTexture(textureFilename), _frameWidth, _frameHeight);
	}
	
	/**
	 * Weist dem Sprite eine neue Textur als Spritesheet zu
	 * @param _texture Die Textur des Spritesheets
	 * @param _frameWidth Breite eines Animationsframes
	 * @param _frameHeight Hoehe eines Animationsframes
	 */
	public void setSpriteSheet(Texture _texture, int _frameWidth, int _frameHeight) {
		texture = _texture;
		frameWidth = _frameWidth;
		frameHeight = _frameHeight;
		
		if(texture != null) {
			framesInWidth = texture.getWidth() / frameWidth;
		}
	}
	
	/**
	 * Fuegt eine Animation dem internen Speicher hinzu.
	 * @param anim Die hinzuzufuegende Animation (nicht 'null')
	 */
	public void addAnimation(Animation anim) {
		if(anim == null) return;
		
		animations.put(anim.name, anim);
	}
	
	/**
	 * Fuegt eine Animation dem internen Speicher hinzu.
	 * Der Verweisname wird automatisch als "anim#0", "anim#1"... erzeugt
	 * @param frames Reihenfolge der Animationsframes als Array (Zaehlung ist nullbasiert von links oben nach rechts unten zeilenweise)
	 * @param fps Anzahl angezeigter Animationsframes pro Sekunde
	 * @param loop Angabe darueber, ob die Animation automatisch wiederholen soll.
	 */
	public void addAnimation(int[] frames, int fps, boolean loop) {
		addAnimation(new Animation(frames, 1.f / fps, loop, "anim#" + animations.size()));
	}
	
	/**
	 * Fuegt eine Animation dem internen Speicher hinzu.
	 * Der Verweisname wird automatisch als "anim#0", "anim#1"... erzeugt
	 * @param frames Reihenfolge der Animationsframes als Array (Zaehlung ist nullbasiert von links oben nach rechts unten zeilenweise)
	 * @param frameLength Laenge eines Frames in Sekunden (Kann aus FPS errechnet werden, s. {@link #addAnimation(int[], int, boolean, String)}
	 * @param loop Angabe darueber, ob die Animation automatisch wiederholen soll.
	 */
	public void addAnimation(int[] frames, float frameLength, boolean loop) {
		addAnimation(new Animation(frames, frameLength, loop, "anim#" + animations.size()));
	}
	
	/**
	 * Fuegt eine Animation dem internen Speicher hinzu.
	 * @param frames Reihenfolge der Animationsframes als Array (Zaehlung ist nullbasiert von links oben nach rechts unten zeilenweise)
	 * @param fps Anzahl angezeigter Animationsframes pro Sekunde
	 * @param loop Angabe darueer, ob die Animation automatisch wiederholen soll.
	 * @param name Verweisname der Animation
	 */
	public void addAnimation(int[] frames, int fps, boolean loop, String name) {
		addAnimation(new Animation(frames, 1.f / fps, loop, name));
	}
	
	/**
	 * Fuegt eine Animation dem internen Speicher hinzu.
	 * @param frames Reihenfolge der Animationsframes als Array (Zaehlung ist nullbasiert von links oben nach rechts unten zeilenweise)
	 * @param frameLength Laenge eines Frames in Sekunden (Kann aus FPS errechnet werden, s. {@link #addAnimation(int[], int, boolean, String)}
	 * @param loop Angabe darueber, ob die Animation automatisch wiederholen soll.
	 * @param name Verweisname der Animation
	 */
	public void addAnimation(int[] frames, float frameLength, boolean loop, String name) {
		addAnimation(new Animation(frames, frameLength, loop, name));
	}
	
	/**
	 * Spielt die Animation ab, die unter dem gegebenen Name abgespeichert ist.
	 * Existiert diese nicht, stoppt die aktuelle Animation.
	 * @param name Name der Animation
	 */
	public void play(String name) {
		play(name, false);
	}
	
	/**
	 * Spielt die Animation ab, die unter dem gegebenen Name abgespeichert ist.
	 * Existiert diese nicht, stoppt die aktuelle Animation.
	 * @param name Name der Animation
	 * @param skipIfPlaying Wenn 'true', wird die Animation nicht gestartet, sofern sie bereits abspielt.
	 * @since 1.2
	 * @date 13.06.2022
	 */
	public void play(String name, boolean skipIfPlaying) {
		if(!animations.containsKey(name)) {
			stop();
			return;
		}
		
		if(skipIfPlaying && currentAnimation != null && currentAnimation.name.equals(name)) return;
		
		currentAnimation = animations.get(name);
		currentFrameIndex = 0;
		animationTimer = 0.f;
		paused = false;
	}
	
	/**
	 * Pausiert die aktuelle Animation.
	 * Keine Aenderung, wenn Animation bereits pausiert ist oder keine laeuft.
	 */
	public void stop() {
		paused = true;
	}
	
	/**
	 * Laesst die aktuelle Animation weiterspielen.
	 * Keine Aenderung, wenn Animation bereits laeuft oder keine laeuft.
	 */
	public void resume() {
		paused = false;
	}
	
	/**
	 * 
	 * @param _flipX Angabe, ob Bild an mittlerer y-Achse gespiegelt werden soll.
	 * @since 1.2
	 * @ate 26.06.2022
	 */
	public void setXFlip(boolean _flipX) {
		flipX = _flipX;
	}

	/**
	 * 
	 * @param _flipY Angabe, ob Bild an mittlerer x-Achse gespiegelt werden soll.
	 * @since 1.2
	 * @ate 26.06.2022
	 */
	public void setYFlip(boolean _flipY) {
		flipY = _flipY;
	}
	
	/**
	 * 
	 * @return Wird Bild gerade an mittlerer y-Achse gespiegelt?
	 * @since 1.2
	 * @date 26.06.2022
	 */
	public boolean getXFlip() {
		return flipX;
	}

	/**
	 * 
	 * @return Wird Bild gerade an mittlerer x-Achse gespiegelt?
	 * @since 1.2
	 * @date 26.06.2022
	 */
	public boolean getYFlip() {
		return flipY;
	}

	/**
	 * Updatet die aktuelle Animation, sofern gerade eine abspielt
	 */
	@Override
	public void update(float delta) {
		if(currentAnimation != null && !paused) {
			animationTimer += delta;
			if(animationTimer >= currentAnimation.frameLength) {
				animationTimer = 0.f;
				currentFrameIndex++;
				
				if(currentFrameIndex >= currentAnimation.frames.length) {
					if(currentAnimation.loop) {
						currentFrameIndex = 0;
					} else {
						currentFrameIndex--;
					}
				}
			}
			
			currentFrame = currentAnimation.frames[currentFrameIndex];
		}
	}

	/**
	 * Rendert den aktuellen Animationsauschnitt der Textur, sofern diese nicht 'null' ist.
	 * @param brush Instanz einer Hilfsklasse, die Render-Funktionalitaeten enthaelt.
	 */
	@Override
	public void draw(Brush brush) {
		if(texture == null) return;
		
		int u = (currentFrame % framesInWidth) * frameWidth;
		int v = (currentFrame / framesInWidth) * frameHeight;
		
		brush.drawTexture(position.x, position.y, position.x + frameWidth, position.y + frameHeight, u, v, u + frameWidth, v + frameHeight, texture, flipX, flipY);
	}

}
