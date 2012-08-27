package com.teamjmonkey.ai.areas;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import java.util.Random;

public class WalkableRectangle implements WalkableArea {

    private Random random = new Random();
    private float startX;
    private float startY;
    private float endX;
    private float endY;

    public WalkableRectangle(float posX, float posY, float width, float height) {
        if (width < 0) {
            this.startX = posX + width;
            this.endX = posX;
        } else {
            this.startX = posX;
            this.endX = posX + width;
        }
        if (height < 0) {
            this.startY = posY + height;
            this.endY = posY;
        } else {
            this.startY = posY;
            this.endY = posY + height;
        }
    }

    public boolean isLocationInside(Vector3f location) {
        return location.getX() >= startX && location.getX() <= endX
                && location.getY() >= startY && location.getY() <= endY;
    }

    public Vector3f getRandomPointInside(float yOffset) {
        return new Vector3f(random.nextFloat() * (endX + FastMath.abs(startX)) - FastMath.abs(startX), yOffset, random.nextFloat() * (endY + FastMath.abs(startX)) - FastMath.abs(startY));
    }
}
