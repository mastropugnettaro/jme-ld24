package com.teamjmonkey.cinematic;

import com.jme3.cinematic.Cinematic;
import com.jme3.cinematic.events.AbstractCinematicEvent;
import com.jme3.post.filters.FadeFilter;

public class FadeEvent extends AbstractCinematicEvent {

    private Cinematic cinematic;
    private FadeFilter fade;
    private boolean in = true;
    private float value;
    private float duration;

    public FadeEvent(Cinematic cinematic, FadeFilter fade, boolean in, float duration) {
        super(1);
        this.in = in;
        value = in ? 0f : 1f;
        this.cinematic = cinematic;
        this.fade = fade;
        this.duration = duration;
    }

    @Override
    public void onPlay() {
        fade.setDuration(duration / cinematic.getSpeed());
        if (in) {
            fade.fadeIn();
        } else {
            fade.fadeOut();
        }
        fade.setValue(value);
    }

    @Override
    public void setTime(float time) {
        super.setTime(time);
        if (time >= fade.getDuration()) {
            value = in ? 1f : 0f;
            fade.setValue(value);
        } else {
            value = time;
            if (in) {
                fade.setValue(time / cinematic.getSpeed());
            } else {
                fade.setValue(1f - time / cinematic.getSpeed());
            }
        }
    }

    @Override
    public void onUpdate(float tpf) {
    }

    @Override
    public void onStop() {
        fade.setValue(in ? 1f : 0f);
        fade.pause();
    }

    @Override
    public void onPause() {
        value = fade.getValue();
        fade.pause();
    }
}
