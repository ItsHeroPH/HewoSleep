package com.github.itsheroph.hewosleep.api.events.world;

import com.github.itsheroph.hewosleep.models.SleepWorld;
import com.github.itsheroph.hewosleep.util.TimeState;

public class WorldTimeStateChangeEvent extends WorldEvent {

    private final TimeState newState;

    public WorldTimeStateChangeEvent(SleepWorld world, TimeState newState) {

        super(world);

        this.newState = newState;

    }


    public TimeState getNewState() {

        return this.newState;

    }

}
