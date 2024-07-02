package com.github.itsheroph.hewosleep.api;

import com.github.itsheroph.hewosleep.HewoSleep;
import com.github.itsheroph.hewosleep.hooks.EssentialsHook;
import com.github.itsheroph.hewosleep.hooks.Hooks;
import com.github.itsheroph.hewosleep.listeners.AfkStatusChangeEventListener;
import com.github.itsheroph.hewosleep.listeners.BedEnterEventListener;
import com.github.itsheroph.hewosleep.listeners.BedLeveEventListener;
import com.github.itsheroph.hewosleep.listeners.PlayerJoinEventListener;
import com.github.itsheroph.hewosleep.listeners.PlayerLeaveEventListener;
import com.github.itsheroph.hewosleep.listeners.PlayerMoveEventListener;
import com.github.itsheroph.hewosleep.listeners.TimeSkipEventListener;
import com.github.itsheroph.hewosleep.listeners.VanishStatusChangeEventListener;
import com.github.itsheroph.hewosleep.models.SleepWorldManager;
import com.github.itsheroph.hewoutil.messaging.HewoMessenger;
import org.bukkit.Bukkit;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class HewoSleepAPI {

    private final HewoSleep plugin;
    private final HewoMessenger messenger;
    private SleepWorldManager manager;

    private boolean enable, ignoreAfkPlayers, ignoreVanishPlayers;
    private int percentage, bedEnterDelay, nightSkipLength;

    private final Map<String, Hooks> hooksMap = new HashMap<>();

    public HewoSleepAPI(HewoSleep plugin) {

        this.plugin = plugin;
        this.messenger = new HewoMessenger(this.getPlugin(), this.getPlugin().getPluginLogger(), "HewoSleepV1");

        this.enable = this.getPlugin().getConfig().getBoolean("enable");
        this.percentage = this.getPlugin().getConfig().getInt("percentage");
        this.bedEnterDelay = this.getPlugin().getConfig().getInt("bed_enter_delay");
        this.nightSkipLength = this.getPlugin().getConfig().getInt("night_skip_length");
        this.ignoreAfkPlayers = this.getPlugin().getConfig().getBoolean("ignore_afk_players");
        this.ignoreVanishPlayers = this.getPlugin().getConfig().getBoolean("ignore_vanish_players");

    }

    public HewoSleep getPlugin() {

        return this.plugin;

    }

    public HewoMessenger getMessenger() {

        return this.messenger;

    }

    public SleepWorldManager getManager() {

        return this.manager;

    }

    public boolean isEnable() {

        return this.enable;

    }

    public void setEnable(boolean enable) {

        this.getPlugin().getConfig().set("enable", enable);
        this.enable = enable;
        this.getPlugin().saveConfig();

    }

    public int getPercentage() {

        return this.percentage;

    }

    public void setPercentage(int percentage) {

        this.getPlugin().getConfig().set("percentage", percentage);
        this.percentage = percentage;
        this.getPlugin().saveConfig();

    }

    public int getBedEnterDelay() {

        return this.bedEnterDelay;

    }

    public void setBedEnterDelay(int bedEnterDelay) {

        this.getPlugin().getConfig().set("bed_enter_delay", bedEnterDelay);
        this.bedEnterDelay = bedEnterDelay;
        this.getPlugin().saveConfig();

    }

    public int getNightSkipLength() {

        return this.nightSkipLength;

    }

    public void setNightSkipLength(int nightSkipLength) {

        this.getPlugin().getConfig().set("night_skip_length", nightSkipLength);
        this.nightSkipLength = nightSkipLength;
        this.getPlugin().saveConfig();

    }

    public boolean ignoreAfkPlayers() {

        return this.ignoreAfkPlayers;

    }

    public void setIgnoreAfkPlayers(boolean ignoreAfkPlayers) {

        this.getPlugin().getConfig().set("ignore_afk_players", ignoreAfkPlayers);
        this.ignoreAfkPlayers = ignoreAfkPlayers;
        this.getPlugin().saveConfig();

    }

    public boolean ignoreVanishPlayers() {

        return this.ignoreVanishPlayers;

    }

    public void setIgnoreVanishPlayers(boolean ignoreVanishPlayers) {

        this.getPlugin().getConfig().set("ignore_vanish_players", ignoreVanishPlayers);
        this.ignoreVanishPlayers = ignoreVanishPlayers;
        this.getPlugin().saveConfig();

    }

    public void registerManager() {

        this.manager = new SleepWorldManager(this);


    }

    public void registerEvents() {

        Bukkit.getPluginManager().registerEvents(new BedEnterEventListener(this.getManager()), this.getPlugin());
        Bukkit.getPluginManager().registerEvents(new BedLeveEventListener(this.getManager()), this.getPlugin());
        Bukkit.getPluginManager().registerEvents(new PlayerJoinEventListener(this.getManager()), this.getPlugin());
        Bukkit.getPluginManager().registerEvents(new PlayerLeaveEventListener(this.getManager()), this.getPlugin());
        Bukkit.getPluginManager().registerEvents(new PlayerMoveEventListener(this.getManager()), this.getPlugin());
        Bukkit.getPluginManager().registerEvents(new TimeSkipEventListener(this.getManager()), this.getPlugin());

        EssentialsHook essentialsHook = (EssentialsHook) this.getHooks("Essentials");

        if(essentialsHook != null && essentialsHook.isEnable()) {

            Bukkit.getPluginManager().registerEvents(new AfkStatusChangeEventListener(this.getManager()), this.getPlugin());
            Bukkit.getPluginManager().registerEvents(new VanishStatusChangeEventListener(this.getManager()), this.getPlugin());

        }

    }

    public @Nullable Hooks getHooks(String name) {

        return this.hooksMap.getOrDefault(name, null);

    }

    public void addHooks(Hooks hooks) {

        this.hooksMap.put(hooks.getName(), hooks);

    }

    public void registerHooks() {

        for(Hooks hooks : this.hooksMap.values()) {

            hooks.register();

        }
    }

}
