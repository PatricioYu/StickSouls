package com.sticksouls.hud;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.sticksouls.inputs.InputsListener;
import com.sticksouls.inputs.MyInput;

public class ConfigurationsHud extends Hud implements MyInput{
	
	private boolean visible = false;
	private Table configurationsTable;
	
	public ConfigurationsHud() {
		InputsListener.addInputs(this);
		
	}
	
	@Override
	protected void createFonts() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void createActors() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void populateStage() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyDown(int keycode) {
		close();
	}

	@Override
	public void display() {
		visible = true;
		InputsListener.setActualIndex(this);
	}

	@Override
	public void close() {
		visible = false;
		InputsListener.setPreviousIndex();	
	}

}
