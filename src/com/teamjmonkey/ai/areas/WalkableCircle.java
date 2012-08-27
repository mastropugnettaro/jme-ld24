package com.teamjmonkey.ai.areas;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import java.util.Random;

public class WalkableCircle implements WalkableArea {

    private Random random = new Random();
    private float centerX;
    private float centerY;
    private float radius;

    public WalkableCircle(float centerX, float centerY, float radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    public boolean isLocationInside(Vector3f location) {
        return FastMath.sqrt(FastMath.sqr(location.getX() - centerX)
                + FastMath.sqr(location.getX() - centerY)) <= radius;
    }

    public Vector3f getRandomPointInside(float yOffset) {
        float rAngle = random.nextFloat() * FastMath.TWO_PI;
        float rRadius = random.nextFloat() * radius;
        return new Vector3f(FastMath.cos(rAngle) * rRadius, yOffset, FastMath.sin(rAngle) * rRadius);
    }
}
