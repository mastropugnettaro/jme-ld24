package com.teamjmonkey.graphics;

public enum MonkeyMaterial {

    NORMAL("Normal.j3m"),
    MAIN_CHARACTER("MainCharacter.j3m");
    private final String path = "Materials/";
    private final String pathToMaterial;

    MonkeyMaterial(String materialLocation) {
        this.pathToMaterial = path + materialLocation;
    }

    public String getPathToMaterial() {
        return pathToMaterial;
    }
}