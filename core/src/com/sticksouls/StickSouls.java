package com.sticksouls;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sticksouls.Screens.Menu;
import com.sticksouls.utils.Render;

public class StickSouls extends Game {
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Render.batch = batch;
		
		this.setScreen(new Menu(this));
		
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		Render.batch.dispose();
		
	}
}
