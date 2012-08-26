package com.teamjmonkey.util;

import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;

public class Util {

    private static Geometry geometry;

    public static Geometry getGeometryFromNode(Spatial s) {

        if (s instanceof Node) {
            s.depthFirstTraversal(sgv);
            return geometry;
        }
        return null;
    }
    
    private static SceneGraphVisitor sgv = new SceneGraphVisitor() {

        public void visit(Spatial spatial) {
            if (spatial instanceof Geometry) {
                geometry = (Geometry) spatial;
                return;
            }
        }
    };
}
