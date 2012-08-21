package com.teamjmonkey.controls;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;

public class TestControl extends BaseControl {

    private Camera cam = myApp.getCamera();

    @Override
    protected void controlUpdate(float tpf) {
        cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
    }
}