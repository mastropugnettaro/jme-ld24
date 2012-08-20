package com.teamjmonkey.util;

public interface Manager {
    public void load(int level); //load everything relevant to that level
    public void cleanup();       //cleanup everything relevant to that level
}