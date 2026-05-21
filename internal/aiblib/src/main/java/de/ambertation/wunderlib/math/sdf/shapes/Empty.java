package org.aiblib.wunderlib.math.sdf.shapes;

import org.aiblib.wunderlib.math.Bounds;
import org.aiblib.wunderlib.math.Float3;
import org.aiblib.wunderlib.math.Transform;
import org.aiblib.wunderlib.math.sdf.SDF;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.KeyDispatchDataCodec;

public class Empty extends SDF {
    public static final MapCodec<Empty> DIRECT_CODEC = MapCodec.unit(Empty::new);
    public static final KeyDispatchDataCodec<Empty> CODEC = KeyDispatchDataCodec.of(DIRECT_CODEC);

    public Empty() {
        super(0);
    }

    @Override
    public KeyDispatchDataCodec<? extends SDF> codec() {
        return CODEC;
    }


    //-------------------------------------------------------------------------------
    @Override
    public double dist(Float3 pos) {
        return Double.MAX_VALUE;
    }

    @Override
    public String toString() {
        return "Empty" + " [" + graphIndex + "]";
    }


    @Override
    public Bounds getBoundingBox() {
        return Bounds.EMPTY;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Transform defaultTransform() {
        return Transform.IDENTITY;
    }
}

