package com.github.itsheroph.hewosleep.models.configuration;

import com.github.itsheroph.hewosleep.HewoSleep;
import com.github.itsheroph.hewosleep.models.SleepWorld;
import com.github.itsheroph.hewosleep.util.version.Version;
import com.github.itsheroph.hewoutil.configuration.HewoConfig;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class WorldConfig {

    private final HewoSleep plugin;
    private final SleepWorld world;
    private final HewoConfig config;

    private boolean enable;
    private int percentage, bedEnterDelay, dayDuration, nightDuration, nightSkippingDuration;

    public WorldConfig(SleepWorld world) {

        this.plugin = world.getManager().getAPI().getPlugin();
        this.world = world;

        File file = new File(new Version(this.plugin).getDataFolder(),
                "world_data" + File.separator + this.getWorld().getBase().getName() + File.separator + "config.yml"
        );
        this.config = new HewoConfig(world.getManager().getAPI().getPlugin(),
                "world/config.yml", file
        );
        this.getConfig().options().copyDefaults(true);

        this.enable = this.getConfig().getBoolean("enable");
        this.percentage = this.getConfig().getInt("percentage");
        this.bedEnterDelay = this.getConfig().getInt("bed_enter_delay");
        this.dayDuration = this.getConfig().getInt("day_length");
        this.nightDuration = this.getConfig().getInt("night_length");
        this.nightSkippingDuration = this.getConfig().getInt("night_skip_length");

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

    public int getDayDuration() {

        return this.dayDuration;

    }

    public void setDayDuration(int dayDuration) {

        this.getConfig().set("day_length", dayDuration);
        this.dayDuration = dayDuration;
        this.saveConfig();

    }

    public int getNightDuration() {

        return this.nightDuration;

    }

    public void setNightDuration(int nightDuration) {

        this.getConfig().set("night_length", nightDuration);
        this.nightDuration = nightDuration;
        this.saveConfig();

    }

    public int getNightSkippingDuration() {

        return this.nightSkippingDuration;

    }

    public void setNightSkippingDuration(int nightSkippingDuration) {

        this.getConfig().set("night_skip_length", nightSkippingDuration);
        this.nightSkippingDuration = nightSkippingDuration;
        this.saveConfig();

    }
}
