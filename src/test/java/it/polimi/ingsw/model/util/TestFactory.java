package it.polimi.ingsw.model.util;
import it.polimi.ingsw.model.Bookshelf;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TestFactory {

    private static int[] convertToArray(String s){
        int[] vet = new int[5];
        int j = 0;
        for(int i=0; i<5; i++){
            vet[i] = Integer.valueOf(s.charAt(j)) - 48;
            j+=2;
        }
        return vet;
    }

    public static InputTest createTest(String path){

        try{
            FileReader file = new FileReader(path);

            BufferedReader reader = new BufferedReader(file);

            int result = 0;
            String line;
            int[][] mat = new int[6][5];


            InputTest inputTest = new InputTest();
            Bookshelf b;

            line = reader.readLine();
            while(reader.ready()) {
                for (int i=0; i<6 ; i++){
                    mat[i] = convertToArray(line);
                    line = reader.readLine();
                }

                line = reader.readLine();
                result = Integer.parseInt(line);
                b = new Bookshelf();
                b.fillBookshelf(mat);
                inputTest.addTest(b, result);

                line = reader.readLine();
                line = reader.readLine();
            }

            reader.close();
            return inputTest;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public static void main(String[] args) {
        InputTest input = TestFactory.createTest("src/test/java/it/polimi/ingsw/model/util/PersonalTestFile.txt");
    }

}
