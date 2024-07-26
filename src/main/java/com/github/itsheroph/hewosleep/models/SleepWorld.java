package com.github.itsheroph.hewosleep.models;

import com.github.itsheroph.hewosleep.models.configuration.BuffConfig;
import com.github.itsheroph.hewosleep.models.configuration.BypassConfig;
import com.github.itsheroph.hewosleep.models.configuration.WorldConfig;
import com.github.itsheroph.hewosleep.models.managers.WorldManager;
import com.github.itsheroph.hewosleep.runnables.SleepWorldRunnable;
import com.github.itsheroph.hewosleep.util.TimeState;
import com.github.itsheroph.hewosleep.util.TimeUtil;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class SleepWorld {

    private final WorldManager manager;
    private final World base;
    private final SleepWorldRunnable runnable;

    private final WorldConfig worldConfig;
    private final BypassConfig bypassConfig;
    private final BuffConfig buffConfig;

    private TimeState timeState;

    public SleepWorld(WorldManager manager, World world) {

        this.manager = manager;
        this.base = world;
        this.timeState = TimeState.getState(this);

        this.worldConfig = new WorldConfig(this);
        this.bypassConfig = new BypassConfig(this);
        this.buffConfig = new BuffConfig(this);

        this.runnable = new SleepWorldRunnable(this);

        this.getRunnable().runTaskTimer(this.getManager().getAPI().getPlugin(), 1L, 1L);

    }

    public WorldManager getManager() {

        return this.manager;

    }

    public World getBase() {

        return this.base;

    }

    public SleepWorldRunnable getRunnable() {

        return this.runnable;

    }

    public void stopRunnable() {

        if(!this.getRunnable().isCancelled()) {

            this.getRunnable().cancel();

        }

    }

    public WorldConfig getWorldConfig() {

        return this.worldConfig;

    }

    public BypassConfig getBypassConfig() {

        return this.bypassConfig;

    }

    public BuffConfig getBuffConfig() {

        return this.buffConfig;

    }

    public List<SleepUser> getUsers() {

        return this.getManager().getAPI().getUserManager().getUsers()
                .stream().filter(player -> player.getWorld() == this)
                .collect(Collectors.toList());

    }

    public List<SleepUser> getSleepingUsers() {

        return this.getUsers().stream()
                .filter(SleepUser::isSleeping)
                .collect(Collectors.toList());

    }

    public List<SleepUser> getValidUsers() {

        return this.getUsers().stream()
                .filter(user -> !user.isIgnored())
                .collect(Collectors.toList());

    }

    public int getSleepersNeeded() {

        int percentage = this.getWorldConfig().getPercentage();
        int numPlayers = this.getValidUsers().size();

        return Math.max(Math.round((float) (numPlayers * percentage) / 100), 1);

    }


    public List<Player> getPlayers() {

        return this.getUsers().stream()
                .map(SleepUser::getBase)
                .collect(Collectors.toList());

    }

    public void clearWeather() {

        this.getBase().setThundering(false);
        this.getBase().setStorm(false);

    }

    public long getTime() {

        return this.getBase().getTime();

    }

    public void setTime(long time) {

        this.getBase().setTime(time);

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

    public TimeState getTimeState() {

        return this.timeState;

    }

    public void setTimeState(TimeState timeState) {

        this.timeState = timeState;

    }
}
