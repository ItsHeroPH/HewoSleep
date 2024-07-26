package com.github.itsheroph.hewosleep.hooks;

import com.github.itsheroph.hewosleep.api.HewoSleepAPI;

public abstract class PluginHook {

    private final HewoSleepAPI api;
    private boolean enable;

    public PluginHook(HewoSleepAPI api) {

        this.api = api;

    }

    public HewoSleepAPI getAPI() {

        return api;

    }

    public boolean isEnable() {

        return this.enable;

    }

    public void setEnable(boolean enable) {

        this.enable = enable;

    }

    public abstract String getName();

    public abstract void register();

}
