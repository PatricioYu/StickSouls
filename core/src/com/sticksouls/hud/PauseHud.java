package com.sticksouls.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.sticksouls.StickSouls;
import com.sticksouls.inputs.InputsListener;
import com.sticksouls.inputs.MyInput;
import com.sticksouls.redes.cliente.Cliente;
import com.sticksouls.redes.servidor.Servidor;
import com.sticksouls.screens.MenuScreen;
import com.sticksouls.utils.FontStyle;
import com.sticksouls.utils.Resources;

public class PauseHud extends Hud implements MyInput {
	
	final StickSouls GAME;
	private ConfigurationsHud configurationsHud;
	
	private Table pauseTable;
	private Table options;
	
	private Label[] optionsText;
	private Label.LabelStyle optionsStyle, optionSelectedStyle;
	
	private int selected = -1;
	private Cliente cliente;
	private Servidor servidor;
		
	public PauseHud(final StickSouls GAME) {
		this.GAME = GAME;
		
		InputsListener.addInputs(this);
	}
	
	public PauseHud(final StickSouls GAME, Servidor servidor) {
		this.GAME = GAME;
		this.servidor = servidor;
		
		InputsListener.addInputs(this);
	}
	
	public PauseHud(final StickSouls GAME, Cliente cliente) {
		this.GAME = GAME;
		this.cliente = cliente;
		
		InputsListener.addInputs(this);
	}
	
	
	@Override
	protected void createFonts() {
		optionsStyle = FontStyle.generateFont(50, "#ffffff", false, Resources.MENU_FONT);
		optionSelectedStyle = FontStyle.generateFont(50, "#ffff00", true, Resources.MENU_FONT);
	}

	@Override
	protected void createActors() {
		pauseTable = new Table();
		pauseTable.setFillParent(true);
		pauseTable.debug();
		
		options = new Table();
		
		optionsText = new Label[3];
		optionsText[0] = new Label("Resume", optionsStyle);
		optionsText[1] = new Label("Configurations", optionsStyle);
		optionsText[2] = new Label("Exit", optionsStyle);
		
	}

	@Override
	protected void populateStage() {
		options.add(optionsText[0]);
		options.row();
		options.add(optionsText[1]);
		options.row();
		options.add(optionsText[2]);
		options.row();
		
		pauseTable.add(options).expand();
		pauseTable.row();
		
		super.stage.addActor(pauseTable);

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
			
			super.stage.draw();
		}
		if(configurationsHud != null) {
			configurationsHud.draw();			
		}
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
		switch(selected) {
		case 0:
			this.close();
			break;
		case 1:
			if(configurationsHud == null) {
				configurationsHud = new ConfigurationsHud();
				InputsListener.setPreviousIndex();
			}
			
			super.visible = false;
			configurationsHud.display();
			
			break;
		case 2:
			if(cliente != null) {
				cliente.closeClient();
			}
			if(servidor != null) {
				servidor.closeServer();
			}
				
			
			GAME.setScreen(new MenuScreen(GAME));
			break;
		}
	}

	@Override
	public void keyDown(int keycode) {
		switch (keycode){
		case Keys.ESCAPE:
			this.close();
			break;
		case Keys.UP:
		case Keys.LEFT:
			if((selected - 1) >= 0) {
				selected--;
				optionSelected(optionsText, selected);
			}
			if (selected == -1) {
				selected = 0;
				optionSelected(optionsText, selected);
			}
			break;
		case Keys.DOWN:
		case Keys.RIGHT:
			if((selected + 1) <= optionsText.length -1) {
				selected++;
				optionSelected(optionsText, selected);
			}
			
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

}
