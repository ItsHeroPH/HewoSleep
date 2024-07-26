package com.github.itsheroph.hewosleep.models.managers;

import com.github.itsheroph.hewosleep.api.HewoSleepAPI;
import com.github.itsheroph.hewosleep.models.SleepWorld;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldManager {

    private final HewoSleepAPI API;
    private final Map<World, SleepWorld> worldMap;

    public WorldManager(HewoSleepAPI api) {

        this.API = api;
        this.worldMap = new HashMap<>();

        for(World world : Bukkit.getWorlds()) {

            if(world.getEnvironment() == Environment.NORMAL || world.getEnvironment() == Environment.CUSTOM) {

                SleepWorld sleepWorld = new SleepWorld(this, world);

                this.addWorld(sleepWorld);

            }
        }

    }

    public HewoSleepAPI getAPI() {

        return this.API;

    }

    public Map<World, SleepWorld> getWorldMap() {

        return this.worldMap;

    }

    public void addWorld(SleepWorld world) {

        this.getWorldMap().put(world.getBase(), world);

    }

    public void removeWorld(SleepWorld world) {

        world.stopRunnable();
        world.getWorldConfig().saveConfig();
        world.getBypassConfig().saveConfig();

        this.getWorldMap().remove(world.getBase());

    }

    public void removeAllWorlds() {

        for(SleepWorld world : this.getWorlds()) {

            this.removeWorld(world);

        }

    }

    public List<SleepWorld> getWorlds() {

        return new ArrayList<>(this.getWorldMap().values());

    }

    public @Nullable SleepWorld getWorld(World world) {

        return this.getWorldMap().getOrDefault(world, null);

    }
}
