package com.sticksouls.characters;

import com.badlogic.gdx.graphics.Texture;
import com.sticksouls.enums.CharacterState;

public abstract class Character {
	protected int hp, stamina, currency;
	protected Inventory inventory;
	protected Texture spriteSheet;
	protected CharacterState state;

	protected Character(int hp, int stamina, int currency) {
		this.hp = hp;
		this.stamina = stamina;
		this.currency = currency;
		
		state = CharacterState.IDLE;
		inventory = new Inventory();
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
}
