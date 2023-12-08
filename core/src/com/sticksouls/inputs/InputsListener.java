package com.sticksouls.inputs;

import java.util.ArrayList;

import com.badlogic.gdx.utils.IntArray;
import com.sticksouls.hud.Hud;

public abstract class InputsListener {
	private static ArrayList<MyInput> inputs = new ArrayList<MyInput>();
	private static IntArray previousIndex = new IntArray();
	private static int index = 0;
	
	public static void addInputs(MyInput input) {
		inputs.add(input);
	}
	
	public static void processKeyboardInputs(int keycode) {
		inputs.get(index).keyDown(keycode);
	}
	
	public static void setMyIndex(MyInput actual) {
		previousIndex.add(index);
		index = inputs.indexOf(actual);
	}
	
	public static void setPreviousIndex() {
		index = previousIndex.get(previousIndex.size-1);
		if(inputs.get(index) instanceof Hud) {
			Hud object = (Hud) inputs.get(index);
			
			object.visible = true;
		}
		previousIndex.removeIndex(previousIndex.size-1);
	}
	
}
