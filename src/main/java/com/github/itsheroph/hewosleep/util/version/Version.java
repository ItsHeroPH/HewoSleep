package com.github.itsheroph.hewosleep.util.version;

import com.github.itsheroph.hewosleep.HewoSleep;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class Version {

    private int major;
    private int minor;
    private int patch;

    public Version(HewoSleep plugin) {

        this(plugin, plugin.getDescription().getVersion());

    }

    public Version(HewoSleep plugin, String version) {

        String[] parts = version.split("\\.");
        if (parts.length < 1 || parts.length > 3) {

            plugin.getPluginLogger().error("Invalid version format: " + version);

        }

        try {

            this.major = Integer.parseInt(parts[0]);
            this.minor = parts.length > 1 ? Integer.parseInt(parts[1]) : 0;
            this.patch = parts.length > 2 ? Integer.parseInt(parts[2]) : 0;

        } catch (NumberFormatException e) {

            plugin.getPluginLogger().error("Invalid version number: " + version);

        }

    }

    public int getMajor() {

        return this.major;

    }

    public int getMinor() {

        return this.minor;

    }

    public int getPatch() {

        return this.patch;


    }

    public String toString() {

        return this.getMajor() + "." + this.getMinor() + "." + this.getPatch();

    }

    public @NotNull File getDataFolder() {

        if(this.getMajor() == 1) {

            return new File(Bukkit.getPluginsFolder().getPath() + File.separator + "HewoSleep");

        } else if(this.getMajor() == 2) {

            if(this.getMinor() == 0) {

                if(this.getPatch() == 0) return new File(Bukkit.getPluginsFolder().getPath() + File.separator + "HewoSleepV2");

            }

        }

        return new File(Bukkit.getPluginsFolder().getPath() + File.separator + "HewoSleepV2");

    }
}
