package com.sticksouls.redes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.sticksouls.items.weapons.Sword;
import com.sticksouls.items.weapons.Weapon;
import com.sticksouls.utils.Render;
import com.sticksouls.utils.Resources;

public class OnlineClientEnemy {
	protected int hp = 100;
	protected float bodyX, bodyY;
	protected boolean alive = true;
	protected Body enemyBody;
	protected Sprite sprite;
	private OrthographicCamera camera;
	private Weapon weapon;
	
	float velocidad = 100.0f; // Puedes ajustar esta velocidad según tus necesidades	
	private int attackTime = 100, attackTimeRemaining = 5;
	private boolean attacking = false, drawable = false;
	private World world;
	public int index;
	
	public OnlineClientEnemy(World world, float x, float y, OrthographicCamera camera) {
		this.camera = camera;
		this.world = world;
		sprite = new Sprite(Resources.ENEMY_TEXTURE);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		bodyDef.fixedRotation = true;
		
		enemyBody = world.createBody(bodyDef);
	
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(5, 12);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1f;
		fixtureDef.friction = 0.4f;
		
		enemyBody.createFixture(fixtureDef);
		
		shape.dispose();
		
		enemyBody.setTransform(x, y, 0);
		
		enemyBody.setUserData(this);
		
		this.weapon = new Sword(enemyBody, world, true);
	}
	
	public void draw() {
		bodyX = enemyBody.getPosition().x;
		bodyY = enemyBody.getPosition().y;
	
		camera.update();
		
		attackTimeRemaining -= Gdx.graphics.getDeltaTime();
		
		if(attacking) {
			attacking = weapon.continueAttack();
			if(!attacking) {
				attackTimeRemaining = attackTime;
			}
		}
		
		weapon.draw();
		
		Render.batch.draw(sprite, bodyX - sprite.getRegionWidth()/2, bodyY - sprite.getRegionHeight()/2);
		
	}
	
	public boolean isDrawable() {
		return drawable;
	}
	
	public void toggleDrawable() {
		this.drawable = true;
	}
	
	public void attack(Vector2 characterPosition) {
		if(attackTimeRemaining <= 0 && !attacking) {
			weapon.botAttack(characterPosition);
			attacking = true;			
		}
	}
	
	protected void movement(Vector2 linearVelocity) {
		enemyBody.setLinearVelocity(linearVelocity);
	}
	
	public void reduceHp(int dmg) {
		if((hp - dmg) > 0) {
			hp -= dmg;			
		} else {
			hp = 0;
			alive = false;
		}
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void setdead() {
		this.alive = false;
	}
	
	public Body getBody() {
		return enemyBody;
	}

	public Body getWeapon() {
		return this.weapon.getBody();
	}
	
	public void setIndex(int index) {
		this.index = index;
	}

}
