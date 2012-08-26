package com.teamjmonkey.controls;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;
import com.teamjmonkey.util.GameState;

public class BobControl extends BaseControl {

    private Camera cam = myApp.getCamera();
    private Vector3f camLocation;
    private Vector3f spatialLocation;
    private float timeCounter = 0;

    @Override
    protected void controlUpdate(float tpf) {

        if (timeCounter >= FastMath.TWO_PI) {
            timeCounter -= FastMath.TWO_PI;
        }

        // check if hes moving, or if the model is not aligned with the camera
        //  if (GameState.isMoving()
        //           || FastMath.abs(spatialLocation.getY() + FastMath.sin(timeCounter) - camLocation.getY()) > 0.04) {

        spatial.setLocalTranslation(spatialLocation.setY(spatialLocation.getY() + 0.2f * FastMath.sin(timeCounter)));
        timeCounter += tpf;
        //   } else {
        //       timeCounter = 0;
        //    }
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);

        if (spatial != null) {
            camLocation = cam.getLocation();
            spatialLocation = spatial.getLocalTranslation();
        }
    }
}