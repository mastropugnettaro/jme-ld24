package com.teamjmonkey.graphics;

public enum Graphics {

    MAIN_CHARACTER("MainCharacter/MainCharacter.j3o"),
    ANOTHER_OBJECT("MainCharacter/MainCharacter.j3o"),
    BULL("bull.j3o");

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