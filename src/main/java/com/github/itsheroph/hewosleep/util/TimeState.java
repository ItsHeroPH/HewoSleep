package com.github.itsheroph.hewosleep.util;

import com.github.itsheroph.hewosleep.models.SleepWorld;

public enum TimeState {
    CANNOT_SLEEP,
    CAN_SLEEP_SOON,
    CAN_SLEEP_NOW;

    public static TimeState getState(SleepWorld world) {

        long time = world.getTime();

        long almostSleepTime = TimeUtil.BED_TIME_NIGHT - 1200;

        if(time >= TimeUtil.BED_TIME_MORNING && (time < almostSleepTime || time >= TimeUtil.TIME_NIGHT_END)) {

            return CANNOT_SLEEP;

        } else if(time >= almostSleepTime && time < TimeUtil.BED_TIME_NIGHT) {

            return CAN_SLEEP_SOON;

        } else {

            return CAN_SLEEP_NOW;

        }
    }
}
