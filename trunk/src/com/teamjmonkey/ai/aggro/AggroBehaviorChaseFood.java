package com.teamjmonkey.ai.aggro;

import com.jme3.scene.Spatial;
import com.teamjmonkey.ai.areas.WalkableArea;
import com.teamjmonkey.animation.AnimType;
import com.teamjmonkey.controls.MoveRandomControl;

public class AggroBehaviorChaseFood extends AggroBehaviorBase {

    private WalkableArea area;
    private float speed;
    private boolean isHappy;

    public AggroBehaviorChaseFood(WalkableArea area, float speed) {
        this.area = area;
        this.speed = speed;
    }

    public void onAggro(Spatial target) {
        isHappy = false;
        interruptOtherActions();
        entity.jumpAnim();
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
            if (isHappy) {
                if (area.isLocationInside(target.getLocalTranslation())) {
                    entity.moveTo(target.getLocalTranslation(), speed, 1f);
                } else {
                    entity.stop();
                    //System.out.println("out of range");
                }
            } else {
                if (entity.getAnimComponent().getCurAnim().equals(AnimType.IDLE)) {
                    isHappy = true;
                }
            }
        }
    }
}
