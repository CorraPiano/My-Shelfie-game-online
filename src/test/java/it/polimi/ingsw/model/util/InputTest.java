package it.polimi.ingsw.model.util;

import it.polimi.ingsw.model.Bookshelf;

import java.util.ArrayList;
import java.util.List;

public class InputTest {
    private List<Bookshelf> inputLibrary;
    private List<Integer> result;
    private  int size;

    public InputTest() {
        size = 0;
        this.result = new ArrayList<>();
        this.inputLibrary = new ArrayList<>();

    }

    public InputTest(List<Bookshelf> inputLibrary , List<Integer> result) {
        size = 0;
        this.result = result;
        this.inputLibrary = inputLibrary;
    }

    public void addTest(Bookshelf b, int r){
        inputLibrary.add(b);
        result.add(r);
        size++;
    }
    public Bookshelf getInputLibrary(int i) {
        return inputLibrary.get(i);
    }

    public Integer getResult(int i) {
        return result.get(i);
    }

    public int numberOfTests() {
        return size;
    }
}
