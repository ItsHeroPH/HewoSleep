package com.github.itsheroph.hewosleep.hooks.placeholderAPI;

import com.github.itsheroph.hewosleep.api.HewoSleepAPI;
import com.github.itsheroph.hewosleep.hooks.PluginHook;

public class PAPIHook extends PluginHook {

    private HewoSleepPAPIExpansion papiExpansion = null;

    public PAPIHook(HewoSleepAPI api) {

        super(api);

    }

    @Override
    public String getName() {

        return "PlaceholderAPI";

    }

    @Override
    public void register() {

        try {

            Class.forName("me.clip.placeholderapi.expansion.PlaceholderExpansion");
            this.papiExpansion = new HewoSleepPAPIExpansion(this.getAPI().getPlugin());
            this.getAPI().getPlugin().getPluginLogger().log("&2&oPlaceholderAPI support is enable!");
            this.getPapiExpansion().register();
            this.setEnable(true);

        } catch (ClassNotFoundException e) {

            this.getAPI().getPlugin().getPluginLogger().error("Your PlaceholderAPI version is incompatible with this version of HewoSleep");
            this.setEnable(false);

        }

    }

    public HewoSleepPAPIExpansion getPapiExpansion() {

        return this.papiExpansion;

    }
}
