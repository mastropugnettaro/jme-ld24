package com.teamjmonkey.entity;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.teamjmonkey.controls.BaseControl;
import com.teamjmonkey.graphics.Graphics;

public abstract class MovableEntity extends BaseEntity {

    private static final float MOVEMENT_ACCURACY = 0.5f;
    //---//
    private Vector3f target;
    private Quaternion turnTo = new Quaternion();
    private Quaternion turnFrom;
    private float lerpAmount;
    private float lerpIncrease;
    private float speed;
    private float turnSpeedMultiplier;
    private boolean isPaused;
    private boolean isMoving;
    private boolean isTurning;
    private boolean isTurned;

    public MovableEntity() {
        super();
    }

    public MovableEntity(Graphics graphics) {
        super(graphics);
        spatial.addControl(new BaseControl() {

            @Override
            protected void controlUpdate(float tpf) {
                updateMoveableEntity(tpf);
            }
        });
    }

    private void updateMoveableEntity(float tpf) {
        if (isPaused) {
            return;
        }
        if (isMoving) {
            if (isTurned) {
                move(tpf);
            } else {
                turn(tpf);
            }
        } else if (isTurning) {
            turn(tpf);
        }
    }

    public void moveTo(Vector3f target, float speed, float turnSpeedMultiplier) {
        this.speed = speed;
        isMoving = true;
        moveAnim();
        lookAt(target, turnSpeedMultiplier);
    }

    private void move(float tpf) {
        float deltaX = target.getX() - spatial.getLocalTranslation().getX();
        float deltaZ = target.getZ() - spatial.getLocalTranslation().getZ();
        float delta = FastMath.sqrt(FastMath.sqr(deltaX) + FastMath.sqr(deltaZ));
        float moveX = 0f;
        float moveY = 0f;

        if (FastMath.abs(deltaX) < MOVEMENT_ACCURACY && FastMath.abs(deltaZ) < MOVEMENT_ACCURACY) {
            isMoving = false;
            idleAnim();
        } else {
            if (delta <= speed * tpf) {
                moveX = deltaX;
                moveY = deltaZ;
            } else {
                float scale = speed / delta;
                moveX = deltaX * scale * tpf;
                moveY = deltaZ * scale * tpf;
            }
            spatial.move(moveX, 0f, moveY);
        }
    }

    public void lookAt(Vector3f target, float turnSpeedMultiplier) {
        lookAt(target, turnSpeedMultiplier, true);
    }

    public void lookAt(Vector3f target, float turnSpeedMultiplier, boolean useWalkAnimation) {
        this.target = target;
        this.turnSpeedMultiplier = turnSpeedMultiplier;
        isTurning = true;
        isTurned = false;
        calcTurn(useWalkAnimation);
    }

    private void calcTurn(boolean useWalkAnimation) {
        Vector3f worldTranslation = spatial.getWorldTranslation();
        Vector3f targetVec = new Vector3f(target);
        targetVec.setY(spatial.getLocalTranslation().getY());
        targetVec.subtractLocal(worldTranslation);
        turnFrom = spatial.getLocalRotation().clone();
        turnTo.lookAt(targetVec, Vector3f.UNIT_Y);
        float angle1 = FastMath.RAD_TO_DEG * turnTo.toAngles(null)[1];
        float angle2 = FastMath.RAD_TO_DEG * turnFrom.toAngles(null)[1];
        if (angle1 < 0f) {
            angle1 += 360f;
        }
        if (angle2 < 0f) {
            angle2 += 360f;
        }
        float angleDelta = FastMath.abs(angle1 - angle2);
        if (angleDelta > 180f) {
            angleDelta = FastMath.abs(angleDelta - 360f);
        }
        if (angleDelta > 1f) {
            lerpIncrease = (180f / angleDelta) * turnSpeedMultiplier;
            lerpAmount = 0f;
            isTurning = true;
            if (useWalkAnimation) {
                moveAnim();
            } else {
                idleAnim();
            }
        } else {
            isTurned = true;
        }
    }

    private void turn(float tpf) {
        if (isTurning) {
            if (lerpAmount < 1f) {
                lerpAmount += lerpIncrease * tpf;
                if (lerpAmount > 1f) {
                    lerpAmount = 1f;
                }
                Quaternion newRotation = Quaternion.ZERO.slerp(turnFrom, turnTo, lerpAmount);
                spatial.setLocalRotation(newRotation);
            } else {
                isTurning = false;
                isTurned = true;
                if (!isMoving) {
                    idleAnim();
                }
            }
        }
    }

    public void pause() {
        isPaused = true;
    }

    public void resume() {
        isPaused = false;
    }

    public void stop() {
        isMoving = false;
        isTurning = false;
        isTurned = false;
        isPaused = false;
        idleAnim();
    }

    public boolean isPaused() {
        return isPaused;
    }

    public boolean isMoving() {
        return isMoving || isTurning;
    }

    public abstract void idleAnim();

    public abstract void moveAnim();

    public abstract void jumpAnim();

    public abstract void attackAnim();
}
