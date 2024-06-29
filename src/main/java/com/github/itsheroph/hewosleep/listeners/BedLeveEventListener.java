package com.github.itsheroph.hewosleep.listeners;

import com.github.itsheroph.hewosleep.api.HewoSleepAPI;
import com.github.itsheroph.hewosleep.models.SleepPlayer;
import com.github.itsheroph.hewosleep.models.SleepWorld;
import com.github.itsheroph.hewosleep.models.SleepWorldManager;
import com.github.itsheroph.hewoutil.messaging.HewoMessenger;
import com.github.itsheroph.hewoutil.messaging.HewoMsgEntry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import java.util.List;

public class BedLeveEventListener implements Listener {

    private final SleepWorldManager manager;

    public BedLeveEventListener(SleepWorldManager manager) {

        this.manager = manager;

    }

    @EventHandler
    public void onPlayerWakeUp(PlayerBedLeaveEvent event) {

        HewoSleepAPI api = this.manager.getAPI();
        HewoMessenger messenger = api.getMessenger();

        if(!api.isEnable()) return;

        Player player = event.getPlayer();
        SleepWorld sleepWorld = this.manager.getSleepWorld(player);
        SleepPlayer sleepPlayer = sleepWorld.getPlayer(player);
        List<Player> playerList = sleepWorld.getAllPlayersInWorld();

        sleepPlayer.setSleeping(false);

        messenger.sendMessage(playerList, "bedLeave_message",
                new HewoMsgEntry("<player>", player.getName())
        );

    }
}
