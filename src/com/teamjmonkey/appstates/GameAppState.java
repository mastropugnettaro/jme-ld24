package com.teamjmonkey.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.scene.Spatial;
import com.teamjmonkey.GameNameGoesHere;
import com.teamjmonkey.animation.AnimManager;
import com.teamjmonkey.controls.MoveRandomControl;
import com.teamjmonkey.level.LevelCommon;
import com.teamjmonkey.level.LevelManager;
import com.teamjmonkey.ui.UIManager;
import com.teamjmonkey.util.GameState;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.input.keyboard.KeyboardInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class GameAppState extends AbstractAppState implements ScreenController, PhysicsCollisionListener {

    private GameNameGoesHere myApp = GameNameGoesHere.getApp();
    private InputManager inputManager = myApp.getInputManager();
    private UIManager uiManager = myApp.getUIManager();
    private LevelManager levelManager = myApp.getLevelManager();
    private Nifty nifty = uiManager.getNifty();
    private final String LEFT_MOVE = "LeftMove";
    private final String RIGHT_MOVE = "RightMove";
    private final String FORWARD_MOVE = "UpMove";
    private final String BACKWARD_MOVE = "BackMove";
    private final String PAUSE = "Pause";
    private final String NEXT_LEVEL = "NextLevel";
    private final String PREVIOUS_LEVEL = "PreviousLevel";
    private final String MAIN_CHARACTER = "mainCharacter";
    private final String ENEMY = "enemy";

    public GameAppState() {
        nifty.registerScreenController(this);
        nifty.addXml("Interface/Nifty/Hud.xml");
    }

    @Override
    public void stateAttached(AppStateManager stateManager) {
        super.stateAttached(stateManager);
        GameState.setGameState(GameState.RUNNING);
        showHud();

        myApp.getBulletAppState().getPhysicsSpace().addCollisionListener(this);

        myApp.getStateManager().attach(new NewFlyCamAppState());
        myApp.getStateManager().attach(myApp.getMonkeyAppStateManager().getAppState(LevelCommon.class));
        myApp.getStateManager().attach(myApp.getMonkeyAppStateManager().getAppState(AnimManager.class));
        myApp.getBulletAppState().setEnabled(true);

        loadDesktopInputs();
    }

    @Override
    public void stateDetached(AppStateManager stateManager) {
        super.stateDetached(stateManager);
        removeDesktopInputs();

        myApp.getBulletAppState().getPhysicsSpace().removeCollisionListener(this);

        // deatch all Level States
        myApp.getStateManager().detach(myApp.getStateManager().getState(LevelCommon.class));
        myApp.getStateManager().detach(myApp.getStateManager().getState(AnimManager.class));
        myApp.getBulletAppState().setEnabled(false);

        // TODO: pause any playing music
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
    }

    @Override
    public void cleanup() {
        super.cleanup();
    }

    private void loadDesktopInputs() {

        if (inputManager.hasMapping("SIMPLEAPP_Exit")) {
            inputManager.deleteMapping("SIMPLEAPP_Exit");
        }

        inputManager.addMapping(PAUSE, new KeyTrigger(KeyInput.KEY_ESCAPE),
                new KeyTrigger(KeyboardInputEvent.KEY_PAUSE),
                new KeyTrigger(KeyboardInputEvent.KEY_P));
        inputManager.addMapping(NEXT_LEVEL, new KeyTrigger(KeyInput.KEY_F2));
        inputManager.addMapping(PREVIOUS_LEVEL, new KeyTrigger(KeyInput.KEY_F1));

        inputManager.addListener(actionListener, PAUSE, NEXT_LEVEL, PREVIOUS_LEVEL);

        // add mappings
        // inputManager.addMapping(LEFT_MOVE, new MouseAxisTrigger(MouseInput.AXIS_X, true));
        //  inputManager.addMapping(RIGHT_MOVE, new MouseAxisTrigger(MouseInput.AXIS_X, false));
        //  inputManager.addMapping(FORWARD_MOVE, new MouseAxisTrigger(MouseInput.AXIS_Y, false));
        //  inputManager.addMapping(BACKWARD_MOVE, new MouseAxisTrigger(MouseInput.AXIS_Y, true));

        inputManager.addListener(analogListener, new String[]{LEFT_MOVE, RIGHT_MOVE, FORWARD_MOVE, BACKWARD_MOVE});
    }

    @Override
    public void update(float tpf) {
        //update loop
    }

    private void removeDesktopInputs() {

        //remove mappings
        //   inputManager.deleteMapping(LEFT_MOVE);
        //   inputManager.deleteMapping(RIGHT_MOVE);
        //    inputManager.deleteMapping(FORWARD_MOVE);
        //    inputManager.deleteMapping(BACKWARD_MOVE);

        inputManager.deleteMapping(PAUSE);
        inputManager.deleteMapping(NEXT_LEVEL);
        inputManager.deleteMapping(PREVIOUS_LEVEL);

        inputManager.removeListener(analogListener);
        inputManager.removeListener(actionListener);
    }
    private AnalogListener analogListener = new AnalogListener() {

        public void onAnalog(String name, float value, float tpf) {

            if (GameState.getGameState() != GameState.RUNNING) {
                return;
            }

            //move main Character
            //   if (name.equals(LEFT_MOVE)) {
            //    } else if (name.equals(RIGHT_MOVE)) {
            //    } else if (name.equals(FORWARD_MOVE)) {
            //    } else if (name.equals(BACKWARD_MOVE)) {
            //    }
        }
    };
    private ActionListener actionListener = new ActionListener() {

        public void onAction(String name, boolean isPressed, float tpf) {

            if (GameState.getGameState() != GameState.RUNNING) {
                return;
            }

            if (!isPressed) {

                if (name.equals(PAUSE) && !isPressed) {
                    myApp.getStateManager().detach(GameAppState.this);
                    myApp.getStateManager().attach(myApp.getMonkeyAppStateManager().getAppState(PauseMenuAppState.class));
                } else if (name.equals(NEXT_LEVEL)) {
                    levelManager.loadNextLevel();
                    myApp.getStateManager().detach(GameAppState.this);
                } else if (name.equals(PREVIOUS_LEVEL)) {
                    levelManager.loadPreviousLevel();
                    myApp.getStateManager().detach(GameAppState.this);
                }
            }
        }
    };

    public void collision(PhysicsCollisionEvent event) {

        Spatial a = event.getNodeA();
        Spatial b = event.getNodeB();

        if (a == null || b == null) {
            return;
        }

        String aName = event.getNodeA().getName();
        String bName = event.getNodeB().getName();

        // success they have collide

        if ((aName.equals(MAIN_CHARACTER) && bName.equals(ENEMY))
                || (bName.equals(MAIN_CHARACTER) && aName.equals(ENEMY))) {

            Spatial enemy = aName.equals(ENEMY) ? a : b;
            Spatial mainCharacter = aName.equals(ENEMY) ? b : a;

            // check if the enemy is in wait state

            // check if the enemy is in attack state

            // note the use of getParent, this was used to line the collision shapes
            MoveRandomControl control = enemy.getParent().getControl(MoveRandomControl.class);
            if (control != null) {
                control.setEnabled(false);
            }

            // THIS MUST BE CALLED SOMEWHERE
            //  MovementControl.setEnabled(true);
            //  MovementControl.resume(); // if neccessary
        }
    }

    // ==== nifty ====
    public void bind(Nifty nifty, Screen screen) {
    }

    public void onStartScreen() {
    }

    public void onEndScreen() {
    }

    public void showHud() {
        nifty.gotoScreen("hud");
    }

    public void removeHud() {
        nifty.gotoScreen("end");
    }
}