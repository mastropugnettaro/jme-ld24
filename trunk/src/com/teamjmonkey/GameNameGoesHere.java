package com.teamjmonkey;

import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.bullet.BulletAppState;
import com.teamjmonkey.appstates.MonkeyAppStateManager;
import com.teamjmonkey.controls.ControlManager;
import com.teamjmonkey.effects.EffectsManager;
import com.teamjmonkey.entity.EntityManager;
import com.teamjmonkey.file.FileManager;
import com.teamjmonkey.file.UserSettings;
import com.teamjmonkey.graphics.GraphicManager;
import com.teamjmonkey.graphics.MaterialManager;
import com.teamjmonkey.level.LevelManager;
import com.teamjmonkey.physics.PhysicsManager;
import com.teamjmonkey.sound.SoundManager;
import com.teamjmonkey.ui.UIManager;
import com.teamjmonkey.util.PreloadManager;

public class GameNameGoesHere extends SimpleApplication {

    private static GameNameGoesHere thisApp;
    private UIManager uiManager;
    private FileManager fileManager;
    private LevelManager levelManager;
    private PreloadManager preloadManager;
    private ControlManager controlManager;
    private GraphicManager graphicManager;
    private MaterialManager materialManager;
    private BulletAppState bulletAppState;
    private EffectsManager effectsManager;
    private EntityManager entityManager;
    private UserSettings userSettings;
    private MonkeyAppStateManager monkeyAppStateManager;
    private PhysicsManager physicsManager;
    private SoundManager soundManager;

    public GameNameGoesHere() {
        super(new StatsAppState());
    }

    @Override
    public void simpleInitApp() {
        thisApp = this;

        // == Move a lot of these into the "initialise once" level === //
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);

        // load needed managers now
        uiManager = new UIManager();

        fileManager = new FileManager(assetManager);
        effectsManager = new EffectsManager();
        controlManager = new ControlManager();
        preloadManager = new PreloadManager();
        graphicManager = new GraphicManager();
        materialManager = new MaterialManager();
        entityManager = new EntityManager();
        userSettings = new UserSettings();
        monkeyAppStateManager = new MonkeyAppStateManager();
        physicsManager = new PhysicsManager();
        soundManager = new SoundManager();

        levelManager = new LevelManager();
        bulletAppState.getPhysicsSpace().enableDebug(assetManager);

        uiManager.showMainMenu();
    }

    public PreloadManager getPreloadManager() {
        return preloadManager;
    }

    public static GameNameGoesHere getApp() {
        return thisApp;
    }

    public UIManager getUIManager() {
        return uiManager;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public ControlManager getControlManager() {
        return controlManager;
    }

    public GraphicManager getGraphicManager() {
        return graphicManager;
    }

    public MaterialManager getMaterialManager() {
        return materialManager;
    }

    public BulletAppState getBulletAppState() {
        return bulletAppState;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public EffectsManager getEffectsManager() {
        return effectsManager;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public UserSettings getUserSettings() {
        return userSettings;
    }

    public MonkeyAppStateManager getMonkeyAppStateManager() {
        return monkeyAppStateManager;
    }

    public PhysicsManager getPhysicsManager() {
        return physicsManager;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }
}