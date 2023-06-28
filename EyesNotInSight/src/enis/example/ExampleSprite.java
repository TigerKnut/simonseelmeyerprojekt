package penis.example;

import penis.engine.*;
import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.util.vector.Vector2f;

/**
 * @author Saris, Jan-Philipp
 * @date 30.05.2022
 */
public class ExampleSprite extends Sprite {
	
	private Vector2f speed;

	public ExampleSprite(GameState _parent) {
		super(_parent);
		
		//Dieser Funktionsaufruf bereitet eine Textur als Spritesheet vor.
		//Ein Spritesheet enth�lt dabei eine Sammlung gleich gro�er Einzelbilder der Breite 'width'
		//und H�he 'height' (�bergabeparameter Nr. 2 & 3, hier beides 16)
		//Die Einzelbilder hei�en Frames und werden bei 0 beginnend von links oben zeilenweise nach
		//rechts unten durchnummeriert.
		setSpriteSheet("star.png", 16, 16);
		
		//F�gt dem Sprite die Informationen �ber eine neue Animation hinzu.
		//Diese besteht aus den Frames der Animation (Parameter Nr. 1, das �bergebene Array),
		//der L�nge eines Frames bzw. der Anzahl an Frames pro Sekunde (Parameter Nr. 2, hier die FPS),
		//der Angabe, ob die Animation automatisch wiederholt werden soll (Parameter Nr. 3, hier ja),
		//und dem Verweisnamen der Animation (Parameter Nr. 4).
		addAnimation(new int[] {0, 1, 2, 3}, 2, true, "example");
		
		//Weist das Sprite an, eine neue Animation zu starten.
		//�bergeben wird der Verweisname, unter dem die Animation zuvor durch 'addAnimation' registriert wurde.
		//Wird keine Animation mit diesem Namen gefunden, so wird die aktuelle Animation einfach gestoppt;
		//das aktuelle Frame wird dabei beibehalten.
		play("example");
		
		//Dieser Vektor gibt die Bewegungsgeschwindigkeit in Pixel/Sekunde an.
		//Er ist kein integraler Bestandteil des Sprites, sondern wird genutzt, um die Bewegungsgeschwindigkeit
		//zeitlich konstant zu halten (s. 'update').
		speed = new Vector2f(32.f, 32.f);
	}
	
	@Override
	public void update(float delta) {
		//Dieser Aufruf holt vom �bergeordneten GameState den Input Manager.
		Input in = parent.getInput();
		
		//In diesen Abfragen wird eine einfach Bewegung realisiert.
		//Dabei wird im Falle einer Bewegung die Bewegungsgeschwindigkeit mit der vergangenen Zeit multipliziert.
		//Dadurch kann eine zeitlich konstante Geschwindigkeit bei variabler Anzahl an FPS (im Window)
		//gew�hrleistet werden. Dieser Ansatz nennt sich delta-time und ist �blich in der Spieleentwicklung.
		if(in.isDown(GLFW_KEY_W)) position.y -= speed.y * delta;
		
		if(in.isDown(GLFW_KEY_S)) position.y += speed.y * delta;
		
		if(in.isDown(GLFW_KEY_A)) position.x -= speed.x * delta;
		
		if(in.isDown(GLFW_KEY_D)) position.x += speed.x * delta;
		
		if(in.justPressed(GLFW_KEY_X)) setXFlip(!getXFlip());
		
		if(in.justPressed(GLFW_KEY_Y)) setYFlip(!getYFlip());
		
		//Ruft die 'update'-Funktion des eigentlichen Sprite-Objektes auf.
		//Darin befindet sich die Animationslogik; wird dieser Aufruf also ausgelassen, gibt es keine Animation.
		//Dieser Aufruf sollte unbedingt am Ende der 'update'-Funktion stehen.
		super.update(delta);
	}

}
