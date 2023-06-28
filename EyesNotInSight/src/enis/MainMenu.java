package penis;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector2f;

import penis.engine.*;

/**
 * 
 * @author Saris, Jan-Philipp
 * @date 30.05.2022
 *
 */
public class MainMenu extends GameState {
	
	private Texture texture;
	private Sprite sprite;
	private List<GameState> gameStates;
	private BitmapFont font;
	private int currentlySelected;

	public MainMenu(Game _game) {
		super(_game);
	}
	
	@Override
	protected void onLoad() {
		gameStates = new ArrayList<>();
		Class<?>[] allClasses = null;
		
		try {
			allClasses = getClasses("penis");
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		for(Class<?> c : allClasses) {
			if(c.isAnnotationPresent(ApplyState.class)) {
				if(GameState.class.isAssignableFrom(c)) {
					try {
						GameState state = (GameState) c.getConstructor(Game.class).newInstance(game);
						gameStates.add(state);
						state.load();
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException | NoSuchMethodException | SecurityException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		loadTexture("star.png");
		loadBitmapFont("Alagard.fnt", "Alagard.png");
		
		input.registerInputButton(GLFW.GLFW_KEY_SPACE);
		input.registerInputButton(GLFW.GLFW_KEY_ENTER);
		input.registerInputButton(GLFW.GLFW_KEY_UP);
		input.registerInputButton(GLFW.GLFW_KEY_DOWN);
	}

	@Override
	protected void onInit() {
		window.setClearColor(0.f, 0.f, 0.f, 1.f);
		window.setSize(400, 400);
		
		texture = loadTexture("star.png");
		font = loadBitmapFont("Alagard.fnt", "Alagard.png");
		
		currentlySelected = 0;
		
		//sprite = new Sprite(this);
		//sprite.setPosition(200, 10);
		//sprite.setSpriteSheet(texture, 16, 16);
		//sprite.addAnimation(new int[] {0, 1, 2, 3}, 2, true, "test");
		//sprite.play("test");
	}

	@Override
	protected void onUpdate(float delta) {
		if(input.justPressed(GLFW.GLFW_KEY_SPACE) || input.justPressed(GLFW.GLFW_KEY_ENTER)) {
			game.changeGameState(gameStates.get(currentlySelected).getStateID());
		}
		
		if(input.justPressed(GLFW.GLFW_KEY_UP)) {
			currentlySelected--;
			if(currentlySelected < 0) currentlySelected = gameStates.size()-1;
		}
		
		if(input.justPressed(GLFW.GLFW_KEY_DOWN)) {
			currentlySelected++;
			if(currentlySelected >= gameStates.size()) currentlySelected = 0;
		}
	}

	@Override
	protected void onRender(Brush brush) {
		//brush.fillRect(50, 50, 80, 80, .6f, 0.f, 1.f, 1.f);
		//brush.drawTexture(20, 100, 84, 164, texture);
		
		float y = 50;
		
		for(int i = 0; i < gameStates.size(); i++) {
			GameState state = gameStates.get(i);
			
			Vector2f size = font.getStringSize(state.getStateDisplayName());
			
			brush.drawString((window.getWidth() - size.x) / 2.f, y, state.getStateDisplayName(), font);
			
			if(currentlySelected == i) {
				brush.drawRect((window.getWidth() - size.x) / 2.f - 1, y, (window.getWidth() - size.x) / 2.f + size.x + 1, y + size.y, 1.f, 1.f, 1.f, 1.f);
			}

			y += font.getFontHeight();
		}
	}

	@Override
	protected void onDestroy() {
		texture = null;
		sprite = null;
		//game.setClearColor(0.f, 0.f, 0.f, 1.f);
	}

	@Override
	public String getStateID() {
		return "main_menu";
	}

	@Override
	public String getStateDisplayName() {
		return "Hauptmenü";
	}
	
	/**
	 * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
	 * Von Seite: https://stackoverflow.com/questions/520328/can-you-find-all-classes-in-a-package-using-reflection
	 *
	 * @param packageName The base package
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @date 01.06.2022
	 */
	private static Class<?>[] getClasses(String packageName)
	        throws ClassNotFoundException, IOException {
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    assert classLoader != null;
	    String path = packageName.replace('.', '/');
	    Enumeration<URL> resources = classLoader.getResources(path);
	    List<File> dirs = new ArrayList<File>();
	    while (resources.hasMoreElements()) {
	        URL resource = resources.nextElement();
	        dirs.add(new File(resource.getFile()));
	    }
	    ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
	    for (File directory : dirs) {
	        classes.addAll(findClasses(directory, packageName));
	    }
	    return classes.toArray(new Class[classes.size()]);
	}

	/**
	 * Recursive method used to find all classes in a given directory and subdirs.
	 * Von Seite: https://stackoverflow.com/questions/520328/can-you-find-all-classes-in-a-package-using-reflection
	 *
	 * @param directory   The base directory
	 * @param packageName The package name for classes found inside the base directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @date 01.06.2022
	 */
	private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
	    List<Class<?>> classes = new ArrayList<Class<?>>();
	    if (!directory.exists()) {
	        return classes;
	    }
	    File[] files = directory.listFiles();
	    for (File file : files) {
	        if (file.isDirectory()) {
	            assert !file.getName().contains(".");
	            classes.addAll(findClasses(file, packageName + "." + file.getName()));
	        } else if (file.getName().endsWith(".class")) {
	            classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
	        }
	    }
	    return classes;
	}

}
