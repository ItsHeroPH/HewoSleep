package com.github.itsheroph.hewosleep.listeners.Essentials;

import com.github.itsheroph.hewosleep.api.HewoSleepAPI;
import com.github.itsheroph.hewosleep.models.SleepUser;
import net.ess3.api.IUser;
import net.ess3.api.events.AfkStatusChangeEvent;
import net.ess3.api.events.VanishStatusChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class StatusChangeEventListener implements Listener {

    private final HewoSleepAPI api;

    public StatusChangeEventListener(HewoSleepAPI api) {

        this.api = api;

        Bukkit.getPluginManager().registerEvents(this, this.getAPI().getPlugin());

    }

    public HewoSleepAPI getAPI() {

        return this.api;

    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onAfkStatusChange(AfkStatusChangeEvent event) {

        IUser EUser = event.getController();
        SleepUser user = this.getAPI().getUserManager().getUser(EUser.getBase());

        if(user == null) return;

        user.setAfk(event.getValue());
        if(event.getValue()) user.setAfkPosition(user.getBase().getLocation());

    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onVanishStatusChange(VanishStatusChangeEvent event) {

        IUser EUser = event.getController();
        SleepUser user = this.getAPI().getUserManager().getUser(EUser.getBase());

        if(user == null) return;

        user.setVanish(event.getValue());

    }
}
