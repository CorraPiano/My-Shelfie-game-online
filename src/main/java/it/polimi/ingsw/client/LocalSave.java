package it.polimi.ingsw.client;

import java.io.*;

public class LocalSave {

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
