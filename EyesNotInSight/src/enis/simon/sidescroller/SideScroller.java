package penis.simon.sidescroller;

import penis.engine.*;
import static org.lwjgl.glfw.GLFW.*;

/*
@author: Seelmeyer, Simon
@date: 
*/


@ApplyState
public class SideScroller extends GameState{
	
	float playerlife = 3;				//leben des spielers
	float enemylife = 3;				//leben des gegners
	float playerdamagetimer = 0;				//läuft mit der Zeit ab, wird gebracht, damit nicht zu viele Kollisionen auf ein Mal gemeldet werden
	float enemydamagetimer = 0;					//läuft mit der Zeit ab, wird gebracht, damit nicht zu viele Kollisionen auf ein Mal gemeldet werden
	public static float px = 0;				//gibt die momentane x-koordinate des spielers an
	public static float py = 0;				//gibt die momentane x-koordinate des spielers an
	private float spawnenemytimer = 5;				//nach Ablauf wird neuer Gegner gespawnt
	private boolean enemydead = false;				//gibt an, ob ein gegner momentan vorhanden ist
	private int kills = 0;				//gibt die Anzahl besiegter Gegner an
	
	Player player;
	Enemy enemy;
	
	
	public SideScroller(Game _game) {
		super(_game);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	protected void onInit() {
		// TODO Auto-generated method stub
		window.setSize(1200, 720);				
		new Level(this);				
		player = new Player(this);				
		enemy = new Enemy(this);				
		
	}

	
	@Override
	protected void onUpdate(float delta) {
		// TODO Auto-generated method stub
		
		if (input.isDown(GLFW_KEY_ESCAPE)) {				
			game.changeGameState();
		}
		
		if (player.pd.intersects(enemy.eh) && playerdamagetimer <= 0) {				
			
			playerdamagetimer = 1;				
			enemylife -= 1;				
		}
		
		if (enemy.ed.intersects(player.ph) && enemydamagetimer <= 0) {				
			
			enemydamagetimer = 1;				
			playerlife -= 1;				
		}
		
		if (enemylife <= 0) {				
			
			enemy.setPaused(true);				
			enemy.setHidden(true);
			enemydead = true;				
			
		}
		
		if (playerlife <= 0) {				
			
			//System.out.println("kills before death: " + kills);				
			player.setPaused(true);				
			player.setHidden(true);
			
		}
		
		if (enemydead) {				
			spawnenemytimer -= delta;		
		}
		
		if (spawnenemytimer <= 0) {				
			kills++;				
			enemy = new Enemy(this);
			enemylife = 3 + kills;
			enemydead = false;
			spawnenemytimer = 5;
		}
		
		playerdamagetimer -= delta;
		enemydamagetimer -= delta;
		
	}

	
	@Override
	protected void onRender(Brush brush) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public String getStateID() {
		// TODO Auto-generated method stub
		return "SideScroller";
	}

	
	@Override
	public String getStateDisplayName() {
		// TODO Auto-generated method stub
		return "Hateshinai toso";
	}
	
}