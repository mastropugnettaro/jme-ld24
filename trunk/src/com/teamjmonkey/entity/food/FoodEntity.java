package com.teamjmonkey.entity.food;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import com.teamjmonkey.animation.AnimComponent;
import com.teamjmonkey.entity.BaseEntity;
import com.teamjmonkey.graphics.Graphics;
import java.io.IOException;

public abstract class FoodEntity extends BaseEntity implements Savable {

    public FoodEntity(Graphics graphics) {
        super(graphics);
    }

    public void remove() {
        cleanup();
        spatial.removeFromParent();
    }

    @Override
    public void write(JmeExporter e) throws IOException {
        OutputCapsule capsule = e.getCapsule(this);
        capsule.write(this.animComponent, "animComponent", null);
    }

    @Override
    public void read(JmeImporter e) throws IOException {
        InputCapsule capsule = e.getCapsule(this);
        animComponent = (AnimComponent) capsule.readSavable("animComponent", null);
    }
}
