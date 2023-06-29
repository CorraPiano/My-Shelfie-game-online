package it.polimi.ingsw.util;

import it.polimi.ingsw.controller.Settings;

import java.net.InetAddress;
import java.util.Scanner;

public class IPLoader {

    public static String getLocalIp(){

        String IP="localhost";
        try {
            IP = InetAddress.getLocalHost().getHostAddress();
        }catch(Exception ignored){}
        System.out.println("your local IP is "+IP);

        System.out.println("Press SPACE to accept the IP or insert a new one");
        Scanner in = new Scanner(System.in);
        try {
            String input = in.nextLine();
            if(input.isEmpty()){
                System.out.println("server ready on " + IP);
                return IP ;
            }
            if (input.matches(Constants.IPV4_PATTERN)) {
                IP = input;
                System.out.println("server ready on " + IP );
                return IP ;
            }
            System.out.println("Invalid IP format");
        } catch(Exception e) {
        }
            System.out.println("error");
            System.out.println("server ready on " + IP );
            //System.out.println(ANSI_YELLOW + "<<INFORMATION>> " + ANSI_RESET + "The IP address will be set to the default: " + Settings.IP + "\n");
            return IP;
        }
}
