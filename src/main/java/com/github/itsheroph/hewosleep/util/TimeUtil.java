package com.github.itsheroph.hewosleep.util;

import org.bukkit.World;
import org.bukkit.World.Environment;

public class TimeUtil {

    public final static double DAY_DURATION = 14000;
    public final static double NIGHT_DURATION = 10000;
    public final static int TIME_NIGHT_END = 23000;
    public final static int BED_TIME_MORNING = 0;
    public final static int BED_TIME_RAIN_NIGHT = 12010;
    public final static int BED_TIME_NIGHT = 12542;

    public static boolean isSleepPossible(World world) {

        boolean isOverworld = world.getEnvironment() == Environment.NORMAL;
        boolean isThundering = world.isThundering();
        long startSleepTime = world.hasStorm() ? BED_TIME_RAIN_NIGHT : BED_TIME_NIGHT;
        boolean isNight = world.getTime() >= startSleepTime && world.getTime() <= TIME_NIGHT_END;

        return isOverworld && (isThundering || isNight);

    }

}
