package com.teamjmonkey.controls;

import com.jme3.math.FastMath;
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

        if (spatial.getName().equals("gun")) {
            spatial.move(cam.getDirection().mult(3));
            spatial.move(cam.getUp().mult(-0.8f));
            spatial.move(cam.getLeft().mult(-1f));
            spatial.rotate(0.3f, FastMath.PI, 0);

        } else {
            spatial.move(cam.getDirection().mult(2));
            spatial.move(cam.getUp().mult(-1.5f));
            spatial.move(cam.getLeft().mult(-1f));

            spatial.setLocalRotation(spatial.getLocalRotation().mult(new Quaternion().fromAngles(FastMath.PI - 0.3f, 0, -0.2f)));
        }
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