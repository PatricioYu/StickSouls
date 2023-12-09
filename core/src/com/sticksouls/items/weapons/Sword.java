package com.sticksouls.items.weapons;

import java.nio.file.spi.FileSystemProvider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.sticksouls.enums.Directions;
import com.sticksouls.utils.Render;

public class Sword extends Weapon {

	private boolean swinging = false;
	private float swingTimer = 0;
	private float maxSwingTime = .3f;
	private Directions direction;
	private RevoluteJoint jointSelected;
	private RevoluteJointDef jointDefSelected;
	
	
	public Sword(final Body CHARACTERBODY, World world) {
		super("Sword", "a Sword", 1, 15, CHARACTERBODY, world);
		jointSelected = rightJoint;
	}
	
	public void draw() {
		super.draw();

		sprite.setRotation(MathUtils.radiansToDegrees * weaponBody.getAngle());
		sprite.setPosition(super.weaponBody.getPosition().x - sprite.getWidth()/2, super.weaponBody.getPosition().y - sprite.getHeight()/2);
		sprite.setOriginCenter();
		
		Render.batch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getOriginX(), sprite.getOriginY(), sprite.getWidth(), sprite.getHeight(), 1, 1, sprite.getRotation());
	}
	
	@Override
	public void attack(Vector2 characterCoordinates) {		
		if(Gdx.input.getY() < positiveLine(Gdx.input.getX(), characterCoordinates) && Gdx.input.getY() < negativeLine(Gdx.input.getX(), characterCoordinates)) {
			direction = Directions.TOP;
		}
		if(Gdx.input.getY() <= positiveLine(Gdx.input.getX(), characterCoordinates) && Gdx.input.getY() >= negativeLine(Gdx.input.getX(), characterCoordinates)) {
			direction = Directions.RIGHT;
		}		
		if(Gdx.input.getY() > positiveLine(Gdx.input.getX(), characterCoordinates) && Gdx.input.getY() > negativeLine(Gdx.input.getX(), characterCoordinates)) {
			direction = Directions.BOTTOM;
		}
		if(Gdx.input.getY() >= positiveLine(Gdx.input.getX(), characterCoordinates) && Gdx.input.getY() <= negativeLine(Gdx.input.getX(), characterCoordinates)) {
			direction = Directions.LEFT;
		}
		
		switch(direction) {
		case TOP:
			jointSelected = super.topJoint;
			jointDefSelected = super.topJointDef;

			super.weaponBody.setAngularVelocity(-25);
			super.weaponBody.setTransform(super.weaponBody.getWorldCenter(), 90 * MathUtils.degRad);
			break;
		case RIGHT:
			jointSelected = super.rightJoint;
			jointDefSelected = super.rightJointDef;			
			
			super.weaponBody.setAngularVelocity(-25);
			break;
		case BOTTOM:
			jointSelected = super.bottomJoint;
			jointDefSelected = super.bottomJointDef;
			
			super.weaponBody.setAngularVelocity(25);
			super.weaponBody.setTransform(super.weaponBody.getWorldCenter(), (90 * MathUtils.degRad));
			break;
		case LEFT:
			jointSelected = super.leftJoint;
			jointDefSelected = super.leftJointDef;
			
			super.weaponBody.setAngularVelocity(25);

			break;
		}
		
		super.destroyAllJoints();
		super.createJoint(jointDefSelected);
		
		swingTimer = maxSwingTime;
		swinging = true;
		
	}
	
	private float positiveLine(float x, Vector2 personaje) {
	    return x - (personaje.x - personaje.y);
	}

	private float negativeLine(float x, Vector2 personaje) {
	    return -x + (personaje.x + personaje.y);
	}


	@Override
	public boolean continueAttack() {
		swingTimer -= Gdx.graphics.getDeltaTime();
		
		return finishAttack();
	}

	@Override
	public boolean finishAttack() {
		if(swingTimer <= 0) {
			super.firstDraw = false;
			swingTimer = 0;
			swinging = false;

			super.weaponBody.setAngularVelocity(0);
			super.destroyAllJoints();
			super.createJoint(super.rightJointDef);
			
			return false;
		}
		
		return true;
	}

}
