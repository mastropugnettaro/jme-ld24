package com.teamjmonkey.controls;

import com.jme3.math.ColorRGBA;

public class FoodControl extends BaseControl {

    private ColorRGBA color;
    private float energy;

    public FoodControl(ColorRGBA color, float energy) {
        this.color = color;
        this.energy = energy;
    }

    @Override
    protected void controlUpdate(float tpf) {
    }

    public ColorRGBA getColor() {
        return color;
    }

    public float getEnergy() {
        return energy;
    }
}
