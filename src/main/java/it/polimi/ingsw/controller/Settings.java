package it.polimi.ingsw.controller;

/**
 * The Settings class contains the configuration parameters for the game server.
 * It specifies the TCP and RMI ports, IP addresses, and timeout duration.
 */
public class Settings {

    /** The TCP port used for client connections. */
    public static int TCPPORT = 8081;

    /** The RMI port used for client connections. */
    public static int RMIPORT = 1099;

    /** The IP address of the server. */
    public static String IP = "localhost";

    /** The server name or IP address. */
    public static String SERVER_NAME = "127.0.0.1";

    /** The name of the RMI remote object. */
    public static String remoteObjectName = "serverSkeleton";


    // to fix javadock
    /** The timeout duration for server operations in milliseconds. */

    //clock del thread che pinga continuamente il server (e il server fai ping al client in risposta) -> PingSender.run()
    //lato TCP, appena la connection del server riceve il ping, invia indietro un ping
    //lato RMI, il ping del server è il ritorno dell'invocazione remota (ping del client)
    public static long clock_pingSender = 3000;


    //clock in ms del thread del listener, che invia le cose al client (se ci sono si sveglia) e che controlla i ping del client -> Listener.run()
    //è il tempo di reazione massimo ad un ritardo del ping di timeout_server
    public static long clock_listener = 3000;

    //tempo in ms limite tra 2 successivi ping del client, altrimenti il server disconnette il giocatore -> Eventkeep.checkConnection()
    public static long timeout_server = 6100;


    //clock in ms del thread del ConnectionChecker, che controlla i ping del server -> ConnectionChecker.run()
    //è il tempo di reazione massimo ad un ritardo del ping di timeout_client
    public static long clock_connectionCheck = 3000;

    //tempo in ms limite tra 2 successivi ping del server, altrimenti il client disconnette il giocatore -> CheckConnection.run()
    public static long timeout_client = 6100;



    //clock del thread che tenta la riconnessione con il server se saltata -> CheckConnection.tryReconnection()
    public static long clock_reconnector = 1000;

    //clock in ms del thread che controlla se il numero di giocatori è torna ad essere maggiore di 2 -> Timer.run()
    public static long  clock_timer = 5000;

    //tempo in ms oltre al quale una partita si chiude se ci sono meno di 2 giocatori, dalll'ultima disconnessione -> Gameplay.checkTimer()
    public static long timeout_timer = 60000;

}