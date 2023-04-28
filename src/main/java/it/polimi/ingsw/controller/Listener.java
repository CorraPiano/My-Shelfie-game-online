package it.polimi.ingsw.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Listener implements PropertyChangeListener {
    //RMIHandlerOut handlerRMI;
    //SocketHanlerOut handlerSocket;
    public Listener() {
        //this.handlerRMI
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(evt.getPropertyName());
    }

}
