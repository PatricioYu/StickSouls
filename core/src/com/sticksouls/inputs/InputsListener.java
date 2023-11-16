package com.sticksouls.inputs;

import java.util.ArrayList;

public abstract class InputsListener {
	private static ArrayList<MyInput> inputs = new ArrayList<MyInput>();
	private static int index = 0, previousIndex = 0;
	
	public static void addInputs(MyInput input) {
		inputs.add(input);
	}
	
	public static void processKeyboardInputs(int keycode) {
		inputs.get(index).keyDown(keycode);
	}
	
	public static void setActualIndex(MyInput actual) {
		previousIndex = index;
		index = inputs.indexOf(actual);
	}
	
	public static void setPreviousIndex() {
		index = previousIndex;
	}
	
	
	
	
}
