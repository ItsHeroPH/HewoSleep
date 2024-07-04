package com.github.itsheroph.hewosleep.listeners;

import com.github.itsheroph.hewosleep.models.SleepPlayer;
import com.github.itsheroph.hewosleep.models.SleepWorld;
import com.github.itsheroph.hewosleep.models.SleepWorldManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class PlayerChangedWorldEventListener implements Listener {

    private final SleepWorldManager manager;

    public PlayerChangedWorldEventListener(SleepWorldManager manager) {

        this.manager = manager;

    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {

        Player player = event.getPlayer();
        SleepPlayer sleepPlayer = this.manager.getSleepPlayer(player);

        if(sleepPlayer == null) return;

        SleepWorld worldFrom = sleepPlayer.getSleepWorld();

        if(worldFrom == null) return;

        worldFrom.removePlayer(player);

        SleepWorld worldTo = this.manager.getSleepWorld(player.getWorld());

        if(worldTo == null) return;

        worldTo.addPlayer(player);

    }
}
