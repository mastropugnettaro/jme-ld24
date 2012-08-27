package com.teamjmonkey.entity;

import com.teamjmonkey.animation.AnimComponent;
import com.teamjmonkey.graphics.Graphics;

public class CreatureArmadilo extends Creature {

    public CreatureArmadilo() {
        super(Graphics.ARMADILO, 6f, 200f); //TODO adjust
        spatial.setName("armadilo");

        animComponent = new AnimComponent(spatial);
    }
}
