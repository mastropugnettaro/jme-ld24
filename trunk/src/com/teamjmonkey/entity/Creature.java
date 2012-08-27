package com.teamjmonkey.entity;

import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.teamjmonkey.ai.aggro.AggroBehaviorChaseFood;
import com.teamjmonkey.ai.aggro.AggroBehaviorEat;
import com.teamjmonkey.ai.areas.WalkableArea;
import com.teamjmonkey.ai.areas.WalkableRectangle;
import com.teamjmonkey.animation.AnimType;
import com.teamjmonkey.controls.AggroControl;
import com.teamjmonkey.controls.FoodControl;
import com.teamjmonkey.controls.MoveRandomControl;
import com.teamjmonkey.graphics.Graphics;
import com.teamjmonkey.util.Util;

public abstract class Creature extends MovableEntity {

    private final float size;
    private final float energyToEvolve;
    private float currentEnergy = 0;
    private ColorRGBA color;

    public Creature(Graphics graphics, float size, float energyToEvolve) {
        super(graphics);
        this.size = size;
        this.energyToEvolve = energyToEvolve;
        this.color = new ColorRGBA(0.5f, 0.5f, 0.5f, 1f);
    }

    @Override
    public void addControl() {
        float circleRadius = 40f;
        WalkableArea wa = new WalkableRectangle(-circleRadius, -circleRadius, 2 * circleRadius, 2 * circleRadius); //TODO adjust adjust walkable area
        spatial.addControl(new MoveRandomControl(this, wa));
        spatial.addControl(new AggroControl(this, circleRadius * 1.5f, size,
                PhysicsCollisionObject.COLLISION_GROUP_06,
                PhysicsCollisionObject.COLLISION_GROUP_04,
                new AggroBehaviorChaseFood(wa, 10f),
                new AggroBehaviorEat(this)));
    }

    public void eat(FoodControl food) {
        addEnergy(food.getEnergy());
        addColor(food.getColor());
    }

    private void addEnergy(float amount) {
        this.currentEnergy += amount;
        if (currentEnergy >= energyToEvolve) {
            evolve();
        }
    }

    private void addColor(ColorRGBA color) {
        //TODO add an algorithm
        float r = (this.color.getRed() + color.getRed()) / 2f;
        float g = (this.color.getGreen() + color.getGreen()) / 2f;
        float b = (this.color.getBlue() + color.getBlue()) / 2f;
        float a = (this.color.getAlpha() + color.getAlpha()) / 2f;
        this.color.set(r, g, b, a);

        updateTexture();
    }

    private void updateTexture() {
        Geometry g = Util.getGeometryFromNode(spatial);
        if (g != null) {
            Material m = g.getMaterial();
            m.setColor("Ambient", color);
            m.setColor("Diffuse", color);
        }
    }

    private void evolve() {
        //TODO evolve...
    }

    @Override
    public void addPhysicsControl() {
    }

    @Override
    public void addMaterial() {
    }

    @Override
    public CollisionShape getCollisionShape() {
        return new SphereCollisionShape(getExtents().z);
    }

    @Override
    public void cleanup() {
        spatial.removeControl(MoveRandomControl.class);
        spatial.getControl(AggroControl.class).cleanup();
        spatial.removeControl(AggroControl.class);
    }

    @Override
    public void finalise() {
        addControl();
    }

    @Override
    public void idleAnim() {
        animComponent.setCurAnim(AnimType.IDLE);
    }

    @Override
    public void moveAnim() {
        animComponent.setCurAnim(AnimType.WALK);
    }

    @Override
    public void jumpAnim() {
        animComponent.setCurAnim(AnimType.JUMP);
    }

    public void eatAnim() {
        animComponent.setCurAnim(AnimType.EAT);
    }

    @Override
    public void attackAnim() {
        animComponent.setCurAnim(AnimType.ATTACK);
    }
}
