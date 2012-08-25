package com.teamjmonkey.appstates;

import com.jme3.app.state.AbstractAppState;
import com.teamjmonkey.util.Manager;
import java.util.ArrayList;

public class MonkeyAppStateManager implements Manager {

    private ArrayList<AbstractAppState> appStates = new ArrayList<AbstractAppState>(5);

    public MonkeyAppStateManager() {
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
