package com.sticksouls.items.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Sword extends Weapon {

	private boolean swinging = false;
	private float swingTimer = 0;
	private float maxSwingTime = 0;
	
	public Sword(final Body CHARACTERBODY, World world) {
		super("Sword", "a Sword", 1, 10, CHARACTERBODY, world);
		
	}

	@Override
	public void attack() {
		super.joint.enableLimit(false);
		//super.weaponBody.setAngularVelocity(-100);
		super.weaponBody.applyAngularImpulse(-100000, false);
		
		//super.joint.enableLimit(true);
		//super.joint.setLimits(0, 0);

	}

}
