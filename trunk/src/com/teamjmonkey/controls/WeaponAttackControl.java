package com.teamjmonkey.controls;

import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Quaternion;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;
import com.teamjmonkey.animation.AnimComponent;
import com.teamjmonkey.entity.weapons.WeaponEntity;
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
    private WeaponEntity weaponEntity;

    @Override
    protected void controlUpdate(float tpf) {


        if (isAttacking) {

            timeCounter += tpf;

            if (timeCounter > 1) {
                isAttacking = false;
                timeCounter = 0;
                weaponEntity.idle();
            }
        }

        /*
        if (isAttacking) {
        timeCounter += tpf * direction;

        if (timeCounter >= 1) {
        direction = direction * -1;
        timeCounter = 1;
        } else if (timeCounter < 0) {
        timeCounter = 0;
        tempRotation.slerp(initialRotation, endRotation, 0);

        direction = direction * -1;
        }

        tempRotation.slerp(initialRotation, endRotation, timeCounter);
        spatial.setLocalRotation(tempRotation);
        }
         *
         */
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);

        if (spatial != null) {
            initialRotation = spatial.getLocalRotation().clone();

            inputManager.addMapping(LEFT_CLICK, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
            inputManager.addListener(actionListener, LEFT_CLICK);

            weaponEntity = ((WeaponEntity) spatial.getUserData("entity"));
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

                    weaponEntity.attack();
                    isAttacking = true;
                }
            }
        }
    };
}