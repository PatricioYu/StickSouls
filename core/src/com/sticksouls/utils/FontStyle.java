package com.sticksouls.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public abstract class FontStyle {
	public static Label.LabelStyle labelStyle;
	
	public static Label.LabelStyle generateFont (int size, String hex, boolean shadow, String font){
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(font));
	    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
	    
	    parameter.size = size;
	    parameter.color = Color.valueOf(hex);
	    parameter.borderWidth = 1;
	    parameter.minFilter = Texture.TextureFilter.Linear; //esto hace que no se vea tan mal cuando se reescala
	    parameter.magFilter = Texture.TextureFilter.Linear;
	    if(shadow) {
	    	parameter.shadowOffsetX = 3;
	    	parameter.shadowOffsetY = 3;
	    }
	    
	    BitmapFont font24 = generator.generateFont(parameter); // tamaño de la fuente 24 pixeles
	    generator.dispose();
	 
	    labelStyle = new Label.LabelStyle();
	    labelStyle.font = font24;
	    return labelStyle;
		
	}
}
