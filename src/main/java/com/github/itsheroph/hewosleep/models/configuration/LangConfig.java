package com.github.itsheroph.hewosleep.models.configuration;

import com.github.itsheroph.hewosleep.HewoSleep;
import com.github.itsheroph.hewosleep.util.version.Version;
import com.github.itsheroph.hewoutil.configuration.HewoLang;
import com.github.itsheroph.hewoutil.messages.HewoMessenger;
import com.github.itsheroph.hewoutil.messages.command.HewoCMDMessenger;

import java.io.File;
import java.util.List;

public class LangConfig {

    private final List<String> supportedLang = List.of("en-US", "fil-PH");

    private final HewoMessenger messenger;
    private final HewoCMDMessenger cmdMessenger;

    public LangConfig(HewoSleep plugin) {

        String lang;
        if(this.supportedLang.contains(plugin.getPluginConfig().getLanguage())) {

            lang = plugin.getPluginConfig().getLanguage();

        } else {

            lang = "en-US";

        }

        File file = new File(new Version(plugin).getDataFolder(), "lang.yml");
        HewoLang config = new HewoLang(plugin, "language/" + lang + ".yml", file.getPath());

        this.messenger = new HewoMessenger(plugin, plugin.getPluginLogger(), "HS2", config.getMessages());
        this.cmdMessenger = new HewoCMDMessenger(plugin, plugin.getPluginLogger(), "HS2", config.getMessages());

    }

    public HewoMessenger getMessenger() {

        return this.messenger;

    }

    public HewoCMDMessenger getCmdMessenger() {

        return this.cmdMessenger;

    }
}
