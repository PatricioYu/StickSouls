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
	
	public void toggleToShow() {
		visible = true;
		InputsListener.setActualIndex(this);
    }
	
	public void toggleToHide() {
		visible = false;
		InputsListener.setPreviousIndex();
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
		toggleToHide();
	}

}
