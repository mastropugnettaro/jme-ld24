package com.teamjmonkey.ai.areas;

import com.jme3.math.Vector3f;

public interface WalkableArea {

    public boolean isLocationInside(Vector3f location);

    public Vector3f getRandomPointInside();
}
