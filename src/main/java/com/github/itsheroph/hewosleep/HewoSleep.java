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

    public HewoSleep() {

        super(new HewoLogger("HewoSleepV1"));

    }

    @Override
    public void onLoad() {

        // Check if the plugin is running on snapshot version
        if(this.getDescription().getVersion().contains("-SNAPSHOT")) {

            this.getPluginLogger().warning(
                    "You are running on a snapshot version of the plugin",
                    "That means the plugin has potentially have a bugs or errors"
            );

        }

        // Load the config.yml file
        this.getPluginLogger().info("Loading configuration file...");
        this.config = new HewoConfig(this, "config.yml");
        this.getConfig().options().copyDefaults(true);
        this.getConfig().options().parseComments(true);
        this.saveDefaultConfig();

    }

    @Override
    public void onEnable() {

        // Load the Hewo Sleep API
        this.getPluginLogger().info("Initializing API...");
        this.api = new HewoSleepAPI(this);

        // Register all the plugin commands
        this.getPluginLogger().info("Registering plugin commands...");
        this.getCommand("hewosleep").setExecutor(new HewoCMDHandler(this,
                new HewoCMDMessenger(this, this.getPluginLogger()),
                    new HelpCommand(this),
                    new ReloadCommand(this),
                    new SetFlagCommand(this),
                    new SleepCommand(this),
                    new VersionCommand(this)
                )
        );

        Bukkit.getLogger().info("-------------------------------------------------------");
        this.getPluginLogger().info(this.getDescription().getName() + "-" + this.getDescription().getVersion());
        this.getPluginLogger().info("Running on Bukkit - Paper v." + this.getDescription().getAPIVersion());
        Bukkit.getLogger().info("-------------------------------------------------------");

    }

    @Override
    public void onDisable() {

        // Unregister all plugin event listener
        HandlerList.unregisterAll(this);

        // Stop all sleep runnables
        this.getAPI().getManager().stopAllSleepRunnables();

        // Save the config.yml
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
