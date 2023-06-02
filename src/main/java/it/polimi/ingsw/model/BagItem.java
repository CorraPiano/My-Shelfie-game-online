package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BagItem {
    private List<Item> bagItems;
    public BagItem() {
        bagItems = new ArrayList<Item>();
        for (int i = 0; i < 22; i++){
            for(ItemType type: ItemType.values()){
                bagItems.add(new Item (type,getItemPathByType(type)));
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
    public static String getItemPathByType(ItemType type) {
        Random random = new Random();
        int i = random.nextInt(3) + 1;

        return switch(type){
            case BLUE -> "/Images/items/Blue" + i + ".png";
            case YELLOW -> "/Images/items/Yellow" + i + ".png";
            case GREEN -> "/Images/items/Green" + i + ".png";
            case CYAN -> "/Images/items/Cyan" + i + ".png";
            case WHITE -> "/Images/items/White" + i + ".png";
            case PINK -> "/Images/items/Pink" + i + ".png";
        };

    }
    public void addBack(Item item){
        bagItems.add(item);
    }

}

