package com.teamjmonkey.controls;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;
import com.teamjmonkey.entity.food.Apple;
import com.teamjmonkey.util.GameState;

public class FoodThrowControl extends BaseControl implements ActionListener {

    private Camera cam = myApp.getCamera();
    private InputManager inputManager = myApp.getInputManager();
    private String LEFT_CLICK = "leftFood";

    public FoodThrowControl() {
        inputManager.addMapping(LEFT_CLICK, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(this, LEFT_CLICK);
    }

    @Override
    protected void controlUpdate(float tpf) {
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);

        if (spatial != null) {
        }
    }

    @Override
    public void cleanup() {
        spatial.removeControl(this);

        inputManager.deleteMapping(LEFT_CLICK);
        inputManager.removeListener(this);
    }

    public void onAction(String name, boolean isPressed, float tpf) {

        if (GameState.getGameState() != GameState.RUNNING) {
            return;
        }

        if (!isPressed && name.equals(LEFT_CLICK)) {
            // apply a force in the cam direction

            ((Apple)(spatial.getUserData("entity"))).addPhysicsControl();

          //  RigidBodyControl control = spatial.getControl(RigidBodyControl.class);
          //  control.setEnabled(true);
           // FoodEntity
       //     control.setCollisionGroup(PhysicsCollisionObject.COLLISION_GROUP_04);
   //         control.setCollideWithGroups(PhysicsCollisionObject.COLLISION_GROUP_01 | PhysicsCollisionObject.COLLISION_GROUP_03);

            spatial.getControl(RigidBodyControl.class).setLinearVelocity(cam.getDirection().mult(25));
            cleanup();
        }
    }

    /*
    private void initCrossHairs() {
    ch = new BitmapText(guiFont, false);
    ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
    ch.setText("+");
    ch.setLocalTranslation(
    settings.getWidth() * 0.5f - guiFont.getCharSet().getRenderedSize() / 3 * 2,
    settings.getHeight() * 0.5f + (ch.getLineHeight() / 2), 0);
    getGuiNode().attachChild(ch);
    }
     *
     */
}
