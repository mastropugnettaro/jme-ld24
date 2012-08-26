package com.teamjmonkey.controls;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.teamjmonkey.ai.areas.WalkableArea;
import java.util.Random;

public class MoveRandomControl extends BaseControl {

    private static final float MOVEMENT_ACCURACY = 0.5f;
    private static final float REDUCE_WAIT_CHANCE = 0.2f;
    private static final float TURN_SPEED_MULTIPLIER = 0.25f;
    //---//
    private final Random random = new Random();
    private final WalkableArea area;
    private final float chanceToWait;
    private final float waitTime;
    private final float baseSpeed;
    private boolean isMoving;
    private boolean isWaiting;
    private boolean isTurned = true;
    private boolean isTurning;
    private boolean isPaused;
    private Vector3f target;
    private Quaternion turnTo = new Quaternion();
    private Quaternion turnFrom;
    private float lerpAmount;
    private float lerpIncrease;
    private float currentChanceToWait;
    private float speed;
    private float waitTimer;

    public MoveRandomControl(WalkableArea area) {
        this(area, 5f);
    }

    public MoveRandomControl(WalkableArea area, float speed) {
        this(area, speed, 0.8f);
    }

    public MoveRandomControl(WalkableArea area, float speed, float chanceToWait) {
        this(area, speed, chanceToWait, 1f);
    }

    public MoveRandomControl(WalkableArea area, float speed, float chanceToWait, float waitTime) {
        this.baseSpeed = speed;
        this.area = area;
        this.chanceToWait = chanceToWait;
        this.waitTime = waitTime;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (isPaused) {
            return;
        }
        if (!isTurned && !isWaiting) {
            turn(tpf);
        } else if (isMoving && !isWaiting) {
            move(tpf);
        } else if (this.random.nextFloat() > currentChanceToWait && !isWaiting) {
            calculateNewTarget();
        } else {
            wait(tpf);
        }
    }

    private void wait(float tpf) {
        if (isWaiting) {
            if (waitTimer <= 0f) {
                isWaiting = false;
                currentChanceToWait -= REDUCE_WAIT_CHANCE;
            } else {
                waitTimer -= tpf;
            }
        } else {
            waitTimer = waitTime;
            isWaiting = true;
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
            }
        } else {
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
            if (angleDelta != 0f) {
                lerpIncrease = (180f / angleDelta) * speed * TURN_SPEED_MULTIPLIER;
                lerpAmount = 0f;
                isTurning = true;
            } else {
                isTurned = true;
            }
        }
    }

    private void move(float tpf) {
        float deltaX = target.getX() - spatial.getLocalTranslation().getX();
        float deltaZ = target.getZ() - spatial.getLocalTranslation().getZ();
        float delta = FastMath.sqrt(FastMath.sqr(deltaX) + FastMath.sqr(deltaZ));
        float moveX = 0f;
        float moveY = 0f;

        if (FastMath.abs(deltaX) < MOVEMENT_ACCURACY && FastMath.abs(deltaZ) < MOVEMENT_ACCURACY) {
            isMoving = false;
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

    private void calculateNewTarget() {
        target = this.area.getRandomPointInside();
        speed = baseSpeed * (random.nextFloat() * 2.0f + 0.5f);
        isTurned = false;
        isMoving = true;
        currentChanceToWait = chanceToWait;
    }

    public void pause() {
        isPaused = true;
    }

    public void resume() {
        calculateNewTarget();
        isTurning = false;
        isTurned = true;
        isWaiting = false;
        isPaused = false;
        isMoving = false;
    }
}
