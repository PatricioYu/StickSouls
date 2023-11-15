package com.sticksouls.hud;

public interface Hud {
	void createFonts();
	void createActors();
	void populateStage();
	void resize(int width, int height);
	void render();
}
