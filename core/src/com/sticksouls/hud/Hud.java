package com.sticksouls.hud;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sticksouls.inputs.InputsListener;
import com.sticksouls.inputs.MyInput;

public abstract class Hud{
	
	private ScreenViewport viewPort;
	protected Stage stage;
	public boolean visible;
	
	public Hud() {
		viewPort = new ScreenViewport();
        stage = new Stage(viewPort);
		createFonts();
		createActors();
		populateStage();
	}
	
	public void resize(int width, int height) {
		viewPort.update(width, height, true);
	}
	
	public abstract void display();
	public abstract void close();
	
	protected abstract void createFonts();
	protected abstract void createActors();
	protected abstract void populateStage();
	public abstract void draw();
	
}
