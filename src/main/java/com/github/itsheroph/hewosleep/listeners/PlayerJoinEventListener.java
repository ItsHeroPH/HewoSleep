package com.github.itsheroph.hewosleep.listeners;

import com.github.itsheroph.hewosleep.models.SleepWorld;
import com.github.itsheroph.hewosleep.models.SleepWorldManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinEventListener implements Listener {

    private final SleepWorldManager manager;

    public PlayerJoinEventListener(SleepWorldManager manager) {

        this.manager = manager;

    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        SleepWorld world = this.manager.getSleepWorld(player);

        if(world != null) {

            world.addPlayer(player);

        }
    }
}
