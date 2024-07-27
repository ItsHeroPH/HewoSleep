package com.github.itsheroph.hewosleep.hooks.placeholderAPI;

import com.github.itsheroph.hewosleep.HewoSleep;
import com.github.itsheroph.hewosleep.models.SleepUser;
import com.github.itsheroph.hewosleep.models.SleepWorld;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String identifier) {

        if(player == null) return null;

        SleepUser user = this.plugin.getAPI().getUserManager().getUser(player);

        if(user == null) return null;

        SleepWorld world = user.getWorld();

        identifier = identifier.toLowerCase();
        switch(identifier) {

            case "number_sleepers":

                return "" + world.getSleepingUsers().size();

            case "sleepers_needed":

                return "" + world.getSleepersNeeded();

            case "players_in_world":

                return "" + world.getUsers().size();

            case "buffs":

                return world.getBuffConfig().getToString(world.getBuffConfig().getBuffsList());

            case "debuffs":

                return world.getBuffConfig().getToString(world.getBuffConfig().getDeBuffsList());

        }

        return null;

    }
}
