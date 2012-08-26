package com.teamjmonkey.graphics;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;
import com.teamjmonkey.GameNameGoesHere;
import com.teamjmonkey.util.Manager;
import com.teamjmonkey.util.PreloadManager;
import java.util.EnumMap;

public class GraphicManager implements Manager {

    private GameNameGoesHere myApp = GameNameGoesHere.getApp();
    private AssetManager assetManager = myApp.getAssetManager();
    private EnumMap<Graphics, Spatial> graphicMap = new EnumMap<Graphics, Spatial>(Graphics.class);
    private PreloadManager preloadManager = myApp.getPreloadManager();

    public void load(int level) {
        //load all needed graphics
        if (level == 1) {
            loadGraphics(new Graphics[]{Graphics.TEST_PLATFORM});
        } else if (level == 2) {
            loadGraphics(new Graphics[]{Graphics.TEST_PLATFORM});
        }
    }

    private void loadGraphics(Graphics[] graphics) {
        for (Graphics graphic : graphics) {
            Spatial s = createSpatial(graphic);
            preloadManager.preload(s);
            graphicMap.put(graphic, s);
        }
    }

    public Spatial createSpatial(Graphics graphic) {
        return assetManager.loadModel(graphic.getPath());
    }

    public void cleanup() {
        graphicMap.clear();

    }
}