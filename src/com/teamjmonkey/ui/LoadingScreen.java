package com.teamjmonkey.ui;

import com.teamjmonkey.GameNameGoesHere;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class LoadingScreen implements ScreenController {

    private GameNameGoesHere myApp = GameNameGoesHere.getApp();
    private UIManager uiManager = myApp.getUIManager();
    private Nifty nifty = uiManager.getNifty();

    public LoadingScreen() {
        nifty.registerScreenController(this);
        nifty.fromXmlWithoutStartScreen("Interface/Nifty/LoadingScreen.xml");
    }

    public void onStartScreen() {
        System.out.println("onStartScreen");
    }

    public void onEndScreen() {
        System.out.println("onEndScreen");
    }

    public void bind(Nifty nifty, Screen screen) {
        System.out.println("bind( " + screen.getScreenId() + ")");
    }

    public void showLoadingScreen() {
        nifty.gotoScreen("loadingScreen");
    }
}