package com.teamjmonkey.animation;

import java.util.HashMap;

import com.jme3.animation.LoopMode;

public class AnimHandler {

    public static HashMap<String, AnimInfo> loadAnimationSet() {

        HashMap<String, AnimInfo> map = new HashMap<String, AnimInfo>();

        map.put(AnimType.ATTACK, new AnimInfo("Attack", 1.0f, 0.1f, LoopMode.DontLoop, AnimConf.UPPER_BODY));
        map.put(AnimType.WALK, new AnimInfo("Walk", 1.0f, 1f, LoopMode.Loop, AnimConf.LOWER_BODY));
        map.put(AnimType.IDLE, new AnimInfo("Stand", 1.0f, 1f, LoopMode.Loop, AnimConf.UPPER_BODY));
        map.put(AnimType.EAT, new AnimInfo("Eat", 1.0f, 1f, LoopMode.DontLoop, AnimConf.UPPER_BODY));
        map.put(AnimType.JUMP, new AnimInfo("Jump", 1.0f, 0.1f, LoopMode.DontLoop, AnimConf.LOWER_BODY));

        return map;
    }
}
