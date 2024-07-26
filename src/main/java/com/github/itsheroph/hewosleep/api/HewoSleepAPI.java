package com.github.itsheroph.hewosleep.api;

import com.github.itsheroph.hewosleep.HewoSleep;
import com.github.itsheroph.hewosleep.commands.*;
import com.github.itsheroph.hewosleep.hooks.PluginHook;
import com.github.itsheroph.hewosleep.listeners.ApiEvents.UserBuffEventsListener;
import com.github.itsheroph.hewosleep.listeners.ApiEvents.WorldEventsListener;
import com.github.itsheroph.hewosleep.listeners.Essentials.StatusChangeEventListener;
import com.github.itsheroph.hewosleep.listeners.Gsit.PlayerPoseEventsListener;
import com.github.itsheroph.hewosleep.listeners.PlayerBedEventsListener;
import com.github.itsheroph.hewosleep.listeners.PlayerChangedWorldEventListener;
import com.github.itsheroph.hewosleep.listeners.PlayerJoinAndLeaveEventListener;
import com.github.itsheroph.hewosleep.listeners.TimeSkipEventListener;
import com.github.itsheroph.hewosleep.models.managers.UserManager;
import com.github.itsheroph.hewosleep.models.managers.WorldManager;
import com.github.itsheroph.hewoutil.messages.HewoMessenger;
import com.github.itsheroph.hewoutil.plugin.command.HewoCMDHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HewoSleepAPI {

    private final HewoSleep plugin;
    private final Map<String, PluginHook> hooksMap;

    private UserManager userManager;
    private WorldManager worldManager;

    public HewoSleepAPI(HewoSleep plugin) {

        this.plugin = plugin;
        this.hooksMap = new HashMap<>();

    }

    public HewoSleep getPlugin() {

        return plugin;

    }

    public Map<String, PluginHook> getHooksMap() {

        return this.hooksMap;

    }

    public List<PluginHook> getAllHooks() {

        return new ArrayList<>(this.getHooksMap().values());

    }

    public List<String> getHookList() {

        return this.getAllHooks().stream().map(PluginHook::getName).collect(Collectors.toList());
    }

    public void addHook(PluginHook hook) {

        this.getHooksMap().put(hook.getName(), hook);

    }

    public void clearHooks() {

        this.getAllHooks().clear();

    }

    public void registerHooks() {

        for(PluginHook hook : this.getAllHooks()) {

            hook.register();

        }
    }

    public HewoMessenger getMessenger() {

        return this.getPlugin().getLangConfig().getMessenger();

    }

    public void initializeManagers() {

        this.worldManager = new WorldManager(this);
        this.userManager = new UserManager(this);

    }

    public void fireEvents(Event... events) {

        for(Event event : events) {

            this.fireEvent(event);

        }

    }

    public void fireEvent(Event event) {

        Bukkit.getPluginManager().callEvent(event);

    }

    public void registerEvents() {

        new PlayerBedEventsListener(this);
        new PlayerChangedWorldEventListener(this);
        new PlayerJoinAndLeaveEventListener(this);
        new TimeSkipEventListener(this);
        new UserBuffEventsListener(this);
        new WorldEventsListener(this);

        if(this.getHookList().contains("Essentials")) {

            new StatusChangeEventListener(this);

        }

        if(this.getHookList().contains("GSit")) {

            new PlayerPoseEventsListener(this);

        }


    }

    public void unregisterEvents() {

        HandlerList.unregisterAll(this.getPlugin());

    }

    public void registerCommands() {

        this.getPlugin().getCommand("hewosleep").setExecutor(
                new HewoCMDHandler(this.getPlugin(),
                        this.getPlugin().getLangConfig().getCmdMessenger(),
                        new BuffCommand(this.getPlugin()),
                        new BypassCommand(this.getPlugin()),
                        new HelpCommand(this.getPlugin()),
                        new ReloadCommand(this.getPlugin()),
                        new SetFlagCommand(this.getPlugin()),
                        new SleepCommand(this.getPlugin()),
                        new VersionCommand(this.getPlugin())
                )
        );

    }

    public UserManager getUserManager() {

        return this.userManager;

    }

    public WorldManager getWorldManager() {

        return this.worldManager;

    }
}
