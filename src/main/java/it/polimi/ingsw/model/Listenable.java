package it.polimi.ingsw.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Listenable {
    PropertyChangeListener listener;
    public PropertyChangeListener getListener() {
        return listener;
    }

    public void setListener(PropertyChangeListener listener) {
        this.listener = listener;
    }

    public void notifyListener(String changeType){
        // Event creation
        PropertyChangeEvent evt = new PropertyChangeEvent(this, changeType, null , this);
        this.listener.propertyChange(evt);
    }
}
