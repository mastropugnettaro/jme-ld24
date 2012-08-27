package com.teamjmonkey.ai.aggro;

import com.jme3.scene.Spatial;
import com.teamjmonkey.animation.AnimType;

public class AggroBehaviorFight extends AggroBehaviorBase {

    private boolean isFighting;
    private float attackSpeed;
    private float cooldown;
    private float attackSpeedTimer;
    private float cooldownTimer;

    public AggroBehaviorFight(float attackSpeed, float cooldown) {
        this.attackSpeed = attackSpeed;
        this.cooldown = cooldown;
    }

    public void update(float tpf, Spatial target, boolean hasOtherAggroType) {
        if (cooldownTimer > 0f) {
            cooldownTimer -= tpf;
        } else {
            if (isFighting) {
                if (attackSpeedTimer > 0f) {
                    attackSpeedTimer -= tpf;
                } else {
                    attack();
                }
            }
        }
    }

    private void attack() {
        entity.attackAnim();
        //TODO do some damage!
        cooldownTimer = cooldown;
    }

    public void onAggro(Spatial target) {
        isFighting = true;
        reset();
    }

    private void reset() {
        attackSpeedTimer = attackSpeed;
        entity.stop();
    }

    public void onAggroLoss() {
        isFighting = false;
    }

    public boolean isFighting() {
        return isFighting;
    }
}
