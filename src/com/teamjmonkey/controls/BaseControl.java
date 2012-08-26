package com.teamjmonkey.controls;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import com.teamjmonkey.GameNameGoesHere;
import com.teamjmonkey.util.GameState;
import java.io.IOException;

public abstract class BaseControl implements Control {

    protected GameNameGoesHere myApp = GameNameGoesHere.getApp();
    protected boolean enabled = true;
    protected Spatial spatial;

    public void setSpatial(Spatial spatial) {
        if (this.spatial != null && spatial != null) {
            throw new IllegalStateException("This control has already been added to a Spatial");
        }
        this.spatial = spatial;
    }

    public Spatial getSpatial() {
        return spatial;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void cleanup() {
        spatial.removeControl(this);
    }

    /**
     * To be implemented in subclass.
     */
    protected abstract void controlUpdate(float tpf);

    public void update(float tpf) {
        if (!enabled || GameState.getGameState() != GameState.RUNNING) {
            return;
        }

        controlUpdate(tpf);
    }

    public void render(RenderManager rm, ViewPort vp) {
        if (!enabled) {
            return;
        }
    }

    public void write(JmeExporter ex) throws IOException {
        OutputCapsule oc = ex.getCapsule(this);
        oc.write(enabled, "enabled", true);
        oc.write(spatial, "spatial", null);
    }

    public void read(JmeImporter im) throws IOException {
        InputCapsule ic = im.getCapsule(this);
        enabled = ic.readBoolean("enabled", true);
        spatial = (Spatial) ic.readSavable("spatial", null);
    }

    public Control cloneForSpatial(Spatial spatial) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}