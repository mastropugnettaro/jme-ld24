package com.teamjmonkey;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.audio.LowPassFilter;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.filters.DepthOfFieldFilter;
import com.jme3.post.filters.FadeFilter;
import com.jme3.post.filters.LightScatteringFilter;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture2D;
import com.jme3.util.SkyFactory;
import com.jme3.water.WaterFilter;
import com.teamjmonkey.cinematic.CinematicComposition;
import com.teamjmonkey.cinematic.GameStartCinematic;
import com.teamjmonkey.controls.ControlManager;
import com.teamjmonkey.entity.EntityManager;
import com.teamjmonkey.graphics.MaterialManager;
import com.teamjmonkey.physics.PhysicsManager;
import com.teamjmonkey.sound.SoundManager;

public class Island_02 extends SimpleApplication {

    private float time = 0.0f;
    private float waterHeight = 0.0f;
    private float initialWaterHeight = 16f;
    private boolean uw = false;
    private Vector3f lightDir = new Vector3f(-4.9236743f, -1.27054665f, 5.896916f);
    private WaterFilter water;
    private AudioNode waves;
    private LowPassFilter aboveWaterAudioFilter = new LowPassFilter(1, 1);
    private float counter = 0;
    private CinematicComposition cc;
    private FilterPostProcessor fpp;
    private FadeFilter fade;

    public static void main(String[] args) {
        Island_02 app = new Island_02();
        app.start();
    }

    @Override
    public void simpleInitApp() {

        // Load Island
        Node island = (Node) assetManager.loadModel("Scenes/island2_1.j3o");
        rootNode.attachChild(island);

        Node mainScene = new Node("Main Scene");
        rootNode.attachChild(mainScene);

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

        fade = new FadeFilter();
        fpp.addFilter(fade);

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

        getAudioRenderer().playSource(waves);
        getViewPort().addProcessor(fpp);

        flyCam.setMoveSpeed(100);

        cam.setLocation(new Vector3f(-186.47707f, 19.662216f, -72.307915f));
        cam.lookAt(new Vector3f(0f, 0f, 0f), Vector3f.UNIT_Y);
        
        cc = new GameStartCinematic(this, fade);
        cc.attach();
        fade.setValue(0f);
    }
    private boolean run = true;

    @Override
    public void simpleUpdate(float tpf) {
        time += tpf;
        if (time > 7f && run) {
            cc.play();
            run = false;
        }
        //System.out.println(cam.getLocation() + " - " + time + " | " + fade.getValue());
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
    }
}
