package com.github.itsheroph.hewosleep.hooks;

import com.github.itsheroph.hewosleep.api.HewoSleepAPI;
import com.github.itsheroph.hewosleep.hooks.placeholderAPI.PapiExpansion;


public class PlaceholderAPIHook extends Hooks {

    private PapiExpansion papiExpansion = null;

    public PlaceholderAPIHook(HewoSleepAPI api) {

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
            this.papiExpansion = new PapiExpansion(this.getAPI().getPlugin());
            this.getAPI().getPlugin().getPluginLogger().log("&2&oPlaceholderAPI support is enable!");
            this.getPapiExpansion().register();
            this.setEnable(true);

        } catch (ClassNotFoundException e) {

            this.getAPI().getPlugin().getPluginLogger().log("&c&oYour PlaceholderAPI version is incompatible with this version of HewoSleep");
            this.setEnable(false);

        }

    }

    public PapiExpansion getPapiExpansion() {

        return this.papiExpansion;

    }
}
