package com.teamjmonkey.animation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;



import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

public class AnimManager extends AbstractAppState implements AnimEventListener{
	
	Collection<?> entList;
	Iterator<?> it;
	HashMap<String, AnimInfo> map;

	public AnimManager(Collection<?> entList, HashMap<String, AnimInfo> map){
		this.entList = entList;
		this.map = map;
	}
	
	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		// TODO Auto-generated method stub
		super.initialize(stateManager, app);
		
	}

	@Override
	public boolean isInitialized() {
		return super.isInitialized();
	}

	@Override
	public void update(float tpf) {
		if(isEnabled()){
			it = entList.iterator();
			while(it.hasNext()){
				Entity ent = it.next();	
				if(ent.animComponent != null){
					if(ent.animComponent.getCurAnim() != null){
						AnimInfo curAnimInfo = map.get(ent.animComponent.getCurAnim());
						AnimChannel channel = ent.animComponent.getAnimControl().getChannel(AnimConf.UPPER_BODY);
						if(channel.getAnimationName() != null){
							if(!channel.getAnimationName().equals(curAnimInfo.name)){
								ent.animComponent.getAnimControl().getChannel(AnimConf.UPPER_BODY).setLoopMode(curAnimInfo.loop);
								ent.animComponent.getAnimControl().getChannel(AnimConf.UPPER_BODY).setSpeed(curAnimInfo.speed);
								ent.animComponent.getAnimControl().getChannel(AnimConf.UPPER_BODY).setAnim(curAnimInfo.name, curAnimInfo.blendTime);
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
	public void onAnimCycleDone(AnimControl arg0, AnimChannel arg1, String arg2) {
		
	}
}
