package com.github.itsheroph.hewosleep.hooks;

import com.github.itsheroph.hewosleep.api.HewoSleepAPI;

public abstract class Hooks {

    private final HewoSleepAPI api;
    private boolean enable;

    public Hooks(HewoSleepAPI api) {

        this.api = api;

    }

    public HewoSleepAPI getAPI() {

        return this.api;

    }

    public abstract String getName();

    public boolean isEnable() {

        return this.enable;

    }

    public void setEnable(boolean enable) {

        this.enable = enable;

    }

    public abstract void register();
}
