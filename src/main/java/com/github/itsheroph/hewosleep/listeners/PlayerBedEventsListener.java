package com.github.itsheroph.hewosleep.listeners;

import com.github.itsheroph.hewosleep.api.HewoSleepAPI;
import com.github.itsheroph.hewosleep.api.events.user.UserSleepingStateChangeEvent;
import com.github.itsheroph.hewosleep.api.events.user.UserSleepingStateChangeEvent.Cause;
import com.github.itsheroph.hewosleep.models.SleepUser;
import com.github.itsheroph.hewosleep.models.SleepWorld;
import com.github.itsheroph.hewoutil.messages.HewoMsgEntry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedEnterEvent.BedEnterResult;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerBedEventsListener implements Listener {

    private final HewoSleepAPI api;
    private final List<BedEnterResult> blockedResult = new ArrayList<>() {{

        add(BedEnterResult.NOT_POSSIBLE_NOW);
        add(BedEnterResult.NOT_POSSIBLE_HERE);
        add(BedEnterResult.TOO_FAR_AWAY);
        add(BedEnterResult.OTHER_PROBLEM);

    }};

    public PlayerBedEventsListener(HewoSleepAPI api) {

        this.api = api;

        Bukkit.getPluginManager().registerEvents(this, this.getAPI().getPlugin());

    }

    public HewoSleepAPI getAPI() {

        return this.api;

    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {

        Player player = event.getPlayer();

        SleepUser user = this.getAPI().getUserManager().getUser(player);

        if(user == null) return;

        SleepWorld world = user.getWorld();

        if(!world.getWorldConfig().isEnable()) return;

        if(event.getBedEnterResult() == BedEnterResult.NOT_SAFE) {

            if(user.hasMonsterBypass()) {

                event.setUseBed(Result.ALLOW);
                this.getAPI().getMessenger().sendMessage(world.getPlayers(), "bed_enter_not_safe_allow",
                        new HewoMsgEntry("<user>", player.getName())
                );

            } else {

                this.getAPI().getMessenger().sendMessage(player, "bed_enter_not_safe_deny",
                        new HewoMsgEntry("<user>", player.getName())
                );
                return;

            }

        }

        if(this.blockedResult.contains(event.getBedEnterResult())) {

            this.getAPI().getMessenger().sendMessage(player, "bed_enter_failed",
                    new HewoMsgEntry("<reason>", event.getBedEnterResult().name())
            );
            event.setCancelled(true);

            return;

        }

        if(this.calculateRemainingCooldown(user) > 0) {

            event.setUseBed(Result.DENY);

            this.getAPI().getMessenger().sendMessage(player, "bed_enter_spam",
                    new HewoMsgEntry("<cooldown>", this.calculateRemainingCooldown(user) / 1000 + "s")
            );

            return;

        }

        user.updateLastMoved();
        user.setSleeping(true);
        this.getAPI().fireEvents(new UserSleepingStateChangeEvent(user, true,Cause.BED));

        this.getAPI().getMessenger().sendMessage(world.getPlayers(), "bed_enter_success",
                new HewoMsgEntry("<user>", player.getName())
        );

        if(world.getSleepingUsers().size() >= world.getSleepersNeeded()) {

            this.getAPI().getMessenger().sendMessage(world.getPlayers(), "enough_player_sleeping", true);

        } else {

            this.getAPI().getMessenger().sendMessage(world.getPlayers(), "not_enough_player_sleeping",
                    new HewoMsgEntry("<number_needed>", world.getSleepersNeeded() - world.getSleepingUsers().size())
            );

        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onPlayerBedLeave(PlayerBedLeaveEvent event) {

        Player player = event.getPlayer();

        SleepUser user = this.getAPI().getUserManager().getUser(player);

        if(user == null) return;

        SleepWorld world = user.getWorld();

        if(!world.getWorldConfig().isEnable()) return;

        if(user.isSleeping()) {

            user.updateLastMoved();
            user.setSleeping(false);
            this.getAPI().fireEvents(new UserSleepingStateChangeEvent(user, false, Cause.BED));

        }

        this.getAPI().getMessenger().sendMessage(world.getPlayers(), "bed_leave_message",
                new HewoMsgEntry("<user>", player.getName())
        );

    }

    private long calculateRemainingCooldown(SleepUser user) {

        int cooldown = user.getWorld().getWorldConfig().getBedEnterDelay();
        long currentTime = System.currentTimeMillis();

        return Math.max(0, (cooldown * 1000L) - (currentTime - user.getLastBedEnter()));

    }
}
