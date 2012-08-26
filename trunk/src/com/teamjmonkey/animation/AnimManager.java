package com.teamjmonkey.animation;

import com.jme3.app.state.AppStateManager;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.app.state.AbstractAppState;
import com.teamjmonkey.GameNameGoesHere;
import com.teamjmonkey.entity.BaseEntity;
import com.teamjmonkey.util.GameState;

public class AnimManager extends AbstractAppState implements AnimEventListener {

    private GameNameGoesHere myApp = GameNameGoesHere.getApp();
    private Collection<?> entList;
    private Iterator<?> it;
    private HashMap<String, AnimInfo> map;

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

        if (GameState.getGameState() == GameState.PAUSED || !isEnabled()) {
            return;
        }

        it = entList.iterator();
        while (it.hasNext()) {
            BaseEntity ent = (BaseEntity) it.next();
            if (ent.getAnimComponent() != null) {
                if (ent.getAnimComponent().getCurAnim() != null) {
                    AnimInfo curAnimInfo = map.get(ent.getAnimComponent().getCurAnim());
                    AnimChannel channel = ent.getAnimComponent().getAnimControl().getChannel(AnimConf.UPPER_BODY);
                    if (channel.getAnimationName() != null) {
                        if (!channel.getAnimationName().equals(curAnimInfo.name)) {
                            ent.getAnimComponent().getAnimControl().getChannel(AnimConf.UPPER_BODY).setLoopMode(curAnimInfo.loop);
                            ent.getAnimComponent().getAnimControl().getChannel(AnimConf.UPPER_BODY).setSpeed(curAnimInfo.speed);
                            ent.getAnimComponent().getAnimControl().getChannel(AnimConf.UPPER_BODY).setAnim(curAnimInfo.name, curAnimInfo.blendTime);
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
    public void onAnimCycleDone(AnimControl arg0, AnimChannel arg1, String arg2) {
    }

    @Override
    public void cleanup() {
        entList.clear();
        it = null;
        map.clear();
    }
}