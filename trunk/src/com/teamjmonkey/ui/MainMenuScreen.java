package com.teamjmonkey.ui;

import com.teamjmonkey.GameNameGoesHere;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.events.NiftyMouseMovedEvent;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.spi.sound.SoundHandle;
import de.lessvoid.nifty.tools.Color;

public class MainMenuScreen implements ScreenController {

    private GameNameGoesHere myApp = GameNameGoesHere.getApp();
    private UIManager uiManager = myApp.getUIManager();
    private Nifty nifty = uiManager.getNifty();
    private Element popupElement;
    private Element currentElement;
    private SoundHandle sound;

    public MainMenuScreen() {
        nifty.registerScreenController(this);
        nifty.fromXml("Interface/Nifty/MainMenu.xml", "start");

        nifty.getSoundSystem().addSound("titleSound", "Sounds/ahem.ogg");

        sound = nifty.getSoundSystem().getSound("titleSound");
        sound.setVolume(0.1f);
    }

    public void onStartScreen() {
        System.out.println("onStartScreen");
    }

    public void onEndScreen() {
        System.out.println("onEndScreen");
    }

    public void goToMainMenu() {
        nifty.gotoScreen("start");
    }

    @NiftyEventSubscriber(pattern = "main.*")
    public void onHover(String id, NiftyMouseMovedEvent event) {

        if (currentElement == null) { //initial element
            if (event.getElement().getRenderer(TextRenderer.class) != null) {
                currentElement = event.getElement();

                // hover
                TextRenderer renderer1 = currentElement.getRenderer(TextRenderer.class);
                renderer1.setColor(Color.BLACK);

                sound.play();
            }
        } else {
            if (event.getElement() != currentElement) {
                currentElement.getRenderer(TextRenderer.class).setColor(Color.WHITE);
                currentElement = null;
            }
        }
    }

    public void bind(Nifty nifty, Screen screen) {
        System.out.println("bind( " + screen.getScreenId() + ")");
        popupElement = nifty.createPopup("popupExit");
    }

    public void showOptionsScreen() {
        nifty.gotoScreen("options");
    }

    public void closePopup() {
        nifty.closePopup(popupElement.getId());
    }

    public void quit() {
        nifty.gotoScreen("end");
    }

    public void showExitDialog() {
        nifty.showPopup(nifty.getCurrentScreen(), popupElement.getId(), null);
    }

    public void exit() {
        myApp.stop();
    }

    public void showLoadingScreen() {
        uiManager.showLoadingScreen();
    }
}