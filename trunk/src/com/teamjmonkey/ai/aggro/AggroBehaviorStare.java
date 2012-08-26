package com.teamjmonkey.ai.aggro;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.teamjmonkey.controls.MoveRandomControl;

public class AggroBehaviorStare extends AggroBehaviorBase {

    private Quaternion turnTo = new Quaternion();
    private Quaternion turnFrom;
    private float lerpAmount;
    private float lerpIncrease;
    private boolean isTurning;
    private float speed;

    public AggroBehaviorStare(float speed) {
        this.speed = speed;
    }

    public void update(float tpf) {
        turn(tpf);
    }

    public void onAggro(Vector3f target) {
        interruptOtherActions();
        calcTarget(target);
    }

    public void updateTarget(Vector3f target) {
        calcTarget(target);
    }

    private void calcTarget(Vector3f target) {
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
            lerpIncrease = (180f / angleDelta) * speed;
            lerpAmount = 0f;
            isTurning = true;
        } else {
            isTurning = false;
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
            }
        }
    }

    private void interruptOtherActions() {
        MoveRandomControl mrc = spatial.getControl(MoveRandomControl.class);
        if (mrc != null) {
            mrc.pause();
        }
    }

    private void resumeOtherActions() {
        MoveRandomControl mrc = spatial.getControl(MoveRandomControl.class);
        if (mrc != null) {
            mrc.resume();
        }
    }

    public void onAggroLoss() {
        resumeOtherActions();
    }
}
