package com.teamjmonkey.appstates;

import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioNode.Status;
import com.teamjmonkey.GameNameGoesHere;

public class BackgroundMusicAppState extends AbstractAppState {

    private boolean isActive;
    private AudioNode music;

    public BackgroundMusicAppState(GameNameGoesHere myApp) {
        music = new AudioNode(myApp.getAssetManager(), "Sounds/Music/bg1.ogg", true);
        music.setLooping(false);
        music.setVolume(0.25f);
        music.setPositional(false);
        music.setDirectional(false);
    }

    @Override
    public void stateAttached(AppStateManager stateManager) {
        isActive = true;
    }

    @Override
    public void stateDetached(AppStateManager stateManager) {
        isActive = false;
        music.stop();
    }

    @Override
    public void update(float tpf) {
        if (isActive) {
            if (music.getStatus() == Status.Stopped) {
                music.play();
            }
        }
    }
}
