package com.teamjmonkey.controls;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.GhostControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.teamjmonkey.ai.aggro.AggroBehavior;

public class AggroControl extends BaseControl implements PhysicsCollisionListener {

    private static final float CHASE_TIME = 1f;
    private final float aggroRadius = 30f;
    private boolean isChasing;
    private float chaseTimer;
    private AggroBehavior[] behaviors;

    public AggroControl(AggroBehavior... behaviors) {
        this.behaviors = behaviors;
        myApp.getBulletAppState().getPhysicsSpace().addCollisionListener(this);
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (isChasing) {
            if (chaseTimer > 0f) {
                chaseTimer -= tpf;
                for (AggroBehavior ab : behaviors) {
                    ab.update(tpf);
                }
            } else {
                isChasing = false;
                for (AggroBehavior ab : behaviors) {
                    ab.onAggroLoss();
                }
            }
        }
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        GhostControl ghost = new GhostControl(new CapsuleCollisionShape(aggroRadius, 4f));
        ghost.setCollisionGroup(PhysicsCollisionObject.COLLISION_GROUP_02);
        ghost.setCollideWithGroups(PhysicsCollisionObject.COLLISION_GROUP_03);
        spatial.addControl(ghost);
        myApp.getBulletAppState().getPhysicsSpace().add(ghost);

        for (AggroBehavior ab : behaviors) {
            ab.init(myApp, spatial);
        }
    }

    public void aggro(Vector3f target) {
        chaseTimer = CHASE_TIME;
        if (isChasing) {
            for (AggroBehavior ab : behaviors) {
                ab.updateTarget(target);
            }
        } else {
            isChasing = true;
            for (AggroBehavior ab : behaviors) {
                ab.onAggro(target);
            }
        }
    }

    public void collision(PhysicsCollisionEvent event) {
        if (event.getObjectA() instanceof CharacterControl && event.getObjectB() instanceof GhostControl) {
            if (event.getNodeB().equals(spatial)) {
                aggro(myApp.getCamera().getLocation());
            }
        } else if (event.getObjectB() instanceof CharacterControl && event.getObjectA() instanceof GhostControl) {
            if (event.getNodeA().equals(spatial)) {
                aggro(myApp.getCamera().getLocation());
            }
        }
    }
}
