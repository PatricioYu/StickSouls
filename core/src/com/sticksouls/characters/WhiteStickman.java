package com.sticksouls.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.sticksouls.enums.CharacterState;
import com.sticksouls.items.weapons.Sword;
import com.sticksouls.utils.Render;
import com.sticksouls.utils.Resources;

public class WhiteStickman extends Character {
	
	private OrthographicCamera camera;
	
	private float dashVelocity = 50000f;
	private float dashTime = .3f; 
	private float dashTimeRemaining = 0f;
	private float movementSpeed = 500f;
	private boolean attacking = false;

	public WhiteStickman(World world, float x, float y, OrthographicCamera camera) {
		super(world, x, y, 100, 100, 50);
		this.camera = camera;

		spriteSheet = new Texture(Resources.WHITE_STICKMAN_SPRITESHEET);
		frames = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/COLUMNS, spriteSheet.getHeight()/ROWS);
		
		//idleSprite = new Sprite(frames[0][0], 0, 0, 32, 32);
		
		walkFrames = new TextureRegion[3];
		
		for(int i = 1; i < 4; i++) {
			walkFrames[i-1] = frames[0][i];
		}
		
		walkAnimation = new Animation<TextureRegion>(0.1f, walkFrames);
		
		super.weapon = new Sword(super.characterBody, world);
		stateTime = 0f;
	}
	
	public void draw() {
		stateTime += Gdx.graphics.getDeltaTime();
		//idleSprite.draw(Render.batch);
		if (dashTimeRemaining > 0) {
			dash();
			dashTimeRemaining -= Gdx.graphics.getDeltaTime();
        } else {
			movement();
		}
		
		if(Gdx.input.isTouched()) {
			weapon.attack();
			attacking = true;
		}
		if(!attacking) {
			weapon.draw();			
		}
		
		// Animacion de mover, habria que crear un enum con las distintas animaciones y switchearlas
		TextureRegion currentFrame = (state == CharacterState.WALK)?walkAnimation.getKeyFrame(stateTime, true):walkFrames[0];
		
		Render.batch.draw(currentFrame, super.characterBody.getPosition().x - currentFrame.getRegionWidth()/2, super.characterBody.getPosition().y - currentFrame.getRegionHeight()/2);
		
	}
	
	private void movement() {
		super.characterBody.setLinearVelocity(0, 0);
		
		float moveX = Gdx.input.isKeyPressed(Keys.D) ? 1 : Gdx.input.isKeyPressed(Keys.A) ? -1 : 0;
        float moveY = Gdx.input.isKeyPressed(Keys.W) ? 1 : Gdx.input.isKeyPressed(Keys.S) ? -1 : 0;

        if(moveX != 0) {
        	state = CharacterState.WALK;
        } else {
        	state = CharacterState.IDLE;
        }
        
		super.characterBody.setLinearVelocity(moveX * movementSpeed , moveY * movementSpeed);
		
		camera.position.set(super.characterBody.getPosition().x, super.characterBody.getPosition().y, 0);
		camera.update();
	}
	
	public void dash() {

		if (dashTimeRemaining > 0) {
			float moveX = Gdx.input.isKeyPressed(Keys.D) ? 1 : Gdx.input.isKeyPressed(Keys.A) ? -1 : 0;
	        float moveY = Gdx.input.isKeyPressed(Keys.W) ? 1 : Gdx.input.isKeyPressed(Keys.S) ? -1 : 0;
			
	        Vector2 impulse = new Vector2(dashVelocity * moveX, dashVelocity * moveY);
	        
            super.characterBody.applyLinearImpulse(impulse, super.characterBody.getLocalCenter(), true);
		} else {
            dashTimeRemaining = dashTime;
        }
    }
	
}