package com.teamjmonkey.animation;

import com.jme3.animation.AnimControl;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.scene.Spatial;
import com.teamjmonkey.GameNameGoesHere;
import java.io.IOException;

public class AnimComponent implements Savable {

    private String curAnim;
    private String prevAnim;
    private AnimControl animControl;
    private Spatial model;

    public AnimComponent(Spatial model) {
        this.model = model;
        if (model != null) {
            AnimControl control = model.getControl(AnimControl.class);
            this.animControl = control;
            GameNameGoesHere app = GameNameGoesHere.getApp();
            control.addListener(app.getAnimManager());
            createChannels();

            animControl.getChannel(AnimConf.UPPER_BODY).setAnim("Walk");

            // animControl.getChannel(AnimConf.UPPER_BODY).setAnim("Idle"); //need init animation
        }
    }

    public AnimComponent(Spatial model, String animType) {
        this.model = model;
        if (model != null) {
            this.animControl = model.getControl(AnimControl.class);
            createChannels();

            animControl.getChannel(AnimConf.UPPER_BODY).setAnim(animType);
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

    @Override
    public void write(JmeExporter e) throws IOException {
    }

    @Override
    public void read(JmeImporter e) throws IOException {
    }
}