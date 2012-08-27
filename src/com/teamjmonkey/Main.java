package com.teamjmonkey;

import com.jme3.system.AppSettings;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Main {

    public static void main(String[] args) {

        GameNameGoesHere app = new GameNameGoesHere();

        // load the application settings
        AppSettings appSettings = new AppSettings(true);
        appSettings.setVSync(true);
        appSettings.setTitle("Time to Grow up!");
        appSettings.setFullscreen(true);

        try {
            appSettings.setIcons(new BufferedImage[]{ImageIO.read(new File("assets/Interface/icon.png"))});
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Icon missing.", ex);
        }

        // set the start image
        appSettings.setSettingsDialogImage("Interface/splash_screen.png");

        // apply the settings
        app.setSettings(appSettings);

        // starts the application (GameNameGoesHere)

        app.start();
    }
}