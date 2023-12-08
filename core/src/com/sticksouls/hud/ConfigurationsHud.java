package com.sticksouls.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.sticksouls.inputs.InputsListener;
import com.sticksouls.inputs.MyInput;
import com.sticksouls.screens.GameScreen;
import com.sticksouls.utils.FontStyle;
import com.sticksouls.utils.Resources;

public class ConfigurationsHud extends Hud implements MyInput{
	
	private Table configurationsTable;
	private Table options;
	
	private Label title;
	private Label[] optionsText;
	private Label.LabelStyle optionsStyle, optionSelectedStyle;
	
	private int selected = -1;

	public ConfigurationsHud() {
		InputsListener.addInputs(this);
		InputsListener.setMyIndex(this);
	}
	
	private void optionSelected(Label[] options, int option) {
		for (int i = 0; i < options.length; i++) {
			options[i].setStyle(optionsStyle);
			if(i == option) {
				options[i].setStyle(optionSelectedStyle);
			}
		}
		
		selected = option;
	}
	

	private void selectOption() {
		System.out.println("selectOption" + selected);
		
		
		switch(selected) {
		// play
		case 0:
			this.close();
			break;
		}
	}
	
	@Override
	protected void createFonts() {
		optionsStyle = FontStyle.generateFont(50, "#ffffff", false, Resources.MENU_FONT);
		optionSelectedStyle = FontStyle.generateFont(50, "#ffff00", true, Resources.MENU_FONT);
	}

	@Override
	protected void createActors() {
		configurationsTable = new Table();
		configurationsTable.setFillParent(true);
		configurationsTable.debug();
		
		options = new Table();
		
		optionsText = new Label[3];
		optionsText[0] = new Label("Back", optionsStyle);
		optionsText[1] = new Label("option1", optionsStyle);
		optionsText[2] = new Label("option2", optionsStyle);
	}

	@Override
	protected void populateStage() {
		options.add(optionsText[0]);
		options.row();
		options.add(optionsText[1]);
		options.row();
		options.add(optionsText[2]);
		options.row();
		
		configurationsTable.add(options).expand();
		configurationsTable.row();
		
		super.stage.addActor(configurationsTable);

	}

	@Override
	public void draw() {
		if(super.visible) {
			for(int i = 0; i < optionsText.length; i++) {
				Vector2 mouseScreenPosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
				Vector2 mouseLocalPosition = optionsText[i].screenToLocalCoordinates(mouseScreenPosition);

				if(optionsText[i].hit(mouseLocalPosition.x, mouseLocalPosition.y, false) != null ) {
					optionSelected(optionsText, i);
					
					if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
						selectOption();
					}
				}
			}
			
			configurationsTable.act(Gdx.graphics.getDeltaTime());
	
			super.stage.draw();
		}
	}

	@Override
	public void keyDown(int keycode) {
		switch(keycode) {
		// go up
		case Keys.W:
		case Keys.UP:
			if((selected - 1) >= 0) {
				selected--;
				optionSelected(optionsText, selected);
			}
			if (selected == -1) {
				selected = 0;
				optionSelected(optionsText, selected);
			}
			break;
			
		case Keys.S:
			
		// go down
		case Keys.DOWN:
			if((selected + 1) <= optionsText.length -1) {
				selected++;
				optionSelected(optionsText, selected);
			}
			
			break;
			
		// select
		case Keys.ENTER:
			selectOption();
			
			break;
		}	
	}

	@Override
	public void display() {
		super.visible = true;
		InputsListener.setMyIndex(this);
	}

	@Override
	public void close() {
		super.visible = false;
		InputsListener.setPreviousIndex();	
	}
	
	public Table getTable() {
		return configurationsTable;
	}
	
}
