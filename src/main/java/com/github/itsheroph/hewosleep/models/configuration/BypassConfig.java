package com.github.itsheroph.hewosleep.models.configuration;

import com.github.itsheroph.hewosleep.HewoSleep;
import com.github.itsheroph.hewosleep.models.SleepWorld;
import com.github.itsheroph.hewosleep.util.version.Version;
import com.github.itsheroph.hewoutil.configuration.HewoConfig;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class BypassConfig {

    private final HewoSleep plugin;
    private final SleepWorld world;
    private final HewoConfig config;

    private boolean survivalIgnored, creativeIgnored, adventureIgnored, spectatorIgnored, afkPlayerIgnored, vanishPlayerIgnored;

    public BypassConfig(SleepWorld world) {

        this.plugin = world.getManager().getAPI().getPlugin();
        this.world = world;

        File file = new File(new Version(this.plugin).getDataFolder(),
                File.separator + "world_data" + File.separator + this.getWorld().getBase().getName() + File.separator + "bypass.yml"
        );
        this.config = new HewoConfig(
                this.getWorld().getManager().getAPI().getPlugin(),
                "world/bypass.yml",
                file
        );
        this.getConfig().options().copyDefaults(true);

        this.survivalIgnored = this.getConfig().getBoolean("ignore_survival");
        this.creativeIgnored = this.getConfig().getBoolean("ignore_creative");
        this.adventureIgnored = this.getConfig().getBoolean("ignore_adventure");
        this.spectatorIgnored = this.getConfig().getBoolean("ignore_spectator");
        this.afkPlayerIgnored = this.getConfig().getBoolean("ignore_afk_player");
        this.vanishPlayerIgnored = this.getConfig().getBoolean("ignore_vanish_player");

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

    public boolean isSurvivalIgnored() {

        return this.survivalIgnored;

    }

    public void setIgnoreSurvival(boolean value) {

        this.getConfig().set("ignore_survival", value);
        this.survivalIgnored = value;
        this.saveConfig();

    }

    public boolean isCreativeIgnored() {

        return this.creativeIgnored;

    }

    public void setIgnoreCreative(boolean value) {

        this.getConfig().set("ignore_creative", value);
        this.creativeIgnored = value;
        this.saveConfig();

    }

    public boolean isAdventureIgnored() {

        return this.adventureIgnored;

    }

    public void setIgnoreAdventure(boolean value) {

        this.getConfig().set("ignore_adventure", value);
        this.adventureIgnored = value;
        this.saveConfig();

    }

    public boolean isSpectatorIgnored() {

        return this.spectatorIgnored;

    }

    public void setIgnoreSpectator(boolean value) {

        this.getConfig().set("ignore_spectator", value);
        this.spectatorIgnored = value;
        this.saveConfig();

    }

    public boolean isAfkPlayerIgnored() {

        return this.afkPlayerIgnored;

    }

    public void setIgnoreAfkPlayer(boolean value) {

        this.getConfig().set("ignore_afk_player", value);
        this.afkPlayerIgnored = value;
        this.saveConfig();

    }

    public boolean isVanishPlayerIgnored() {

        return this.vanishPlayerIgnored;

    }

    public void setIgnoreVanishPlayer(boolean value) {

        this.getConfig().set("ignore_vanish_player", value);
        this.vanishPlayerIgnored = value;
        this.saveConfig();

    }
}
