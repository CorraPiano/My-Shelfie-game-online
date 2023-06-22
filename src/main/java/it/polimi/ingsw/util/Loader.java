package it.polimi.ingsw.util;

import com.google.gson.Gson;

import java.io.*;
import java.net.URL;


public class Loader {
    public int[][] LoadBoard() {
        Gson gson=new Gson();
        try {
            InputStream is = this.getClass().getResourceAsStream("/BoardMask.txt");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader file = new BufferedReader(isr);
            //FileReader file = new FileReader(url);
            //BufferedReader reader = new BufferedReader(file);
            String json = file.readLine();
            file.close();
            isr.close();
            is.close();
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
