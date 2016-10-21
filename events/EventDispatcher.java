package edu.virginia.engine.events;

import java.util.ArrayList;
import java.util.HashMap;

public class EventDispatcher implements IEventDispatcher {

    HashMap<String, ArrayList<IEventListener>> all_listeners = new HashMap<>();

    public void addEventListener(IEventListener listener, String eventType){
        if(!all_listeners.containsKey(eventType))
            all_listeners.put(eventType, new ArrayList<>());
        all_listeners.get(eventType).add(listener);
    }

    public void removeEventListener(IEventListener listener, String eventType){
        if(all_listeners.containsKey(eventType))
            all_listeners.get(eventType).remove(listener);
            if (all_listeners.get(eventType).size() == 0);
                all_listeners.remove(eventType);
    }

    public void dispatchEvent(Event event) {
        if (all_listeners.containsKey(event.getEventType()))
            for(IEventListener listener : all_listeners.get(event.getEventType()))
                listener.handleEvent(event);
    }

    public boolean hasEventListener(IEventListener listener, String eventType){
        if (all_listeners.size()==0) return false;
        else return true;
    }
}
