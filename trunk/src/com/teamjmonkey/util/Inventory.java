package com.teamjmonkey.util;

import com.jme3.math.ColorRGBA;
import com.teamjmonkey.GameNameGoesHere;
import com.teamjmonkey.entity.food.FoodEntity;

public final class Inventory {

    private final int NUM_ITEMS = 3;
    private boolean filled[] = new boolean[NUM_ITEMS];
    private FoodEntity entities[] = new FoodEntity[NUM_ITEMS];
    private GameNameGoesHere myApp = GameNameGoesHere.getApp();

    public Inventory() {
        clearInventory();
    }

    private void clearInventory() {
        for (int i = 0; i < NUM_ITEMS; i++) {
            filled[i] = false;
        }
    }

    public void removeFromInventory(int index) {
        //move everything to the front if 1 is removed
    }

    private boolean isFilled(int index) {
        return filled[index];
    }

    private boolean isCompletelyFilled() {

        boolean isCompletelyFilled = true;

        for (int i = 0; i < NUM_ITEMS; i++) {
            if (!filled[i]) {
                isCompletelyFilled = false;
            }
        }

        return isCompletelyFilled;
    }

    private int getLastFilled() {

        int index = 0;

        for (int i = 0; i < NUM_ITEMS; i++) {
            if (filled[i]) {
                index = i;
            }
        }

        return index;
    }

    public void addToInventory(FoodEntity entity) {

        // dont add anything if the inventory is full
        if (isCompletelyFilled()) {
            return;
        }

        addPic(entity.getPicture(), entity.getColor(), getLastFilled());
    }

    private void addPic(String fileName, ColorRGBA color, int index) {
        myApp.getUIManager().addInventoryItem(fileName, color, index);
    }
}