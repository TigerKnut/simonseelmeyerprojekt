package penis.engine;

import static org.lwjgl.glfw.GLFW.*;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;

import org.lwjgl.util.vector.Vector2f;

/**
 * Uebernimmt die Eingabe-Verwaltung und -Verarbeitung.
 * 
 * @author Saris, Jan-Philipp
 * @since 1.0
 * @date 30.05.2022
 */

public class Input {
	
	/**
	 * Hilfklasse, die den Zustand einer Taste beschreibt.
	 * 
	 * @author Saris, Jan-Philipp
	 * @since 1.0
	 * @date 30.05.2022
	 */
	private class ButtonState {
		
		public boolean prevPressed, nowPressed;
		
		public ButtonState() {
			prevPressed = false;
			nowPressed = false;
		}
		
	}
	
	/**
	 * Hilfsklasse, die Informationen ueber den Zustand der Maus enthält.
	 * 
	 * @author Saris, Jan-Philipp
	 * @since 1.0
	 * @date 30.05.2022
	 */
	private class MouseState {
		
		public Vector2f position;
		public ButtonState leftButton, rightButton;
		
		public MouseState() {
			position = new Vector2f();
			leftButton = new ButtonState();
			rightButton = new ButtonState();
		}
		
	}
	
	
	private HashMap<Integer, ButtonState> buttons;
	private MouseState mouse;
	private long windowHandle;
	
	/**
	 * Erstellt die fuer die Speicherung der Eingabezustaende noetigen Container.
	 */
	public Input() {
		windowHandle = 0;
		
		buttons = new HashMap<>();
		mouse = new MouseState();
	}
	
	/**
	 * Speichert die zum Fenster zugehoerige ID ab. Diese ist noetig, um mit GLFW Eingaben zu verarbeiten.
	 * @param _windowHandle Mit dem Fenster verknuepfte ID, zurueckgegeben von {@link Window#create(int, int, String)}
	 */
	public void setWindow(long _windowHandle ) {
		windowHandle = _windowHandle;
	}
	
	/**
	 * Updatet die Zustaende der Tasten und der Maus.
	 */
	public void update() {
		for(Entry<Integer, ButtonState> entry : buttons.entrySet()) {
			ButtonState button = entry.getValue();
			button.prevPressed = button.nowPressed;
			button.nowPressed = (glfwGetKey(windowHandle, entry.getKey()) == GLFW_PRESS);
		}
		
		double[] mouseX = new double[1], mouseY = new double[1];
		glfwGetCursorPos(windowHandle, mouseX, mouseY);
		
		mouse.position.x = (float)mouseX[0];
		mouse.position.y = (float)mouseY[0];
		
		mouse.leftButton.prevPressed = mouse.leftButton.nowPressed;
		mouse.rightButton.prevPressed = mouse.rightButton.nowPressed;

		mouse.leftButton.nowPressed = (glfwGetMouseButton(windowHandle, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS);
		mouse.rightButton.nowPressed = (glfwGetMouseButton(windowHandle, GLFW_MOUSE_BUTTON_RIGHT) == GLFW_PRESS);
	}
	
	/**
	 * Registriert eine Taste fuer den update-Aufruf.
	 * @param keyCode Tastencode nach {@link org.lwjgl.glfw.GLFW} (z. B. GLFW_KEY_W).
	 */
	public void registerInputButton(int keyCode) {
		if(glfwGetKeyScancode(keyCode) == -1) return;
		
		if(keyCode == GLFW_MOUSE_BUTTON_LEFT) return;
		if(keyCode == GLFW_MOUSE_BUTTON_RIGHT) return;
		
		buttons.putIfAbsent(keyCode, new ButtonState());
	}
	
	/**
	 * private Hilfsfunktion mit der ein ButtonState zurueckgegeben oder ggf. ein neuer erzeugt,
	 * registriert und zurueckgegeben wird.
	 * @param keyCode Tastencode nach {@link org.lwjgl.glfw.GLFW} (z. B. GLFW_KEY_W).
	 * @return Der mit 'keyCode' verbundenen ButtonState.
	 */
	private ButtonState getButton(int keyCode) {
		if(keyCode == GLFW_MOUSE_BUTTON_LEFT) return mouse.leftButton;
		
		if(keyCode == GLFW_MOUSE_BUTTON_RIGHT) return mouse.rightButton;
		
		if(!buttons.containsKey(keyCode)) {
			registerInputButton(keyCode);
		}
		
		return buttons.get(keyCode);
	}
	
	/**
	 * Eingabeüberpruefung (Taste nicht gedrueckt?)
	 * @param keyCode Tastencode nach {@link org.lwjgl.glfw.GLFW} (z. B. GLFW_KEY_W).
	 * @return Ergebnis der Eingabeüberpruefung
	 */
	public boolean isUp(int keyCode) {
		return !isDown(keyCode);
	}
	 /**
	  * Eingabeüberpruefung (Taste gedrueckt?)
	  * @param keyCode Tastencode nach {@link org.lwjgl.glfw.GLFW} (z. B. GLFW_KEY_W).
	  * @return Ergebnis der Eingabeüberpruefung
	  */
	public boolean isDown(int keyCode) {
		ButtonState btn = getButton(keyCode);
		
		return btn.nowPressed;
	}
	
	/**
	 * Eingabeüberpruefung (Taste in diesem Frame neu gedrueckt?)
	 * @param keyCode Tastencode nach {@link org.lwjgl.glfw.GLFW} (z. B. GLFW_KEY_W).
	 * @return Ergebnis der Eingabeüberpruefung
	 */
	public boolean justPressed(int keyCode) {
		ButtonState btn = getButton(keyCode);
		
		return btn.nowPressed && !btn.prevPressed;
	}
	
	/**
	 * Eingabeüberpruefung (Taste in diesem Frame neu losgelassen?)
	 * @param keyCode Tastencode nach {@link org.lwjgl.glfw.GLFW} (z. B. GLFW_KEY_W).
	 * @return Ergebnis der Eingabeüberpruefung
	 */
	public boolean justReleased(int keyCode) {
		ButtonState btn = getButton(keyCode);
		
		return btn.prevPressed && !btn.nowPressed;
	}
	
	/**
	 * 
	 * @return x-Komponente der Mausposition.
	 */
	public int getMouseX() {
		return (int)mouse.position.x;
	}
	
	/**
	 * 
	 * @return y-Komponente der Mausposition.
	 */
	public int getMouseY() {
		return (int)mouse.position.y;
	}
	
	/**
	 * 
	 * @return Mausposition als Vektor (Kopie(!) des internen Vektors).
	 */
	public Vector2f getMousePos() {
		return new Vector2f(mouse.position);
	}

}
