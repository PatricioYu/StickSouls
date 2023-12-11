package com.sticksouls.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sticksouls.enums.CharacterState;
import com.sticksouls.items.weapons.Sword;
import com.sticksouls.utils.Render;
import com.sticksouls.utils.Resources;

public class WhiteStickman extends Character {
	
	private OrthographicCamera camera;
	
	private float dashVelocity = 50000f, dashTime = .3f, dashTimeRemaining = 0f, movementSpeed = 500f;
	// body's x and y
	private float bodyX, bodyY;
	
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
		bodyX = super.characterBody.getPosition().x;
		bodyY = super.characterBody.getPosition().y;
		
		stateTime += Gdx.graphics.getDeltaTime();
		
		if (dashTimeRemaining > 0) {
			dash();
			dashTimeRemaining -= Gdx.graphics.getDeltaTime();
        } else {
			movement();
		}
		
		// Attack
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !attacking) {
			
			Vector3 screenCoordinates = new Vector3(bodyX, bodyY, 0);
			camera.project(screenCoordinates);
			
			weapon.attack(new Vector2(screenCoordinates.x, screenCoordinates.y));
			attacking = true;
		}
		if(attacking) {
			attacking = weapon.continueAttack();
		}
		weapon.draw();			
		
		
		// Animacion de mover, habria que crear un enum con las distintas animaciones y switchearlas
		TextureRegion currentFrame = (state == CharacterState.WALK)?walkAnimation.getKeyFrame(stateTime, true):walkFrames[0];
		
		Render.batch.draw(currentFrame, bodyX - currentFrame.getRegionWidth()/2, bodyY - currentFrame.getRegionHeight()/2);		
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
		
		camera.position.set(bodyX, bodyY, 0);
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