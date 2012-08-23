/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamjmonkey.appstates;

import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.teamjmonkey.GameNameGoesHere;
import com.teamjmonkey.util.Manager;
import java.lang.Class;
import java.util.ArrayList;

/**
 *
 * @author Wesley
 */
public class MonkeyAppStateManager implements Manager {

    // Make sure to use .addXML in the constructors
    // Initialise instead of stateAttached?

    private GameNameGoesHere myApp;
    private AppStateManager stateManager;
    private ArrayList<AbstractAppState> appStates = new ArrayList<AbstractAppState>(5);

    public MonkeyAppStateManager() {
        myApp = GameNameGoesHere.getApp();
        stateManager = myApp.getStateManager();
    }

    public <T extends AbstractAppState> AbstractAppState getAppState(Class<T> appStateClass) {

        for (AbstractAppState state : getStates()) {
            if (appStateClass.isAssignableFrom(state.getClass())) {
                return (T) state;
            }
        }

        // return a new instance
        try {
            AbstractAppState state = appStateClass.newInstance();
            appStates.add(state);
            return (T) state;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    protected ArrayList<AbstractAppState> getStates() {
        return appStates;
    }

    public void load(int level) {
    }

    public void cleanup() {
    }
}
