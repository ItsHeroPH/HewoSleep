package com.github.itsheroph.hewosleep.listeners;

import com.github.itsheroph.hewosleep.api.HewoSleepAPI;
import com.github.itsheroph.hewosleep.api.events.world.WorldTimeStateChangeEvent;
import com.github.itsheroph.hewosleep.models.SleepWorld;
import com.github.itsheroph.hewosleep.util.TimeState;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.TimeSkipEvent;

public class TimeSkipEventListener implements Listener {

    private final HewoSleepAPI api;

    public TimeSkipEventListener(HewoSleepAPI api) {

        this.api = api;

        Bukkit.getPluginManager().registerEvents(this, this.getAPI().getPlugin());

    }

    public HewoSleepAPI getAPI() {

        return api;

    }

    @EventHandler(
            priority = EventPriority.LOW
    )
    public void onTimeSkips(TimeSkipEvent event) {

        SleepWorld world = this.getAPI().getWorldManager().getWorld(event.getWorld());

        if(world == null || !world.getWorldConfig().isEnable()) return;

        TimeState nextState = TimeState.getState(world);
        if(world.getTimeState() != nextState) {

            world.setTimeState(nextState);
            this.getAPI().fireEvent(new WorldTimeStateChangeEvent(world, nextState));

        }

    }
}
