package com.sticksouls;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sticksouls.hud.ConfigurationsHud;
import com.sticksouls.inputs.InputManager;
import com.sticksouls.screens.MenuScreen;
import com.sticksouls.utils.Render;

public class StickSouls extends Game {
	public ConfigurationsHud configurationsHud;
	private AssetManager assetManager;
	private InputManager inputManager;

	@Override
	public void create () {		
		Render.batch = new SpriteBatch();
		
		this.setScreen(new MenuScreen(this));
		
		configurationsHud = new ConfigurationsHud();
		

		
		//Create Inputs
		inputManager = new InputManager();
		
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		super.render();
		configurationsHud.draw();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		Render.batch.dispose();
		
	}
}
