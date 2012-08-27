package com.teamjmonkey.cinematic;

import com.jme3.animation.LoopMode;
import com.jme3.cinematic.Cinematic;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.PlayState;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.math.Spline.SplineType;
import com.jme3.math.Vector3f;
import com.jme3.post.filters.FadeFilter;
import com.jme3.scene.CameraNode;
import com.teamjmonkey.GameNameGoesHere;
import com.teamjmonkey.Island_02;

public class GameStartCinematic implements CinematicComposition {

    private Island_02 myApp;
    private Cinematic cinematic;
    private FadeFilter fade;
    private MotionEvent seagullCamMotion1;
    private MotionEvent seagullCamMotion2;
    private final float overallTime = 40f;

    public GameStartCinematic(Island_02 myApp, FadeFilter fade) {
        this.myApp = myApp;
        this.fade = fade;
        this.cinematic = new Cinematic(myApp.getRootNode(), overallTime, LoopMode.DontLoop);
        createCamMotionPath();
        createCinematic();
    }

    private void createCinematic() {
        cinematic.addCinematicEvent(0f, new FadeEvent(cinematic, fade, true, 1f));
        cinematic.activateCamera(0f, "seagull");
        cinematic.addCinematicEvent(2f, seagullCamMotion1);
        cinematic.addCinematicEvent(12f, seagullCamMotion2);

    }

    private void createCamMotionPath() {
        CameraNode seagull = cinematic.bindCamera("seagull", myApp.getCamera());
        seagull.setLocalTranslation(new Vector3f(-231.00694f, 269.15887f, 319.6499f));
        seagull.lookAt(new Vector3f(0f, 0f, 0f), Vector3f.UNIT_Y);
        //Motion1
        MotionPath path1 = new MotionPath();
        path1.addWayPoint(new Vector3f(-231.00694f, 269.15887f, 319.6499f));
        path1.addWayPoint(new Vector3f(-271.03436f, 274.78607f, 166.41858f));
        path1.addWayPoint(new Vector3f(-282.98575f, 198.66827f, -62.538017f));
        path1.addWayPoint(new Vector3f(-141.32166f, 241.95816f, -299.23657f));
        path1.addWayPoint(new Vector3f(69.37767f, 282.1654f, -395.867f));
        path1.setCurveTension(0.5f);
        seagullCamMotion1 = new MotionEvent(seagull, path1, 10f);
        seagullCamMotion1.setLookAt(new Vector3f(0f, 0f, 0f), Vector3f.UNIT_Y);
        seagullCamMotion1.setDirectionType(MotionEvent.Direction.LookAt);
        seagullCamMotion1.setLoopMode(LoopMode.DontLoop);
        
        //Motion2
        MotionPath path2 = new MotionPath();
        path2.addWayPoint(new Vector3f(69.37767f, 282.1654f, -395.867f));
        path2.addWayPoint(new Vector3f(49.096973f, 199.68199f, -280.14606f));
        path2.addWayPoint(new Vector3f(69.881424f, 80.421936f, -153.33533f));
        path2.addWayPoint(new Vector3f(92.93746f, 41.409966f, -79.42096f));
        path2.addWayPoint(new Vector3f(135.33469f, 37.869682f, -40.803993f));
        path2.addWayPoint(new Vector3f(232.73439f, 62.335537f, 4.7981267f));
        path2.addWayPoint(new Vector3f(151.06522f, 40.954773f, 108.32132f));
        path2.addWayPoint(new Vector3f(70.51622f, 30.993803f, 89.91843f));
        path2.addWayPoint(new Vector3f(-52.995872f, 27.911129f, 33.102673f));
        path2.addWayPoint(new Vector3f(-96.421425f, 27.326252f, -49.235554f));
        path2.addWayPoint(new Vector3f(-186.47707f, 19.662216f, -72.307915f));
        path2.addWayPoint(new Vector3f(-187f, 19.808556f, -72.36449f));
        
        path2.setCurveTension(0.6f);
        path2.setPathSplineType(SplineType.CatmullRom);
        seagullCamMotion2 = new MotionEvent(seagull, path2, 24f);
        seagullCamMotion2.setDirectionType(MotionEvent.Direction.Path);
        seagullCamMotion2.setLoopMode(LoopMode.DontLoop);
    }

    public void attach() {
        myApp.getStateManager().attach(cinematic);
    }

    public void detach() {
        myApp.getStateManager().detach(cinematic);
    }

    public void play() {
        cinematic.play();
    }

    public void pause() {
        cinematic.pause();
    }

    public void stop() {
        cinematic.stop();
    }

    public boolean isRunning() {
        return cinematic.getPlayState() == PlayState.Playing;
    }
}
