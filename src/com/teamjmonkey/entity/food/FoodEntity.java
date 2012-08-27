package com.teamjmonkey.entity.food;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;

import com.jme3.math.ColorRGBA;

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

    public abstract String getPicture();

    public abstract ColorRGBA getColor();

    public abstract int getEnergy();

    @Override
    protected void addControl() {
    }

    @Override
    public void cleanup() {
    }

    @Override
    public void finalise() {
    }

    @Override
    public void write(JmeExporter e) throws IOException {
    }

    @Override
    public void read(JmeImporter e) throws IOException {
    }
}
