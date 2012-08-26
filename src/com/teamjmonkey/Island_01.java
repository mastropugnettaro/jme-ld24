package com.teamjmonkey;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.MeshCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;

public class Island_01 extends SimpleApplication {

    public static void main(String[] args) {
        Island_01 app = new Island_01();
        app.start();
    }
    Geometry geom;

    @Override
    public void simpleInitApp() {


        BulletAppState bulletAppState = new BulletAppState();
        bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        stateManager.attach(bulletAppState);
//        bulletAppState.getPhysicsSpace().setAccuracy(1f/30f);
        bulletAppState.getPhysicsSpace().enableDebug(assetManager);

        // Load Island
        Node island = (Node) assetManager.loadModel("Models/Islands/ogre/island_01.j3o");
        rootNode.attachChild(island);

        // Load Island CollisionMesh
        Node islandCollision = (Node) assetManager.loadModel("Models/Islands/ogre/island_01_collision.j3o");
        Geometry geoCollision = (Geometry) islandCollision.getChild(0);
        CollisionShape colShape = new MeshCollisionShape(geoCollision.getMesh());
        colShape.setMargin(0.005f);
        RigidBodyControl rigControl = new RigidBodyControl(colShape, 0);
        island.addControl(rigControl);
        bulletAppState.getPhysicsSpace().add(rigControl);


        // Load trees
        Node trees = (Node) assetManager.loadModel("Models/Islands/ogre/trees_01.j3o");
        rootNode.attachChild(trees);
        for (Spatial sp : trees.getChildren()) {
            Node nd = (Node) sp;
            nd.detachAllChildren();

            if (nd.getName().indexOf("plant_01") == 0) {
                // Load Tree
                Node tree_1 = (Node) assetManager.loadModel("Models/Plants/ogre/plant_01.j3o");
                tree_1.getChild(1).setQueueBucket(Bucket.Transparent);
                nd.attachChild(tree_1);
            }
            else if (nd.getName().indexOf("plant_02") == 0) {
                // Load Tree
                Node tree_1 = (Node) assetManager.loadModel("Models/Plants/ogre/plant_02.j3o");
                tree_1.getChild(1).setQueueBucket(Bucket.Transparent);
                nd.attachChild(tree_1);
            }
            else if (nd.getName().indexOf("plant_03") == 0) {
                // Load Tree
                Node tree_1 = (Node) assetManager.loadModel("Models/Plants/ogre/plant_03.j3o");
                tree_1.getChild(1).setQueueBucket(Bucket.Transparent);
                nd.attachChild(tree_1);
            }            
            else if (nd.getName().indexOf("plant_04") == 0) {
                // Load Tree
                Node tree_1 = (Node) assetManager.loadModel("Models/Plants/ogre/plant_04.j3o");
                tree_1.getChild(1).setQueueBucket(Bucket.Transparent);
                nd.attachChild(tree_1);
            }
        }

        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(new Vector3f(-0.8f, -0.6f, -0.08f).normalizeLocal());
        dl.setColor(new ColorRGBA(1, 1, 1, 1));
        rootNode.addLight(dl);


        AmbientLight al = new AmbientLight();
        al.setColor(new ColorRGBA(2f, 1.5f, 1.5f, 1f));
        rootNode.addLight(al);

        flyCam.setMoveSpeed(100);
        viewPort.setBackgroundColor(ColorRGBA.Gray);

    }

    @Override
    public void simpleUpdate(float tpf) {
    }
}
