package com.teamjmonkey.animation;

import java.util.EnumMap;
import java.util.HashMap;

import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;


public class AnimHandler {
	
	public static HashMap<String, AnimInfo> loadAnimationSet(){
		
		HashMap<String, AnimInfo>  map = new HashMap<String, AnimInfo>();
		
		map.put(AnimType.ATTACK1, new AnimInfo("attack1", 1.0f, 0.1f, LoopMode.DontLoop, AnimConf.UPPER_BODY));
		map.put(AnimType.ATTACK2, new AnimInfo("attack2",1.0f, 0.1f,LoopMode.DontLoop, AnimConf.UPPER_BODY));
		map.put(AnimType.WALK, new AnimInfo("Walk", 1.0f, 1f, LoopMode.Loop, AnimConf.LOWER_BODY));
		map.put(AnimType.RUN, new AnimInfo("run", 1.0f, 0.1f, LoopMode.Loop, AnimConf.LOWER_BODY));
		map.put(AnimType.IDLE1, new AnimInfo("Stand", 1.0f, 1f, LoopMode.Loop, AnimConf.UPPER_BODY));
		map.put(AnimType.JUMP, new AnimInfo("Jump", 1.0f, 0.1f, LoopMode.DontLoop, AnimConf.LOWER_BODY));
		
		return map;
	}
	
	
}
