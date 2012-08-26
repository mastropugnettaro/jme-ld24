package com.teamjmonkey.controls;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;
import com.teamjmonkey.entity.MainCharacter;
import com.teamjmonkey.util.GameState;

public class MainCharacterControl extends BaseControl implements ActionListener, PhysicsCollisionListener {

    private Vector3f walkDirection = new Vector3f();
    private boolean left = false, right = false, up = false, down = false;
    private CharacterControl player;
    private Camera cam = myApp.getCamera();
    private InputManager inputManager = myApp.getInputManager();
    private final String LEFT_MOVE = "LeftMove";
    private final String RIGHT_MOVE = "RightMove";
    private final String FORWARD_MOVE = "UpMove";
    private final String BACKWARD_MOVE = "BackMove";
    private final String JUMP = "Jump";
    private final String MAIN_CHARACTER = "mainCharacter";
    private final String ENEMY = "enemy";

    public MainCharacterControl() {
        addDesktopInputs();
        myApp.getBulletAppState().getPhysicsSpace().addCollisionListener(this);
    }

    @Override
    protected void controlUpdate(float tpf) {

        if (player == null) {
            return;
        }

        Vector3f camDir = cam.getDirection().clone();
        Vector3f camLeft = cam.getLeft().clone();
        walkDirection.set(0, 0, 0);

        if (left) {
            walkDirection.addLocal(camLeft);
        }
        if (right) {
            walkDirection.addLocal(camLeft.negate());
        }
        if (up) {
            walkDirection.addLocal(camDir);
        }
        if (down) {
            walkDirection.addLocal(camDir.negate());
        }

        walkDirection.setY(0).normalizeLocal().multLocal(0.2f);
        player.setWalkDirection(walkDirection);
        cam.setLocation(player.getPhysicsLocation());
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);

        if (spatial != null) {
            player = ((MainCharacter) spatial.getUserData("entity")).getCharacterControl();
        }
    }

    public void cleanUp() {
        spatial.removeControl(this);
        removeDesktopInput();

        GameState.setMoving(false);
    }

    public void onAction(String binding, boolean value, float tpf) {

        if (binding.equals(LEFT_MOVE)) {
            left = value;
        } else if (binding.equals(RIGHT_MOVE)) {
            right = value;
        } else if (binding.equals(FORWARD_MOVE)) {
            up = value;
        } else if (binding.equals(BACKWARD_MOVE)) {
            down = value;
        } else if (binding.equals(JUMP)) {
            if (value) {
                player.jump();
            }
        }

        boolean isMoving = left || right || up || down;
        GameState.setMoving(isMoving);
    }

    private void addDesktopInputs() {
        inputManager.addMapping(LEFT_MOVE, new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping(RIGHT_MOVE, new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping(FORWARD_MOVE, new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping(BACKWARD_MOVE, new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping(JUMP, new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, LEFT_MOVE, RIGHT_MOVE, FORWARD_MOVE, BACKWARD_MOVE, JUMP);
    }

    private void removeDesktopInput() {
        inputManager.deleteMapping(LEFT_MOVE);
        inputManager.deleteMapping(RIGHT_MOVE);
        inputManager.deleteMapping(FORWARD_MOVE);
        inputManager.deleteMapping(BACKWARD_MOVE);
        inputManager.deleteMapping(JUMP);
        inputManager.removeListener(this);
    }

    public void collision(PhysicsCollisionEvent event) {

        Spatial a = event.getNodeA();
        Spatial b = event.getNodeB();

        if (a == null || b == null) {
            return;
        }

        String aName = event.getNodeA().getName();
        String bName = event.getNodeB().getName();

        // success they have collide

        if ((aName.equals(MAIN_CHARACTER) && bName.equals(ENEMY))
                || (bName.equals(MAIN_CHARACTER) && aName.equals(ENEMY))) {

            Spatial enemy = aName.equals(ENEMY) ? a : b;
            Spatial mainCharacter = aName.equals(ENEMY) ? b : a;

            // check if the enemy is in wait state

            // check if the enemy is in attack state

            // note the use of getParent, this was used to line the collision shapes
            MoveRandomControl control = enemy.getParent().getControl(MoveRandomControl.class);
            if (control != null) {
                control.setEnabled(false);
            }

            // THIS MUST BE CALLED SOMEWHERE
            //  MovementControl.setEnabled(true);
            //  MovementControl.resume(); // if neccessary
        }
    }
}