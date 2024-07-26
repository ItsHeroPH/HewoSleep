package com.github.itsheroph.hewosleep.api.events;

import com.github.itsheroph.hewosleep.api.HewoSleepAPI;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class HewoSleepEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancel = false;

    private final HewoSleepAPI api;

    public HewoSleepEvent(HewoSleepAPI api) {

        this.api = api;

    }

    public HewoSleepAPI getAPI() {

        return api;

    }

    @Override
    public @NotNull HandlerList getHandlers() {

        return handlers;

    }

    public static @NotNull HandlerList getHandlerList() {

        return handlers;

    }

    @Override
    public boolean isCancelled() {

        return this.cancel;

    }

    @Override
    public void setCancelled(boolean cancel) {

        this.cancel = cancel;

    }

}
