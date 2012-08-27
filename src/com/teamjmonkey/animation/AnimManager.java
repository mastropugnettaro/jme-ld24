package com.teamjmonkey.animation;

import com.jme3.app.state.AppStateManager;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.app.state.AbstractAppState;
import com.teamjmonkey.GameNameGoesHere;
import com.teamjmonkey.entity.BaseEntity;
import com.teamjmonkey.util.GameState;

public class AnimManager extends AbstractAppState implements AnimEventListener {

    private GameNameGoesHere myApp = GameNameGoesHere.getApp();
    public Collection<?> entList;
    private Iterator<?> it;
    private HashMap<String, AnimInfo> map;
    private Iterator<?> it2;

    public AnimManager() {
    }

    @Override
    public void stateAttached(AppStateManager stateManager) {
        super.stateAttached(stateManager);

        this.entList = myApp.getLevelManager().getCurrentLevel().getAllEntities();
        this.map = AnimHandler.loadAnimationSet();


    }

    @Override
    public void stateDetached(AppStateManager stateManager) {
        super.stateDetached(stateManager);
        cleanup();
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        if (!isEnabled()) {
            return;
        }
        it = entList.iterator();
        while (it.hasNext()) {
            BaseEntity ent = (BaseEntity) it.next();
            if (ent.getAnimComponent() != null) {
                if (ent.getAnimComponent().getCurAnim() != null) {
                    AnimInfo curAnimInfo = map.get(ent.getAnimComponent().getCurAnim());
                    AnimChannel channel = ent.getAnimComponent().getAnimControl().getChannel(AnimConf.UPPER_BODY);

                    if (GameState.getGameState() == GameState.RUNNING) {
                        if (channel.getAnimationName() != null ) {
                            if (!channel.getAnimationName().equals(curAnimInfo.name) || ent.getAnimComponent().getAnimControl().getChannel(AnimConf.UPPER_BODY).getSpeed() == 0f) {
                                ent.getAnimComponent().getAnimControl().getChannel(AnimConf.UPPER_BODY).setLoopMode(curAnimInfo.loop);
                                ent.getAnimComponent().getAnimControl().getChannel(AnimConf.UPPER_BODY).setSpeed(curAnimInfo.speed);
                                ent.getAnimComponent().getAnimControl().getChannel(AnimConf.UPPER_BODY).setAnim(curAnimInfo.name, curAnimInfo.blendTime);
                            }
                        }
                    }

                }
            }
        }

    }


    @Override
    public void onAnimChange(AnimControl arg0, AnimChannel arg1, String arg2) {
    }

    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animation) {
        Collection<?> list = myApp.getLevelManager().getCurrentLevel().getAllEntities();
        
        if(list == null) return;
        it2 = list.iterator();
        while (it2.hasNext()) {
            BaseEntity ent = (BaseEntity) it2.next();
            if (ent.getAnimComponent() != null) {
                if (ent.getAnimComponent().getCurAnim() != null) {
                    String animName = channel.getAnimationName();
                    if (animName.equals("Jump") || animName.equals("Attack") || animName.equals("Eat")  ) {
                        if (control.getSpatial().getName().equals("bull")) {
                            ent.getAnimComponent().setCurAnim(AnimType.STAND);
                        } else {
                            ent.getAnimComponent().setCurAnim(AnimType.IDLE);                      
                        }

                    }
                }
            }
        }

    }

    public void freezeAnimations() {
        it = entList.iterator();
        while (it.hasNext()) {
            BaseEntity ent = (BaseEntity) it.next();
            if (ent.getAnimComponent() != null) {
                if (ent.getAnimComponent().getCurAnim() != null) {
                    AnimChannel channel = ent.getAnimComponent().getAnimControl().getChannel(AnimConf.UPPER_BODY);
                    if (channel.getAnimationName() != null) {
                        ent.getAnimComponent().getAnimControl().getChannel(AnimConf.UPPER_BODY).setSpeed(0f);
                    }
                }
            }
        }
    }

    @Override
    public void cleanup() {
        entList = null;
        it = null;
        map.clear();
    }
}