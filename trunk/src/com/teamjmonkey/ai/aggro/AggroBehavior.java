package com.teamjmonkey.ai.aggro;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.teamjmonkey.GameNameGoesHere;
import com.teamjmonkey.entity.MovableEntity;

public interface AggroBehavior {

    public void init(GameNameGoesHere myApp, MovableEntity entity, Spatial spatial);

    public void update(float tpf, Vector3f target, boolean hasPtherAggroType);

    public void onAggro(Vector3f target);

    public void onAggroLoss();
}
