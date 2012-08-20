package com.teamjmonkey.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.MouseAxisTrigger;
import com.teamjmonkey.GameNameGoesHere;
import com.teamjmonkey.util.GameState;

public class GameAppState extends AbstractAppState {

    private GameNameGoesHere myApp = GameNameGoesHere.getApp();
    private InputManager inputManager = myApp.getInputManager();
    private final String LEFT_MOVE = "LeftMove";
    private final String RIGHT_MOVE = "RightMove";
    private final String FORWARD_MOVE = "UpMove";
    private final String BACKWARD_MOVE = "BackMove";

    public GameAppState() {
    }

    @Override
    public void stateAttached(AppStateManager stateManager) {
        inputManager.setCursorVisible(false);
        GameState.setGameState(GameState.RUNNING);

        loadDesktopInputs();
    }

    @Override
    public void stateDetached(AppStateManager stateManager) {
        removeDesktopInputs();
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

        // add mappings
        inputManager.addMapping(LEFT_MOVE, new MouseAxisTrigger(MouseInput.AXIS_X, true));
        inputManager.addMapping(RIGHT_MOVE, new MouseAxisTrigger(MouseInput.AXIS_X, false));
        inputManager.addMapping(FORWARD_MOVE, new MouseAxisTrigger(MouseInput.AXIS_Y, false));
        inputManager.addMapping(BACKWARD_MOVE, new MouseAxisTrigger(MouseInput.AXIS_Y, true));

        inputManager.addListener(analogListener, new String[]{LEFT_MOVE, RIGHT_MOVE, FORWARD_MOVE, BACKWARD_MOVE});
    }

    @Override
    public void update(float tpf) {
    }

    private void removeDesktopInputs() {

        //remove mappings
        inputManager.deleteMapping(LEFT_MOVE);
        inputManager.deleteMapping(RIGHT_MOVE);
        inputManager.deleteMapping(FORWARD_MOVE);
        inputManager.deleteMapping(BACKWARD_MOVE);

        inputManager.removeListener(analogListener);
    }

    private AnalogListener analogListener = new AnalogListener() {

        public void onAnalog(String name, float value, float tpf) {

            //move main Character
            if (name.equals(LEFT_MOVE)) {
            } else if (name.equals(RIGHT_MOVE)) {
            } else if (name.equals(FORWARD_MOVE)) {
            } else if (name.equals(BACKWARD_MOVE)) {
            }
        }
    };
}