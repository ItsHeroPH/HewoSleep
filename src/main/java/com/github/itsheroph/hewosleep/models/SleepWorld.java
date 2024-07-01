package com.github.itsheroph.hewosleep.models;

import com.github.itsheroph.hewosleep.hooks.EssentialsHook;
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

    private final HashMap<Player, SleepPlayer> players;
    private TimeState timeState;

    public SleepWorld(SleepWorldManager manager, World world) {

        this.manager = manager;
        this.world = world;
        this.runnable = new SleepWorldRunnable(manager.getAPI(), this, manager);

        this.players = new HashMap<>();
        this.timeState = TimeState.getState(this);

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

        if(time >= TimeUtil.TIME_NIGHT_END) {

            nightSkipped = true;

        }

        this.setTime(time);

        return nightSkipped;

    }

    public HashMap<Player, SleepPlayer> getAllPlayers() {

        return this.players;

    }

    public List<SleepPlayer> getAFKPlayers() {

        return this.getAllPlayers().values().stream()
                .filter(this::isPlayerAFK)
                .collect(Collectors.toList());

    }

    public List<SleepPlayer> getVanishedPlayers() {

        return this.getAllPlayers().values().stream()
                .filter(this::isPlayerVanished)
                .collect(Collectors.toList());

    }

    public List<SleepPlayer> getSleepingPlayers() {

        return this.getAllPlayers().values().stream()
                .filter(this::isPlayerSleeping)
                .collect(Collectors.toList());

    }

    public List<Player> getAllPlayersInWorld() {

        return this.getAllPlayers().keySet().stream()
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

        EssentialsHook essentialsHook = (EssentialsHook) this.getManager().getAPI().getHooks("Essentials");
        boolean ignoreAFKPlayers = this.getManager().getAPI().ignoreAfkPlayers();
        boolean ignoreVanishPlayers = this.getManager().getAPI().ignoreVanishPlayers();
        boolean hasEssentials = essentialsHook != null && essentialsHook.isEnable();

        int percentage = this.getManager().getAPI().getPercentage();
        int numAfkPlayers = this.getAFKPlayers().size();
        int numVanishPlayers = this.getVanishedPlayers().size();
        int numPlayers = this.getAllPlayers().size();

        if(ignoreAFKPlayers) numPlayers = numPlayers - numAfkPlayers;
        if(ignoreVanishPlayers && hasEssentials) numPlayers = numPlayers - numVanishPlayers;

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
