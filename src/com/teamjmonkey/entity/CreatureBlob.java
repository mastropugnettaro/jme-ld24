package com.teamjmonkey.entity;

import com.teamjmonkey.animation.AnimComponent;
import com.teamjmonkey.graphics.Graphics;

public class CreatureBlob extends Creature {

    public CreatureBlob() {
        super(Graphics.BLOB, 4f, 100f); //TODO adjust
        spatial.setName("blob");

        animComponent = new AnimComponent(spatial);
    }
}
