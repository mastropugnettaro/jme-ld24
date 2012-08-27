package com.teamjmonkey.controls;

public enum MonkeyControl {

    LOOK_AT(LookAtControl.class),
    SET_TO_CAM(MainCharacterControl.class),
    WEAPON_FOLLOW_CAM(WeaponFollowCamControl.class),
    BOB_CONTROL(BobControl.class),
    WEAPON_ATTACK_CONTROL(WeaponAttackControl.class),
    FOOD_THROW_CONTROL(FoodThrowControl.class);

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

    public Class<? extends BaseControl> getClazz() {
        return clazz;
    }
}