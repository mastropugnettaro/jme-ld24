package com.teamjmonkey.graphics;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.teamjmonkey.GameNameGoesHere;
import com.teamjmonkey.util.Manager;
import com.teamjmonkey.util.PreloadManager;
import java.util.EnumMap;
import java.util.Iterator;

public class MaterialManager implements Manager {

    private EnumMap<MonkeyMaterial, Material> materialMap = new EnumMap<MonkeyMaterial, Material>(MonkeyMaterial.class);
    private GameNameGoesHere myApp = GameNameGoesHere.getApp();
    private AssetManager assetManager = myApp.getAssetManager();
    private PreloadManager preloadManager = myApp.getPreloadManager();

    public void load(int level) {

        //create an instance of these materials and use them for each level
        if(level == 1) {
            loadMaterials(new MonkeyMaterial[]{MonkeyMaterial.NORMAL, MonkeyMaterial.MAIN_CHARACTER});
        } else if (level == 2) {
            loadMaterials(new MonkeyMaterial[]{MonkeyMaterial.MAIN_CHARACTER});
        }
    }

    private void loadMaterials(MonkeyMaterial[] monkeyMaterials) {
        for (MonkeyMaterial monkeyMaterial : monkeyMaterials) {
            Material m = assetManager.loadMaterial(monkeyMaterial.getPathToMaterial());
            materialMap.put(monkeyMaterial, m);
            preloadManager.preload(m);
        }
    }

    public Material getMaterial(MonkeyMaterial monkeyMaterial) {
        return materialMap.get(monkeyMaterial);
    }

    // remove all entries to the materialMap
    public void cleanup() {
        Iterator it = materialMap.keySet().iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }
}