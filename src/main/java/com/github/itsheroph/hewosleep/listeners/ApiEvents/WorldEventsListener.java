package com.github.itsheroph.hewosleep.listeners.ApiEvents;

import com.github.itsheroph.hewosleep.api.HewoSleepAPI;
import com.github.itsheroph.hewosleep.api.events.user.UserBuffsReceivedEvent;
import com.github.itsheroph.hewosleep.api.events.user.UserDeBuffsReceivedEvent;
import com.github.itsheroph.hewosleep.api.events.user.UserSleepingStateChangeEvent.Cause;
import com.github.itsheroph.hewosleep.api.events.world.SleepPossibleNowEvent;
import com.github.itsheroph.hewosleep.api.events.world.SleepPossibleSoonEvent;
import com.github.itsheroph.hewosleep.api.events.world.WorldBecomeMorningEvent;
import com.github.itsheroph.hewosleep.api.events.world.WorldTimeStateChangeEvent;
import com.github.itsheroph.hewosleep.models.SleepUser;
import com.github.itsheroph.hewosleep.models.SleepWorld;
import com.github.itsheroph.hewosleep.util.TimeState;
import com.github.itsheroph.hewosleep.util.TimeUtil;
import com.github.itsheroph.hewoutil.messages.HewoMessenger;
import com.github.itsheroph.hewoutil.messages.HewoMsgEntry;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class WorldEventsListener implements Listener {

    private final HewoSleepAPI api;

    public WorldEventsListener(HewoSleepAPI api) {

        this.api = api;

        Bukkit.getPluginManager().registerEvents(this, this.getAPI().getPlugin());

    }

    public HewoSleepAPI getAPI() {

        return this.api;

    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onWorldBecomeMorning(WorldBecomeMorningEvent event) {

        SleepWorld world = event.getWorld();
        HewoMessenger messenger = this.getAPI().getMessenger();

        for(SleepUser user : world.getUsers()) {

            if(user.isSleeping()) {

                user.setSleeping(false, Cause.NIGHT_SKIPPED);

                List<PotionEffect> buffs = world.getBuffConfig().getBuffsList();

                if(!buffs.isEmpty()) {

                    this.getAPI().fireEvents(new UserBuffsReceivedEvent(user, buffs));

                }

            } else {

                List<PotionEffect> deBuffs = world.getBuffConfig().getDeBuffsList();

                if(!deBuffs.isEmpty()) {

                    if(!user.isIgnored()) {

                        this.getAPI().fireEvents(new UserDeBuffsReceivedEvent(user, deBuffs));

                    }

                }

            }

            messenger.sendMessage(user.getBase(), "morning_message",
                    new HewoMsgEntry("<user>", user.getBase().getName())
            );

        }

    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void canSleepSoon(SleepPossibleSoonEvent event) {

        SleepWorld world = event.getWorld();
        HewoMessenger messenger = this.getAPI().getMessenger();

        messenger.sendMessage(world.getPlayers(), "can_sleep_soon", true);

    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void canSleepNow(SleepPossibleNowEvent event) {

        SleepWorld world = event.getWorld();
        HewoMessenger messenger = this.getAPI().getMessenger();

        messenger.sendMessage(world.getPlayers(), "can_sleep_now", true);

    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onWorldTimeStateChange(WorldTimeStateChangeEvent event) {

        SleepWorld world = event.getWorld();
        TimeState state = event.getNewState();

        switch(state) {

            case CAN_SLEEP_SOON:

                this.getAPI().fireEvents(new SleepPossibleSoonEvent(world));

                break;
            case CAN_SLEEP_NOW:

                this.getAPI().fireEvents(new SleepPossibleNowEvent(world));

                break;
            case CANNOT_SLEEP:

                if(world.getTime() <= TimeUtil.TIME_DAY_TIME || world.getTime() >= TimeUtil.TIME_NIGHT_END) {

                    this.getAPI().fireEvents(new WorldBecomeMorningEvent(world));

                }

                break;

        }
    }
}
