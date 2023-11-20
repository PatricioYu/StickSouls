package com.sticksouls.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.sticksouls.utils.Render;
import com.sticksouls.utils.Resources;

public class WhiteStickman extends Character {

	public WhiteStickman(World world, float x, float y) {
		super(world, x, y, 100, 100, 50);

		spriteSheet = new Texture(Resources.WHITE_STICKMAN_SPRITESHEET);
		frames = TextureRegion.split(spriteSheet, WIDTH, HEIGHT);
		
		idleSprite = new Sprite(frames[0][0], 0, 0, WIDTH, HEIGHT);
		
		walkFrames = new TextureRegion[3];
		
		for(int i = 1; i < 4; i++) {
			walkFrames[i-1] = frames[0][i];
		}
		
		walkAnimation = new Animation<TextureRegion>(0.025f, walkFrames);
	
		stateTime = 0f;
	}
	
	public void draw() {
		idleSprite.draw(Render.batch);
	}
}