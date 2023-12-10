package com.sticksouls.redes.servidor;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sticksouls.utils.FontStyle;
import com.sticksouls.utils.Resources;

public class ConsolaDebug{

	private ScreenViewport screenViewport;
	private Stage stage;
	private Table contenedor, tabla;
	private ArrayList<Label> mensajes;
    private ScrollPane scrollPane;
	
	private Label.LabelStyle labelStyle;
	
	public ConsolaDebug() {
    	screenViewport = new ScreenViewport();
        stage = new Stage(screenViewport);
		crearFuentes();
		crearActores();
		poblarStage();
	}

	private void poblarStage() {

		scrollPane = new ScrollPane(contenedor);
		tabla.add(contenedor);
		stage.addActor(tabla);
        scrollPane.setFillParent(true);
        scrollPane.setScrollingDisabled(true, false);
		
	}

	private void crearActores() {
		tabla = new Table();
		tabla.setDebug(true);
		tabla.setFillParent(true);
		contenedor = new Table();
		contenedor.setFillParent(false);
		contenedor.setDebug(false);
		mensajes = new ArrayList<Label>();
		
	}

	private void crearFuentes() {
		labelStyle = FontStyle.generateFont(23, "#ffffff", false, Resources.MENU_FONT);	}
	
	public void render() {
		stage.draw();
		
	}
	
    public void reEscalar(int width, int heigth) {
    	screenViewport.update(width, heigth, true);
    }
	
	public void agregarMensajes(String msg) {
		mensajes.add(new Label(msg, labelStyle));
		actualizarTabla();
	}
	
	public void actualizarTabla() {
		if(contenedor.getChildren().size < mensajes.size()) {
			contenedor.clear();
			for (Label label : mensajes) {
				contenedor.add(label).row();
			}
		}
	}
	
}