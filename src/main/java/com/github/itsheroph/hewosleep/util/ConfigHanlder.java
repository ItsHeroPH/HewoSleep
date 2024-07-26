package com.github.itsheroph.hewosleep.util;

import com.github.itsheroph.hewosleep.HewoSleep;
import com.github.itsheroph.hewosleep.util.version.Version;
import com.github.itsheroph.hewoutil.configuration.HewoConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public class ConfigHanlder {

    private final HewoSleep plugin;

    public ConfigHanlder(HewoSleep plugin) {

        this.plugin = plugin;

        if(plugin.getDataFolder().exists()) {

            Instant startTime = Instant.now();
            plugin.getPluginLogger().warning("HewoSleepV1 configuration detected!");
            plugin.getPluginLogger().log("&7&oStarting migrating HewoSleepV1 -> HewoSleepV2");

            File oldFolder = plugin.getDataFolder();
            File newFolder = new Version(plugin.getDescription().getVersion()).getDataFolder();

            if(!newFolder.exists()) {

                newFolder.mkdirs();

            }

            this.handleWorldDataFiles(oldFolder, newFolder);

            if(oldFolder.renameTo(new File(Bukkit.getPluginsFolder(), "HewoSleep_Old"))) {

                plugin.getPluginLogger().warning("Renamed /plugins/HewoSleep to /plugins/HewoSleep_Old...");
                plugin.getPluginLogger().warning("You can now safely delete the old configuration.");

            }

            Duration totalTime = Duration.between(startTime, Instant.now());
            plugin.getPluginLogger().log("&a&oSuccessfully migrated from HewoSleepV1 -> HewoSleepV2. (took " + totalTime.toMillis() + "ms.)");

        }

    }

    private void handleWorldDataFiles(File oldFolder, File newFolder) {

        File file = new File(oldFolder, "world_data");

        if(file.exists()) {

            this.plugin.getPluginLogger().log("&7&oHewoSleep v1.2 world configuration folder detected!");

            if(file.listFiles() == null) {

                this.plugin.getPluginLogger().error("Nothing needs to migrate in world configs.");
                return;

            }

            for(File worldData : Objects.requireNonNull(file.listFiles())) {

                try {

                    this.handleWorldConfigs(worldData, newFolder, "buffs.yml");
                    this.handleWorldConfigs(worldData, newFolder, "bypass.yml");
                    this.handleWorldConfigs(worldData, newFolder, "config.yml");

                } catch (IOException e) {

                    this.plugin.getPluginLogger().error(e.getMessage());

                }

            }

            this.plugin.getPluginLogger().log("&7&oHewoSleep v1.2 world configuration successfully migrated!");

        } else {

            this.plugin.getPluginLogger().error("World configuration is incompatible to newer version. World Config migration failed.");

        }

    }

    private void handleWorldConfigs(File oldFolder, File newFolder, String fileName) throws IOException {

        File file = new File(oldFolder, fileName);
        YamlConfiguration oldConfig = YamlConfiguration.loadConfiguration(file);

        File newFile = new File(newFolder, "world_data" + File.separator + oldFolder.getName() + File.separator + fileName);
        HewoConfig newConfig = new HewoConfig(this.plugin, "world/" + fileName, newFile);

        for(String path : newConfig.getConfig().getKeys(false)) {

            newConfig.getConfig().set(path, oldConfig.get(path));

        }

        newConfig.getConfig().save(newFile);

    }
}
