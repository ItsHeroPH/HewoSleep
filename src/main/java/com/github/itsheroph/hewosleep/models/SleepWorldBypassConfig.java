package com.github.itsheroph.hewosleep.models;

import com.github.itsheroph.hewosleep.HewoSleep;
import com.github.itsheroph.hewosleep.hooks.essentials.EssentialsHook;
import com.github.itsheroph.hewoutil.configuration.HewoConfig;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class SleepWorldBypassConfig {

    private final SleepWorld world;
    private final HewoConfig config;

    private boolean ignore_survival, ignore_creative, ignore_adventure, ignore_spectator, ignore_afk_player, ignore_vanish_player;

    public SleepWorldBypassConfig(HewoSleep plugin, SleepWorld world) {

        this.world = world;
        this.config = new HewoConfig(plugin, "bypass.yml", "world_data" + File.separator + world.getWorld().getName() + File.separator + "bypass.yml");
        this.getConfig().options().copyDefaults(true);
        this.config.saveDefaultConfig();

        this.ignore_survival = this.getConfig().getBoolean("ignore_survival");
        this.ignore_creative = this.getConfig().getBoolean("ignore_creative");
        this.ignore_adventure = this.getConfig().getBoolean("ignore_adventure");
        this.ignore_spectator = this.getConfig().getBoolean("ignore_spectator");
        this.ignore_afk_player = this.getConfig().getBoolean("ignore_afk_player");
        this.ignore_vanish_player = this.getConfig().getBoolean("ignore_vanish_player");

    }

    public SleepWorld getWorld() {

        return this.world;

    }

    public YamlConfiguration getConfig() {

        return this.config.getConfig();

    }

    public void saveConfig() {

        this.config.saveConfig();

    }

    public boolean ignore_survival() {

        return this.ignore_survival;

    }

    public boolean isPlayerIgnored(SleepPlayer player) {

        EssentialsHook essentialsHook = (EssentialsHook) this.getWorld().getManager().getAPI().getHooks("Essentials");
        boolean hasEssentials = essentialsHook != null && essentialsHook.isEnable();

        boolean ignore_survival = this.ignore_survival();
        boolean ignore_creative = this.ignore_creative();
        boolean ignore_adventure = this.ignore_adventure();
        boolean ignore_spectator = this.ignore_spectator();
        boolean ignore_afk_player = this.ignore_afk_player();
        boolean ignore_vanish_player = this.ignore_vanish_player();

        GameMode gamemode = player.getPlayer().getGameMode();

        if(gamemode == GameMode.SURVIVAL && ignore_survival) return true;
        if(gamemode == GameMode.CREATIVE && ignore_creative) return true;
        if(gamemode == GameMode.ADVENTURE && ignore_adventure) return true;
        if(gamemode == GameMode.SPECTATOR && ignore_spectator) return true;
        if(player.isAfk() && ignore_afk_player) return true;
        if(player.isVanished() && ignore_vanish_player && hasEssentials) return true;

        return false;

    }

    public void setIgnore_survival(boolean ignore_survival) {

        this.getConfig().set("ignore_survival", ignore_survival);
        this.ignore_survival = ignore_survival;
        this.saveConfig();

    }

    public boolean ignore_creative() {

        return this.ignore_creative;

    }

    public void setIgnore_creative(boolean ignore_creative) {

        this.getConfig().set("ignore_creative", ignore_creative);
        this.ignore_creative = ignore_creative;
        this.saveConfig();

    }

    public boolean ignore_adventure() {

        return this.ignore_adventure;

    }

    public void setIgnore_adventure(boolean ignore_adventure) {

        this.getConfig().set("ignore_adventure", ignore_adventure);
        this.ignore_adventure = ignore_adventure;
        this.saveConfig();

    }

    public boolean ignore_spectator() {

        return this.ignore_spectator;

    }

    public void setIgnore_spectator(boolean ignore_spectator) {

        this.getConfig().set("ignore_spectator", ignore_spectator);
        this.ignore_spectator = ignore_spectator;
        this.saveConfig();

    }

    public boolean ignore_afk_player() {

        return this.ignore_afk_player;

    }

    public void setIgnore_afk_player(boolean ignore_afk_player) {

        this.getConfig().set("ignore_afk_player", ignore_afk_player);
        this.ignore_afk_player = ignore_afk_player;
        this.saveConfig();

    }

    public boolean ignore_vanish_player() {

        return ignore_vanish_player;

    }

    public void setIgnore_vanish_player(boolean ignore_vanish_player) {

        this.getConfig().set("ignore_vanish_player", ignore_vanish_player);
        this.ignore_vanish_player = ignore_vanish_player;
        this.saveConfig();

    }
}
