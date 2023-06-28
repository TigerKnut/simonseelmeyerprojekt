package penis.enrique.undertale;

import penis.engine.*;

@ApplyState
public class Undertale extends GameState {
	
	private Texture tex, tex1, tex2, tex3, tex4, tex5;
	
	public Undertale(Game _game) {
		super(_game);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onLoad() {
	
	}

	@Override
	protected void onInit() {
		window.setSize(1280, 720);

		tex  = loadTexture("Sprites/Spielfeld.png");
		tex1 = loadTexture("Sprites/Herz (cyan).png");
		tex2 = loadTexture("Sprites/Fight (normal).png");
		//tex3 = loadTexture("Sprites/Chara);
		tex4 = loadTexture("Sprites/Chara Schwert.png");
		
		new Player(this);
		new Enemy(this);
	}

	@Override
	protected void onUpdate(float delta) {
		
		
	}

	@Override
	protected void onRender(Brush brush) {
		
		brush.drawTexture(515, 280, tex);
		brush.drawTexture(150, 625, tex2);

		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getStateID() {
		// TODO Auto-generated method stub
		return "Undertale";
	}

	@Override
	public String getStateDisplayName() {
		// TODO Auto-generated method stub
		return "Undertale";
	}

}
