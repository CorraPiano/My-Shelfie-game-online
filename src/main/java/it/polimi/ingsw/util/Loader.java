package it.polimi.ingsw.util;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Loader {
    static public int[][] LoadBoard() {
        Gson gson=new Gson();
        try {
            FileReader file = new FileReader("src/main/java/it/polimi/ingsw/util/BoardMask");
            BufferedReader reader = new BufferedReader(file);
            String json = reader.readLine();
            reader.close();
            file.close();
            //System.out.println(json);
            int[][] mask = gson.fromJson(json,int[][].class);
            //System.out.println(mask);
            return mask;
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
