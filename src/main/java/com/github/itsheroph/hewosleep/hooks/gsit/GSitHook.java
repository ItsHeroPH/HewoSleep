package com.github.itsheroph.hewosleep.hooks.gsit;

import com.github.itsheroph.hewosleep.api.HewoSleepAPI;
import com.github.itsheroph.hewosleep.hooks.PluginHook;

public class GSitHook extends PluginHook {

    public GSitHook(HewoSleepAPI api) {

        super(api);

    }

    @Override
    public String getName() {

        return "GSit";

    }

    @Override
    public void register() {

        try {

            Class.forName("dev.geco.gsit.api.event.PlayerGetUpPoseEvent");
            Class.forName("dev.geco.gsit.api.event.PlayerPoseEvent");
            this.getAPI().getPlugin().getPluginLogger().log("&2&oGSit support is enable!");
            this.setEnable(true);

        } catch (ClassNotFoundException e) {

            this.getAPI().getPlugin().getPluginLogger().error("Your GSit version is incompatible with this version of HewoSleep");
            this.setEnable(false);

        }

    }
}
