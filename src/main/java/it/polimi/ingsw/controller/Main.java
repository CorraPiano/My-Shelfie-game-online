package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.Controller;

import java.io.IOException;
import java.rmi.registry.Registry;
import java.rmi.*;
import java.rmi.registry.*;

public class Main {
    public static void main(String[] args) throws IOException, AlreadyBoundException, RemoteException{
        try{
            Controller controller = new Controller();
            LocateRegistry.createRegistry(1099);
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("controller", controller);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}