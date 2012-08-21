package com.teamjmonkey.controls;

import com.jme3.scene.control.Control;

public class ControlManager {

    public Control getControl(MonkeyControl monkeyControl) {
        if (monkeyControl != null) {
            return monkeyControl.createControl();
        }

        return null;
    }
}
