package com.sticksouls.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

public class Menu implements Screen, Hud {
	
	final private StickSouls game;
	private ScreenViewport viewPort;
	private Stage stage;
	private OrthographicCamera camera;
	
	private Table interfaz;
	private Table options;
	
	private Label title;
	private Label[] optionsText;
	private Label.LabelStyle titleStyle, optionsStyle;
	
	public Menu(final StickSouls game) {
		this.game = game;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		createFonts();
		createActors();
		populateStage();
		
		Render.batch = game.batch;
	}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 1);
		camera.update();
		Render.batch.setProjectionMatrix(camera.combined);
		Render.batch.begin();

		for(int i = 0; i < optionsText.length; i++) {
			Vector2 mouseScreenPosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
			Vector2 mouseLocalPosition = optionsText[i].screenToLocalCoordinates(mouseScreenPosition);

			if(optionsText[i].hit(mouseLocalPosition.x, mouseLocalPosition.y, false) != null ) {
			    //System.out.println("Dentro de " + optionsText[i].getText());
				
				if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
					System.out.println("Clic izquierdo en " + optionsText[i].getText());
					
				}
			}
		}
		
		Render.batch.end();
		
		interfaz.act(delta);
		
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		viewPort.update(width, height, true);
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
	public void createFonts() {
		titleStyle = FontStyle.generateFont(80, "#ffffff", true, Resources.MENU_FONT);
		optionsStyle = FontStyle.generateFont(50, "#ffffff", true, Resources.MENU_FONT);
	}

	@Override
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
		
		optionsText[0].setBounds(optionsText[0].getX(), optionsText[0].getY(), optionsText[0].getWidth(), optionsText[0].getHeight());
		
	}

	@Override
	public void populateStage() {
		
		interfaz.add(title).padTop(10);
		interfaz.row();
		
		//Tabla opciones
		options.add(optionsText[0]);
		options.row();
		options.add(optionsText[1]);
		options.row();
		options.add(optionsText[2]);
		options.row();
		
		
		interfaz.add(options).expand();
		interfaz.row();
		
		
		stage.addActor(interfaz);
	}

	@Override
	public void render() {
		
		
	}

}
