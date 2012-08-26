package com.teamjmonkey.controls;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;

public class WeaponFollowCamControl extends BaseControl {

    private Camera cam = myApp.getCamera();
    private Vector3f location;

    @Override
    protected void controlUpdate(float tpf) {
        spatial.setLocalTranslation(location.add(0, 0, -4));

        Vector3f vectorDifference = new Vector3f(cam.getLocation().subtract(spatial.getWorldTranslation()));
        spatial.setLocalTranslation(vectorDifference.addLocal(spatial.getLocalTranslation()));

        Quaternion worldDiff = new Quaternion(cam.getRotation().subtract(spatial.getWorldRotation()));
        spatial.setLocalRotation(worldDiff.addLocal(spatial.getLocalRotation()));

        spatial.setLocalRotation(spatial.getLocalRotation().mult(new Quaternion().fromAngles(0, 0, 0.2f)));
        spatial.move(cam.getDirection().mult(3));
        spatial.move(cam.getLeft().mult(1).negateLocal());

    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        location = cam.getLocation();

        if (spatial != null) {
            spatial.addControl(myApp.getControlManager().getControl(MonkeyControl.BOB_CONTROL));
        }
    }

    @Override
    public void cleanup() {
        spatial.getControl(MonkeyControl.BOB_CONTROL.getClazz()).cleanup();
        spatial.removeControl(this);
    }
}