package com.sticksouls.items.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sticksouls.items.Item;

public abstract class Weapon extends Item {
	protected int baseDmg, empoweredDmg;
	protected float frameDuration = 0.1f;
	protected Texture spriteSheet;
	protected TextureRegion[][] basicAttackFrames;
	protected TextureRegion[][] empoweredAttackFrames;
	protected Animation<TextureRegion> basicAttackAnimation;
	protected Animation<TextureRegion> empoweredAttackAnimation;
	
	protected Weapon(String name, String description) {
		super(name, description);
	}
	
	// getters
	public int getBaseDmg() {
		return baseDmg;
	}
	
	public int getEmpoweredDmg() {
		return empoweredDmg;
	}
}
