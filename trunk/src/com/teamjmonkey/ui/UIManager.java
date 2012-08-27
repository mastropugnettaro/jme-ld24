package com.teamjmonkey.ui;

import com.jme3.app.state.AppStateManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;
import com.teamjmonkey.GameNameGoesHere;
import com.teamjmonkey.appstates.LoadingScreenAppState;
import com.teamjmonkey.appstates.MainMenuAppState;
import com.teamjmonkey.util.Manager;
import de.lessvoid.nifty.Nifty;

public class UIManager implements Manager {

    private GameNameGoesHere myApp;
    private AppStateManager stateManager;
    private Nifty nifty;
    private final float WIDTH;
    private Vector2f initialFoodHUDPosition;

    public UIManager() {
        myApp = GameNameGoesHere.getApp();
        WIDTH = myApp.getSettings().getWidth() * 0.15f;
        initialFoodHUDPosition = new Vector2f(
            myApp.getSettings().getWidth() * 0.2f, myApp.getSettings().getHeight() * 0.05f);

        initialiseNifty();
        stateManager = myApp.getStateManager();
    }

    @Override
    public void load(int level) {
        //load any nifty effects and uiImages on the screen
    }

    private void initialiseNifty() {
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(myApp.getAssetManager(),
                myApp.getInputManager(),
                myApp.getAudioRenderer(),
                myApp.getGuiViewPort());
        nifty = niftyDisplay.getNifty();

        nifty.enableAutoScaling(1280, 720);
        myApp.getGuiViewPort().addProcessor(niftyDisplay);
        //nifty.setDebugOptionPanelColors(true);
    }

    public Nifty getNifty() {
        return nifty;
    }

    public void cleanup() {

        //remove everything from the GUI
        myApp.getGuiNode();
    }

    public void showMainMenu() {
        stateManager.attach(myApp.getMonkeyAppStateManager().getAppState(MainMenuAppState.class));
    }

    public void showLoadingScreen() {
        stateManager.attach(myApp.getMonkeyAppStateManager().getAppState(LoadingScreenAppState.class));
    }

    private Picture border;

    private Node products = new Node("products");

    private void createBorder(Vector2f position) {
        border = new Picture("border");
        border.setImage(myApp.getAssetManager(), "Interface/inventoryBorder.png", true);

        border.setWidth(WIDTH);
        border.setHeight(WIDTH);

        border.setPosition(position.getX(), position.getY());
        myApp.getGuiNode().attachChild(border);
    }

    public void createUIProducts() {

       // String[] fileNames = {"appleImg.png", "appleImg.png", "appleImg.png"};
       // ColorRGBA[] colors = {ColorRGBA.Pink, ColorRGBA.Green, ColorRGBA.Red};

        for (int i = 0, length = 3; i < length; i++) {

            Vector2f position = initialFoodHUDPosition.clone();
            position.setX(position.getX() + (WIDTH * i) + (myApp.getSettings().getWidth() * 0.08f * i));
            createBorder(position);
        }

        myApp.getGuiNode().attachChild(products);
    }

    public void addInventoryItem(String fileName, ColorRGBA color, int index) {
        Picture p = null;
        p = new Picture("hudPic");
        p.setImage(myApp.getAssetManager(), fileName, true);
        p.setWidth(WIDTH);
        p.setHeight(WIDTH);

        p.getMaterial().setColor("Color", color);

        Vector2f position = initialFoodHUDPosition.clone();
        position.setX(position.getX() + (WIDTH * index) + (myApp.getSettings().getWidth() * 0.08f * index));
        p.setPosition(position.getX(), position.getY());
        createBorder(position);

        products.attachChild(p);
        myApp.getGuiNode().attachChild(products);
    }

    // between 0 and 2
    public void setImageToIndex(Picture image, int index) {

        if (index < 0) {
            index = 0;
        } else if (index > 2) {
            index = 2;
        }

        // find the position to put the image
    }
}