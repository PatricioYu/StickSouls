package com.sticksouls.redes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.sticksouls.characters.Character;
import com.sticksouls.enums.CharacterState;
import com.sticksouls.enums.Directions;
import com.sticksouls.items.weapons.Sword;
import com.sticksouls.utils.Render;
import com.sticksouls.utils.Resources;

public class OnlinePlayer extends Character {
	
	private TextureRegion currentFrame;
	private boolean attacking;
	
	public OnlinePlayer(World world, float x, float y, OrthographicCamera camera) {
		super(world, x, y, 100, 100, 50);

		spriteSheet = new Texture(Resources.WHITE_STICKMAN_SPRITESHEET);
		frames = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/COLUMNS, spriteSheet.getHeight()/ROWS);
		
		//idleSprite = new Sprite(frames[0][0], 0, 0, 32, 32);
		
		walkFrames = new TextureRegion[3];
		
		for(int i = 1; i < 4; i++) {
			walkFrames[i-1] = frames[0][i];
		}
		
		walkAnimation = new Animation<TextureRegion>(0.1f, walkFrames);
		currentFrame = walkFrames[0];
		
		super.weapon = new Sword(super.characterBody, world);
		stateTime = 0f;
	}
	
	public void draw() {

		stateTime += Gdx.graphics.getDeltaTime();
		

		if(attacking) {
			attacking = weapon.continueAttack();
		}
		
		weapon.draw();
		
		TextureRegion currentFrame = (state == CharacterState.WALK)?walkAnimation.getKeyFrame(stateTime, true):walkFrames[0];
		
		Render.batch.draw(currentFrame, super.characterBody.getPosition().x - currentFrame.getRegionWidth()/2, super.characterBody.getPosition().y - currentFrame.getRegionHeight()/2);		
		
	}
	
	public void attack(Directions direction) {
		if(!attacking) {
			attacking = true;
			weapon.attack(direction);
		}
	}
	
	public void movement(Vector2 linearVelocity) {
		super.characterBody.setLinearVelocity(linearVelocity);
		
		if(linearVelocity.x != 0) {
        	state = CharacterState.WALK;
        } else {
        	state = CharacterState.IDLE;
        }
        
	}
}
