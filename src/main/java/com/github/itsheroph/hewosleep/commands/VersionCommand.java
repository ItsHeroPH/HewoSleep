package com.github.itsheroph.hewosleep.commands;

import com.github.itsheroph.hewosleep.HewoSleep;
import com.github.itsheroph.hewoutil.messaging.HewoMsgEntry;
import com.github.itsheroph.hewoutil.messaging.commands.HewoCMDMessenger;
import com.github.itsheroph.hewoutil.plugin.commands.HewoCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class VersionCommand extends HewoCommand {

    private final HewoSleep plugin;

    public VersionCommand(HewoSleep plugin) {

        super(new HewoCMDMessenger(plugin, plugin.getPluginLogger(), "HewoSleepV1"));

        this.plugin = plugin;

    }

    @Override
    public String getName() {

        return "version";

    }

    @Override
    public List<String> getAliases() {

        return List.of("ver");

    }

    @Override
    public List<String> getOptions() {

        return List.of();

    }

    @Override
    public String getPermission() {

        return "hewosleep.command.version";

    }

    @Override
    public boolean mayExecute(CommandSender commandSender) {

        return (commandSender instanceof Player || commandSender instanceof ConsoleCommandSender);

    }

    @Override
    public boolean execute(CommandSender commandSender, String[] strings) {

        this.getMessenger().sendMessage(commandSender, "command_version",
                new HewoMsgEntry("<version>", this.plugin.getDescription().getVersion())
        );

        return true;
    }
}
