package com.teamjmonkey.ai.aggro;

import com.jme3.scene.Spatial;
import com.teamjmonkey.animation.AnimType;
import com.teamjmonkey.controls.FoodControl;
import com.teamjmonkey.entity.Creature;
import com.teamjmonkey.entity.food.FoodEntity;

public class AggroBehaviorEat extends AggroBehaviorBase {

    private Creature creature;
    private Spatial target;
    private boolean done;

    public AggroBehaviorEat(Creature creature) {
        this.creature = creature;
    }

    public void update(float tpf, Spatial target, boolean hasOtherAggroType) {
        if (done) {
            return;
        }
        if (!creature.getAnimComponent().getCurAnim().equals(AnimType.EAT)) {
            this.target = target;
            eat();
        }
    }

    public void onAggro(Spatial target) {
        done = false;
        creature.stop();
        creature.eatAnim();
        this.target = target;
    }

    private void eat() {
        FoodControl fc = target.getControl(FoodControl.class);
        FoodEntity fe = (FoodEntity)target.getUserData("entity");
        if (fc != null && fe != null) {
            creature.stop();
            creature.eat(fc);
            fe.remove();
            done = true;
        }
    }

    public void onAggroLoss() {
    }
}
