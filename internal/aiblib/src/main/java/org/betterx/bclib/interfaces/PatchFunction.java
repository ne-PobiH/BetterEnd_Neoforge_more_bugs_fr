package org.aiblib.bclib.interfaces;

import org.aiblib.bclib.api.v2.datafixer.MigrationProfile;
import org.aiblib.bclib.api.v2.datafixer.PatchDidiFailException;

@FunctionalInterface
public interface PatchFunction<T, R> {
    R apply(T t, MigrationProfile profile) throws PatchDidiFailException;
}
