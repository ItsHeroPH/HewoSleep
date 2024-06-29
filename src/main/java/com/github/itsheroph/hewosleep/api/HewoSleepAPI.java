package com.github.itsheroph.hewosleep.api;

import com.github.itsheroph.hewosleep.HewoSleep;
import com.github.itsheroph.hewosleep.models.SleepWorldManager;
import com.github.itsheroph.hewoutil.messaging.HewoMessenger;

public class HewoSleepAPI {

    private final HewoSleep plugin;
    private final HewoMessenger messenger;
    private final SleepWorldManager manager;

    private boolean enable, ignoreAfkPlayers;
    private int percentage, bedEnterDelay;

    public HewoSleepAPI(HewoSleep plugin) {

        this.plugin = plugin;
        this.messenger = new HewoMessenger(this.getPlugin(), this.getPlugin().getPluginLogger(), "HewoSleepV1");
        this.manager = new SleepWorldManager(this);

        this.enable = this.getPlugin().getConfig().getBoolean("enable");
        this.percentage = this.getPlugin().getConfig().getInt("percentage");
        this.bedEnterDelay = this.getPlugin().getConfig().getInt("bed_enter_delay");
        this.ignoreAfkPlayers = this.getPlugin().getConfig().getBoolean("ignore_afk_players");


    }

    public HewoSleep getPlugin() {

        return this.plugin;

    }

    public HewoMessenger getMessenger() {

        return this.messenger;

    }

    public SleepWorldManager getManager() {

        return this.manager;

    }

    public boolean isEnable() {

        return this.enable;

    }

    public void setEnable(boolean enable) {

        this.getPlugin().getConfig().set("enable", enable);
        this.enable = enable;
        this.getPlugin().saveConfig();

    }

    public int getPercentage() {

        return this.percentage;

    }

    public void setPercentage(int percentage) {

        this.getPlugin().getConfig().set("percentage", percentage);
        this.percentage = percentage;
        this.getPlugin().saveConfig();

    }

    public int getBedEnterDelay() {

        return this.bedEnterDelay;

    }

    public void setBedEnterDelay(int bedEnterDelay) {

        this.getPlugin().getConfig().set("bed_enter_delay", percentage);
        this.bedEnterDelay = bedEnterDelay;
        this.getPlugin().saveConfig();

    }

    public boolean ignoreAfkPlayers() {

        return this.ignoreAfkPlayers;

    }

    public void setIgnoreAfkPlayers(boolean ignoreAfkPlayers) {

        this.getPlugin().getConfig().set("ignore_afk_players", ignoreAfkPlayers);
        this.ignoreAfkPlayers = ignoreAfkPlayers;
        this.getPlugin().saveConfig();

    }
}
