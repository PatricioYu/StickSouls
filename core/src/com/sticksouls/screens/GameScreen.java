package com.sticksouls.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.sticksouls.StickSouls;
import com.sticksouls.characters.WhiteStickman;
import com.sticksouls.inputs.InputsListener;
import com.sticksouls.inputs.MyInput;
import com.sticksouls.utils.Render;

public class GameScreen implements Screen, MyInput{
	
	private final StickSouls GAME;
	private InputMultiplexer inputHandler;
	private WhiteStickman whiteStickman;
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;	
	
	public GameScreen(final StickSouls GAME) {
		this.GAME = GAME;
		
		// Create a new OrthographicCamera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// Set an initial zoom level
		camera.zoom = 1.0f;
		
		// Initialize Box2D
		Box2D.init();
		// Create world and setup debugRenderer
		world = new World(new Vector2(0, 0), true);
		debugRenderer = new Box2DDebugRenderer();
		
		whiteStickman = new WhiteStickman(world, 0, 0);
	}

	@Override
	public void show() {
		InputsListener.addInputs(this);
		InputsListener.setActualIndex(this);
	}

	@Override
	public void render(float delta) {
		Render.batch.begin();
		
		// Stepping the simulation
		world.step(1/144f, 6, 2);
		debugRenderer.render(world, camera.combined);
		
		whiteStickman.draw();
		
		Render.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		world.dispose();
		debugRenderer.dispose();
	}

	@Override
	public void keyDown(int keycode) {
		// TODO Auto-generated method stub
		
	}
	
}
