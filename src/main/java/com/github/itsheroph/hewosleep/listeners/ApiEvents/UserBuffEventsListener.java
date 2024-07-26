package com.github.itsheroph.hewosleep.listeners.ApiEvents;

import com.github.itsheroph.hewosleep.api.HewoSleepAPI;
import com.github.itsheroph.hewosleep.api.events.user.UserBuffsReceivedEvent;
import com.github.itsheroph.hewosleep.api.events.user.UserDeBuffsReceivedEvent;
import com.github.itsheroph.hewosleep.models.SleepUser;
import com.github.itsheroph.hewosleep.models.SleepWorld;
import com.github.itsheroph.hewoutil.messages.HewoMessenger;
import com.github.itsheroph.hewoutil.messages.HewoMsgEntry;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class UserBuffEventsListener implements Listener {

    private final HewoSleepAPI api;

    public UserBuffEventsListener(HewoSleepAPI api) {

        this.api = api;

        Bukkit.getPluginManager().registerEvents(this, this.getAPI().getPlugin());

    }

    public HewoSleepAPI getAPI() {

        return this.api;

    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onUserReceiveBuff(UserBuffsReceivedEvent event) {

        HewoMessenger messenger = this.getAPI().getMessenger();
        SleepUser user = event.getUser();
        SleepWorld world = user.getWorld();

        user.getBase().addPotionEffects(event.getBuffs());

        messenger.sendMessage(user.getBase(), "buff_received",
                new HewoMsgEntry("<buff_list>", world.getBuffConfig().getToString(event.getBuffs()))
        );

    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onUserReceiveDeBuff(UserDeBuffsReceivedEvent event) {

        HewoMessenger messenger = this.getAPI().getMessenger();
        SleepUser user = event.getUser();
        SleepWorld world = user.getWorld();

        user.getBase().addPotionEffects(event.getDeBuffs());

        messenger.sendMessage(user.getBase(), "debuff_received",
                new HewoMsgEntry("<debuff_list>", world.getBuffConfig().getToString(event.getDeBuffs()))
        );

    }

}
