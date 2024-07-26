package com.github.itsheroph.hewosleep.models;

import com.github.itsheroph.hewosleep.models.configuration.BypassConfig;
import com.github.itsheroph.hewosleep.models.managers.UserManager;
import com.github.itsheroph.hewosleep.runnables.SleepUserRunnable;
import com.github.itsheroph.hewosleep.util.Permissions;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class SleepUser {

    private final UserManager manager;
    private final Player base;
    private final SleepUserRunnable runnable;

    private boolean afk, vanish, sleeping = false;
    private long lastBedEnter = 0;
    private long lastMoved = 0;
    private Location position;
    private Location afkPosition = null;

    public SleepUser(UserManager manager, Player base) {

        this.manager = manager;
        this.base = base;
        this.position = base.getLocation();
        this.runnable = new SleepUserRunnable(this);

        this.getRunnable().runTaskTimer(this.getManager().getAPI().getPlugin(), 1L, 1L);

    }

    public UserManager getManager() {

        return this.manager;

    }

    public Player getBase() {

        return this.base;

    }

    public @NotNull SleepWorld getWorld() {

        return Objects.requireNonNull(this.getManager().getAPI().getWorldManager().getWorld(this.getBase().getWorld()));

    }

    public SleepUserRunnable getRunnable() {

        return this.runnable;

    }

    public void stopRunnable() {

        if(!this.getRunnable().isCancelled()) {

            this.getRunnable().cancel();

        }

    }

    public boolean isAfk() {

        return this.afk;

    }

    public void setAfk(boolean afk) {

        this.afk = afk;

    }

    public boolean isVanish() {

        return this.vanish;

    }

    public void setVanish(boolean vanish) {

        this.vanish = vanish;

    }

    public boolean isSleeping() {

        return this.sleeping;

    }

    public void setSleeping(boolean sleeping) {

        if(sleeping) {

            this.lastBedEnter = System.currentTimeMillis();
            this.sleeping = true;

        } else {

            this.sleeping = false;

        }

    }

    public boolean hasMonsterBypass() {

        return this.getBase().hasPermission(Permissions.MONSTER_BYPASS);

    }

    public boolean isIgnored() {

        SleepWorld world = this.getWorld();
        BypassConfig bypass = world.getBypassConfig();
        boolean hasEssentials = this.getManager().getAPI().getHookList().contains("Essentials");

        if(this.getBase().getGameMode() == GameMode.SURVIVAL && bypass.isSurvivalIgnored()) return true;
        if(this.getBase().getGameMode() == GameMode.CREATIVE && bypass.isCreativeIgnored()) return true;
        if(this.getBase().getGameMode() == GameMode.ADVENTURE && bypass.isAdventureIgnored()) return true;
        if(this.getBase().getGameMode() == GameMode.SPECTATOR && bypass.isSpectatorIgnored()) return true;
        if(this.isAfk() && bypass.isAfkPlayerIgnored()) return true;
        if(this.isVanish() && bypass.isVanishPlayerIgnored() && hasEssentials) return true;

        return false;

    }

    public long getLastBedEnter() {

        return this.lastBedEnter;

    }

    public long getLastMoved() {

        return this.lastMoved;

    }

    public void updateLastMoved() {

        this.lastMoved = System.currentTimeMillis();

    }

    public Location getPosition() {

        return this.position;

    }

    public void setPosition(Location position) {

        this.position = position;

    }

    public @Nullable Location getAfkPosition() {

        return this.afkPosition;

    }

    public void setAfkPosition(Location afkPosition) {

        this.afkPosition = afkPosition;

    }
}
