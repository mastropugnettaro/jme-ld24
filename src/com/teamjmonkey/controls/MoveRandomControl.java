package com.teamjmonkey.controls;

import com.teamjmonkey.ai.areas.WalkableArea;
import com.teamjmonkey.animation.AnimType;
import com.teamjmonkey.entity.MovableEntity;
import java.util.Random;

public class MoveRandomControl extends BaseControl {

    private static final float REDUCE_WAIT_CHANCE = 0.2f;
    private static final float TURN_SPEED_MULTIPLIER = 0.25f;
    //---//
    private MovableEntity entity;
    private final Random random = new Random();
    private final WalkableArea area;
    private final float chanceToWait;
    private final float maxWaitTime;
    private final float baseSpeed;
    private boolean isPaused;
    private Float pauseTimer = null;
    private float currentChanceToWait;
    private boolean isWaiting;
    private float waitTimer;

    public MoveRandomControl(MovableEntity entity, WalkableArea area) {
        this(entity, area, 5f);
    }

    public MoveRandomControl(MovableEntity entity, WalkableArea area, float speed) {
        this(entity, area, speed, 0.8f);
    }

    public MoveRandomControl(MovableEntity entity, WalkableArea area, float speed, float chanceToWait) {
        this(entity, area, speed, chanceToWait, 5f);
    }

    public MoveRandomControl(MovableEntity entity, WalkableArea area, float speed, float chanceToWait, float maxWaitTime) {
        this.entity = entity;
        this.baseSpeed = speed;
        this.area = area;
        this.chanceToWait = chanceToWait;
        this.maxWaitTime = maxWaitTime;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (isPaused) {
            if (pauseTimer != null) {
                if (pauseTimer > 0) {
                    pauseTimer -= tpf;
                } else {
                    resume();
                }
            }
            return;
        } else if (entity.isMoving()) {
            return;
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
            waitTimer = maxWaitTime * random.nextFloat();
            isWaiting = true;
        }
    }

    private void calculateNewTarget() {
        entity.moveTo(this.area.getRandomPointInside(),
                baseSpeed * (random.nextFloat() * 2.0f + 0.5f),
                TURN_SPEED_MULTIPLIER);
        currentChanceToWait = chanceToWait;
    }

    public void pause(float time) {
        pauseTimer = time;
        pause();
    }

    public void pause() {
        isPaused = true;
        entity.stop();
    }

    public void resume() {
        calculateNewTarget();
        pauseTimer = null;
        isWaiting = false;
        isPaused = false;
    }
}
