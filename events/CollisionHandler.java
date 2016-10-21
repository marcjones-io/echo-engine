package edu.virginia.engine.events;

public class CollisionHandler implements IEventListener {

    public CollisionHandler() {}
    public String status = "";




    @Override
    public void handleEvent(Event event) {

        status = "hey watchout " + event.getSource().toString() + " !! ";
        System.out.println("Hey watchout!");

    }

}
