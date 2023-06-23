package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BagItem {
    private List<Item> bagItems;

    /**
     * Default constructor. Fills the bag with all the items to be drawn.
     * A bag is a container for items and cards.
     *
     */
    public BagItem() {
        bagItems = new ArrayList<Item>();
        for (int i = 0; i < 22; i++){
            for(ItemType type: ItemType.values()){
                bagItems.add(new Item (type,getItemPathByType(type)));
            }
        }
    };

    /**
     * Draws an Item from the item bag.
     *
     * @return the item drawn.
     */
    public Item drawItem() {
        Collections.shuffle(bagItems);
        if (! bagItems.isEmpty()){
            return bagItems.remove(0);
        }
        return null;
    }

    /**
     * Returns size of bag item.
     *
     * @return returns an int representing items left in the bag.
     */
    public int getBagItemSize() { return bagItems.size();}

    /**
     * Gets the path to item's image. There are 3 different images for each color
     *
     * @param type item's color.
     * @return String of path randomly selected.
     */
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

    /**
     * Puts an item back into the bag item.
     *
     * @param item item to put back.
     */
    public void addBack(Item item){
        bagItems.add(item);
    }

}

