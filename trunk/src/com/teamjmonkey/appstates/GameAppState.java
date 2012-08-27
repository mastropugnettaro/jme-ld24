package com.teamjmonkey.appstates;

import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;
import com.teamjmonkey.GameNameGoesHere;
import com.teamjmonkey.animation.AnimManager;
import com.teamjmonkey.entity.BaseEntity;
import com.teamjmonkey.entity.food.FoodEntity;
import com.teamjmonkey.entity.weapons.WeaponEntity;
import com.teamjmonkey.level.LevelCommon;
import com.teamjmonkey.level.LevelManager;
import com.teamjmonkey.ui.UIManager;
import com.teamjmonkey.util.GameState;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.input.keyboard.KeyboardInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.SizeValue;

public class GameAppState extends AbstractAppState implements ScreenController {

    private GameNameGoesHere myApp = GameNameGoesHere.getApp();
    private InputManager inputManager = myApp.getInputManager();
    private UIManager uiManager = myApp.getUIManager();
    private LevelManager levelManager = myApp.getLevelManager();
    private Nifty nifty = uiManager.getNifty();
    private final String PAUSE = "Pause";
    private final String NEXT_LEVEL = "NextLevel";
    private final String PREVIOUS_LEVEL = "PreviousLevel";
    private Element healthBar;
    private TextRenderer text;
    private int health = 100;

    public GameAppState() {
        nifty.registerScreenController(this);
        nifty.addXml("Interface/Nifty/Hud.xml");
    }

    @Override
    public void stateAttached(AppStateManager stateManager) {
        super.stateAttached(stateManager);
        GameState.setGameState(GameState.RUNNING);
        showHud();

        myApp.getStateManager().attach(myApp.getMonkeyAppStateManager().getAppState(NewFlyCamAppState.class));
        myApp.getStateManager().attach(myApp.getMonkeyAppStateManager().getAppState(LevelCommon.class));
        myApp.getStateManager().attach(myApp.getMonkeyAppStateManager().getAppState(AnimManager.class));
        myApp.getBulletAppState().setEnabled(true);

        loadDesktopInputs();

        createUIProducts();
    }

    @Override
    public void stateDetached(AppStateManager stateManager) {
        super.stateDetached(stateManager);
        removeDesktopInputs();
        // deatch all Level States
        myApp.getStateManager().detach(myApp.getStateManager().getState(LevelCommon.class));
        myApp.getStateManager().getState(AnimManager.class).freezeAnimations();
        myApp.getStateManager().detach(myApp.getStateManager().getState(AnimManager.class));      
        myApp.getStateManager().detach(myApp.getStateManager().getState(NewFlyCamAppState.class));
        myApp.getBulletAppState().setEnabled(false);

        // TODO: pause any playing music
        myApp.getGuiNode().detachAllChildren();
    }

    @Override
    public void cleanup() {
        super.cleanup();
    }

    private void loadDesktopInputs() {

        if (inputManager.hasMapping("SIMPLEAPP_Exit")) {
            inputManager.deleteMapping("SIMPLEAPP_Exit");
        }

        inputManager.addMapping(PAUSE, new KeyTrigger(KeyInput.KEY_ESCAPE),
                new KeyTrigger(KeyboardInputEvent.KEY_PAUSE),
                new KeyTrigger(KeyboardInputEvent.KEY_P));
        inputManager.addMapping(NEXT_LEVEL, new KeyTrigger(KeyInput.KEY_F2));
        inputManager.addMapping(PREVIOUS_LEVEL, new KeyTrigger(KeyInput.KEY_F1));

        inputManager.addListener(actionListener, PAUSE, NEXT_LEVEL, PREVIOUS_LEVEL);

    }

    @Override
    public void update(float tpf) {
        //update loop
        super.update(tpf);

        setHealth(0.2f);
    }

    private void removeDesktopInputs() {

        inputManager.deleteMapping(PAUSE);
        inputManager.deleteMapping(NEXT_LEVEL);
        inputManager.deleteMapping(PREVIOUS_LEVEL);

        inputManager.removeListener(actionListener);
    }
    private ActionListener actionListener = new ActionListener() {

        public void onAction(String name, boolean isPressed, float tpf) {

            if (GameState.getGameState() != GameState.RUNNING) {
                return;
            }

            if (!isPressed) {

                if (name.equals(PAUSE) && !isPressed) {
                    myApp.getStateManager().detach(GameAppState.this);
                    myApp.getStateManager().attach(myApp.getMonkeyAppStateManager().getAppState(PauseMenuAppState.class));
                } else if (name.equals(NEXT_LEVEL)) {
                    levelManager.loadNextLevel();
                    myApp.getStateManager().detach(GameAppState.this);
                } else if (name.equals(PREVIOUS_LEVEL)) {
                    levelManager.loadPreviousLevel();
                    myApp.getStateManager().detach(GameAppState.this);
                }
            }
        }
    };

    // ==== nifty ====
    public void bind(Nifty nifty, Screen screen) {
        healthBar = nifty.getScreen("hud").findElementByName("healthBar");
    }

    public void onStartScreen() {
    }

    public void onEndScreen() {
    }

    public void showHud() {
        nifty.gotoScreen("hud");
    }

    public void removeHud() {
        nifty.gotoScreen("end");
    }

    public void setHealth(final float health) {
        final int MIN_WIDTH = 10; //10 pixels
        int pixelWidth = (int) (MIN_WIDTH + (healthBar.getParent().getWidth() - MIN_WIDTH) * health);
        healthBar.setConstraintWidth(new SizeValue(pixelWidth + "px"));
        healthBar.getParent().layoutElements();

        this.health = (int) health;
    }

    public int getHealth() {
        return health;
    }

    private Picture border;
    private float WIDTH = myApp.getSettings().getWidth()*0.15f;
    private Vector2f initialFoodHUDPosition = new Vector2f(myApp.getSettings().getWidth()*0.2f, myApp.getSettings().getHeight()*0.05f);
    private Node products = new Node("products");

    private void createBorder(Vector2f position) {
        border = new Picture("border");
        border.setImage(myApp.getAssetManager(), "Interface/inventoryBorder.png", true);

        border.setWidth(WIDTH);
        border.setHeight(WIDTH);

        border.setPosition(position.getX(), position.getY());
        myApp.getGuiNode().attachChild(border);
    }

    private void createUIProducts() {

        String[] fileNames = {"appleImg.png", "appleImg.png", "appleImg.png"};
        ColorRGBA[] colors = {ColorRGBA.Pink, ColorRGBA.Green, ColorRGBA.Red};
        String location = "Interface/";

        Picture p = null;
        for (int i = 0, length = fileNames.length; i < length; i++) {

            p = new Picture("hudPic");
            p.setImage(myApp.getAssetManager(), location + fileNames[i], true);
            p.setWidth(WIDTH);
            p.setHeight(WIDTH);

            p.getMaterial().setColor("Color", colors[i]);

            Vector2f position = initialFoodHUDPosition.clone();
            position.setX(position.getX() + (WIDTH * i) + (myApp.getSettings().getWidth()*0.08f * i));
            p.setPosition(position.getX(), position.getY());
            createBorder(position);

            products.attachChild(p);
        }

        myApp.getGuiNode().attachChild(products);
    }

    // between 0 and 2
    public void setImageToIndex(Picture image, int index) {

        if(index < 0) {
            index = 0;
        } else if (index > 2) {
            index = 2;
        }

        // find the position to put the image

    }





    public void equipItem(BaseEntity entity) {

        if (entity instanceof FoodEntity) {



        } else if (entity instanceof WeaponEntity) {

        }


    }








}