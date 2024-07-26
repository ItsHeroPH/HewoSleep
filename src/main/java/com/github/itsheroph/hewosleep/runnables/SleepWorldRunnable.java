package com.github.itsheroph.hewosleep.runnables;

import com.github.itsheroph.hewosleep.models.SleepWorld;
import com.github.itsheroph.hewosleep.util.TimeUtil;
import org.bukkit.GameRule;
import org.bukkit.scheduler.BukkitRunnable;

public class SleepWorldRunnable extends BukkitRunnable {

    private final SleepWorld world;

    private boolean isNightSkipping = false;

    public SleepWorldRunnable(SleepWorld world) {

        this.world = world;

    }

    public SleepWorld getWorld() {

        return this.world;

    }

    private double calculateSpeedup() {

        double dayDuration = this.getWorld().getWorldConfig().getDayDuration() * 20;
        double nightDuration = this.getWorld().getWorldConfig().getNightDuration() * 20;
        double nightSkipDuration = this.getWorld().getWorldConfig().getNightSkippingDuration() * 20;

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

        int sleepersNumber = this.getWorld().getSleepingUsers().size();
        int sleepersNeeded = this.getWorld().getSleepersNeeded();

        if(!this.getWorld().getWorldConfig().isEnable()) return;

        if(sleepersNumber >= sleepersNeeded && sleepersNumber > 0) {

            this.isNightSkipping = true;
            this.getWorld().clearWeather();

        } else {

            this.isNightSkipping = false;

        }

        if(this.getWorld().getBase().getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE) == Boolean.FALSE) return;

        double acceleration = this.calculateSpeedup();
        boolean isNightSkipped = this.getWorld().addTime(acceleration);

        if(isNightSkipped) {

            this.isNightSkipping = false;

        }

    }
}
