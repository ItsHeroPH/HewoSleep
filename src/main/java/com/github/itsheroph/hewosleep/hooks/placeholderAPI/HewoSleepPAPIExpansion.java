package com.github.itsheroph.hewosleep.hooks.placeholderAPI;

import com.github.itsheroph.hewosleep.HewoSleep;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.jetbrains.annotations.NotNull;

public class HewoSleepPAPIExpansion extends PlaceholderExpansion {

    private final HewoSleep plugin;

    public HewoSleepPAPIExpansion(HewoSleep plugin) {

        this.plugin = plugin;


    }

    @Override
    public boolean canRegister() {

        return true;

    }

    @Override
    public boolean persist() {

        return true;

    }

    @Override
    public @NotNull String getIdentifier() {

        return "hewosleep";

    }

    @Override
    public @NotNull String getAuthor() {

        return this.plugin.getDescription().getAuthors().toString();

    }

    @Override
    public @NotNull String getVersion() {

        return this.plugin.getDescription().getVersion();

    }

}
