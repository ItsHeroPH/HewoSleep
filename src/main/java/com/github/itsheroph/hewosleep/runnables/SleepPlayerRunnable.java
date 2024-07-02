package com.github.itsheroph.hewosleep.runnables;

import com.github.itsheroph.hewosleep.models.SleepPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SleepPlayerRunnable extends BukkitRunnable {

    private final SleepPlayer player;

    public SleepPlayerRunnable(SleepPlayer player) {

        this.player = player;

    }

    public SleepPlayer getPlayer() {

        return this.player;

    }

    @Override
    public void run() {

        SleepPlayer sleepPlayer = this.getPlayer();
        Player playerBase = sleepPlayer.getPlayer();
        long lastMoved = sleepPlayer.getLastMoved();
        long currentTime = System.currentTimeMillis();

        if(!sleepPlayer.isAfk()) {

            if(currentTime - lastMoved >= 5000) {

                sleepPlayer.setAfkPosition(playerBase.getLocation());
                sleepPlayer.setAfk(true);

            }

        } else {

            Location afk = sleepPlayer.getAfkPosition();
            if(afk == null || afk.distanceSquared(sleepPlayer.getPosition()) > 9) sleepPlayer.setAfk(false);

        }


    }
}
