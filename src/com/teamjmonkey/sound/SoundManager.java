package com.teamjmonkey.sound;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioRenderer;
import com.teamjmonkey.GameNameGoesHere;
import com.teamjmonkey.util.Manager;
import java.util.ArrayList;
import java.util.EnumMap;

public class SoundManager implements Manager {

    private static EnumMap<MonkeySound, AudioNode> soundMap;
    private GameNameGoesHere myApp;
    private AudioRenderer audioRenderer;
    private AssetManager assetManager;

    public SoundManager() {
        myApp = GameNameGoesHere.getApp();
        this.audioRenderer = myApp.getAudioRenderer();
        this.assetManager = myApp.getAssetManager();

        soundMap = new EnumMap<MonkeySound, AudioNode>(MonkeySound.class);
    }

    public void load(int level) {
        if (level == 1) {
            loadSoundEffects(new MonkeySound[]{MonkeySound.TEST_SOUND});
            loadMusic(new MonkeySound[]{MonkeySound.TEST_MUSIC});
        }
    }

    // loads all sound effects which will be needed for that level
    private void loadSoundEffects(MonkeySound[] sounds) {

        for (MonkeySound s : sounds) {
            AudioNode soundNode = new AudioNode(assetManager, s.path());
            soundMap.put(s, soundNode);
        }
    }

    // load all music which will be streamed
    public void loadMusic(MonkeySound[] music) {

        for (MonkeySound s : music) {
            if (s != null) {
                AudioNode musicNode = new AudioNode(assetManager, s.path(), true);
                musicNode.setLooping(true);
                musicNode.setPositional(false);
                musicNode.setDirectional(false);

                soundMap.put(s, musicNode);
            }
        }
    }

    public void unloadMusic(MonkeySound[] music) {
        for (MonkeySound s : music) {
            soundMap.remove(s);
        }
    }

    public void unloadAllMusic() {
        ArrayList<MonkeySound> musicList = new ArrayList<MonkeySound>();

        for (MonkeySound s : soundMap.keySet()) {
            if (soundMap.get(s).isLooping()) {
                musicList.add(s);
            }
        }

        for (MonkeySound monkeySound : musicList) {
            soundMap.get(monkeySound).stop();
            soundMap.remove(monkeySound);
        }
    }

    public void play(MonkeySound sound) {
        AudioNode toPlay = soundMap.get(sound);

        if (toPlay != null) {
            if (sound.isMusic()) {

                if (myApp.getUserSettings().isMusicMuted()) {
                    return;
                }

                toPlay.play();
            } else {

                if (myApp.getUserSettings().isSoundFXMuted()) {
                    return;
                }

                toPlay.playInstance();
            }
        }
    }

    // pause the music
    public void pause(MonkeySound sound) {

        AudioNode toPause = soundMap.get(sound);

        if (toPause != null) {
            audioRenderer.pauseSource(toPause);
        }
    }

    // if paused it will play, if playing it will be paused
    public void togglePlayPause(MonkeySound sound) {
        AudioNode toToggle = soundMap.get(sound);

        if (toToggle != null) {
            if (toToggle.getStatus() == AudioNode.Status.Paused
                    || toToggle.getStatus() == AudioNode.Status.Stopped) {
                play(sound);
            } else {
                pause(sound);
            }
        }
    }

    // tries to stop a sound, will probably only work for streaming music though
    void stop(MonkeySound sound) {
        AudioNode toStop = soundMap.get(sound);

        toStop.stop();
    }

    public void cleanup() {
        unloadAllMusic();

        System.gc();
    }
}