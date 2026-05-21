package org.aiblib.bclib.interfaces;

import org.aiblib.bclib.api.v2.datafixer.MigrationProfile;
import org.aiblib.bclib.api.v2.datafixer.PatchDidiFailException;

@FunctionalInterface
public interface PatchBiFunction<U, V, R> {
    R apply(U t, V v, MigrationProfile profile) throws PatchDidiFailException;
}