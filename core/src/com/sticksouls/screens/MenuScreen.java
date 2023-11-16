package com.sticksouls.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sticksouls.StickSouls;
import com.sticksouls.hud.Hud;
import com.sticksouls.utils.FontStyle;
import com.sticksouls.utils.Render;
import com.sticksouls.utils.Resources;

public class MenuScreen extends Hud implements Screen, InputProcessor{
	
	private final StickSouls GAME;
	private ScreenViewport viewPort;
	private Stage stage;
	private OrthographicCamera camera;
	
	private Table interfaz;
	private Table options;
	
	private Label title;
	private Label[] optionsText;
	private Label.LabelStyle titleStyle, optionsStyle, optionSelectedStyle;
	
	private int selected = 0;
	
	public MenuScreen(final StickSouls GAME) {
		this.GAME = GAME;
		
		//camera = new OrthographicCamera();
		//camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 1);
		//camera.update();
		//Render.batch.setProjectionMatrix(camera.combined);
		Render.batch.begin();

		for(int i = 0; i < optionsText.length; i++) {
			Vector2 mouseScreenPosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
			Vector2 mouseLocalPosition = optionsText[i].screenToLocalCoordinates(mouseScreenPosition);

			if(optionsText[i].hit(mouseLocalPosition.x, mouseLocalPosition.y, false) != null ) {
			    //System.out.println("Dentro de " + optionsText[i].getText());
				
				optionSelected(optionsText, i);
				
				if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
					System.out.println("Clic izquierdo en " + optionsText[i].getText());
					
					selectOption();
				}
			}
		}
		
		Render.batch.end();
		
		interfaz.act(delta);
		
		stage.draw();
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
			GAME.setScreen(new GameScreen());
			
			break;
			
		case 2:
	        Gdx.app.exit();
			
			break;
		}
	}

	@Override
	public void resize(int width, int height) {
		viewPort.update(width, height, true);
	}

	@Override
	public void dispose() {
		stage.dispose();
		
	}

	
	@Override
	public void createFonts() {
		titleStyle = FontStyle.generateFont(80, "#ffffff", false, Resources.MENU_FONT);
		optionsStyle = FontStyle.generateFont(50, "#ffffff", false, Resources.MENU_FONT);
		optionSelectedStyle = FontStyle.generateFont(50, "#ffff00", true, Resources.MENU_FONT);
		
	}

	public void createActors() {
		viewPort = new ScreenViewport();
		stage = new Stage(viewPort);
		
		//Tablas
		interfaz = new Table();
		interfaz.setFillParent(true);
		interfaz.debug();
		
		options = new Table();
		
		//Labels
		title = new Label("StickSouls", titleStyle);
		
		optionsText = new Label[3];
		
		optionsText[0] = new Label("Jugar", optionsStyle);
		optionsText[1] = new Label("Configuraciones", optionsStyle);
		optionsText[2] = new Label("Salir", optionsStyle);
		
		//optionsText[0].setBounds(optionsText[0].getX(), optionsText[0].getY(), optionsText[0].getWidth(), optionsText[0].getHeight());
	}

	public void populateStage() {
		//Tabla opciones
		options.add(optionsText[0]);
		options.row();
		options.add(optionsText[1]);
		options.row();
		options.add(optionsText[2]);
		options.row();
		
		
		interfaz.add(title).padTop(10);
		interfaz.row();
		interfaz.add(options).expand();
		interfaz.row();
		
		stage.addActor(interfaz);
	}
	

	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		// go up
		case Keys.W:
		case Keys.UP:
			if((selected - 1) >= 0) {
				selected--;
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
		
		System.out.println(keycode);
		
		return false;
	}
	
	//Funciones y metodos implementados sin usar
	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	public void render() {
		
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}

}
