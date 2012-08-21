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
            loadGraphics(new Graphics[]{Graphics.MAIN_CHARACTER, Graphics.ANOTHER_OBJECT});
        } else if (level == 2) {
            loadGraphics(new Graphics[]{Graphics.MAIN_CHARACTER});
        }
    }

    private void loadGraphics(Graphics[] graphics) {
        for (Graphics graphic : graphics) {
            preloadManager.preload(createSpatial(graphic));
        }
    }

    public Spatial createSpatial(Graphics graphic) {
        return assetManager.loadModel(graphic.getPath());
    }

    public void cleanup() {
        // nothing ?
    }
}