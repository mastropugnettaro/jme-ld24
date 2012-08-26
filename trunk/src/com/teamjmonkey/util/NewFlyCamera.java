package com.teamjmonkey.util;

import com.jme3.collision.MotionAllowedListener;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.*;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

public class NewFlyCamera implements AnalogListener, ActionListener {

    private static String[] mappings = new String[]{
        "FLYCAM_Left",
        "FLYCAM_Right",
        "FLYCAM_Up",
        "FLYCAM_Down",
        "FLYCAM_RotateDrag",
    };

    protected Camera cam;
    protected Vector3f initialUpVec;
    protected float rotationSpeed = 0.4f;
    protected MotionAllowedListener motionAllowed = null;
    protected boolean enabled = true;
    protected boolean dragToRotate = false;
    protected boolean canRotate = false;
    protected InputManager inputManager;
    protected boolean firstPersonConstraint = false;

    /**
     * Creates a new FlyByCamera to control the given Camera object.
     * @param cam
     */
    public NewFlyCamera(Camera cam) {
        this.cam = cam;
        initialUpVec = cam.getUp().clone();
    }

    /**
     * Sets the up vector that should be used for the camera.
     * @param upVec
     */
    public void setUpVector(Vector3f upVec) {
        initialUpVec.set(upVec);
    }

    public void setMotionAllowedListener(MotionAllowedListener listener) {
        this.motionAllowed = listener;
    }

    /**
     * Sets the rotation speed.
     * @param rotationSpeed
     */
    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    /**
     * Gets the move speed. The speed is given in world units per second.
     * @return rotationSpeed
     */
    public float getRotationSpeed() {
        return rotationSpeed;
    }

    /**
     * @param enable If false, the camera will ignore input.
     */
    public void setEnabled(boolean enable) {
        if (enabled && !enable) {
            if (inputManager != null && (!dragToRotate || (dragToRotate && canRotate))) {
                inputManager.setCursorVisible(true);
            }
        }
        enabled = enable;
    }

    /**
     * @return If enabled
     * @see FlyByCamera#setEnabled(boolean)
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @return If drag to rotate feature is enabled.
     *
     * @see FlyByCamera#setDragToRotate(boolean)
     */
    public boolean isDragToRotate() {
        return dragToRotate;
    }

    /**
     * Set if drag to rotate mode is enabled.
     *
     * When true, the user must hold the mouse button
     * and drag over the screen to rotate the camera, and the cursor is
     * visible until dragged. Otherwise, the cursor is invisible at all times
     * and holding the mouse button is not needed to rotate the camera.
     * This feature is disabled by default.
     *
     * @param dragToRotate True if drag to rotate mode is enabled.
     */
    public void setDragToRotate(boolean dragToRotate) {
        this.dragToRotate = dragToRotate;
        if (inputManager != null) {
            inputManager.setCursorVisible(dragToRotate);
        }
    }

    /**
     * @param firstPersonConstraint True to constraint along X axis of view
     */
    public void setConstraint(boolean firstPersonConstraint) {
        this.firstPersonConstraint = firstPersonConstraint;
    }

    /**
     * @return If constrained rotatation is enabled.
     *
     * @see FlyByCamera#setConstraint(boolean)
     */
    public boolean isConstrained() {
        return firstPersonConstraint;
    }

    /**
     * Registers the FlyByCamera to receive input events from the provided
     * Dispatcher.
     * @param inputManager
     */
    public void registerWithInput(InputManager inputManager) {
        this.inputManager = inputManager;

        // both mouse and button - rotation of cam
        inputManager.addMapping("FLYCAM_Left", new MouseAxisTrigger(MouseInput.AXIS_X, true),
                new KeyTrigger(KeyInput.KEY_LEFT));

        inputManager.addMapping("FLYCAM_Right", new MouseAxisTrigger(MouseInput.AXIS_X, false),
                new KeyTrigger(KeyInput.KEY_RIGHT));

        inputManager.addMapping("FLYCAM_Up", new MouseAxisTrigger(MouseInput.AXIS_Y, false),
                new KeyTrigger(KeyInput.KEY_UP));

        inputManager.addMapping("FLYCAM_Down", new MouseAxisTrigger(MouseInput.AXIS_Y, true),
                new KeyTrigger(KeyInput.KEY_DOWN));

        // mouse only - zoom in/out with wheel, and rotate drag
        inputManager.addMapping("FLYCAM_RotateDrag", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));

        inputManager.addListener(this, mappings);
        inputManager.setCursorVisible(dragToRotate || !isEnabled());
    }

    /**
     * Registers the FlyByCamera to receive input events from the provided
     * Dispatcher.
     * @param inputManager
     */
    public void unregisterInput() {

        if (inputManager == null) {
            return;
        }

        for (String s : mappings) {
            if (inputManager.hasMapping(s)) {
                inputManager.deleteMapping(s);
            }
        }

        inputManager.removeListener(this);
        inputManager.setCursorVisible(!dragToRotate);
    }

    protected void rotateCamera(float value, Vector3f axis) {
        if (dragToRotate) {
            if (canRotate) {
//                value = -value;
            } else {
                return;
            }
        }

        Matrix3f mat = new Matrix3f();
        mat.fromAngleNormalAxis(rotationSpeed * value, axis);

        Vector3f up = cam.getUp();
        Vector3f left = cam.getLeft();
        Vector3f dir = cam.getDirection();

        mat.mult(up, up);
        mat.mult(left, left);
        mat.mult(dir, dir);

        if (firstPersonConstraint && up.getY() < 0) {
            return;
        }

        Quaternion q = new Quaternion();
        q.fromAxes(left, up, dir);
        q.normalizeLocal();

        cam.setAxes(q);
    }

    public void onAnalog(String name, float value, float tpf) {
        if (!enabled) {
            return;
        }

        if (name.equals("FLYCAM_Left")) {
            rotateCamera(value, initialUpVec);
        } else if (name.equals("FLYCAM_Right")) {
            rotateCamera(-value, initialUpVec);
        } else if (name.equals("FLYCAM_Up")) {
            rotateCamera(-value, cam.getLeft());
        } else if (name.equals("FLYCAM_Down")) {
            rotateCamera(value, cam.getLeft());
        }
    }

    public void onAction(String name, boolean value, float tpf) {
        if (!enabled) {
            return;
        }

        if (name.equals("FLYCAM_RotateDrag") && dragToRotate) {
            canRotate = value;
            inputManager.setCursorVisible(!value);
        }
    }
}