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

        Player player = this.getPlayer().getPlayer();
        SleepPlayer sleepPlayer = this.getPlayer();
        Location lastPostion = sleepPlayer.getPosition();
        long lastMoved = sleepPlayer.getLastMoved();
        long currentTime = System.currentTimeMillis();

        sleepPlayer.setPosition(player.getLocation());

        if(player.getLocation().getX() == lastPostion.getX() && player.getLocation().getZ() == lastPostion.getZ()) {

            if(currentTime - lastMoved >= 180000) sleepPlayer.setAfk(true);

        } else {

            sleepPlayer.setAfk(false);

        }

    }
}
