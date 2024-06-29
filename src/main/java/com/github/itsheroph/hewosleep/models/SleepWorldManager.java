package com.github.itsheroph.hewosleep.models;

import com.github.itsheroph.hewosleep.api.HewoSleepAPI;
import com.github.itsheroph.hewosleep.listeners.BedEnterEventListener;
import com.github.itsheroph.hewosleep.listeners.BedLeveEventListener;
import com.github.itsheroph.hewosleep.listeners.PlayerJoinEventListener;
import com.github.itsheroph.hewosleep.listeners.PlayerLeaveEventListener;
import com.github.itsheroph.hewosleep.listeners.TimeSkipEventListener;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SleepWorldManager {

    private final HewoSleepAPI api;
    private final HashMap<World, SleepWorld> sleepWorldMap;

    public SleepWorldManager(HewoSleepAPI api) {

        this.api = api;
        this.sleepWorldMap = new HashMap<>();

        for(World world : Bukkit.getWorlds()) {

            if(world.getEnvironment() == Environment.NORMAL) {

                SleepWorld sleepWorld = new SleepWorld(this, world);

                this.getSleepWorldMap().put(world, sleepWorld);

            }

        }

        Bukkit.getPluginManager().registerEvents(new BedEnterEventListener(this), this.getAPI().getPlugin());
        Bukkit.getPluginManager().registerEvents(new BedLeveEventListener(this), this.getAPI().getPlugin());
        Bukkit.getPluginManager().registerEvents(new PlayerJoinEventListener(this), this.getAPI().getPlugin());
        Bukkit.getPluginManager().registerEvents(new PlayerLeaveEventListener(this), this.getAPI().getPlugin());
        Bukkit.getPluginManager().registerEvents(new TimeSkipEventListener(this), this.getAPI().getPlugin());

    }

    public HewoSleepAPI getAPI() {

        return this.api;

    }

    public HashMap<World, SleepWorld> getSleepWorldMap() {

        return this.sleepWorldMap;

    }


    public void stopAllSleepRunnables() {

        for(SleepWorld world : this.getSleepWorldMap().values()) {

            world.stopRunnable();

            for(SleepPlayer player : world.getAllPlayers().values()) {

                player.stopRunnable();

            }

        }

    }

    public SleepWorld getSleepWorld(Player player) {

        return this.getSleepWorld(player.getWorld());

    }

    public SleepWorld getSleepWorld(World world) {

        return this.getSleepWorldMap().getOrDefault(world, null);


    }

    public SleepPlayer getSleepPlayer(Player player) {

        SleepWorld world = this.getSleepWorld(player);

        if(world != null) {

            return world.getPlayer(player);

        }

        return null;

    }
}
