package com.github.itsheroph.hewosleep.listeners;

import com.github.itsheroph.hewosleep.api.HewoSleepAPI;
import com.github.itsheroph.hewosleep.models.SleepUser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMovedEventListener implements Listener {

    private final HewoSleepAPI api;

    public PlayerMovedEventListener(HewoSleepAPI api) {

        this.api = api;

        Bukkit.getPluginManager().registerEvents(this, this.getAPI().getPlugin());

    }

    public HewoSleepAPI getAPI() {

        return this.api;

    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onPlayerMoved(PlayerMoveEvent event) {

        Player player = event.getPlayer();
        SleepUser user = this.getAPI().getUserManager().getUser(player);

        if(user == null) return;

        if(event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockZ() == event.getTo().getBlockZ() && event.getFrom().getBlockY() == event.getTo().getBlockY()) return;

        user.setPosition(event.getTo());

        if(user.isAfk()) {

            Location from = event.getFrom();
            Location to = event.getTo();

            if(to.getY() >= from.getBlockY() + 1) {

                user.updateLastMoved();

                return;

            }

        }

        Location afk = user.getAfkPosition();
        if(afk == null || afk.distanceSquared(user.getPosition()) > 9) {

            user.updateLastMoved();

        }

    }
}
