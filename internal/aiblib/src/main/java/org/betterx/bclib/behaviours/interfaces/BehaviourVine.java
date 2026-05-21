package org.aiblib.bclib.behaviours.interfaces;

import org.aiblib.bclib.interfaces.tools.AddMineableHoe;
import org.aiblib.bclib.interfaces.tools.AddMineableShears;

/**
 * Interface for blocks that are vines.
 * <p>
 * This will add the {@link AddMineableShears}, {@link AddMineableHoe} and {@link BehaviourCompostable} behaviours.
 */
public interface BehaviourVine extends AddMineableShears, AddMineableHoe, BehaviourPlantLike, BehaviourCompostable, BehaviourClimable {
}
