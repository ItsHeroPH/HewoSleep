package com.github.itsheroph.hewosleep.listeners;

import com.github.itsheroph.hewosleep.models.SleepPlayer;
import com.github.itsheroph.hewosleep.models.SleepWorldManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveEventListener implements Listener {

    private final SleepWorldManager manager;

    public PlayerMoveEventListener(SleepWorldManager manager) {

        this.manager = manager;

    }

    @EventHandler
    public void onPlayerMoved(PlayerMoveEvent event) {

        Player player = event.getPlayer();
        SleepPlayer sleepPlayer = this.manager.getSleepPlayer(player);

        if(sleepPlayer == null) return;

        if(event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockZ() == event.getTo().getBlockZ() && event.getFrom().getBlockY() == event.getTo().getBlockY()) return;

        sleepPlayer.setPosition(event.getTo());

        if(sleepPlayer.isAfk()) {

            Location from = event.getFrom();
            Location to = event.getTo();

            if(to.getY() >= from.getBlockY() + 1) {

                sleepPlayer.updatePlayerMove();

                return;

            }

        }

        Location afk = sleepPlayer.getAfkPosition();
        if(afk == null || afk.distanceSquared(sleepPlayer.getPosition()) > 9) {

            sleepPlayer.updatePlayerMove();

        }
    }
}
