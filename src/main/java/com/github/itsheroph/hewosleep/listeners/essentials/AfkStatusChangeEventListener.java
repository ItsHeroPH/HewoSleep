package com.github.itsheroph.hewosleep.listeners.essentials;

import com.github.itsheroph.hewosleep.models.SleepPlayer;
import com.github.itsheroph.hewosleep.models.SleepWorldManager;
import net.ess3.api.IUser;
import net.ess3.api.events.AfkStatusChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class AfkStatusChangeEventListener implements Listener {

    private final SleepWorldManager manager;

    public AfkStatusChangeEventListener(SleepWorldManager manager) {

        this.manager = manager;

    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onPlayerAfkStatusChange(AfkStatusChangeEvent event) {

        IUser user = event.getController();
        SleepPlayer sleepPlayer = this.manager.getSleepPlayer(user.getBase());

        if(sleepPlayer != null) {

            sleepPlayer.setAfk(event.getValue());
            if(event.getValue()) sleepPlayer.setAfkPosition(user.getBase().getLocation());

        }

    }
}