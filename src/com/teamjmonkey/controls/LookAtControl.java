package com.teamjmonkey.controls;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;

public class LookAtControl extends BaseControl {

    private Camera cam = myApp.getCamera();
    private Vector3f location;

    @Override
    protected void controlUpdate(float tpf) {
        cam.lookAt(location, Vector3f.UNIT_Y);
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        location = spatial.getWorldTranslation();
    }
}