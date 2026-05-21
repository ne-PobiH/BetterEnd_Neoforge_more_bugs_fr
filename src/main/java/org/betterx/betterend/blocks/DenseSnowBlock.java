package org.betterx.betterend.blocks;

import org.aiblib.bclib.behaviours.BehaviourBuilders;
import org.aiblib.bclib.behaviours.interfaces.BehaviourSnow;
import org.aiblib.bclib.blocks.BaseBlock;

public class DenseSnowBlock extends BaseBlock implements BehaviourSnow {
    public DenseSnowBlock() {
        super(BehaviourBuilders.createSnow());
    }
}
