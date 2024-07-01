package com.github.itsheroph.hewosleep.listeners;

import com.github.itsheroph.hewosleep.models.SleepPlayer;
import com.github.itsheroph.hewosleep.models.SleepWorldManager;
import net.ess3.api.IUser;
import net.ess3.api.events.VanishStatusChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VanishStatusChangeEventListener implements Listener {

    private final SleepWorldManager manager;

    public VanishStatusChangeEventListener(SleepWorldManager manager) {

        this.manager = manager;

    }

    @EventHandler
    public void onPlayerVanishStatusChange(VanishStatusChangeEvent event) {

        IUser user = event.getAffected();
        SleepPlayer sleepPlayer = this.manager.getSleepPlayer(user.getBase());

        if(sleepPlayer != null) {

            sleepPlayer.setVanished(event.getValue());

        }
    }
}
