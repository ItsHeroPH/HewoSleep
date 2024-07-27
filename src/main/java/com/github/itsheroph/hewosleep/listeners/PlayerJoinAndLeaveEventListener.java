package com.github.itsheroph.hewosleep.listeners;

import com.github.itsheroph.hewosleep.api.HewoSleepAPI;
import com.github.itsheroph.hewosleep.models.SleepUser;
import com.github.itsheroph.hewosleep.models.SleepWorld;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinAndLeaveEventListener implements Listener {

    private final HewoSleepAPI api;

    public PlayerJoinAndLeaveEventListener(HewoSleepAPI api) {

        this.api = api;

        Bukkit.getPluginManager().registerEvents(this, this.getAPI().getPlugin());

    }

    public HewoSleepAPI getAPI() {

        return this.api;

    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onPlayerJoined(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        SleepWorld world = this.getAPI().getWorldManager().getWorld(player.getWorld());

        if(world == null) return;

        SleepUser newUser = new SleepUser(this.getAPI().getUserManager(), player);
        this.getAPI().getUserManager().addUser(newUser);

    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onPlayerLeave(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        SleepUser user = this.getAPI().getUserManager().getUser(player);

        // if user is not exist, we will just ignore it
        if(user == null) return;

        // else if user existed, then we will remove it
        this.getAPI().getUserManager().removeUser(user);

    }
}
