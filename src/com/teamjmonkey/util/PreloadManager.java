package com.teamjmonkey.util;

import com.jme3.material.Material;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;
import com.teamjmonkey.GameNameGoesHere;

public class PreloadManager {

    private GameNameGoesHere myApp = GameNameGoesHere.getApp();
    private RenderManager renderManager = myApp.getRenderManager();

    public void preload(Material m) {
        m.preload(renderManager);
    }

    public void preload(Spatial s) {
        renderManager.preloadScene(s);
    }
}