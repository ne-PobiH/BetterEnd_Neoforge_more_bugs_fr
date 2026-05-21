package org.aiblib.wunderlib.math.sdf.interfaces;

public interface Rotatable extends RootedSDF, Transformable {
    void rotate(double angle);
}

