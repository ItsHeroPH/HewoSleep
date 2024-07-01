package com.github.itsheroph.hewosleep;

import com.github.itsheroph.hewosleep.api.HewoSleepAPI;
import com.github.itsheroph.hewosleep.commands.HelpCommand;
import com.github.itsheroph.hewosleep.commands.ReloadCommand;
import com.github.itsheroph.hewosleep.commands.SetFlagCommand;
import com.github.itsheroph.hewosleep.commands.SleepCommand;
import com.github.itsheroph.hewosleep.commands.VersionCommand;
import com.github.itsheroph.hewoutil.configuration.HewoConfig;
import com.github.itsheroph.hewoutil.messaging.commands.HewoCMDMessenger;
import com.github.itsheroph.hewoutil.messaging.logging.HewoLogger;
import com.github.itsheroph.hewoutil.plugin.HewoPlugin;
import com.github.itsheroph.hewoutil.plugin.commands.HewoCMDHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

public class HewoSleep extends HewoPlugin {

    private HewoSleepAPI api;

    @Override
    public void onLoad() {

        this.setPluginLogger(new HewoLogger(this,"HewoSleepV1"));

        // Check if the plugin is running on snapshot version
        if(this.getDescription().getVersion().contains("-SNAPSHOT")) {

            this.getPluginLogger().log(
                    "&eYou are running on a snapshot version of the plugin",
                    "&eThat means the plugin has potentially have a bugs or errors"
            );

        }

        // Load the config.yml file
        this.getPluginLogger().log("&7&oLoading configuration file...");
        this.config = new HewoConfig(this, "config.yml");
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();

        // Load the Hewo Sleep API
        this.getPluginLogger().log("&7&oInitializing API...");
        this.api = new HewoSleepAPI(this);

        boolean hasEssentials = Bukkit.getPluginManager().getPlugin("Essentials") != null;

        // checks if essentials is supported
        if(hasEssentials) {

            this.getAPI().setEssentials(true);
            this.getPluginLogger().log("&7&oEnabling Essentials support");

        }

    }

    @Override
    public void onEnable() {

        // Register sleep world manager
        this.getAPI().registerManager();

        // Register all events
        this.getAPI().registerEvents();

        // Register all the plugin commands
        this.getPluginLogger().log("&7&oRegistering plugin commands...");
        this.getCommand("hewosleep").setExecutor(new HewoCMDHandler(this,
                new HewoCMDMessenger(this, this.getPluginLogger()),
                    new HelpCommand(this),
                    new ReloadCommand(this),
                    new SetFlagCommand(this),
                    new SleepCommand(this),
                    new VersionCommand(this)
                )
        );

        this.getPluginLogger().log(false,
                "",
                "&6HewoSleep &3&lv" + this.getDescription().getVersion(),
                "&7Running on Bukkit - Paper v" + this.getDescription().getAPIVersion(),
                ""
        );

    }

    @Override
    public void onDisable() {

        // Unregister all plugin event listener
        HandlerList.unregisterAll(this);

        // Stop all sleep runnables
        this.getAPI().getManager().stopAllSleepRunnables();

        // Save the config.yml
        this.getPluginLogger().log("&7&oSaving configuration file...");
        this.saveConfig();

        this.config = null;
        this.api = null;

    }

    public void reload() {

        // reload the plugin
        this.onDisable();
        this.onLoad();
        this.onEnable();

    }

    public HewoSleepAPI getAPI() {

        return this.api;

    }
}
