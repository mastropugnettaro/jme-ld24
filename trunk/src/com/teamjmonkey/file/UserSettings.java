package com.teamjmonkey.file;

import com.teamjmonkey.GameNameGoesHere;

public class UserSettings {

    private GameNameGoesHere myApp;
    private FileManager fileManager;
    private boolean muteSoundFX;
    private boolean muteMusic;

    public UserSettings() {
        myApp = GameNameGoesHere.getApp();
        fileManager = myApp.getFileManager();
        muteSoundFX = fileManager.getMuteSoundFX();
        muteMusic = fileManager.getMuteMusic();
    }

    public void setMuteSoundFx(boolean mute) {
        muteSoundFX = mute;
        fileManager.setMuteSoundFX(mute);
    }

    public boolean isSoundFXMuted() {
        return muteSoundFX;
    }

    public void setMuteMusic(boolean mute) {
        muteMusic = mute;
        fileManager.setMuteMusic(mute);
    }

    public boolean isMusicMuted() {
        return muteMusic;
    }
}