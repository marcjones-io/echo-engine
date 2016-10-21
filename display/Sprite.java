package edu.virginia.engine.display;

import java.util.ArrayList;

public class Sprite extends DisplayObjectContainer {

	public Sprite(String id) {
		super(id);
	}

	public Sprite(String id, String imageFileName) {
		super(id, imageFileName);
	}

	@Override
	protected void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
	}
}