package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Gameplay;

public class Timer implements Runnable{
    private final Controller controller;
    private final Gameplay gameplay;
    public Timer(Controller controller,Gameplay gameplay){
        this.controller=controller;
        this.gameplay=gameplay;
    }
    public void run(){
        int numDisconnection = gameplay.getNumDisconnection();
        System.out.println("timer "+numDisconnection+" partito");
        Long time = System.currentTimeMillis();
        synchronized (controller) {
            while (gameplay.checkTimer(time)) {
                try {
                    controller.wait(5000);
                } catch (Exception e) {
                }
            }
            if (gameplay.isFinished() || gameplay.currentPlayerIsConnected() || gameplay.getNumDisconnection() != numDisconnection) {
                System.out.println("timer " + numDisconnection + " terminato");
                return;
            }
            if (gameplay.getNumPlayersConnected() >= 2) {
                gameplay.endTurn();
                System.out.println("timer "+numDisconnection+" terminato");
                return;
            }
            controller.endgame(gameplay);
            System.out.println("timer "+numDisconnection+" terminato");
        }

    }
}
