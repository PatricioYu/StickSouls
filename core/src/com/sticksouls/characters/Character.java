package com.sticksouls.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.sticksouls.enums.CharacterState;

public abstract class Character {
	protected final int WIDTH = 32, HEIGHT = 32;
	protected int hp, stamina, currency;
	protected float frameDuration = 0.1f, stateTime;
	protected Inventory inventory;
	protected Sprite idleSprite;
	protected Texture spriteSheet;
	protected TextureRegion[][] frames;
	protected TextureRegion[] walkFrames;
	protected Animation<TextureRegion> walkAnimation;
	protected Body characterBody;
	protected CharacterState state;

	protected Character(World world, float x, float y, int hp, int stamina, int currency) {
		this.hp = hp;
		this.stamina = stamina;
		this.currency = currency;
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		
		characterBody = world.createBody(bodyDef);
		
		// Create a circle shape and set its radius to 6
		CircleShape circle = new CircleShape();
		circle.setRadius(6f);
		
		// Create a fixture definition to apply our shape to
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.4f;
		
		// Create our fixture and attach it to the body
		Fixture fixture = characterBody.createFixture(fixtureDef);
		
		circle.dispose();
		
		characterBody.setTransform(x, y, 0);
		
		state = CharacterState.IDLE;
		inventory = new Inventory();
	}

	// getters
	public int getHp() {
		return hp;
	}

	public int getStamina() {
		return stamina;
	}

	public int getCurrency() {
		return currency;
	}
	
	public Sprite getSprite() {
		return idleSprite;
	}
}
