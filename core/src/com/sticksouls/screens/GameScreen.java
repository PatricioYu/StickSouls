package com.sticksouls.screens;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.sticksouls.StickSouls;
import com.sticksouls.inputs.InputsListener;
import com.sticksouls.inputs.MyInput;

public class GameScreen implements Screen, MyInput{
	
	private final StickSouls GAME;
	private InputMultiplexer inputHandler;
	
	public GameScreen(final StickSouls GAME) {
		this.GAME = GAME;
	}

	@Override
	public void show() {
		InputsListener.addInputs(this);
		InputsListener.setActualIndex(this);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
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
		
	}

	@Override
	public void keyDown(int keycode) {
		// TODO Auto-generated method stub
		
	}
	
}
