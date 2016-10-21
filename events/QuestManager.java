package edu.virginia.engine.events;

public class QuestManager implements IEventListener {

    public QuestManager() {}

    public String status = "";

    @Override
    public void handleEvent(Event event) {
        if(event.getEventType() == "got coin"){
            event.getSource();
            status = "Picked Up Coin! Quest Complete.";
        }
    }
}
