package com.github.itsheroph.hewosleep.util;

import com.github.itsheroph.hewosleep.models.SleepWorld;

public enum TimeState {
    CANNOT_SLEEP,
    CAN_SLEEP_SOON,
    CAN_SLEEP_NOW;

    public boolean isNextState(TimeState state) {

        return values()[(this.ordinal() + 1) % values().length] == state;

    }

    public static TimeState getState(SleepWorld world) {

        long time = world.getTime();

        long almostSleepTime = TimeUtil.BED_TIME_NIGHT - 1200;

        if(time >= 0 && time < almostSleepTime) {

            return CANNOT_SLEEP;

        } else if(time >= almostSleepTime && time < TimeUtil.BED_TIME_NIGHT) {

            return CAN_SLEEP_SOON;

        } else {

            return CAN_SLEEP_NOW;

        }
    }
}
