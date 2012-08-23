/*@wezrule jME Nifty Tutorials 2012 */
package com.teamjmonkey.ui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class OptionsScreen implements ScreenController {

    private Nifty nifty;

    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        System.out.println("bind( " + screen.getScreenId() + ")");
    }

    public void onStartScreen() {
        System.out.println("onStartScreen");
    }

    public void onEndScreen() {
        System.out.println("onEndScreen");
    }

    public void gotoMainMenu() {
        nifty.gotoScreen("start");
    }

    public String getPlayerName() {
        return "wes";
    }
}