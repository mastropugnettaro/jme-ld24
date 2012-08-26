package com.teamjmonkey.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.teamjmonkey.util.NewFlyCamera;

public class NewFlyCamAppState extends AbstractAppState {

    private Application app;
    private NewFlyCamera flyCam;

    public NewFlyCamAppState() {
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
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        this.app = app;

        if (app.getInputManager() != null) {

            flyCam = new NewFlyCamera(app.getCamera());
            flyCam.setConstraint(true);
            flyCam.registerWithInput(app.getInputManager());
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        flyCam.setEnabled(enabled);
    }

    @Override
    public void cleanup() {
        super.cleanup();

        flyCam.unregisterInput();
    }
}
