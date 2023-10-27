package com.sticksouls.items;

public class Item {
	private String name, description;
	
	protected Item(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
}
