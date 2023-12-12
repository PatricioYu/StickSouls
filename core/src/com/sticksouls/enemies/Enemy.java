package com.sticksouls.enemies;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.sticksouls.utils.Render;
import com.sticksouls.utils.Resources;

public class Enemy {
	protected int hp = 100;
	protected float bodyX, bodyY;
	protected boolean alive = true;
	protected Body enemyBody;
	protected Sprite sprite;
	private OrthographicCamera camera;
	
	public Enemy(World world, float x, float y, OrthographicCamera camera) {
		this.camera = camera;
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
	}
	
	public void draw() {
		bodyX = enemyBody.getPosition().x;
		bodyY = enemyBody.getPosition().y;
		
		Vector3 screenCoordinates = new Vector3(bodyX, bodyY, 0);
		camera.project(screenCoordinates);
		camera.update();
		
		enemyBody.setLinearVelocity(0,0);
		
		Render.batch.draw(sprite, bodyX - sprite.getRegionWidth()/2, bodyY - sprite.getRegionHeight()/2);
	}
	
	public void reduceHp(int dmg) {
		if((hp - dmg) >= 0) {
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
	
	public void attack() {

	}

}
