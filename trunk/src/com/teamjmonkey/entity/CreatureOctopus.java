package com.teamjmonkey.entity;

import com.teamjmonkey.animation.AnimComponent;
import com.teamjmonkey.graphics.Graphics;

public class CreatureOctopus extends Creature {

    public CreatureOctopus() {
        super(Graphics.OCTOPUS, 20f, 400f); //TODO adjust
        spatial.setName("octopus");

        animComponent = new AnimComponent(spatial);
    }
}
