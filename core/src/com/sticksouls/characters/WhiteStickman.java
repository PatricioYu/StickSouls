package com.sticksouls.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.sticksouls.utils.Render;
import com.sticksouls.utils.Resources;

public class WhiteStickman extends Character {
	
	private OrthographicCamera camera;

	public WhiteStickman(World world, float x, float y, OrthographicCamera camera) {
		super(world, x, y, 100, 100, 50);
		
		this.camera = camera;

		spriteSheet = new Texture(Resources.WHITE_STICKMAN_SPRITESHEET);
		frames = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/COLUMNS, spriteSheet.getHeight()/ROWS);
		
		idleSprite = new Sprite(frames[0][0], 0, 0, 32, 32);
		
		walkFrames = new TextureRegion[3];
		
		for(int i = 1; i < 4; i++) {
			walkFrames[i-1] = frames[0][i];
		}
		
		walkAnimation = new Animation<TextureRegion>(0.025f, walkFrames);
		
		stateTime = 0f;
	}
	
	public void draw() {
		idleSprite.draw(Render.batch);
		movement();
		// Animacion de mover, habria que crear un enum con las distintas animaciones y switchearlas
		Render.batch.draw(walkAnimation.getKeyFrame(stateTime), super.characterBody.getPosition().x, super.characterBody.getPosition().y);
	}
	
	private void movement() {
		float velocidad = 500f;

		super.characterBody.setLinearVelocity(0, 0);
		
		float moveX = Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D) ? 1 : Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A) ? -1 : 0;
        float moveY = Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W) ? 1 : Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S) ? -1 : 0;

		super.characterBody.setLinearVelocity(moveX * velocidad, moveY * velocidad);
		
		camera.position.set(super.characterBody.getPosition().x, super.characterBody.getPosition().y, 0);
		camera.update();
	}
}