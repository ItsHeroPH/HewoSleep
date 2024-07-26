package com.github.itsheroph.hewosleep.util.version;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class Version {

    private final String version;


    public Version(String version) {

        this.version = version;

    }

    public int getMajor() {

        return Integer.parseInt(this.version.split("\\.")[0]);

    }

    public int getMinor() {

        return Integer.parseInt(this.version.split("\\.")[1]);

    }

    public int getPatch() {

        return this.version.split("\\.").length > 2 ? Integer.parseInt(this.version.split("\\.")[2]) : 0;


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
