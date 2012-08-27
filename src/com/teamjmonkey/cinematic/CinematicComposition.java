package com.teamjmonkey.cinematic;

public interface CinematicComposition {

    public void attach();
    
    public void detach();
    
    public void play();

    public void pause();

    public void stop();

    public boolean isRunning();
}
