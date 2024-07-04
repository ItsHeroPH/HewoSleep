package com.github.itsheroph.hewosleep.commands;

import com.github.itsheroph.hewosleep.HewoSleep;
import com.github.itsheroph.hewoutil.messaging.commands.HewoCMDMessenger;
import com.github.itsheroph.hewoutil.plugin.commands.HewoCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class HelpCommand extends HewoCommand {

    public HelpCommand(HewoSleep plugin) {

        super(new HewoCMDMessenger(plugin, plugin.getPluginLogger(), "HewoSleepV1"));

    }

    @Override
    public String getName() {

        return "help";

    }

    @Override
    public List<String> getAliases() {

        return List.of("h");

    }

    @Override
    public List<String> getOptions() {

        return List.of(
                "buff",
                "bypass",
                "help",
                "reload",
                "setflag",
                "sleep",
                "version"
        );

    }

    @Override
    public String getPermission() {

        return "hewosleep.command.help";

    }

    @Override
    public boolean mayExecute(CommandSender commandSender) {

        return (commandSender instanceof Player || commandSender instanceof ConsoleCommandSender);

    }

    @Override
    public boolean execute(CommandSender commandSender, String[] arguments) {

        if(arguments.length <= 1) {

            this.getMessenger().sendMessage(commandSender, "command_help_noArgs_header", false);
            this.getMessenger().sendMessage(commandSender, "command_help_noArgs_buff", false);
            this.getMessenger().sendMessage(commandSender, "command_help_noArgs_bypass", false);
            this.getMessenger().sendMessage(commandSender, "command_help_noArgs_help", false);
            this.getMessenger().sendMessage(commandSender, "command_help_noArgs_reload", false);
            this.getMessenger().sendMessage(commandSender, "command_help_noArgs_setflag", false);
            this.getMessenger().sendMessage(commandSender, "command_help_noArgs_sleep", false);
            this.getMessenger().sendMessage(commandSender, "command_help_noArgs_version", false);
            this.getMessenger().sendMessage(commandSender, "command_help_noArgs_footer", false);

            return true;

        }

        switch(arguments[1]) {
            case "buff":

                this.getMessenger().sendMessage(commandSender, "command_buff_usage", true);

                return true;
            case "bypass":

                this.getMessenger().sendMessage(commandSender, "command_bypass_usage", true);

                return true;
            case "help":

                this.getMessenger().sendMessage(commandSender, "command_help_usage", true);

                return true;
            case "reload":

                this.getMessenger().sendMessage(commandSender, "command_reload_usage", true);

                return true;
            case "setflag":

                this.getMessenger().sendMessage(commandSender, "command_setflag_usage", true);

                return true;
            case "sleep":

                this.getMessenger().sendMessage(commandSender, "command_sleep_usage", true);

                return true;
            case "version":

                this.getMessenger().sendMessage(commandSender, "command_version_usage", true);

                return true;

        }

        this.getMessenger().sendMessage(commandSender, "command_unknown", true);
        this.getMessenger().sendMessage(commandSender, "command_help_usage", true);

        return true;
    }
}
