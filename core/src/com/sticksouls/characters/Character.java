package com.sticksouls.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.sticksouls.enums.CharacterState;
import com.sticksouls.items.weapons.Weapon;

public abstract class Character {
	protected final int ROWS = 1, COLUMNS = 4;
	protected int hp = 100, stamina, currency;
	protected float frameDuration = 0.1f, stateTime;
	protected Weapon weapon;
	// protected Sprite idleSprite;
	protected Texture spriteSheet;
	protected TextureRegion[][] frames;
	protected TextureRegion[] walkFrames;
	protected Animation<TextureRegion> walkAnimation;
	protected Body characterBody;
	protected CharacterState state;
	protected boolean alive = true;

	protected Character(World world, float x, float y, int hp, int stamina, int currency) {
		this.hp = hp;
		this.stamina = stamina;
		this.currency = currency;
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		bodyDef.fixedRotation = true;
		
		characterBody = world.createBody(bodyDef);
		
		// Create a circle shape and set its radius to 6
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(5, 12);
		
		// Create a fixture definition to apply our shape to
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1f;
		fixtureDef.friction = 0.4f;
		
		// Attach the fixture to the body
		characterBody.createFixture(fixtureDef);
				
		shape.dispose();
		
		characterBody.setTransform(x, y, 0);
		characterBody.setUserData(this);
		state = CharacterState.IDLE;
	}

	public void reduceHp(int dmg) {
		if((hp - dmg) > 0) {
			hp -= dmg;			
		} else {
			hp = 0;
			alive = false;
		}
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
	
	public boolean isAlive() {
		return alive;
	}
	
	public Body getWeapon() {
		return this.weapon.getBody();
	}
	
	public Body getBody() {
		return this.characterBody;
	}
	
	public Vector2 getBodyPosition() {
		return this.characterBody.getPosition();
	}
	
}
