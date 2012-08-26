package com.teamjmonkey.ai.aggro;

import com.jme3.math.Vector3f;
import com.teamjmonkey.ai.areas.WalkableArea;
import com.teamjmonkey.controls.MoveRandomControl;

public class AggroBehaviorChase extends AggroBehaviorBase {

    private WalkableArea area;
    private float speed;

    public AggroBehaviorChase(WalkableArea area, float speed) {
        this.area = area;
        this.speed = speed;
    }

    public void onAggro(Vector3f target) {
        interruptOtherActions();
        entity.moveTo(target, speed, 1f);
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

    public void update(float tpf, Vector3f target, boolean hasOtherAggroType) {
        if (!hasOtherAggroType) {
            if (area.isLocationInside(target)) {
                entity.moveTo(target, speed, 1f);
            } else {
                entity.stop();
            }
        }
    }
}
