package com.github.itsheroph.hewosleep.models.configuration;

import com.github.itsheroph.hewosleep.HewoSleep;
import com.github.itsheroph.hewosleep.util.version.Version;
import com.github.itsheroph.hewoutil.configuration.HewoConfig;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class PluginConfig {

    private final HewoSleep plugin;
    private final HewoConfig config;

    private final String language;
    private final boolean checkUpdateEnable;
    private final boolean EssentialsSupported;
    private final boolean GSitSupported;

    public PluginConfig(HewoSleep plugin) {

        this.plugin = plugin;
        File file = new File(new Version(this.plugin).getDataFolder(), "config.yml");
        this.config = new HewoConfig(plugin, "config.yml", file);
        this.getConfig().options().copyDefaults(true);

        this.language = this.getConfig().getString("language");
        this.checkUpdateEnable = this.getConfig().getBoolean("check_updates");
        this.EssentialsSupported = this.getConfig().getBoolean("essentials_support");
        this.GSitSupported = this.getConfig().getBoolean("gsit_support");

    }

    public HewoSleep getPlugin() {

        return this.plugin;

    }

    public YamlConfiguration getConfig() {

        return this.config.getConfig();

    }

    public String getLanguage() {

        return this.language;

    }

    public boolean isCheckUpdateEnable() {

        return this.checkUpdateEnable;

    }

    public boolean isEssentialsSupported() {

        return this.EssentialsSupported;

    }

    public boolean isGSitSupported() {

        return this.GSitSupported;

    }
}
