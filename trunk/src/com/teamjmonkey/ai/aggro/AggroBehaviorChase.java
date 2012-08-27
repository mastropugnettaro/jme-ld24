package com.teamjmonkey.ai.aggro;

import com.jme3.scene.Spatial;
import com.teamjmonkey.ai.areas.WalkableArea;
import com.teamjmonkey.controls.MoveRandomControl;

public class AggroBehaviorChase extends AggroBehaviorBase {

    private WalkableArea area;
    private float speed;

    public AggroBehaviorChase(WalkableArea area, float speed) {
        this.area = area;
        this.speed = speed;
    }

    public void onAggro(Spatial target) {
        interruptOtherActions();
        entity.moveTo(target.getLocalTranslation(), speed, 1f);
    }

    private void interruptOtherActions() {
        MoveRandomControl mrc = spatial.getControl(MoveRandomControl.class);
        if (mrc != null) {
            mrc.pause();
        }
        entity.stop();
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
            if (area.isLocationInside(target.getLocalTranslation())) {
                entity.moveTo(target.getLocalTranslation(), speed, 1f);
            } else {
                entity.lookAt(target.getLocalTranslation(), 1f, false);
                //System.out.println("out of range");
            }
        }
    }
}
