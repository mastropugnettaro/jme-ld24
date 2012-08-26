package com.teamjmonkey.ai.aggro;

import com.jme3.scene.Spatial;
import com.teamjmonkey.GameNameGoesHere;
import com.teamjmonkey.entity.MovableEntity;

public abstract class AggroBehaviorBase implements AggroBehavior {

    protected GameNameGoesHere myApp;
    protected MovableEntity entity;
    protected Spatial spatial;

    public void init(GameNameGoesHere myApp, MovableEntity entity, Spatial spatial) {
        this.myApp = myApp;
        this.entity = entity;
        this.spatial = spatial;
    }

    public void setEntity(MovableEntity entity) {
        this.entity = entity;
    }
}
