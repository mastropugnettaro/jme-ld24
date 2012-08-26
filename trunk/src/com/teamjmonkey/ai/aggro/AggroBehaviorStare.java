package com.teamjmonkey.ai.aggro;

import com.jme3.math.Vector3f;
import com.teamjmonkey.controls.MoveRandomControl;

public class AggroBehaviorStare extends AggroBehaviorBase {

    private float speed;

    public AggroBehaviorStare(float speed) {
        this.speed = speed;
    }

    public void onAggro(Vector3f target) {
        interruptOtherActions();
        entity.lookAt(target, speed);
    }

    public void updateTarget(Vector3f target) {
        entity.lookAt(target, speed);
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

    public void update(float tpf) {
    }
}
