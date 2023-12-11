package com.sticksouls.redes.cliente;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sticksouls.StickSouls;
import com.sticksouls.hud.Hud;
import com.sticksouls.inputs.InputsListener;
import com.sticksouls.inputs.MyInput;
import com.sticksouls.redes.RedUtils;
import com.sticksouls.redes.cliente.juego.GameScreenClient;
import com.sticksouls.screens.MenuScreen;
import com.sticksouls.utils.FontStyle;
import com.sticksouls.utils.Render;
import com.sticksouls.utils.Resources;

public class ClientScreen extends Hud implements Screen, MyInput{
	
	final StickSouls GAME;
	private Cliente cliente;
	
	private Table clientTable;
	private Table options;
	private Label title;
	private Label[] optionsText;
	private Label.LabelStyle titleStyle, optionsStyle, optionSelectedStyle;
	
	private int selected = -1;
	private boolean ready = false;
	private boolean player1;
	
	
	public ClientScreen(final StickSouls GAME) {
		this.GAME = GAME;
		super.visible = true;
		
		cliente = new Cliente(this);
	}
	
	public void ready(boolean player1) {
		this.player1 = player1;
		ready = true;
	}
	
	
	@Override
	public void show() {
		InputsListener.addInputs(this);
		InputsListener.setMyIndex(this);
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 1);
		Render.batch.begin();
		if(ready) {
			GAME.setScreen(new GameScreenClient(GAME, cliente, player1));
		}
		this.draw();			
	
		Render.batch.end();
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
		// back
		case 0:
			GAME.setScreen(new MenuScreen(GAME));
			RedUtils.hiloCliente.sendMessage("disconnect");
			cliente.closeClient();
			break;
			
		}
	}

	// Hud
	
	@Override
	public void display() {
		
	}
	
	@Override
	public void close() {
	
	}
	
	@Override
	protected void createFonts() {
		titleStyle = FontStyle.generateFont(50, "#ffffff", false, Resources.MENU_FONT);
		optionsStyle = FontStyle.generateFont(50, "#ffffff", false, Resources.MENU_FONT);
		optionSelectedStyle = FontStyle.generateFont(50, "#ffff00", true, Resources.MENU_FONT);
	}
	
	@Override
	protected void createActors() {
		
		clientTable = new Table();
		clientTable.setFillParent(true);
		clientTable.debug();
		
		options = new Table();
		
		//Labels
		title = new Label("Esperando al otro jugador", titleStyle);
		
		optionsText = new Label[1];
		optionsText[0] = new Label("Volver", optionsStyle);
	}
	
	@Override
	protected void populateStage() {
		options.add(optionsText[0]);
		options.row();

		clientTable.add(title).padTop(10);
		clientTable.row();
		clientTable.add(options).expand();
		clientTable.row();
		
		super.stage.addActor(clientTable);
	}
	
	@Override
	public void draw() {
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
		
		clientTable.act(Gdx.graphics.getDeltaTime());

		super.stage.draw();
	}
	


	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		stage.dispose();
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
	

}
