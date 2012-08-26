package com.teamjmonkey.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.teamjmonkey.GameNameGoesHere;
import com.teamjmonkey.util.NewFlyCamera;

public class NewFlyCamAppState extends AbstractAppState {

    private NewFlyCamera flyCam;
    private GameNameGoesHere myApp = GameNameGoesHere.getApp();

    public NewFlyCamAppState() {
        flyCam = new NewFlyCamera(myApp.getCamera());
        flyCam.setConstraint(true);
    }

    /**
     *  This is called by SimpleApplication during initialize().
     */
    void setCamera(NewFlyCamera cam) {
        this.flyCam = cam;
    }

    public NewFlyCamera getCamera() {
        return flyCam;
    }

    @Override
    public void stateAttached(AppStateManager stateManager) {
        super.stateAttached(stateManager);

        setEnabled(true);
        flyCam.registerWithInput(myApp.getInputManager());
    }

    @Override
    public void stateDetached(AppStateManager stateManager) {
        super.stateDetached(stateManager);

        setEnabled(false);
        flyCam.unregisterInput();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        flyCam.setEnabled(enabled);
    }

    @Override
    public void cleanup() {
        super.cleanup();
    }
}
