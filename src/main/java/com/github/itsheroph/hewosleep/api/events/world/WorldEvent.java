package com.github.itsheroph.hewosleep.api.events.world;

import com.github.itsheroph.hewosleep.api.events.HewoSleepEvent;
import com.github.itsheroph.hewosleep.models.SleepWorld;

public class WorldEvent extends HewoSleepEvent {

    private final SleepWorld world;

    public WorldEvent(SleepWorld world) {

        super(world.getManager().getAPI());

        this.world = world;

    }

    public SleepWorld getWorld() {

        return this.world;

    }
}
