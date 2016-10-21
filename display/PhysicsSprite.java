package edu.virginia.engine.display;

import java.util.ArrayList;

public class PhysicsSprite extends Sprite {

    private boolean hasPhysics = false;
    public void hasPhysics(boolean bool) {
        hasPhysics = bool;
    }

    public double velocity, acceleration;
    int gravity = 6;


    public PhysicsSprite(String id) {
        super(id);
        hasPhysics(false);
    }

    public PhysicsSprite(String id, String imageFileName) {
        super(id, imageFileName);
        hasPhysics(false);
    }
//
//
//    @Override
//    protected void update(ArrayList<String> pressedKeys) {
//        super.update(pressedKeys);
//        if (hasPhysics){
//            applyPhysics();
//        }
//    }

    public void applyPhysics() {
            super.setPosition(getPosition().x,getPosition().y+gravity);
    }


}