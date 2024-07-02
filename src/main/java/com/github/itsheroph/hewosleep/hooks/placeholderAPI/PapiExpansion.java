package com.github.itsheroph.hewosleep.hooks.placeholderAPI;

import com.github.itsheroph.hewosleep.HewoSleep;
import com.github.itsheroph.hewosleep.models.SleepWorld;
import com.github.itsheroph.hewosleep.models.SleepWorldManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PapiExpansion extends PlaceholderExpansion {

    private final HewoSleep plugin;

    public PapiExpansion(HewoSleep plugin) {

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

        return "ItsHeroPH";

    }

    @Override
    public @NotNull String getVersion() {

        return this.plugin.getDescription().getVersion();

    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String identifier) {

        if(player == null) return null;

        SleepWorldManager manager = this.plugin.getAPI().getManager();
        SleepWorld sleepWorld = manager.getSleepWorld(player);

        if(sleepWorld == null) return null;

        identifier = identifier.toLowerCase();
        switch(identifier) {

            case "number_sleepers":

                return "" + sleepWorld.getSleepingPlayers().size();

            case "sleepers_needed":

                return "" + sleepWorld.getSleepersNeeded();

            case "player_in_world":

                return "" + sleepWorld.getAllPlayers().size();

        }

        return null;

    }
}
