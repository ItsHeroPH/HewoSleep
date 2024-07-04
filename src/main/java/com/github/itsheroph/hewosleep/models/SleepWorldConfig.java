package com.github.itsheroph.hewosleep.models;

import com.github.itsheroph.hewosleep.HewoSleep;
import com.github.itsheroph.hewoutil.configuration.HewoConfig;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class SleepWorldConfig {

    private final SleepWorld world;
    private final HewoConfig config;

    private boolean enable;
    private int percentage, bedEnterDelay, dayLength, nightLength, nightSkipLength;

    public SleepWorldConfig(HewoSleep plugin, SleepWorld world) {
        

        this.world = world;
        this.config = new HewoConfig(plugin, "config.yml", "world_data" + File.separator + world.getWorld().getName() + File.separator + "config.yml");
        this.getConfig().options().copyDefaults(true);
        this.config.saveDefaultConfig();

        this.enable = this.getConfig().getBoolean("enable");
        this.percentage = this.getConfig().getInt("percentage");
        this.bedEnterDelay = this.getConfig().getInt("bed_enter_delay");
        this.dayLength = this.getConfig().getInt("day_length");
        this.nightLength = this.getConfig().getInt("night_length");
        this.nightSkipLength = this.getConfig().getInt("night_skip_length");

    }

    public SleepWorld getWorld() {

        return this.world;

    }

    public YamlConfiguration getConfig() {
        
        return this.config.getConfig();
        
    }

    public void saveConfig() {

        this.config.saveConfig();

    }

    public boolean isEnable() {

        return this.enable;

    }

    public void setEnable(boolean enable) {

        this.getConfig().set("enable", enable);
        this.enable = enable;
        this.saveConfig();

    }

    public int getPercentage() {

        return this.percentage;

    }

    public void setPercentage(int percentage) {

        this.getConfig().set("percentage", percentage);
        this.percentage = percentage;
        this.saveConfig();

    }

    public int getBedEnterDelay() {

        return this.bedEnterDelay;

    }

    public void setBedEnterDelay(int bedEnterDelay) {

        this.getConfig().set("bed_enter_delay", bedEnterDelay);
        this.bedEnterDelay = bedEnterDelay;
        this.saveConfig();

    }

    public int getDayLength() {

        return this.dayLength;

    }

    public void setDayLength(int dayLength) {

        this.getConfig().set("day_length", dayLength);
        this.dayLength = dayLength;
        this.saveConfig();

    }

    public int getNightLength() {

        return this.nightLength;

    }

    public void setNightLength(int nightLength) {

        this.getConfig().set("night_length", nightLength);
        this.nightLength = nightLength;
        this.saveConfig();

    }

    public int getNightSkipLength() {

        return this.nightSkipLength;

    }

    public void setNightSkipLength(int nightSkipLength) {

        this.getConfig().set("night_skip_length", nightSkipLength);
        this.nightSkipLength = nightSkipLength;
        this.saveConfig();

    }

}
