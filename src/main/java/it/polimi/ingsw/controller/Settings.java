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

    /** The timeout duration for server operations in milliseconds. */
    public static long timeout = 10000;
}