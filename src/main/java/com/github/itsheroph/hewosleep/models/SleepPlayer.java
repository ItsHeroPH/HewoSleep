package com.github.itsheroph.hewosleep.models;

import com.github.itsheroph.hewosleep.api.HewoSleepAPI;
import com.github.itsheroph.hewosleep.runnables.SleepPlayerRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SleepPlayer {

    private final SleepWorld sleepWorld;
    private final Player player;
    private final SleepPlayerRunnable runnable;

    private boolean afk;
    private boolean sleeping;
    private Location position;
    private long lastBedEntered = 0;
    private long lastMoved = 0;

    public SleepPlayer(SleepWorld world, Player player) {

        this.sleepWorld = world;
        this.player = player;
        this.runnable = new SleepPlayerRunnable(this);
        this.afk = false;
        this.sleeping = false;
        this.position = player.getLocation();

        this.runnable.runTaskTimer(
                this.getSleepWorld().getManager().getAPI().getPlugin(),
                1L, 1L
        );

    }

    public SleepWorld getSleepWorld() {

        return this.sleepWorld;

    }

    public Player getPlayer() {

        return this.player;

    }

    public SleepPlayerRunnable getRunnable() {

        return this.runnable;

    }

    public void stopRunnable() {

        if(this.getRunnable().isCancelled()) {

            this.getRunnable().cancel();

        }

    }

    public boolean isAfk() {

        return this.afk;

    }

    public void setAfk(boolean afk) {

        this.afk = afk;

    }

    public boolean isSleeping() {

        return this.sleeping;

    }

    public void setSleeping(boolean sleeping) {

        if(sleeping) {

            this.lastBedEntered = System.currentTimeMillis();
            this.sleeping = true;

        } else {

            this.sleeping = false;

        }

    }

    public Location getPosition() {

        return this.position;

    }

    public void setPosition(Location position) {

        Location lastPosition = this.getPosition();

        if(position.getX() != lastPosition.getX() || position.getZ() != lastPosition.getZ()) {

            this.position = position;
            this.lastMoved = System.currentTimeMillis();
        }

    }

    public long getLastBedEntered() {

        return this.lastBedEntered;

    }

    public long getLastMoved() {

        return this.lastMoved;

    }

    public long calculateRemainingCooldown() {

        HewoSleepAPI api = this.getSleepWorld().getManager().getAPI();

        int cooldown = api.getBedEnterDelay();
        long currentTime = System.currentTimeMillis();

        return Math.max(0, (cooldown * 1000L) - (currentTime - this.getLastBedEntered()));

    }
}
