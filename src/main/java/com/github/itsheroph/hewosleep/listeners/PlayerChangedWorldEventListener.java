package com.github.itsheroph.hewosleep.listeners;

import com.github.itsheroph.hewosleep.api.HewoSleepAPI;
import com.github.itsheroph.hewosleep.models.SleepUser;
import com.github.itsheroph.hewosleep.models.SleepWorld;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class PlayerChangedWorldEventListener implements Listener {

    private final HewoSleepAPI api;

    public PlayerChangedWorldEventListener(HewoSleepAPI api) {

        this.api = api;

        Bukkit.getPluginManager().registerEvents(this, this.getAPI().getPlugin());

    }

    public HewoSleepAPI getAPI() {

        return this.api;

    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {

        Player player = event.getPlayer();

        SleepUser user = this.getAPI().getUserManager().getUser(player);
        SleepWorld newWorld = this.getAPI().getWorldManager().getWorld(player.getWorld());

        if(user == null && newWorld != null) {

            SleepUser newUser = new SleepUser(this.getAPI().getUserManager(), player);

            this.getAPI().getUserManager().addUser(newUser);

        }

        if(user != null && newWorld == null) {

            this.getAPI().getUserManager().removeUser(user);

        }

    }
}
