package com.github.itsheroph.hewosleep.runnables;

import com.github.itsheroph.hewosleep.api.HewoSleepAPI;
import com.github.itsheroph.hewosleep.models.SleepWorld;
import com.github.itsheroph.hewosleep.models.SleepWorldManager;
import com.github.itsheroph.hewosleep.util.TimeUtil;
import org.bukkit.GameRule;
import org.bukkit.scheduler.BukkitRunnable;

public class SleepWorldRunnable extends BukkitRunnable {

    private final HewoSleepAPI api;
    private final SleepWorld world;
    private final SleepWorldManager manager;

    private boolean isNightSkipping = false;

    public SleepWorldRunnable(HewoSleepAPI api, SleepWorld world, SleepWorldManager manager) {

        this.api = api;
        this.world = world;
        this.manager = manager;

    }

    public HewoSleepAPI getAPI() {

        return this.api;

    }

    public SleepWorldManager getManager() {

        return this.manager;

    }

    public SleepWorld getWorld() {

        return this.world;

    }

    private double calculateSpeedup() {

        double dayDuration = this.getWorld().getConfig().getDayLength() * 20;
        double nightDuration = this.getWorld().getConfig().getNightLength() * 20;
        double nightSkipDuration = this.getWorld().getConfig().getNightSkipLength() * 20;

        double daySpeedup = Math.min(TimeUtil.DAY_DURATION / dayDuration, 14000);
        double nightSpeedup = Math.min(TimeUtil.NIGHT_DURATION / nightDuration, 10000);
        double sleepSpeedup = Math.min(TimeUtil.NIGHT_DURATION / nightSkipDuration, 10000);
        final double speedup;

        if(this.getWorld().getTime() >= TimeUtil.BED_TIME_NIGHT) {

            speedup = this.isNightSkipping ? sleepSpeedup : nightSpeedup;

        } else {

            speedup = daySpeedup;

        }

        return speedup;

    }

    @Override
    public void run() {

        int sleepersNumber = this.getWorld().getSleepingPlayers().size();
        int sleepersNeeded = this.getWorld().getSleepersNeeded();

        if(!this.getWorld().getConfig().isEnable()) return;

        if(sleepersNumber >= sleepersNeeded && sleepersNumber > 0) {

            this.isNightSkipping = true;
            this.getWorld().clearWeather();

        } else {

            this.isNightSkipping = false;

        }

        if(world.getWorld().getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE) == Boolean.FALSE) return;

        double acceleration = this.calculateSpeedup();
        boolean isNightSkipped = this.getWorld().addTime(acceleration);

        if(isNightSkipped) {

            this.isNightSkipping = false;

        }
    }
}
