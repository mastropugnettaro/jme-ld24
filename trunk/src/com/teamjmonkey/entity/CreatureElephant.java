package com.teamjmonkey.entity;

import com.teamjmonkey.animation.AnimComponent;
import com.teamjmonkey.graphics.Graphics;

public class CreatureElephant extends Creature {

    public CreatureElephant() {
        super(Graphics.ELEPHANT, 6f, 300f); //TODO adjust
        spatial.setName("elephant");

        animComponent = new AnimComponent(spatial);
    }
}
