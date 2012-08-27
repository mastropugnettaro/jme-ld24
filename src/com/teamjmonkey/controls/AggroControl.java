package com.teamjmonkey.controls;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.scene.Spatial;
import com.teamjmonkey.ai.aggro.AggroBehavior;
import com.teamjmonkey.entity.MovableEntity;

public class AggroControl extends BaseControl implements PhysicsCollisionListener, PhysicsTickListener {

    private MovableEntity entity;
    private GhostControl losGhost;
    private GhostControl attackGhost;
    private int collideWithGroup;
    private final float losRadius;
    private final float fightRadius;
    private boolean hasLosAggro;
    private boolean hadLosAggro;
    private boolean hasAttackAggro;
    private boolean hadAttackAggro;
    private Spatial losTarget;
    private Spatial attackTarget;
    private AggroBehavior[] losBehaviors;
    private AggroBehavior[] attackBehaviors;

    public AggroControl(MovableEntity entity, float losRadius, float fightRadius,
            int collideWithGroup, AggroBehavior losBehavior, AggroBehavior attackBehavior) {
        this(entity, losRadius, fightRadius, collideWithGroup,
                losBehavior == null ? null : new AggroBehavior[]{losBehavior},
                attackBehavior == null ? null : new AggroBehavior[]{attackBehavior});
    }

    public AggroControl(MovableEntity entity, float losRadius, float fightRadius,
            int collideWithGroup, AggroBehavior[] losBehaviors, AggroBehavior[] attackBehaviors) {
        this.entity = entity;
        this.losBehaviors = losBehaviors == null ? new AggroBehavior[0] : losBehaviors;
        this.attackBehaviors = attackBehaviors == null ? new AggroBehavior[0] : attackBehaviors;
        this.losRadius = losRadius;
        this.fightRadius = fightRadius;
        this.collideWithGroup = collideWithGroup;
        myApp.getBulletAppState().getPhysicsSpace().addCollisionListener(this);
        myApp.getBulletAppState().getPhysicsSpace().addTickListener(this);
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (hasLosAggro && hadLosAggro) {
            fireLosUpdate(tpf, losTarget);
        } else if (!hasLosAggro && hadLosAggro) {
            fireLosAggroLoss();
        } else if (hasLosAggro && !hadLosAggro) {
            fireLosOnAggro(losTarget);
        }
        hadLosAggro = hasLosAggro;

        if (hasAttackAggro && hadAttackAggro) {
            fireAttackUpdate(tpf, attackTarget);
        } else if (!hasAttackAggro && hadAttackAggro) {
            fireAttackAggroLoss();
        } else if (hasAttackAggro && !hadAttackAggro) {
            fireAttackOnAggro(attackTarget);
        }
        hadAttackAggro = hasAttackAggro;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);

        if (spatial != null) {
            losGhost = new GhostControl(new SphereCollisionShape(losRadius));
            attackGhost = new GhostControl(new SphereCollisionShape(fightRadius));
            addGhost(losGhost);
            addGhost(attackGhost);
            fireInit();
        }
    }

    private void addGhost(GhostControl ghost) {
        ghost.setCollisionGroup(PhysicsCollisionObject.COLLISION_GROUP_02);
        ghost.setCollideWithGroups(collideWithGroup);
        spatial.addControl(ghost);
        myApp.getBulletAppState().getPhysicsSpace().add(ghost);
    }

    private void fireInit() {
        for (AggroBehavior ab : losBehaviors) {
            ab.init(myApp, entity, spatial);
        }
        for (AggroBehavior ab : attackBehaviors) {
            ab.init(myApp, entity, spatial);
        }
    }

    private void fireLosUpdate(float tpf, Spatial target) {
        for (AggroBehavior ab : losBehaviors) {
            ab.update(tpf, target, hasAttackAggro && attackBehaviors.length > 0);
        }
    }

    private void fireAttackUpdate(float tpf, Spatial target) {
        for (AggroBehavior ab : attackBehaviors) {
            ab.update(tpf, target, hasLosAggro && losBehaviors.length > 0);
        }
    }

    private void fireLosOnAggro(Spatial target) {
        for (AggroBehavior ab : losBehaviors) {
            ab.onAggro(target);
        }
    }

    private void fireAttackOnAggro(Spatial target) {
        for (AggroBehavior ab : attackBehaviors) {
            ab.onAggro(target);
        }
    }

    private void fireLosAggroLoss() {
        for (AggroBehavior ab : losBehaviors) {
            ab.onAggroLoss();
        }
    }

    private void fireAttackAggroLoss() {
        for (AggroBehavior ab : attackBehaviors) {
            ab.onAggroLoss();
        }
    }

    public void collision(PhysicsCollisionEvent event) {
        if (spatial == null || event.getNodeA() == null || event.getNodeB() == null) {
            return;
        }

        PhysicsCollisionObject object = null;
        Spatial target = null;
        if (event.getNodeA().equals(spatial)) {
            object = event.getObjectA();
            target = event.getNodeB();
        }
        if (event.getNodeB().equals(spatial)) {
            object = event.getObjectB();
            target = event.getNodeA();
        }
        if (object != null && target != null && object.equals(losGhost)) {
            hasLosAggro = true;
            losTarget = target;
        } else if (object != null && target != null && object.equals(attackGhost)) {
            hasAttackAggro = true;
            attackTarget = target;
        }
    }

    public void prePhysicsTick(PhysicsSpace space, float tpf) {
        hasLosAggro = false;
        hasAttackAggro = false;
    }

    public void physicsTick(PhysicsSpace space, float tpf) {
    }

    @Override
    public void cleanup() {
        spatial.removeControl(losGhost);

        myApp.getBulletAppState().getPhysicsSpace().removeCollisionListener(this);

        myApp.getBulletAppState().getPhysicsSpace().remove(losGhost);
        spatial.removeControl(attackGhost);
        myApp.getBulletAppState().getPhysicsSpace().remove(attackGhost);

        spatial.removeControl(this);
    }
}
