package com.teamjmonkey.entity;

import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.GhostControl;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.teamjmonkey.controls.MonkeyControl;
import java.io.IOException;

public class MainCharacter extends BaseEntity implements Savable {

    private CharacterControl player;
    private GhostControl control;

    public MainCharacter() {
        super();
        spatial = new Node("mainCharacter");
        spatial.setUserData("entity", this);
    }

    @Override
    public CollisionShape getCollisionShape() {
        return new CapsuleCollisionShape(1.5f, 3f, 1);
    }

    @Override
    public void addPhysicsControl() {
        player = new CharacterControl(getCollisionShape(), 0.1f);
        player.setJumpSpeed(20);
        player.setFallSpeed(30);
        player.setGravity(30);
        player.setPhysicsLocation(new Vector3f(0, 10, 0));
        player.setCollisionGroup(PhysicsCollisionObject.COLLISION_GROUP_03);
        player.setCollideWithGroups(PhysicsCollisionObject.COLLISION_GROUP_01 | PhysicsCollisionObject.COLLISION_GROUP_02 | PhysicsCollisionObject.COLLISION_GROUP_04);
        bulletAppState.getPhysicsSpace().add(player);

        spatial.addControl(player);

      //  control = new GhostControl(getCollisionShape());
     //   spatial.addControl(control);
      //  bulletAppState.getPhysicsSpace().add(control);


    }

    public CharacterControl getCharacterControl() {
        return player;
    }

    @Override
    public void addMaterial() {
    }

    @Override
    public void addControl() {
        spatial.addControl(controlManager.getControl(MonkeyControl.SET_TO_CAM));
    }

    @Override
    public void cleanup() {
        spatial.getControl(MonkeyControl.SET_TO_CAM.getClazz()).cleanup();
        bulletAppState.getPhysicsSpace().remove(player);
        spatial.removeControl(player);
        spatial.setUserData("entity", null);
        player = null;
    }

    @Override
    public void finalise() {
        addPhysicsControl();
        addControl();
    }

    @Override
    public void write(JmeExporter e) throws IOException {
        OutputCapsule capsule = e.getCapsule(this);
        capsule.write(this.player, "player", null);
    }

    @Override
    public void read(JmeImporter e) throws IOException {
        InputCapsule capsule = e.getCapsule(this);
        player = (CharacterControl) capsule.readSavable("player", null);
    }
}
