package com.github.itsheroph.hewosleep.listeners.Gsit;

import com.github.itsheroph.hewosleep.api.HewoSleepAPI;
import com.github.itsheroph.hewosleep.api.events.user.UserSleepingStateChangeEvent.Cause;
import com.github.itsheroph.hewosleep.models.SleepUser;
import dev.geco.gsit.api.event.PlayerGetUpPoseEvent;
import dev.geco.gsit.api.event.PlayerPoseEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PlayerPoseEventsListener implements Listener {

    private final HewoSleepAPI api;

    public PlayerPoseEventsListener(HewoSleepAPI api) {

        this.api = api;

        Bukkit.getPluginManager().registerEvents(this, this.getAPI().getPlugin());

    }

    public HewoSleepAPI getAPI() {

        return this.api;

    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onPlayerPose(PlayerPoseEvent event) {

        Player player = event.getPlayer();
        SleepUser user = this.getAPI().getUserManager().getUser(player);

        if(user == null) return;

        if(event.getPoseSeat().getPose() == Pose.SLEEPING) {

            user.updateLastMoved();
            user.setSleeping(true, Cause.PLUGIN);

        }

    }
    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onPlayerGetUpPose(PlayerGetUpPoseEvent event) {

        Player player = event.getPlayer();
        SleepUser user = this.getAPI().getUserManager().getUser(player);

        if(user == null) return;

        if(event.getPoseSeat().getPose() == Pose.SLEEPING) {

            user.updateLastMoved();
            user.setSleeping(false, Cause.PLUGIN);

        }

    }
}
