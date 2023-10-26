package com.sticksouls;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class StickSouls extends ApplicationAdapter {
	AssetManager assetManager;
	SpriteBatch batch;
	OrthographicCamera camera;	
	
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
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		
		// check if the assets are already loaded
		if(assetManager.update()) {
			batch.draw(assetManager.get("badlogic.jpg", Texture.class), 0, 0);
		}
		
		batch.end();
	}
	
	@Override
	public void dispose () {
		assetManager.dispose();
		batch.dispose();
	}
}
