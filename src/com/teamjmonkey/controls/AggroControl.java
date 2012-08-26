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
import com.teamjmonkey.entity.MovableEntity;

public class AggroControl extends BaseControl implements PhysicsCollisionListener {

    private MovableEntity entity;
    private GhostControl losGhost;
    private GhostControl fightGhost;
    private static final float CHASE_TIME = 1f;
    private final float losRadius;
    private final float fightRadius;
    private boolean isChasing;
    private float chaseTimer;
    private AggroBehavior[] losBehaviors;
    private AggroBehavior[] attackBehaviors;

    public AggroControl(MovableEntity entity, float losRadius, float fightRadius, AggroBehavior losBehavior, AggroBehavior fightBehavior) {
        this(entity, losRadius, fightRadius,
                losBehavior == null ? new AggroBehavior[0] : new AggroBehavior[]{losBehavior},
                fightBehavior == null ? new AggroBehavior[0] : new AggroBehavior[]{fightBehavior});
    }

    public AggroControl(MovableEntity entity, float losRadius, float fightRadius, AggroBehavior[] losBehaviors, AggroBehavior[] fightBehaviors) {
        this.entity = entity;
        this.losBehaviors = losBehaviors == null ? new AggroBehavior[0] : losBehaviors;
        this.attackBehaviors = fightBehaviors == null ? new AggroBehavior[0] : fightBehaviors;
        this.losRadius = losRadius;
        this.fightRadius = fightRadius;
        myApp.getBulletAppState().getPhysicsSpace().addCollisionListener(this);
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (isChasing) {
            if (chaseTimer > 0f) {
                chaseTimer -= tpf;
                fireLosUpdate(tpf);
            } else {
                isChasing = false;
                fireLosAggroLoss();
            }
        }
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);

        if (spatial != null) {
            losGhost = new GhostControl(new CapsuleCollisionShape(losRadius, 4f));
            fightGhost = new GhostControl(entity.getCollisionShape());
            addGhost(losGhost);
            addGhost(fightGhost);
            fireInit();
        }
    }

    private void addGhost(GhostControl ghost) {
        ghost.setCollisionGroup(PhysicsCollisionObject.COLLISION_GROUP_02);
        ghost.setCollideWithGroups(PhysicsCollisionObject.COLLISION_GROUP_03);
        spatial.addControl(ghost);
        myApp.getBulletAppState().getPhysicsSpace().add(ghost);
    }

    public void aggro(Vector3f target) {
        chaseTimer = CHASE_TIME;
        if (isChasing) {
            fireLosUpdateTarget(target);
        } else {
            isChasing = true;
            fireLosOnAggro(target);
        }
    }

    private void fireInit() {
        for (AggroBehavior ab : losBehaviors) {
            ab.init(myApp, entity, spatial);
        }
        for (AggroBehavior ab : attackBehaviors) {
            ab.init(myApp, entity, spatial);
        }
    }

    private void fireLosUpdate(float tpf) {
        for (AggroBehavior ab : losBehaviors) {
            ab.update(tpf);
        }
    }

    private void fireLosUpdateTarget(Vector3f target) {
        for (AggroBehavior ab : losBehaviors) {
            ab.updateTarget(target);
        }
    }

    private void fireLosOnAggro(Vector3f target) {
        for (AggroBehavior ab : losBehaviors) {
            ab.onAggro(target);
        }
    }

    private void fireLosAggroLoss() {
        for (AggroBehavior ab : losBehaviors) {
            ab.onAggroLoss();
        }
    }

    public void collision(PhysicsCollisionEvent event) {

        if(spatial == null || event.getNodeA() == null || event.getNodeB() == null) {
            return;
        }

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

    @Override
    public void cleanup() {
        spatial.removeControl(losGhost);

        myApp.getBulletAppState().getPhysicsSpace().removeCollisionListener(this);

        myApp.getBulletAppState().getPhysicsSpace().remove(losGhost);
        spatial.removeControl(fightGhost);
        myApp.getBulletAppState().getPhysicsSpace().remove(fightGhost);

        spatial.removeControl(this);
    }
}
