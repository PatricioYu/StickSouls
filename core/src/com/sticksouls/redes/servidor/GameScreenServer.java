package com.sticksouls.redes.servidor;

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
import com.sticksouls.StickSouls;
import com.sticksouls.contactlistener.MyContactListener;
import com.sticksouls.enemies.Enemy;
import com.sticksouls.hud.PauseHud;
import com.sticksouls.inputs.InputsListener;
import com.sticksouls.inputs.MyInput;
import com.sticksouls.redes.OnlinePlayer;
import com.sticksouls.redes.RedUtils;
import com.sticksouls.utils.Render;
import com.sticksouls.utils.Resources;

public class GameScreenServer implements Screen, MyInput{
	
	public OnlinePlayer player1, player2;
	private final StickSouls GAME;
	private InputMultiplexer inputHandler;
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;
	private PauseHud menuPause;

	private MyContactListener contactListener;
	private Servidor servidor;
		
	public GameScreenServer(final StickSouls GAME, Servidor servidor) {
		this.GAME = GAME;
		this.servidor = servidor;
		menuPause = new PauseHud(GAME, servidor);
		
		// Create a new OrthographicCamera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// Set an initial zoom level
		camera.zoom = 0.6f;
		// Initialize Box2D
		Box2D.init();
		// Create world and setup debugRenderer
		world = new World(new Vector2(0, 0), true);
		contactListener = new MyContactListener();
		world.setContactListener(contactListener);
		
		RedUtils.hiloServidor.setGame(this);
		
	}

	@Override
	public void show() {
		InputsListener.addInputs(this);
		InputsListener.setMyIndex(this);
		
		debugRenderer = new Box2DDebugRenderer();
		
		player1 = new OnlinePlayer(world, 0, 0, camera);
		player2 = new OnlinePlayer(world, 30, 0, camera);

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
		            
		            enemies.add(new Enemy(world, worldX + 12, worldY + 12, camera, true));
		            enemies.get(enemies.size() - 1).setMyIndex(enemies.size() - 1);
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
		
		Render.batch.begin();
		
		player1.draw();			
		player2.draw();
		
		world.step(1/144f, 6, 2);
	
		
		Iterator<Enemy> iterator = enemies.iterator();
		
		while(iterator.hasNext()) {
			Enemy e = iterator.next();
			
			if(e.isAlive()) {
				if(e.getBody().getPosition().dst(player1.getBodyPosition()) < e.getBody().getPosition().dst(player2.getBodyPosition())) {
					e.draw(player1.getBodyPosition());					
				}else {
					e.draw(player2.getBodyPosition());					
				}
				RedUtils.hiloServidor.sendAllMessage("enemy#" + enemies.indexOf(e) + "#" + e.getBody().getLinearVelocity().x + "#" + e.getBody().getLinearVelocity().y);
			} else {
				RedUtils.hiloServidor.sendAllMessage("destroyEnemy#" + enemies.indexOf(e));
				world.destroyBody(e.getWeapon());
				world.destroyBody(e.getBody());
				
				iterator.remove();
				
				//enemies.remove(enemies.indexOf(e));
			}	
		}

		//debugRenderer.render(world, camera.combined);		
		
		menuPause.draw();
		Render.batch.end();
		
		
		
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
		case Keys.W:
			camera.position.set(camera.position.x, camera.position.y + 20, 0);
			break;
		case Keys.A:
			camera.position.set(camera.position.x - 20, camera.position.y, 0);
			break;
		case Keys.S:
			camera.position.set(camera.position.x, camera.position.y - 20, 0);
			break;
		case Keys.D:
			camera.position.set(camera.position.x + 20, camera.position.y, 0);
			break;
		

		case Keys.ESCAPE:
			menuPause.display();
			break;
		} 
	}
	
	public void destroyPlayer(OnlinePlayer player) {
		world.destroyBody(player.getWeapon());
		world.destroyBody(player.getBody());
	}
	
	public ArrayList<Enemy> getEnemies(){
		return enemies;
	}
}
