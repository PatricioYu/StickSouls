package com.sticksouls.characters;

import java.util.ArrayList;

import com.sticksouls.items.Item;
import com.sticksouls.items.weapons.Weapon;

public class Inventory {
	private Weapon[] equippedWeapons = new Weapon[2];
	private ArrayList<Item> items = new ArrayList<Item>();
	
	public void equipWeapon(boolean primary, Weapon weapon) {
		if(primary) {
			this.equippedWeapons[0] = weapon;
		} else {
			this.equippedWeapons[1] = weapon;
		}
	}
}
