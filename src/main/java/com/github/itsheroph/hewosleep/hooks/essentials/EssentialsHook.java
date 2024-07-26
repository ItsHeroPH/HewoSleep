package com.github.itsheroph.hewosleep.hooks.essentials;

import com.github.itsheroph.hewosleep.api.HewoSleepAPI;
import com.github.itsheroph.hewosleep.hooks.PluginHook;

public class EssentialsHook extends PluginHook {

    public EssentialsHook(HewoSleepAPI api) {

        super(api);

    }

    @Override
    public String getName() {

        return "Essentials";

    }

    @Override
    public void register() {

        try {

            Class.forName("net.ess3.api.events.AfkStatusChangeEvent");
            Class.forName("net.ess3.api.events.VanishStatusChangeEvent");
            this.getAPI().getPlugin().getPluginLogger().log("&2&oEssentials support is enable!");
            this.setEnable(true);

        } catch (ClassNotFoundException e) {

            this.getAPI().getPlugin().getPluginLogger().error("Your Essentials version is incompatible with this version of HewoSleep");
            this.setEnable(false);

        }

    }
}
