package com.teamjmonkey.animation;

import com.jme3.animation.AnimControl;
import com.jme3.scene.Spatial;

public class AnimComponent {

    private String curAnim;
    private String prevAnim;
    private AnimControl animControl;
    private Spatial model;

    public AnimComponent(Spatial model) {
        this.model = model;
        if (model != null) {
            this.animControl = model.getControl(AnimControl.class);
            createChannels();

            animControl.getChannel(AnimConf.UPPER_BODY).setAnim("Walk");

           // animControl.getChannel(AnimConf.UPPER_BODY).setAnim("Idle"); //need init animation
        }

    }

    public AnimComponent(String curAnim) {
        this.curAnim = curAnim;
        this.prevAnim = curAnim;
    }

    public String getCurAnim() {
        return curAnim;
    }

    public void setCurAnim(String curAnim) {
        setPrevAnim(this.curAnim);
        this.curAnim = curAnim;
    }

    public String getPrevAnim() {
        return prevAnim;
    }

    public void setPrevAnim(String prevAnim) {
        this.prevAnim = prevAnim;
    }

    public AnimControl getAnimControl() {
        return animControl;
    }

    public void setAnimControl(AnimControl animControl) {
        this.animControl = animControl;
    }

    public void createChannels() {
        getAnimControl().createChannel();
        getAnimControl().createChannel();
        getAnimControl().createChannel();
    }

    public Spatial getModel() {
        return model;
    }

    public void setModel(Spatial model) {
        this.model = model;
    }
}