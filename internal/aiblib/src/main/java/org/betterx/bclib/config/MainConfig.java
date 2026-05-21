package org.aiblib.bclib.config;

import org.aiblib.wunderlib.configs.ConfigFile;
import org.aiblib.bclib.BCLib;

public class MainConfig extends ConfigFile {
    public final static Group PATCH_GROUP = new Group(BCLib.C.namespace, Configs.MAIN_PATCH_CATEGORY, 0);

    public final BooleanValue applyPatches = new BooleanValue(
            PATCH_GROUP.title(),
            "apply_patches",
            true
    ).setGroup(PATCH_GROUP);


    public MainConfig() {
        super(BCLib.C, "main");
    }

    public boolean applyPatches() {
        return applyPatches.get();
    }
}
