/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamjmonkey.tests;

import com.jme3.app.SimpleApplication;
import com.jme3.system.JmeContext.Type;
import com.teamjmonkey.file.FileManager;

/**
 *
 * @author Wesley
 */
public class DatabaseTest extends SimpleApplication {

    public static void main(String args[]) {
        new DatabaseTest().start(Type.Headless);
    }

    @Override
    public void simpleInitApp() {
        FileManager fileManager = new FileManager(assetManager);
        fileManager.setHighScore(27, 1);
        System.out.println(fileManager.getHighScore(1));
        boolean completed = fileManager.getCompleted(2);
        fileManager.setPostProcessing(false);
        System.out.println(fileManager.getPostProcessing());
        fileManager.getFastestTime(2);

        System.out.println("gersdsds" + fileManager.getMuteMusic());
        System.out.println("gereesss" + fileManager.getMuteSoundFX());
        System.out.println("sfdsdfs" + completed);
    }
}
