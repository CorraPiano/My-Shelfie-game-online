package it.polimi.ingsw.client;

import java.io.*;

/**
 * The LocalSave class provides utility methods for reading, storing, and clearing the client's ID.
 */
public class LocalSave {

    /**
     * Recovers the client's ID from the ID file.
     *
     * @return The client's ID if successfully recovered, or null if the ID file is empty or cannot be read.
     */
    public static String recoverID(){
        try {
            FileReader file = new FileReader("src/main/java/it/polimi/ingsw/client/ID");
            BufferedReader reader = new BufferedReader(file);
            String ID = reader.readLine();
            reader.close();
            file.close();
            //String ID_PATTERN = "^(\\d+)\\.(\\d+)\\.(\\d+)";
            if (!ID.isEmpty() && ID.charAt(0) == '#')
                return ID;
            else
                return null;
        } catch(Exception e){
            System.out.println("We can't read ID file!");
            System.out.println("You can find your ID in the ID file");
            return null;
        }
    }

    /**
     * Stores the client's ID in the ID file.
     *
     * @param ID The client's ID to be stored.
     */
    public static void storeID(String ID) {
        try{
            FileWriter file = new FileWriter("src/main/java/it/polimi/ingsw/client/ID");
            PrintWriter writer = new PrintWriter(file);
            writer.print(ID);
            writer.close();
            file.close();
        } catch(Exception e) {
            System.out.println("We can't save your ID!");
            System.out.println("Please write it down or save it into ID file");
            System.out.println("Your ID is: " + ID);
        }
    }

    /**
     * Clears the client's ID from the ID file.
     */
    public static void clear() {
        try{
            FileWriter file = new FileWriter("src/main/java/it/polimi/ingsw/client/ID");
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();
            file.close();
        } catch(Exception e){
            System.out.println("We can't remove the ID from the file");
            System.out.println("Please remove the content from the file ID");
        }

    }
}
