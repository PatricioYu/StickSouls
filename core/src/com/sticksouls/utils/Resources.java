package com.sticksouls.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public abstract class Resources {
	/* assets/fonts */
	public static final String MENU_FONT = "fonts/alagard.ttf";
	
	/* assets/sprites */
	// - characters
	public static final String WHITE_STICKMAN_SPRITESHEET = "sprites/walkingSpritesheet.png";
	public static final String BLACK_STICKMAN_SPRITESHEET = "sprites/walkingSpritesheetBlackVer.png";
	
	// - enemies
	public static final String RED_VER_ENEMY_IDLE = "sprites/idleRedVer.png";
	public static final Texture ENEMY_TEXTURE = new Texture(RED_VER_ENEMY_IDLE);

	// - weapons
	public static final String SWORD = "sprites/swordSprite.png";
	
	/* assets/maps */
	public static final TiledMap MAP = new TmxMapLoader().load("maps/map1.tmx");
	
	
}
