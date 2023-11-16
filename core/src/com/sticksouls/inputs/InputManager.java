package com.sticksouls.inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

public class InputManager extends InputAdapter {
	
	public InputManager() {
		Gdx.input.setInputProcessor(this);
	}
	
	 @Override
	 public boolean keyDown(int keycode) {
		 InputsListener.processKeyboardInputs(keycode);
		 return true; // Indica que la tecla ha sido manejada
	 }

}
