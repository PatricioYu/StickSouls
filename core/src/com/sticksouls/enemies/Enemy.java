package com.sticksouls.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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

public class Enemy {
	protected int hp = 100;
	protected float bodyX, bodyY;
	protected boolean alive = true;
	protected Body enemyBody;
	protected Sprite sprite;
	private OrthographicCamera camera;
	private Weapon weapon;
	
	float velocidad = 100.0f; // Puedes ajustar esta velocidad según tus necesidades	
	private int attackTime = 100, attackTimeRemaining = 5;
	private boolean attacking = false;
	private World world;
	
	public Enemy(World world, float x, float y, OrthographicCamera camera) {
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
	
	public void draw(Vector2 characterPosition) {
		bodyX = enemyBody.getPosition().x;
		bodyY = enemyBody.getPosition().y;
		
		float distance = enemyBody.getPosition().dst(characterPosition);
		
		if(distance <= 100) {
			movement(characterPosition);
			if(attackTimeRemaining <= 0 && !attacking) {
				weapon.botAttack(characterPosition);
				attacking = true;
			}
		}else {
			enemyBody.setLinearVelocity(0,0);			
		}
		
		
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
	
	private void movement(Vector2 characterPosition) {
		enemyBody.setLinearVelocity(0,0);
		Vector2 direction = new Vector2(characterPosition.x - enemyBody.getPosition().x, characterPosition.y - enemyBody.getPosition().y).nor();

		enemyBody.setLinearVelocity(direction.scl(velocidad));
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
	
	public Body getBody() {
		return enemyBody;
	}

	public Body getWeapon() {
		return this.weapon.getBody();
	}

}
