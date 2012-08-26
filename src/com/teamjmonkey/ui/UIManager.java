package com.teamjmonkey.ui;

import com.jme3.app.state.AppStateManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.teamjmonkey.GameNameGoesHere;
import com.teamjmonkey.appstates.LoadingScreenAppState;
import com.teamjmonkey.appstates.MainMenuAppState;
import com.teamjmonkey.util.Manager;
import de.lessvoid.nifty.Nifty;

public class UIManager implements Manager {

    private GameNameGoesHere myApp;
    private AppStateManager stateManager;
    private Nifty nifty;

    public UIManager() {
        myApp = GameNameGoesHere.getApp();

        initialiseNifty();
        stateManager = myApp.getStateManager();
    }

    @Override
    public void load(int level) {
        //load any nifty effects and uiImages on the screen
    }

    private void initialiseNifty() {
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(myApp.getAssetManager(),
                myApp.getInputManager(),
                myApp.getAudioRenderer(),
                myApp.getGuiViewPort());
        nifty = niftyDisplay.getNifty();

        nifty.enableAutoScaling(1280, 720);
        myApp.getGuiViewPort().addProcessor(niftyDisplay);
        //nifty.setDebugOptionPanelColors(true);
    }

    public Nifty getNifty() {
        return nifty;
    }

    public void cleanup() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void showMainMenu() {
        stateManager.attach(myApp.getMonkeyAppStateManager().getAppState(MainMenuAppState.class));
    }

    public void showLoadingScreen() {
        stateManager.attach(myApp.getMonkeyAppStateManager().getAppState(LoadingScreenAppState.class));
    }
}