package com.github.itsheroph.hewosleep.runnables;

import com.github.itsheroph.hewosleep.models.SleepUser;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SleepUserRunnable extends BukkitRunnable {

    private final SleepUser user;

    public SleepUserRunnable(SleepUser user){

        this.user = user;

    }

    public SleepUser getUser() {

        return this.user;

    }

    @Override
    public void run() {

        SleepUser user = this.getUser();
        Player player = user.getBase();
        long lastMoved = user.getLastMoved();
        long currentTime = System.currentTimeMillis();

        if(lastMoved == 0) return;

        if(!user.isAfk()) {

            if(currentTime - lastMoved >= 360000) {

                user.setAfkPosition(player.getLocation());
                user.setAfk(true);

            }

        } else {

            Location afkLoc = user.getAfkPosition();
            if(afkLoc == null || afkLoc.distanceSquared(player.getLocation()) >= 9) {

                user.setAfk(false);

            }

        }
    }
}
