package com.teamjmonkey.ai.aggro;

import com.jme3.scene.Spatial;
import com.teamjmonkey.controls.MoveRandomControl;

public class AggroBehaviorStare extends AggroBehaviorBase {

    private float speed;

    public AggroBehaviorStare(float speed) {
        this.speed = speed;
    }

    public void onAggro(Spatial target) {
        interruptOtherActions();
        entity.lookAt(target.getLocalTranslation(), speed, false);
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

    public void update(float tpf, Spatial target, boolean hasOtherAggroType) {
        if (!hasOtherAggroType) {
            entity.lookAt(target.getLocalTranslation(), speed, false);
        }
    }
}
