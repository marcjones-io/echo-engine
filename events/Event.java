package edu.virginia.engine.events;

/**
 * Created by kaivon on 7/19/16.
 */
public class Event {

    private String eventType;
    private IEventDispatcher source; //the object that created this event with the new keyword.

    public Event(IEventDispatcher src, String type) {
        source = src;
        eventType = type;
    }

    public String getEventType(){ return eventType; }
    public void setEventType(String type) { eventType = type; }

    public IEventDispatcher getSource(){  return source; }
    public void setSource(IEventDispatcher newsrc) { source = newsrc; }

}
