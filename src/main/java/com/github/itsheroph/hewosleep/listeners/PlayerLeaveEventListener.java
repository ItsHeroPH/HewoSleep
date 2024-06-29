package com.github.itsheroph.hewosleep.listeners;

import com.github.itsheroph.hewosleep.models.SleepWorld;
import com.github.itsheroph.hewosleep.models.SleepWorldManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveEventListener implements Listener {

    private final SleepWorldManager manager;

    public PlayerLeaveEventListener(SleepWorldManager manager) {

        this.manager = manager;

    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        SleepWorld sleepWorld = this.manager.getSleepWorld(player);

        if(sleepWorld != null) {

            sleepWorld.removePlayer(player);

        }
    }
}
