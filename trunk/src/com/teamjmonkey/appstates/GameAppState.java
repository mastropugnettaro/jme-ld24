package com.teamjmonkey.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.teamjmonkey.GameNameGoesHere;
import com.teamjmonkey.ui.UIManager;
import com.teamjmonkey.util.GameState;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.input.keyboard.KeyboardInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class GameAppState extends AbstractAppState implements ScreenController {

    private GameNameGoesHere myApp = GameNameGoesHere.getApp();
    private InputManager inputManager = myApp.getInputManager();
    private UIManager uiManager = myApp.getUIManager();
    private Nifty nifty = uiManager.getNifty();
    private final String LEFT_MOVE = "LeftMove";
    private final String RIGHT_MOVE = "RightMove";
    private final String FORWARD_MOVE = "UpMove";
    private final String BACKWARD_MOVE = "BackMove";
    private final String PAUSE = "Pause";

    public GameAppState() {
        nifty.registerScreenController(this);
        nifty.addXml("Interface/Nifty/Hud.xml");
    }

    @Override
    public void stateAttached(AppStateManager stateManager) {
        inputManager.setCursorVisible(false);
        GameState.setGameState(GameState.RUNNING);
        showHud();

        //super.stateAttached(stateManager);
//        myApp.getFlyByCamera().setEnabled(true);
        myApp.getInputManager().setCursorVisible(false);

        //press esc and open the pause menu
        // myApp.getStateManager().detach(myApp.getStateManager().getState(ResetStatsState.class));
        // myApp.getStateManager().detach(myApp.getStateManager().getState(DebugKeysAppState.class));
        // inputManager.addMapping(null, triggers);
        // myApp.getStateManager().detach(myApp.getStateManager().getState(FlyCamAppState.class));
        // myApp.getStateManager().attach(new FlyCamAppState());

        loadDesktopInputs();
    }

    @Override
    public void stateDetached(AppStateManager stateManager) {
        removeDesktopInputs();
        removeHud();

        // pause any playing music
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

        inputManager.deleteMapping("SIMPLEAPP_Exit");

        inputManager.addMapping(PAUSE, new KeyTrigger(KeyboardInputEvent.KEY_ESCAPE),
                new KeyTrigger(KeyboardInputEvent.KEY_PAUSE),
                new KeyTrigger(KeyboardInputEvent.KEY_P));

        inputManager.addListener(actionListener, PAUSE);

        // add mappings
        inputManager.addMapping(LEFT_MOVE, new MouseAxisTrigger(MouseInput.AXIS_X, true));
        inputManager.addMapping(RIGHT_MOVE, new MouseAxisTrigger(MouseInput.AXIS_X, false));
        inputManager.addMapping(FORWARD_MOVE, new MouseAxisTrigger(MouseInput.AXIS_Y, false));
        inputManager.addMapping(BACKWARD_MOVE, new MouseAxisTrigger(MouseInput.AXIS_Y, true));

        inputManager.addListener(analogListener, new String[]{LEFT_MOVE, RIGHT_MOVE, FORWARD_MOVE, BACKWARD_MOVE});
    }

    @Override
    public void update(float tpf) {
        //update loop
    }

    private void removeDesktopInputs() {

        //remove mappings
        inputManager.deleteMapping(LEFT_MOVE);
        inputManager.deleteMapping(RIGHT_MOVE);
        inputManager.deleteMapping(FORWARD_MOVE);
        inputManager.deleteMapping(BACKWARD_MOVE);

        inputManager.deleteMapping(PAUSE);

        inputManager.removeListener(analogListener);
        inputManager.removeListener(actionListener);
    }
    private AnalogListener analogListener = new AnalogListener() {

        public void onAnalog(String name, float value, float tpf) {

            if (GameState.getGameState() != GameState.RUNNING) {
                return;
            }

            //move main Character
            if (name.equals(LEFT_MOVE)) {
            } else if (name.equals(RIGHT_MOVE)) {
            } else if (name.equals(FORWARD_MOVE)) {
            } else if (name.equals(BACKWARD_MOVE)) {
            }
        }
    };

    private ActionListener actionListener = new ActionListener() {

        public void onAction(String name, boolean isPressed, float tpf) {

            if (name.equals(PAUSE) && !isPressed) {
                myApp.getStateManager().detach(GameAppState.this);
                myApp.getStateManager().attach(new PauseMenuAppState());
            }
        }
    };

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