package com.teamjmonkey.util;

public enum GameState {

    LOADING_LEVEL, MAIN_SCREEN, PAUSED, RUNNING, DEAD, FINISHED, NONE;
    private static GameState currentGameState = GameState.NONE;
    private static GameState previousGameState = GameState.NONE;

    public static GameState getGameState() {
        return currentGameState;
    }

    public static void setGameState(GameState currentGameState) {
        previousGameState = GameState.currentGameState; //store the currentgamestate into previous
        GameState.currentGameState = currentGameState; //store new game state into currentgamestate
    }

    public static GameState getPreviousGameState() {
        return previousGameState;
    }

    private static boolean moving = false;

    public static boolean isMoving() {
        return moving;
    }

    public static void setMoving(boolean moving) {
        GameState.moving = moving;
    }
}