package com.teamjmonkey.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.teamjmonkey.GameNameGoesHere;
import com.teamjmonkey.level.LevelManager;
import com.teamjmonkey.ui.UIManager;
import com.teamjmonkey.util.GameState;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class LoadingScreenAppState extends AbstractAppState implements ScreenController {

    private AppStateManager stateManager;
    private int frameCount = 0;
    private GameNameGoesHere myApp = GameNameGoesHere.getApp();
    private UIManager uiManager = myApp.getUIManager();
    private LevelManager levelManager = myApp.getLevelManager();
    private Nifty nifty = uiManager.getNifty();
    private int FRAME_COUNT = 150;
    private boolean isLoaded = false;

    public LoadingScreenAppState() {
        nifty.registerScreenController(this);
        nifty.addXml("Interface/Nifty/LoadingScreen.xml");
    }

    @Override
    public void stateAttached(AppStateManager stateManager) {
        this.stateManager = stateManager;
        GameState.setGameState(GameState.LOADING_LEVEL);
        frameCount = 0;
    }

    @Override
    public void stateDetached(AppStateManager stateManager) {
       // uiManager.getNifty().gotoScreen("end");
        isLoaded = true;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
    }

    @Override
    public void update(float tpf) {

        if (frameCount == 1) {
            //load the screen
            showLoadingScreen();
        } else if (frameCount == FRAME_COUNT) { //using 150 as a debug, this is where you load the game

            //at end of loading
            stateManager.detach(this);

            myApp.getInputManager().setCursorVisible(false);
            levelManager.load(levelManager.getCurrentIntLevel());
            stateManager.attach(myApp.getMonkeyAppStateManager().getAppState(GameAppState.class));
        }

        frameCount++;
    }

    @Override
    public void cleanup() {
        super.cleanup();
    }

    //==== Nifty functions ====
    public void onStartScreen() {
        System.out.println("onStartScreen");
    }

    public void onEndScreen() {
        System.out.println("onEndScreen");
    }

    public void bind(Nifty nifty, Screen screen) {
        System.out.println("bind( " + screen.getScreenId() + ")");
    }

    public void showLoadingScreen() {
        nifty.gotoScreen("loadingScreen");
    }
}