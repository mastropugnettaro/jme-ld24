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
    private MainMenuAppState mainMenuAppState;
    private AppStateManager stateManager;
    private Nifty nifty;
    private LoadingScreenAppState loadingScreenAppState;

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
        nifty.setDebugOptionPanelColors(true);
    }

    public Nifty getNifty() {
        return nifty;
    }

    public void cleanup() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void showMainMenu() {
        if (mainMenuAppState == null) {
            mainMenuAppState = new MainMenuAppState();
        }

        stateManager.attach(mainMenuAppState);
    }

    public void showLoadingScreen() {
        if (loadingScreenAppState == null) {
            loadingScreenAppState = new LoadingScreenAppState();
        }

        stateManager.attach(loadingScreenAppState);
    }
}