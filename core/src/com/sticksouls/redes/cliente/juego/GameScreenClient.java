package com.sticksouls.redes.cliente.juego;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.sticksouls.StickSouls;
import com.sticksouls.hud.PauseHud;
import com.sticksouls.inputs.InputsListener;
import com.sticksouls.inputs.MyInput;
import com.sticksouls.redes.OnlineClientEnemy;
import com.sticksouls.redes.OnlinePlayer;
import com.sticksouls.redes.OnlineWhiteStickman;
import com.sticksouls.redes.RedUtils;
import com.sticksouls.redes.cliente.Cliente;
import com.sticksouls.utils.Render;
import com.sticksouls.utils.Resources;

public class GameScreenClient implements Screen, MyInput{
	
	private final StickSouls GAME;
	private InputMultiplexer inputHandler;
	private OnlineWhiteStickman whiteStickman;
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private ArrayList<OnlineClientEnemy> enemies = new ArrayList<OnlineClientEnemy>();
	private OrthographicCamera camera;
	private PauseHud menuPause;
	private boolean gameOver = false;
	
	private Cliente cliente;
	public OnlinePlayer player2;
	
	public GameScreenClient(final StickSouls GAME, Cliente cliente, boolean player1) {
		this.GAME = GAME;
		this.cliente = cliente;
		menuPause = new PauseHud(GAME, cliente);
		
		// Create a new OrthographicCamera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// Set an initial zoom level
		camera.zoom = 0.6f;
		// Initialize Box2D
		Box2D.init();
		// Create world and setup debugRenderer
		world = new World(new Vector2(0, 0), true);

		debugRenderer = new Box2DDebugRenderer();
		
		if(player1) {
			whiteStickman = new OnlineWhiteStickman(world, 0, 0, camera);
			player2 = new OnlinePlayer(world, 30, 0, camera);			
			RedUtils.player1 = true;
		}else {
			whiteStickman = new OnlineWhiteStickman(world, 30, 0, camera);
			player2 = new OnlinePlayer(world, 0, 0, camera);						
			RedUtils.player1 = false;
		}
		
		RedUtils.hiloCliente.setGame(this);
	}

	@Override
	public void show() {
		InputsListener.addInputs(this);
		InputsListener.setMyIndex(this);
		
		// Map render
		Render.tiledMapRenderer = new OrthogonalTiledMapRenderer(Resources.MAP);
		
		// Create the collision of the walls on the map
		TiledMapTileLayer layer = (TiledMapTileLayer) Resources.MAP.getLayers().get("Wall");
		
		for(int i = 0; i < layer.getHeight(); i++) {
			for(int j = 0; j < layer.getWidth(); j++) {
				Cell cell = layer.getCell(j, i);
				
				if(cell != null && cell.getTile().getObjects().getCount() == 1) {
					float worldX = j * layer.getTileWidth();
		            float worldY = i * layer.getTileHeight();
		            
		            BodyDef bd = new BodyDef();
		            bd.position.set(worldX + layer.getTileWidth()/2, worldY + layer.getTileHeight()/2);
		            bd.type = BodyType.StaticBody;
		            
		            PolygonShape shape = new PolygonShape();
		            shape.setAsBox(layer.getTileWidth()/2, layer.getTileHeight()/2);
		            
		            FixtureDef fixDef = new FixtureDef();
		            fixDef.shape = shape;
		            
		            Body body = world.createBody(bd);
		            body.createFixture(fixDef);
		            shape.dispose();
				}
			}
		}
		
		layer = (TiledMapTileLayer) Resources.MAP.getLayers().get("EnemySpawn");
		
		for(int i = 0; i < layer.getHeight(); i++) {
			for(int j = 0; j < layer.getWidth(); j++) {
				Cell cell = layer.getCell(j, i);
				
				if(cell != null && cell.getTile().getObjects().getCount() == 1) {
					float worldX = j * layer.getTileWidth();
		            float worldY = i * layer.getTileHeight();
		            
		            enemies.add(new OnlineClientEnemy(world, worldX + 12, worldY + 12, camera));
		            //enemies.get(enemies.size()-1).setIndex(enemies.size()-1);
				}
			}
		}
		
		
	}

	@Override
	public void render(float delta) {
		camera.update();
		Render.tiledMapRenderer.setView(camera);
		Render.tiledMapRenderer.render();
		Render.batch.setProjectionMatrix(camera.combined);
		
		//debugRenderer.render(world, camera.combined);
		
		Render.batch.begin();
		
		// Hud's
		menuPause.draw();
		
		// Stepping the simulation
		world.step(1/144f, 6, 2);
		
		// Player movement and sprite

		if(whiteStickman.isAlive()) {
			whiteStickman.draw();						
		}
		else {
			menuPause.display();
			if(!gameOver) {
				world.destroyBody(whiteStickman.getWeapon());
				world.destroyBody(whiteStickman.getBody());
				gameOver = true;
			}
		}
		player2.draw();
		
		Iterator<OnlineClientEnemy> iterator = enemies.iterator();
		
		while(iterator.hasNext()) {
			OnlineClientEnemy e = iterator.next();
			
			if(e.isDrawable() && e.isAlive()) {
				e.draw();
			}else if(!e.isAlive()) {
				iterator.remove();
				//enemies.remove(enemies.indexOf(e));
			}
		}
		
		
		Render.batch.end();
		
	}
	
	public void destroyPlayer(OnlinePlayer player) {
		world.destroyBody(player.getWeapon());
		world.destroyBody(player.getBody());
	}
	
	public void destroyEnemy(int index) {
		//for(int i = 0; i < enemies.size-1; i++) {
			//if(enemies.get(i).index == index){
				enemies.get(index).setdead();
				world.destroyBody(enemies.get(index).getWeapon());
				world.destroyBody(enemies.get(index).getBody());	
			//}
		//}

	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		world.dispose();
		debugRenderer.dispose();
	}

	@Override
	public void keyDown(int keycode) {
		switch(keycode) {
		
		case Keys.SPACE:
			
			whiteStickman.dash();
			break;
			
		case Keys.ESCAPE:
			menuPause.display();
			break;
		} 
	}
	
	public ArrayList<OnlineClientEnemy> getEnemies(){
		return enemies;
	}
	
}
