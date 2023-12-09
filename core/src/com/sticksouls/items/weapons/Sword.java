package com.sticksouls.items.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.sticksouls.enums.Directions;

public class Sword extends Weapon {

	private boolean swinging = false;
	private float swingTimer = 0;
	private float maxSwingTime = .3f;
	private Directions direction;
	private RevoluteJoint jointSelected;
	private RevoluteJointDef jointDefSelected;
	
	
	public Sword(final Body CHARACTERBODY, World world) {
		super("Sword", "a Sword", 1, 15, CHARACTERBODY, world);
		
	}

	@Override
	public void attack(Vector2 characterCoordinates) {
		
		System.out.println(Gdx.input.getY() + " " + positiveLine(Gdx.input.getX(), characterCoordinates));
		
		if(Gdx.input.getY() < positiveLine(Gdx.input.getX(), characterCoordinates) && Gdx.input.getY() < negativeLine(Gdx.input.getX(), characterCoordinates)) {
			System.out.println("mitad arriba");
			direction = Directions.TOP;
		}
		if(Gdx.input.getY() <= positiveLine(Gdx.input.getX(), characterCoordinates) && Gdx.input.getY() >= negativeLine(Gdx.input.getX(), characterCoordinates)) {
			System.out.println("mitad derecha");
			direction = Directions.RIGHT;
		}		
		if(Gdx.input.getY() > positiveLine(Gdx.input.getX(), characterCoordinates) && Gdx.input.getY() > negativeLine(Gdx.input.getX(), characterCoordinates)) {
			System.out.println("mitad abajo");
			direction = Directions.BOTTOM;
		}
		if(Gdx.input.getY() >= positiveLine(Gdx.input.getX(), characterCoordinates) && Gdx.input.getY() <= negativeLine(Gdx.input.getX(), characterCoordinates)) {
			System.out.println("mitad izquierda");
			direction = Directions.LEFT;
		}
		
		
		switch(direction) {
		case TOP:
			jointSelected = super.topJoint;
			jointDefSelected = super.topJointDef;

			//jointSelected.setLimits(90 * MathUtils.degRad, 90 * MathUtils.degRad);
			
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
