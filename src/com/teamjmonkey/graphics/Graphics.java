package com.teamjmonkey.graphics;

public enum Graphics {

    BULL("bull.j3o"),
    TEST_PLATFORM("testFloor.j3o"),
    SPEAR("Weapons/spear.j3o"),
    GUN("Weapons/gun.j3o"),
    SWORD("Weapons/sword.j3o"),
    APPLE("Food/apple.j3o");

    private String fileLocation;
    private String pathToModel = "Models/";

    Graphics(String modelLocation) {
        fileLocation = pathToModel + modelLocation;
    }

    Graphics(String modelLocation, String pathToModel) { //maybe for UI Elements? to change the path to Interface
        fileLocation = pathToModel + modelLocation;
    }

    public String getPath() {
        return fileLocation;
    }
}