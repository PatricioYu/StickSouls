package com.sticksouls.items.weapons;

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
	private RevoluteJoint jointSelected;
	private RevoluteJointDef jointDefSelected;
	
	
	public Sword(final Body CHARACTERBODY, World world) {
		super("Sword", "a Sword", 1, 15, CHARACTERBODY, world);
	}
	
	public Sword(final Body CHARACTERBODY, World world, boolean enemy) {
		super("Sword", "a Sword", 1, 15, CHARACTERBODY, world);
		super.enemy = enemy;
	}
	
	public void draw() {
		super.draw();
		
		if(!swinging) {
			jointSelected = rightJoint;
		}
		sprite.setRotation(MathUtils.radiansToDegrees * weaponBody.getAngle());
		sprite.setPosition(super.weaponBody.getPosition().x - sprite.getWidth()/2, super.weaponBody.getPosition().y - sprite.getHeight()/2);
		sprite.setOriginCenter();
		
		Render.batch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getOriginX(), sprite.getOriginY(), sprite.getWidth(), sprite.getHeight(), 1, 1, sprite.getRotation());
	}
	
	@Override	
	public void botAttack(Vector2 characterCoordinates) {		
		if(characterCoordinates.y < positiveLine(characterCoordinates.x, super.CHARACTERBODY.getPosition()) && characterCoordinates.y < negativeLine(characterCoordinates.x, super.CHARACTERBODY.getPosition())) {
			direction = Directions.BOTTOM;
		}
		if(characterCoordinates.y <= positiveLine(characterCoordinates.x, super.CHARACTERBODY.getPosition()) && characterCoordinates.y >= negativeLine(characterCoordinates.x, super.CHARACTERBODY.getPosition())) {
			direction = Directions.RIGHT;
		}		
		if(characterCoordinates.y > positiveLine(characterCoordinates.x, super.CHARACTERBODY.getPosition()) && characterCoordinates.y > negativeLine(characterCoordinates.x, super.CHARACTERBODY.getPosition())) {
			direction = Directions.TOP;
		}
		if(characterCoordinates.y >= positiveLine(characterCoordinates.x, super.CHARACTERBODY.getPosition()) && characterCoordinates.y <= negativeLine(characterCoordinates.x, super.CHARACTERBODY.getPosition())) {
			direction = Directions.LEFT;
		}
		
		switch(direction) {
		case TOP:
			jointSelected = super.topJoint;
			jointDefSelected = super.topJointDef;

			super.weaponBody.setAngularVelocity(-25);
			super.weaponBody.setTransform(super.weaponBody.getWorldCenter().x + jointSelected.getLocalAnchorB().x, super.weaponBody.getWorldCenter().y + jointSelected.getLocalAnchorB().y , 90 * MathUtils.degRad);
			
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
			super.weaponBody.setTransform(super.weaponBody.getWorldCenter().x + jointSelected.getLocalAnchorB().x, super.weaponBody.getWorldCenter().y + jointSelected.getLocalAnchorB().y, (90 * MathUtils.degRad));
			break;
		case LEFT:
			jointSelected = super.leftJoint;
			jointDefSelected = super.leftJointDef;
			
			super.weaponBody.setAngularVelocity(25);

			break;
		}
		
		super.destroyAllMyJoints(null);
		jointSelected = super.createJoint(jointDefSelected);
		
		swingTimer = maxSwingTime;
		swinging = true;
		
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
			super.weaponBody.setTransform(super.weaponBody.getWorldCenter().x + jointSelected.getLocalAnchorB().x, super.weaponBody.getWorldCenter().y + jointSelected.getLocalAnchorB().y , 90 * MathUtils.degRad);
			
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
			super.weaponBody.setTransform(super.weaponBody.getWorldCenter().x + jointSelected.getLocalAnchorB().x, super.weaponBody.getWorldCenter().y + jointSelected.getLocalAnchorB().y, (90 * MathUtils.degRad));
			break;
		case LEFT:
			jointSelected = super.leftJoint;
			jointDefSelected = super.leftJointDef;
			
			super.weaponBody.setAngularVelocity(25);

			break;
		}
		
		super.destroyAllMyJoints(null);
		jointSelected = super.createJoint(jointDefSelected);
		
		swingTimer = maxSwingTime;
		swinging = true;
		
	}
	
	@Override
	public void attack(Directions direction) {	
		switch(direction) {
		case TOP:
			jointSelected = super.topJoint;
			jointDefSelected = super.topJointDef;

			super.weaponBody.setAngularVelocity(-25);
			super.weaponBody.setTransform(super.weaponBody.getWorldCenter().x + jointSelected.getLocalAnchorB().x, super.weaponBody.getWorldCenter().y + jointSelected.getLocalAnchorB().y , 90 * MathUtils.degRad);
			
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
			super.weaponBody.setTransform(super.weaponBody.getWorldCenter().x + jointSelected.getLocalAnchorB().x, super.weaponBody.getWorldCenter().y + jointSelected.getLocalAnchorB().y, (90 * MathUtils.degRad));
			break;
		case LEFT:
			jointSelected = super.leftJoint;
			jointDefSelected = super.leftJointDef;
			
			super.weaponBody.setAngularVelocity(25);

			break;
		}
		
		super.destroyAllMyJoints(null);
		jointSelected = super.createJoint(jointDefSelected);
		
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
			super.destroyAllMyJoints(jointSelected);
			super.rightJoint = super.createJoint(super.rightJointDef);
			
			return false;
		}
		
		return true;
	}
	
}
