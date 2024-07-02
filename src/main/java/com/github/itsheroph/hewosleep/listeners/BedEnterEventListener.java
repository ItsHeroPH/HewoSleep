package com.github.itsheroph.hewosleep.listeners;

import com.github.itsheroph.hewosleep.api.HewoSleepAPI;
import com.github.itsheroph.hewosleep.models.SleepPlayer;
import com.github.itsheroph.hewosleep.models.SleepWorld;
import com.github.itsheroph.hewosleep.models.SleepWorldManager;
import com.github.itsheroph.hewoutil.messaging.HewoMessenger;
import com.github.itsheroph.hewoutil.messaging.HewoMsgEntry;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedEnterEvent.BedEnterResult;

import java.util.ArrayList;
import java.util.List;

public class BedEnterEventListener implements Listener {

    private final SleepWorldManager manager;
    private final List<BedEnterResult> blockedResults;

    public BedEnterEventListener(SleepWorldManager manager) {

        this.manager = manager;
        this.blockedResults = new ArrayList<>() {{
            add(BedEnterResult.NOT_POSSIBLE_NOW);
            add(BedEnterResult.NOT_POSSIBLE_HERE);
            add(BedEnterResult.TOO_FAR_AWAY);
            add(BedEnterResult.OTHER_PROBLEM);
        }};

    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {

        HewoSleepAPI api = this.manager.getAPI();
        HewoMessenger messenger = api.getMessenger();

        if(!api.isEnable()) return;

        Player player = event.getPlayer();
        SleepWorld sleepWorld = this.manager.getSleepWorld(player);

        if(sleepWorld == null) return;

        SleepPlayer sleepPlayer = sleepWorld.getPlayer(player);
        List<Player> playerList = sleepWorld.getAllPlayersInWorld();

        if(sleepPlayer == null) return;

        if(event.getBedEnterResult() == BedEnterResult.NOT_SAFE) {

            if(player.hasPermission("hewosleep.monster")) {

                event.setUseBed(Result.ALLOW);

                messenger.sendMessage(playerList, "bedEnter_NotSafe_Allowed",
                        new HewoMsgEntry("<player>", player.getName())
                );

            } else {

                messenger.sendMessage(player, "bedEnter_NotSafe_NotAllowed",
                        new HewoMsgEntry("<player>", player.getName())
                );

                return;

            }

        }

        if(this.blockedResults.contains(event.getBedEnterResult())) {

            messenger.sendMessage(player, "bedEnter_Failed",
                    new HewoMsgEntry("<player>", player.getName()),
                    new HewoMsgEntry("<reason>", event.getBedEnterResult().name())
            );

            event.setCancelled(true);

            return;

        }

        if(sleepPlayer.calculateRemainingCooldown() > 0) {

            event.setUseBed(Result.DENY);

            messenger.sendMessage(player, "bedEnter_Spam",
                    new HewoMsgEntry("<cooldown>", sleepPlayer.calculateRemainingCooldown() / 1000 + "s")
            );

            return;

        }

        sleepPlayer.setSleeping(true);
        
        messenger.sendMessage(playerList, "bedEnter_Success",
                new HewoMsgEntry("<player>", player.getName())
        );

        if(sleepWorld.getSleepingPlayers().size() >= sleepWorld.getSleepersNeeded()) {

            messenger.sendMessage(playerList, "enough_player_sleeping", true);

        } else {

            messenger.sendMessage(playerList, "not_enough_player_sleeping",
                    new HewoMsgEntry("<sleepers_needed>", sleepWorld.getSleepersNeeded() - sleepWorld.getSleepingPlayers().size())
            );

        }

    }
}
