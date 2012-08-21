package com.teamjmonkey.controls;

public enum MonkeyControl {

    LOOK_AT_ORIGIN(TestControl.class);
    private Class<? extends BaseControl> clazz;

    MonkeyControl(Class<? extends BaseControl> control) {
        clazz = control;
    }

    public BaseControl createControl() {
        try {
            return (BaseControl) clazz.newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}