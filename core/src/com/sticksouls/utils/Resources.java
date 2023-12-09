package com.sticksouls.utils;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public abstract class Resources {
	
	// assets
	public static final String MENU_FONT = "fonts/alagard.ttf";
	public static final String WHITE_STICKMAN_SPRITESHEET = "sprites/walkingSpritesheet.png";
	public static final String BLACK_STICKMAN_SPRITESHEET = "sprites/walkingSpritesheetBlackVer.png";
	public static final String SWORD = "sprites/swordSprite.png";
	
	// maps
	public static final TiledMap MAP = new TmxMapLoader().load("maps/map1.tmx");
	
	
}
