package com.github.itsheroph.hewosleep.models.configuration;

import com.github.itsheroph.hewosleep.HewoSleep;
import com.github.itsheroph.hewosleep.models.SleepWorld;
import com.github.itsheroph.hewosleep.util.ConfigHanlder;
import com.github.itsheroph.hewosleep.util.version.Version;
import com.github.itsheroph.hewoutil.configuration.HewoConfig;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BuffConfig {

    private final HewoSleep plugin;
    private final SleepWorld world;
    private final HewoConfig config;

    private final List<PotionEffect> buffs;
    private final List<PotionEffect> deBuffs;

    public BuffConfig(SleepWorld world) {

        this.plugin = world.getManager().getAPI().getPlugin();
        this.world = world;

        File file = new File(new Version(this.plugin.getDescription().getVersion()).getDataFolder(),
                File.separator + "world_data" + File.separator + this.getWorld().getBase().getName() + File.separator + "buffs.yml"
        );
        this.config = new HewoConfig(plugin, "world/buffs.yml", file);

        this.buffs = this.parsePotionList("buffs");
        this.deBuffs = this.parsePotionList("deBuffs");

    }

    public SleepWorld getWorld() {

        return this.world;

    }

    public YamlConfiguration getConfig() {

        return this.config.getConfig();

    }

    public List<PotionEffect> getBuffsList() {

        return this.buffs;

    }

    public List<PotionEffect> getDeBuffsList() {

        return this.deBuffs;

    }

    public String getToString(List<PotionEffect> potions) {

        List<String> list = new ArrayList<>();
        for(PotionEffect buff : potions) {

            list.add(buff.getType().getName().toLowerCase());

        }

        return list.toString().replace("[", "").replace("]", "");

    }

    private List<PotionEffect> parsePotionList(String selectionPath) {

        List<PotionEffect> potionEffectList = new ArrayList<>();

        if(this.getConfig().isConfigurationSection(selectionPath)) {

            ConfigurationSection selection = this.getConfig().getConfigurationSection(selectionPath);
            if(selection != null) {

                for(String path : selection.getKeys(false)) {

                    int time = this.getConfig().getInt(selectionPath + "." + path + ".time");
                    int level = this.getConfig().getInt(selectionPath + "." + path + ".level");

                    PotionEffectType type = PotionEffectType.getByName(path.toUpperCase());

                    if(type != null) {

                        potionEffectList.add(new PotionEffect(type, 20 * time, level - 1));

                    } else {

                        this.plugin.getPluginLogger().error("&oFaulty Potion Effect: &6&o" + path + "&c&o of duration &6&o" + time + "s &c&o and level &6&o" + level);

                    }

                }

            }

        }

        return potionEffectList;

    }
}
