package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.EmptyBagException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BagItem {
    private List<Item> bagItems;
    public BagItem() {
        bagItems = new ArrayList<Item>();
        for (int i = 0; i < 22; i++){
            for(ItemType type: ItemType.values()){
                bagItems.add(new Item (type));
            }
        }
    };
    public Item drawItem() {
        Collections.shuffle(bagItems);
        if (! bagItems.isEmpty()){
            return bagItems.remove(0);
        }
        return null;
    }
    public int getBagItemSize() { return bagItems.size();}
    public void addBack(Item item){
        bagItems.add(item);
    }

}

