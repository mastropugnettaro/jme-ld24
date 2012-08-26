package com.teamjmonkey.animation;

import com.jme3.animation.LoopMode;

public class AnimInfo {

    public String name;
    public float speed;
    public float blendTime;
    public LoopMode loop;
    public Integer channelId;

    public AnimInfo(String name, float speed, float blendTime, LoopMode loop, Integer channelId) {
        this.name = name;
        this.speed = speed;
        this.blendTime = blendTime;
        this.loop = loop;
        this.channelId = channelId;
    }
}
