package com.sticksouls.hud;

public abstract class Hud {
	
	public Hud() {
		createFonts();
		createActors();
		populateStage();
	}
	
	
	protected abstract void createFonts();
	protected abstract void createActors();
	protected abstract void populateStage();
	protected abstract void resize(int width, int height);
	protected abstract void render();
}
