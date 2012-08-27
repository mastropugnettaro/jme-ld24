package com.teamjmonkey.level;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.audio.LowPassFilter;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.filters.DepthOfFieldFilter;
import com.jme3.post.filters.LightScatteringFilter;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture2D;
import com.jme3.util.SkyFactory;
import com.jme3.water.WaterFilter;
import com.teamjmonkey.GameNameGoesHere;
import com.teamjmonkey.controls.ControlManager;
import com.teamjmonkey.entity.EntityManager;
import com.teamjmonkey.graphics.MaterialManager;
import com.teamjmonkey.physics.PhysicsManager;
import com.teamjmonkey.sound.SoundManager;
import com.teamjmonkey.util.GameState;

public class LevelCommon extends AbstractAppState {

    // This part is to emulate tides, slightly varrying the height of the water plane
    private float time = 0.0f;
    private float waterHeight = 0.0f;
    private float initialWaterHeight = 0f;
    private boolean uw = false;
    private GameNameGoesHere myApp;
    private Node rootNode;
    private ControlManager controlManager;
    private EntityManager entityManager;
    private MaterialManager materialManager;
    private PhysicsManager physicsManager;
    private SoundManager soundManager;
    private Camera cam;
    private AssetManager assetManager;
    private Vector3f lightDir = new Vector3f(-4.9236743f, -1.27054665f, 5.896916f);
    private WaterFilter water;
    private AudioNode waves;
    private LowPassFilter aboveWaterAudioFilter = new LowPassFilter(1, 1);
    private float counter = 0;

    public LevelCommon() {
        myApp = GameNameGoesHere.getApp();
        rootNode = myApp.getRootNode();
        cam = myApp.getCamera();
        assetManager = myApp.getAssetManager();
        entityManager = myApp.getEntityManager();
        loadCommon();
    }

    // Load water
    // Load common sounds
    // Load filters for it
    public void loadCommon() {

        Node mainScene = new Node("Main Scene");
        rootNode.attachChild(mainScene);

        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(lightDir);
        sun.setColor(ColorRGBA.White.clone().multLocal(1.7f));
        rootNode.addLight(sun);

        Spatial sky = SkyFactory.createSky(assetManager, "Scenes/Beach/FullskiesSunset0068.dds", false);
        sky.setLocalScale(350);

        mainScene.attachChild(sky);

        water = new WaterFilter(rootNode, lightDir);

        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);

        fpp.addFilter(water);
        BloomFilter bloom = new BloomFilter();

        bloom.setExposurePower(55);
        bloom.setBloomIntensity(1.0f);

        fpp.addFilter(bloom);
        LightScatteringFilter lsf = new LightScatteringFilter(lightDir.mult(-300));
        lsf.setLightDensity(1.0f);
        fpp.addFilter(lsf);

        DepthOfFieldFilter dof = new DepthOfFieldFilter();
        dof.setFocusDistance(0);
        dof.setFocusRange(100);
        fpp.addFilter(dof);

        water.setWaveScale(0.003f);
        water.setMaxAmplitude(1f);
        water.setFoamExistence(new Vector3f(1f, 4, 0.5f));
        water.setFoamTexture((Texture2D) assetManager.loadTexture("Common/MatDefs/Water/Textures/foam2.jpg"));

        water.setRefractionStrength(0.2f);

        water.setWaterHeight(initialWaterHeight);
        uw = cam.getLocation().y < waterHeight;

        waves = new AudioNode(assetManager, "Sounds/Environment/Ocean Waves.ogg", false);
        waves.setLooping(true);
        waves.setReverbEnabled(true);
        if (uw) {
            waves.setDryFilter(new LowPassFilter(0.5f, 0.1f));
        } else {
            waves.setDryFilter(aboveWaterAudioFilter);
        }

        myApp.getViewPort().addProcessor(fpp);
    }

    @Override
    public void stateAttached(AppStateManager stateManager) {
        super.stateAttached(stateManager);
        cam.setLocation(new Vector3f(0, 10, 0));
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        app.getAudioRenderer().playSource(waves);
    }

    @Override
    public void stateDetached(AppStateManager stateManager) {
        super.stateDetached(stateManager);
        myApp.getAudioRenderer().stopSource(waves);
    }

    @Override
    public void update(float tpf) {

        if (GameState.getGameState() == GameState.PAUSED) {
            return;
        }

        super.update(tpf);
        time += tpf;
        waterHeight = (float) Math.cos(((time * 0.6f) % FastMath.TWO_PI)) * 1.5f;
        water.setWaterHeight(initialWaterHeight + waterHeight);
        if (water.isUnderWater() && !uw) {

            waves.setDryFilter(new LowPassFilter(0.5f, 0.1f));
            uw = true;
        }
        if (!water.isUnderWater() && uw) {
            uw = false;
            waves.setDryFilter(new LowPassFilter(1, 1f));
        }

        if (water.isUnderWater()) {
            counter += tpf;

            if (counter > 1) { //1 second underwater kill
                counter = 0;
                myApp.getLevelManager().restartLevel();
            }

        } else {
            counter = 0;
        }
    }
}
