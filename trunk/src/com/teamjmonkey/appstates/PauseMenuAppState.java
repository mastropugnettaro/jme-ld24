package com.teamjmonkey.appstates;

import com.jme3.app.Application;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.teamjmonkey.GameNameGoesHere;
import com.teamjmonkey.ui.UIManager;
import com.teamjmonkey.util.GameState;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.events.NiftyMouseMovedEvent;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.spi.sound.SoundHandle;
import de.lessvoid.nifty.tools.Color;

public class PauseMenuAppState extends AbstractAppState implements ScreenController {

    private GameNameGoesHere myApp = GameNameGoesHere.getApp();
    private InputManager inputManager = myApp.getInputManager();
    private UIManager uiManager = myApp.getUIManager();
    private Nifty nifty = uiManager.getNifty();
    private Element currentElement;
    private SoundHandle sound;

    public PauseMenuAppState() {
        nifty.registerScreenController(this);
        nifty.addXml("Interface/Nifty/PauseMenu.xml");

        sound = nifty.getSoundSystem().getSound("titleSound");
    }

    @Override
    public void stateAttached(AppStateManager stateManager) {
        GameState.setGameState(GameState.PAUSED);
        showPauseMenu();

        stateManager.detach(stateManager.getState(FlyCamAppState.class));
        myApp.getInputManager().setCursorVisible(true);

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
    }

    @Override
    public void update(float tpf) {
        //update loop
    }

    private void removeDesktopInputs() {
        //remove mappings
    }

    // ==== nifty ====

    public void bind(Nifty nifty, Screen screen) {
    }

    public void onStartScreen() {
    }

    public void onEndScreen() {
    }

    public void showPauseMenu() {
        nifty.gotoScreen("pause");
    }

    public void resume() {
        myApp.getStateManager().detach(this);
        myApp.getStateManager().attach(new GameAppState());
    }

    public void restart() {
    }

    public void showMainMenu() {
    }

    public void showOptionsScreen() {
    }

    public void exit() {
        myApp.stop();
    }
}