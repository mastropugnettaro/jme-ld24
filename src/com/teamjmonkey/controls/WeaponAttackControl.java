package com.teamjmonkey.controls;

import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;
import com.teamjmonkey.util.GameState;

public class WeaponAttackControl extends BaseControl {

    private float timeCounter = 0;
    private int direction = 1;
    private Camera cam = myApp.getCamera();
    private Quaternion initialRotation;
    private Quaternion endRotation = new Quaternion();
    private Quaternion tempRotation;
    private final String LEFT_CLICK = "LeftClick";
    private InputManager inputManager = myApp.getInputManager();
    private boolean isAttacking = false;

    @Override
    protected void controlUpdate(float tpf) {

        if (isAttacking) {
            timeCounter += tpf * direction;

            if (timeCounter >= 1) {
                direction = direction * -1;
                timeCounter = 1;
            } else if (timeCounter < 0) {
                timeCounter = 0;
                tempRotation.slerp(initialRotation, endRotation, 0);
                isAttacking = false;
                direction = direction * -1;
            }

            tempRotation.slerp(initialRotation, endRotation, timeCounter);
            spatial.setLocalRotation(tempRotation);
        }
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);

        if (spatial != null) {
            initialRotation = spatial.getLocalRotation().clone();

            inputManager.addMapping(LEFT_CLICK, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
            inputManager.addListener(actionListener, LEFT_CLICK);
        }
    }

    @Override
    public void cleanup() {
        // remove it all
        spatial.removeControl(this);
        inputManager.deleteMapping(LEFT_CLICK);
        inputManager.removeListener(actionListener);
    }

    private ActionListener actionListener = new ActionListener() {

        public void onAction(String name, boolean isPressed, float tpf) {

            if (GameState.getGameState() == GameState.PAUSED) {
                return;
            }

            if (!isPressed && name.equals(LEFT_CLICK)) {
                if (!isAttacking) {
                    initialRotation = spatial.getLocalRotation().clone();

                    Vector3f crossLocal = cam.getDirection().clone().crossLocal(initialRotation.mult(Vector3f.UNIT_Y)).normalizeLocal();
                    spatial.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.PI / 2, crossLocal));
                    endRotation = spatial.getLocalRotation().clone();

                    tempRotation = new Quaternion();
                    isAttacking = true;
                }
            }
        }
    };
}