package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.ControllerSkeleton;
import it.polimi.ingsw.controller.Settings;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {

    public static void main(String[] args) throws IOException, AlreadyBoundException, NotBoundException {
        ControllerSkeleton controller = getServerRMI();
        Client client = new Client();
        GraphicsInterface graphicsInterface = new GraphicsInterface(controller,client);
        System.out.println("client avviato");
        graphicsInterface.joinMatch();
    }

    private static ControllerSkeleton getServerRMI() throws RemoteException, NotBoundException {
        Registry registry;
        ControllerSkeleton controller;
        registry = LocateRegistry.getRegistry("localhost",1099);
        controller = (ControllerSkeleton) registry.lookup(Settings.remoteObjectName);
        return controller;
    }
}

