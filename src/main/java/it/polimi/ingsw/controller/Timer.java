package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Gameplay;

/**
 * The Timer class represents a timer for managing turns in the game.
 * It is responsible for timing the duration of each turn and triggering
 * actions accordingly.
 */
public class Timer implements Runnable{
    private final Controller controller;
    private final Gameplay gameplay;

    /**
     * Constructs a Timer object.
     *
     * @param controller the controller responsible for game flow and actions.
     * @param gameplay   the Gameplay object representing the current game state.
     */
    public Timer(Controller controller,Gameplay gameplay){
        this.controller=controller;
        this.gameplay=gameplay;
    }

    /**
     * Executes the timer logic.
     * Monitors the elapsed time of the current turn and performs actions based on it.
     */
    public void run(){
        int numDisconnection = gameplay.getNumDisconnection();
        System.out.println("timer "+numDisconnection+" partito");
        Long time = System.currentTimeMillis();
        synchronized (controller) {
            while (gameplay.checkTimer(time,numDisconnection)) {
                try {
                    controller.wait(Settings.clock_timer);
                } catch (Exception ignored) {}
            }
            if (gameplay.isFinished() || gameplay.getNumDisconnection() != numDisconnection) {
                System.out.println("timer " + numDisconnection + " terminato");
                return;
            }
            if (gameplay.getNumPlayersConnected() >= 2) {
                if(!gameplay.currentPlayerIsConnected())
                    gameplay.endTurn();
                System.out.println("timer "+numDisconnection+" terminato");
                return;
            }
            controller.endgame(gameplay);
            System.out.println("timer "+numDisconnection+" terminato");
        }

    }
}
