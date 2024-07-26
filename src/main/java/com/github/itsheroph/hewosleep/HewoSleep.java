package com.github.itsheroph.hewosleep;

import com.github.itsheroph.hewosleep.api.HewoSleepAPI;
import com.github.itsheroph.hewosleep.hooks.essentials.EssentialsHook;
import com.github.itsheroph.hewosleep.hooks.gsit.GSitHook;
import com.github.itsheroph.hewosleep.hooks.placeholderAPI.PAPIHook;
import com.github.itsheroph.hewosleep.models.configuration.LangConfig;
import com.github.itsheroph.hewosleep.models.configuration.PluginConfig;
import com.github.itsheroph.hewosleep.util.ConfigHanlder;
import com.github.itsheroph.hewosleep.util.UpdateChecker;
import com.github.itsheroph.hewosleep.util.version.Version;
import com.github.itsheroph.hewoutil.gui.HewoMenuManager;
import com.github.itsheroph.hewoutil.messages.logging.HewoLogger;
import com.github.itsheroph.hewoutil.plugin.HewoPlugin;
import com.github.itsheroph.hewoutil.plugin.HewoPluginReloadable;
import org.bukkit.Bukkit;

public class HewoSleep extends HewoPlugin implements HewoPluginReloadable {

    private HewoSleepAPI api;
    private PluginConfig config;
    private LangConfig langConfig;

    public HewoSleep() {

        super(new HewoLogger("HewoSleepV2"));

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

        new ConfigHanlder(this);
        
        // Initialize the API
        this.getPluginLogger().log("&7&oInitializing the API...");
        this.api = new HewoSleepAPI(this);

        this.getPluginLogger().log("&7&oInitializing the Configuration File...");
        this.config = new PluginConfig(this);
        this.langConfig = new LangConfig(this);

        // Check all supported plugin if available
        boolean hasEssentials = Bukkit.getPluginManager().getPlugin("Essentials") != null;
        boolean hasPlaceholderAPI = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
        boolean hasGSit = Bukkit.getPluginManager().getPlugin("GSit") != null;

        if(hasEssentials && this.getPluginConfig().isEssentialsSupported()) {

            this.getAPI().addHook(
                    new EssentialsHook(this.getAPI())
            );
            this.getPluginLogger().log("&7&oInitializing Essentials support");

        }

        if(hasPlaceholderAPI) {

            this.getAPI().addHook(
                    new PAPIHook(this.getAPI())
            );
            this.getPluginLogger().log("&7&oInitializing PlaceholderAPI support");

        }

        if(hasGSit && this.getPluginConfig().isGSitSupported()) {

            this.getAPI().addHook(
                    new GSitHook(this.getAPI())
            );
            this.getPluginLogger().log("&7&oInitializing GSit support");

        }

    }

    @Override
    public void onEnable() {

        this.getAPI().initializeManagers();

        HewoMenuManager.setup(this);

        this.getAPI().registerHooks();

        this.getAPI().registerEvents();

        this.getAPI().registerCommands();

        this.getPluginLogger().log(false,
                "",
                "&6HewoSleep &3&lv" + this.getDescription().getVersion(),
                "&7Running on Bukkit " + Bukkit.getBukkitVersion().split("-")[0],
                ""
        );

        UpdateChecker update = new UpdateChecker(this);

        if(!update.isLatestVersion() && this.getPluginConfig().isCheckUpdateEnable()) {

            Version latestVersion = update.parseLatestVersion();

            this.getPluginLogger().log(false,
                    "&3================================================",
                    "&6HewoSleep &av" + latestVersion.getMajor() + "." + latestVersion.getMinor() + "." + latestVersion.getPatch() + " &6is now out!",
                    "&6Download the latest version:",
                    "&a" + UpdateChecker.RELEASES_URL,
                    "&3================================================"
            );

        }

    }

    @Override
    public void onDisable() {

        HewoMenuManager.disable();

        if(this.getAPI() != null) {

            this.getAPI().unregisterEvents();
            this.getAPI().clearHooks();

            if(this.getAPI().getUserManager() != null) this.getAPI().getUserManager().removeAllUser();
            if(this.getAPI().getWorldManager() != null) this.getAPI().getWorldManager().removeAllWorlds();

            this.api = null;

        }

        if(this.getPluginConfig() != null) {

            this.config = null;

        }

        if(this.getLangConfig() != null) {

            this.langConfig = null;

        }

    }

    @Override
    public void reload() {

        this.onDisable();
        this.onLoad();
        this.onEnable();

    }

    public HewoSleepAPI getAPI() {

        return this.api;

    }

    public PluginConfig getPluginConfig() {

        return this.config;

    }

    public LangConfig getLangConfig() {

        return this.langConfig;

    }
}
