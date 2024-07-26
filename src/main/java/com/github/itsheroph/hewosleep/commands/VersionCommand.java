package com.github.itsheroph.hewosleep.commands;

import com.github.itsheroph.hewosleep.HewoSleep;
import com.github.itsheroph.hewosleep.util.Permissions;
import com.github.itsheroph.hewoutil.messages.HewoMsgEntry;
import com.github.itsheroph.hewoutil.plugin.command.HewoCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

public class VersionCommand extends HewoCommand {

    private final HewoSleep plugin;

    public VersionCommand(HewoSleep plugin) {

        super(plugin.getLangConfig().getCmdMessenger());

        this.plugin = plugin;

    }
    @Override
    public String getName() {

        return "version";

    }

    @Override
    public List<String> getAliases() {

        return List.of();

    }

    @Override
    public List<String> getOptions(CommandSender sender, String[] strings) {

        return List.of();

    }

    @Override
    public String getPermission() {

        return Permissions.COMMAND_VERSION;

    }

    @Override
    public boolean mayExecute(CommandSender commandSender) {

        return Permissions.canExecuteByAll(commandSender);

    }

    @Override
    public boolean execute(CommandSender commandSender, String[] strings) {

        this.getMessenger().sendMessage(commandSender, "command_version_message",
                new HewoMsgEntry("<version>", "HewoSleep v" + this.plugin.getDescription().getVersion())
        );
        return true;

    }
}
