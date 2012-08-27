package com.teamjmonkey.level;

import com.jme3.app.state.AbstractAppState;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.math.Quaternion;

import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.FadeFilter;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.teamjmonkey.GameNameGoesHere;
import com.teamjmonkey.ai.aggro.AggroBehaviorChase;
import com.teamjmonkey.ai.aggro.AggroBehaviorFight;
import com.teamjmonkey.ai.areas.WalkableArea;
import com.teamjmonkey.ai.areas.WalkableRectangle;
import com.teamjmonkey.animation.AnimManager;
import com.teamjmonkey.appstates.LoadingScreenAppState;

import com.teamjmonkey.cinematic.CinematicComposition;
import com.teamjmonkey.cinematic.GameStartCinematic;

import com.teamjmonkey.controls.AggroControl;

import com.teamjmonkey.controls.ControlManager;
import com.teamjmonkey.controls.MoveRandomControl;
import com.teamjmonkey.entity.Enemy;
import com.teamjmonkey.entity.Entity;
import com.teamjmonkey.entity.EntityManager;
import com.teamjmonkey.entity.MainCharacter;
import com.teamjmonkey.entity.food.Apple;
import com.teamjmonkey.graphics.GraphicManager;
import com.teamjmonkey.graphics.MaterialManager;
import com.teamjmonkey.physics.PhysicsManager;
import com.teamjmonkey.sound.SoundManager;
import com.teamjmonkey.util.Manager;
import com.teamjmonkey.util.PreloadManager;

public class LevelManager extends AbstractAppState implements Manager {

    private GameNameGoesHere myApp;
    private Node rootNode;
    private ControlManager controlManager;
    private EntityManager entityManager;
    private MaterialManager materialManager;
    private PhysicsManager physicsManager;
    private SoundManager soundManager;
    private GraphicManager graphicsManager;
    private PreloadManager preloadManager;
    private boolean stateInitialised;
    private AnimManager animManager;
    private Level currentLevel;
    private int currentIntLevel;
    private final int NUM_LEVELS;
    private Node island;
    private CinematicComposition cc;
    private FadeFilter fade;
    private MainCharacter mainCharacter;

    public LevelManager() {
        myApp = GameNameGoesHere.getApp();
        rootNode = myApp.getRootNode();
        controlManager = myApp.getControlManager();
        entityManager = myApp.getEntityManager();
        materialManager = myApp.getMaterialManager();
        physicsManager = myApp.getPhysicsManager();
        soundManager = myApp.getSoundManager();
        preloadManager = myApp.getPreloadManager();
        graphicsManager = myApp.getGraphicManager();
        animManager = myApp.getAnimManager();
        stateInitialised = false;
        currentIntLevel = 1;
        NUM_LEVELS = 5;
    }

    public int getCurrentIntLevel() {
        return currentIntLevel;
    }

    public void setCurrentIntLevel(int level) {
        this.currentIntLevel = level;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    // only call this once during the first ever level
    public void initialiseGameStatesOnce() {
        // Load LevelCommon

        // Load Island
        island = (Node) myApp.getAssetManager().loadModel("Scenes/island2_1.j3o");
        rootNode.attachChild(island);

        myApp.getBulletAppState().getPhysicsSpace().addAll(island);

        island.getChild("SpawningPoints").setCullHint(Spatial.CullHint.Always);

        //Spawning enemies
        Node level2enemie = (Node) ((Node) ((Node) island.getChild("SpawningPoints")).getChild("2")).getChild("Enemy");
        Node level3enemie = (Node) ((Node) ((Node) island.getChild("SpawningPoints")).getChild("3")).getChild("Enemy");
        Node level4enemie = (Node) ((Node) ((Node) island.getChild("SpawningPoints")).getChild("4")).getChild("Enemy");

        //Blobs
        for (Spatial point : level2enemie.getChildren()) {
            addEnemy((Enemy) Entity.ENEMY_BLOB.createEntity(), 2f, point.getWorldTranslation().add(0f, -15.4556f, 0f));
        }
        //Armadilos
        for (Spatial point : level3enemie.getChildren()) {
            addEnemy((Enemy) Entity.ENEMY_ARMADILO.createEntity(), 4f, point.getWorldTranslation().add(0f, -15.4556f, 0f));
        }
        //Elephants
        for (Spatial point : level4enemie.getChildren()) {
            addEnemy((Enemy) Entity.ENEMY_ELEPHANT.createEntity(), 6f, point.getWorldTranslation().add(0f, -15.4556f, 0f));
        }
    }

    private void addEnemy(Enemy enemy, float enemySize, Vector3f spawn) {
        float areaSizeX = 20f;
        float areaSizeZ = 20f;
        WalkableArea wa = new WalkableRectangle(spawn.getX() - (areaSizeX / 2f), spawn.getZ() - (areaSizeZ / 2f), 20f, 20f);
        enemy.getSpatial().addControl(new MoveRandomControl(enemy, wa));
        enemy.getSpatial().addControl(new AggroControl(enemy, 25f, enemySize,
                PhysicsCollisionObject.COLLISION_GROUP_02,
                PhysicsCollisionObject.COLLISION_GROUP_03,
                new AggroBehaviorChase(wa, 6f),
                new AggroBehaviorFight(1f, 2f)));
        enemy.finalise();
        enemy.getSpatial().setLocalTranslation(wa.getRandomPointInside(spawn.getY()));
        rootNode.attachChild(enemy.getSpatial());
    }

    public void initialiseEachLevel() {
        Node level1Food = (Node) ((Node) ((Node) island.getChild("SpawningPoints")).getChild("1")).getChild("Food");
        //Node level2Food = (Node) ((Node) ((Node) island.getChild("SpawningPoints")).getChild("2")).getChild("Food");
        //Node level3Food = (Node) ((Node) ((Node) island.getChild("SpawningPoints")).getChild("3")).getChild("Food");
        //Node level4Food = (Node) ((Node) ((Node) island.getChild("SpawningPoints")).getChild("4")).getChild("Food");

        Node[] foodSpawnLocations = new Node[]{level1Food}; //level2Food, level3Food, level4Food};

        for (Node node : foodSpawnLocations) {
            for (Spatial point : node.getChildren()) {
                Vector3f worldTranslation = point.getWorldTranslation();

                Apple apple = (Apple) entityManager.create(Entity.APPLE);
                apple.getSpatial().setLocalTranslation(worldTranslation.add(0, -15f, 0));
                apple.finalise();
                rootNode.attachChild(apple.getSpatial());
                currentLevel.getAllEntities().add(apple);
            }
        }

        /*
        FilterPostProcessor fpp = myApp.getFpp();
        myApp.getCamera().setLocation(new Vector3f(-186.47707f, 19.662216f, -72.307915f));
        myApp.getCamera().lookAt(new Vector3f(0f, 0f, 0f), Vector3f.UNIT_Y);

        fade = new FadeFilter();
        fpp.addFilter(fade);

        cc = new GameStartCinematic(myApp, fade);
        cc.attach();
        fade.setValue(0f);

        myApp.getStateManager().attach(this);
         */
        mainCharacter = (MainCharacter) entityManager.create(Entity.MAIN_CHARACTER);
        mainCharacter.getSpatial().move(-130, 40, -60);
        mainCharacter.finalise();
        rootNode.attachChild(mainCharacter.getSpatial());
        currentLevel.getAllEntities().add(mainCharacter);
    }
    private float time = 0;
    private boolean run = true;

    @Override
    public void update(float tpf) {
        super.update(tpf);

        time += tpf;
        if (time > 7f && run) {
            cc.play();
            run = false;
            myApp.getStateManager().detach(this);
        }
    }

    public Node getIsland() {
        return island;
    }

    public void load(int level) {

        if (!stateInitialised) {
            initialiseGameStatesOnce();
        }

        materialManager.load(level);
        physicsManager.load(level);
        soundManager.load(level);
        graphicsManager.load(level);

        switch (level) {
            case 1:
                currentLevel = new Level1();
                break;
            case 2:
                currentLevel = new Level2();
                break;
            case 3:
                currentLevel = new Level3(); // new part of the map
                break;
            case 4:
                currentLevel = new Level4(); // new part of the map
                break;
            case 5:
                currentLevel = new Level5(); // new part of the map opens
                break;
        }

        initialiseEachLevel();
    }

    public void restartLevel() {

        mainCharacter.getCharacterControl().setPhysicsLocation(new Vector3f(-130, 40, -60));

        // cleanup();

        //this calls currentLevel.load() inside
      //  myApp.getStateManager().attach(myApp.getMonkeyAppStateManager().getAppState(LoadingScreenAppState.class));
    }

    public void loadNextLevel() {
        if (currentIntLevel == NUM_LEVELS) {
            currentIntLevel = 1;
        } else {
            currentIntLevel++;
        }

        cleanup();
        myApp.getStateManager().attach(myApp.getMonkeyAppStateManager().getAppState(LoadingScreenAppState.class));
    }

    public void loadPreviousLevel() {
        if (currentIntLevel == 1) {
            currentIntLevel = NUM_LEVELS;
        } else {
            currentIntLevel--;
        }

        cleanup();
        myApp.getStateManager().attach(myApp.getMonkeyAppStateManager().getAppState(LoadingScreenAppState.class));
    }

    @Override
    public void cleanup() {


        materialManager.cleanup();
        physicsManager.cleanup();
        soundManager.cleanup();
        graphicsManager.cleanup();

//        currentLevel.cleanup();
        //  animManager.cleanup();

    }
}
