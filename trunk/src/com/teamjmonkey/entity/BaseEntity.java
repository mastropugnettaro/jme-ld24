package com.teamjmonkey.entity;

import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.teamjmonkey.GameNameGoesHere;
import com.teamjmonkey.controls.ControlManager;
import com.teamjmonkey.graphics.GraphicManager;
import com.teamjmonkey.graphics.Graphics;
import com.teamjmonkey.graphics.MaterialManager;
import com.teamjmonkey.util.PreloadManager;

public abstract class BaseEntity {

    protected GameNameGoesHere myApp = GameNameGoesHere.getApp();
    protected Node rootNode = myApp.getRootNode();
    protected Node guiNode = myApp.getGuiNode();
    protected BulletAppState bulletAppState = myApp.getBulletAppState();
    protected AssetManager assetManager = myApp.getAssetManager();
    protected PreloadManager preloadManager = myApp.getPreloadManager();
    protected GraphicManager graphicManager = myApp.getGraphicManager();
    protected AppStateManager stateManager = myApp.getStateManager();
    protected ControlManager controlManger = myApp.getControlManager();
    protected MaterialManager materialManager = myApp.getMaterialManager();
    protected ControlManager controlManager = myApp.getControlManager();
    protected Spatial spatial;

    public BaseEntity(Graphics graphic) {
        spatial = graphicManager.createSpatial(graphic);
    }

    public BaseEntity() {
    }

    protected abstract CollisionShape getCollisionShape();

    public abstract void addMaterial();

    public abstract void addControl();

    public abstract void cleanup();

    public void addPhysicsControl() {
        RigidBodyControl rigidBodyControl = new RigidBodyControl(getCollisionShape(), 1);
        rigidBodyControl.setKinematic(true);
        rigidBodyControl.setRestitution(1);
        spatial.addControl(rigidBodyControl);
        bulletAppState.getPhysicsSpace().add(rigidBodyControl);
    }

    public Spatial getSpatial() {
        return spatial;
    }

    protected CollisionShape createNewBoxCollisionShape() {
        return new BoxCollisionShape(getExtents());
    }

    protected CollisionShape createNewMeshCollisionShape() {
        return CollisionShapeFactory.createMeshShape(spatial);
    }

    protected CollisionShape createNewSphereCollisionShape() {
        return new SphereCollisionShape(getExtents().x);
    }

    private Vector3f getExtents() {
        return ((BoundingBox) spatial.getWorldBound()).getExtent(null);
    }
}