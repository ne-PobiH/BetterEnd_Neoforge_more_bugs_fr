package org.aiblib.bclib.sdf.primitive;

import org.aiblib.bclib.util.MHelper;

public class SDFSphere extends SDFPrimitive {
    private float radius;

    public SDFSphere setRadius(float radius) {
        this.radius = radius;
        return this;
    }

    @Override
    public float getDistance(float x, float y, float z) {
        return MHelper.length(x, y, z) - radius;
    }
}
