package com.github.itsheroph.hewosleep.api;

import com.github.itsheroph.hewosleep.HewoSleep;
import com.github.itsheroph.hewosleep.hooks.essentials.EssentialsHook;
import com.github.itsheroph.hewosleep.hooks.Hooks;
import com.github.itsheroph.hewosleep.listeners.essentials.AfkStatusChangeEventListener;
import com.github.itsheroph.hewosleep.listeners.BedEnterEventListener;
import com.github.itsheroph.hewosleep.listeners.BedLeveEventListener;
import com.github.itsheroph.hewosleep.listeners.PlayerJoinEventListener;
import com.github.itsheroph.hewosleep.listeners.PlayerLeaveEventListener;
import com.github.itsheroph.hewosleep.listeners.PlayerMoveEventListener;
import com.github.itsheroph.hewosleep.listeners.TimeSkipEventListener;
import com.github.itsheroph.hewosleep.listeners.essentials.VanishStatusChangeEventListener;
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

    private final Map<String, Hooks> hooksMap = new HashMap<>();

    public HewoSleepAPI(HewoSleep plugin) {

        this.plugin = plugin;
        this.messenger = new HewoMessenger(this.getPlugin(), this.getPlugin().getPluginLogger(), "HewoSleepV1");

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
