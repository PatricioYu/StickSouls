package com.sticksouls.contactlistener;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.sticksouls.characters.WhiteStickman;
import com.sticksouls.enemies.Enemy;
import com.sticksouls.items.weapons.Sword;
import com.sticksouls.items.weapons.Weapon;

public class MyContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		Body bodyA = contact.getFixtureA().getBody();
		Body bodyB = contact.getFixtureB().getBody();
			
		if(bodyA.getUserData() instanceof Weapon && bodyB.getUserData() instanceof Enemy ) {
			Weapon weapon = (Weapon) bodyA.getUserData();
			if(!weapon.isEnemy()) {
				Enemy enemy = (Enemy) bodyB.getUserData();
				
				enemy.reduceHp(weapon.getBaseDmg());
			}
		}
		
		if(bodyB.getUserData() instanceof Weapon && bodyA.getUserData() instanceof WhiteStickman) {
			Weapon weapon = (Weapon) bodyB.getUserData();
			WhiteStickman target = (WhiteStickman) bodyA.getUserData();
			target.reduceHp(weapon.getBaseDmg());	
		}
		
	}

	@Override
	public void endContact(Contact contact) {
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
	}

}
