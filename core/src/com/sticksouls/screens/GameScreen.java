package com.sticksouls.screens;

import java.util.ArrayList;

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
import com.sticksouls.characters.WhiteStickman;
import com.sticksouls.enemies.Enemy;
import com.sticksouls.hud.PauseHud;
import com.sticksouls.inputs.InputsListener;
import com.sticksouls.inputs.MyInput;
import com.sticksouls.utils.Render;
import com.sticksouls.utils.Resources;

public class GameScreen implements Screen, MyInput{
	
	private final StickSouls GAME;
	private InputMultiplexer inputHandler;
	private WhiteStickman whiteStickman;
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;
	private PauseHud menuPause;
	
	public GameScreen(final StickSouls GAME) {
		this.GAME = GAME;
		menuPause = new PauseHud(GAME);
		
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
		
		whiteStickman = new WhiteStickman(world, 0, 0, camera);
		enemies.add(new Enemy(world, 50, 50, camera));
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
		whiteStickman.draw();			
		for(Enemy e: enemies){
			e.draw();
		}
		
		
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
		Resources.ENEMY_TEXTURE.dispose();
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
	
	
}
