package com.teamjmonkey.ai.aggro;

import com.jme3.scene.Spatial;
import com.teamjmonkey.GameNameGoesHere;

public abstract class AggroBehaviorBase implements AggroBehavior {

    protected GameNameGoesHere myApp;
    protected Spatial spatial;

    public void init(GameNameGoesHere myApp, Spatial spatial) {
        this.myApp = myApp;
        this.spatial = spatial;
    }
}
