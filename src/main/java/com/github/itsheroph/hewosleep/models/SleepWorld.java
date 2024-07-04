package com.github.itsheroph.hewosleep.models;

import com.github.itsheroph.hewosleep.runnables.SleepWorldRunnable;
import com.github.itsheroph.hewosleep.util.TimeState;
import com.github.itsheroph.hewosleep.util.TimeUtil;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class SleepWorld {

    private final SleepWorldManager manager;
    private final World world;
    private final SleepWorldRunnable runnable;

    private final SleepWorldConfig config;
    private final SleepWorldBuffConfig buffConfig;
    private final SleepWorldBypassConfig bypassConfig;

    private final HashMap<Player, SleepPlayer> players;
    private TimeState timeState;

    public SleepWorld(SleepWorldManager manager, World world) {

        this.manager = manager;
        this.world = world;
        this.runnable = new SleepWorldRunnable(manager.getAPI(), this, manager);
        this.config = new SleepWorldConfig(this.getManager().getAPI().getPlugin(), this);
        this.buffConfig = new SleepWorldBuffConfig(this.getManager().getAPI().getPlugin(), this);
        this.bypassConfig = new SleepWorldBypassConfig(this.getManager().getAPI().getPlugin(), this);

        this.players = new HashMap<>();
        this.timeState = TimeState.getState(this);

        for(Player player : this.getAllPlayersInWorld()) {

            this.addPlayer(player);

        }

        this.runnable.runTaskTimer(manager.getAPI().getPlugin(), 1L, 1L);

    }

    public SleepWorldManager getManager() {

        return this.manager;

    }

    public SleepWorldRunnable getRunnable() {

        return this.runnable;

    }

    public void stopRunnable() {

        if(!this.getRunnable().isCancelled()) {

            this.getRunnable().cancel();

        }
    }

    public World getWorld() {

        return this.world;

    }

    public SleepWorldConfig getConfig() {

        return this.config;

    }

    public SleepWorldBuffConfig getBuffConfig() {

        return this.buffConfig;

    }

    public SleepWorldBypassConfig getBypassConfig() {

        return this.bypassConfig;

    }

    public void clearWeather() {

        this.getWorld().setThundering(false);
        this.getWorld().setStorm(false);

    }

    public long getTime() {

        return this.getWorld().getTime();

    }

    public void setTime(long time) {

        this.getWorld().setTime(time);

    }

    public boolean addTime(double deltaTicks) {

        assert deltaTicks >= 0;

        boolean nightSkipped = false;

        long time = (long) (this.getTime() + deltaTicks);

        if(time >= TimeUtil.TIME_NIGHT_END + 500) {

            nightSkipped = true;

        }

        this.setTime(time);

        return nightSkipped;

    }

    public HashMap<Player, SleepPlayer> getAllPlayers() {

        return this.players;

    }

    public List<SleepPlayer> getSleepingPlayers() {

        return this.getAllPlayers().values().stream()
                .filter(this::isPlayerSleeping)
                .collect(Collectors.toList());

    }

    public List<Player> getAllPlayersInWorld() {

        return this.getWorld().getPlayers().stream()
                .filter(this::isInValidEnvironment)
                .collect(Collectors.toList());

    }

    public SleepPlayer getPlayer(Player player) {

        return this.getAllPlayers().getOrDefault(player, null);

    }

    public void addPlayer(Player player) {

        this.getAllPlayers().put(player, new SleepPlayer(this, player));

    }

    public void removePlayer(Player player) {

        if(this.getPlayer(player) != null) {

            this.getPlayer(player).stopRunnable();

        }

        this.getAllPlayers().remove(player);

    }

    public int getSleepersNeeded() {

        int percentage = this.getConfig().getPercentage();
        int numPlayersTotal = this.getAllPlayers().size();
        int numPlayersIgnore = this.getAllPlayers().values().stream()
                .filter(this.getBypassConfig()::isPlayerIgnored)
                .collect(Collectors.toList()).size();

        int numPlayers = numPlayersTotal - numPlayersIgnore;

        return Math.max(Math.round((float) (numPlayers * percentage) / 100), 1);

    }

    public TimeState getTimeState() {

        return this.timeState;

    }

    public void setTimeState(TimeState timeState) {

        this.timeState = timeState;

    }

    private boolean isInValidEnvironment(Player player) {

        return player.getWorld().getEnvironment() == World.Environment.NORMAL;

    }

    private boolean isPlayerAFK(SleepPlayer player) {

        return player.isAfk();

    }

    private boolean isPlayerVanished(SleepPlayer player) {

        return player.isVanished();

    }

    private boolean isPlayerSleeping(SleepPlayer player) {

        return player.isSleeping();

    }
}
