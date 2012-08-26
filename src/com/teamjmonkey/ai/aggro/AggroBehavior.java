package com.teamjmonkey.ai.aggro;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.teamjmonkey.GameNameGoesHere;

public interface AggroBehavior {

    public void init(GameNameGoesHere myApp, Spatial spatial);

    public void update(float tpf);

    public void updateTarget(Vector3f target);

    public void onAggro(Vector3f target);

    public void onAggroLoss();
}
