package edu.virginia.engine.events;

public interface IEventDispatcher {
    public void addEventListener(IEventListener listener, String eventType); //AKA ADD OBSERVER
    public void removeEventListener(IEventListener listener, String eventType); //AKA REMOVE OBSERVER
    public void dispatchEvent(Event event); //AKA NOTIFY
    public boolean hasEventListener(IEventListener listener, String eventType);

}
