package com.sticksouls;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sticksouls.screens.MenuScreen;
import com.sticksouls.utils.Render;

public class StickSouls extends Game {
	private AssetManager assetManager;
	public SpriteBatch batch;
	private OrthographicCamera camera;	
	private World world;
	private Box2DDebugRenderer debugRenderer;
	
	@Override
	public void create () {
		// Initialize the AssetManager for asset loading
		assetManager = new AssetManager();

		assetManager.load("badlogic.jpg", Texture.class);
		
				
		// Create a new OrthographicCamera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// Set an initial zoom level
		camera.zoom = 1.0f;
		
		batch = new SpriteBatch();
		Render.batch = batch;
		
		this.setScreen(new MenuScreen(this));
		
		// Initialize Box2D
		Box2D.init();
		// Create world and setup debugRenderer
		world = new World(new Vector2(0, 0), true);
		debugRenderer = new Box2DDebugRenderer();

	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		super.render();
	    	
		// Stepping the simulation
		world.step(1/144f, 6, 2);
		debugRenderer.render(world, camera.combined);
	}
	
	@Override
	public void dispose () {
		super.dispose();
		Render.batch.dispose();
		
	}
}
