package com.github.itsheroph.hewosleep.commands;

import com.github.itsheroph.hewosleep.HewoSleep;
import com.github.itsheroph.hewosleep.util.Permissions;
import com.github.itsheroph.hewoutil.commands.HewoSubCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

public class HelpCommand extends HewoSubCommand {

    public HelpCommand(HewoSleep plugin) {

        super(plugin.getLangConfig().getCmdMessenger());

    }

    @Override
    public String getName() {

        return "help";

    }

    @Override
    public List<String> getOptions(CommandSender sender, String[] arguments) {

        if(arguments.length == 2) {

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

        return List.of();

    }

    @Override
    public String getPermission() {

        return Permissions.COMMAND_HELP;

    }

    @Override
    public boolean mayExecute(CommandSender commandSender) {

        return Permissions.canExecuteByAll(commandSender);

    }

    @Override
    public boolean execute(CommandSender commandSender, String[] arguments) {

        if(arguments.length <= 1) {

            this.getMessenger().sendMessage(commandSender, "command_help_header", false);
            this.getMessenger().sendMessage(commandSender, "command_help_buff", false);
            this.getMessenger().sendMessage(commandSender, "command_help_bypass", false);
            this.getMessenger().sendMessage(commandSender, "command_help_help", false);
            this.getMessenger().sendMessage(commandSender, "command_help_reload", false);
            this.getMessenger().sendMessage(commandSender, "command_help_setflag", false);
            this.getMessenger().sendMessage(commandSender, "command_help_sleep", false);
            this.getMessenger().sendMessage(commandSender, "command_help_version", false);
            this.getMessenger().sendMessage(commandSender, "command_help_footer", false);

            return true;

        }

        if(arguments.length == 2) {

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

                default:

                    this.getMessenger().sendMessage(commandSender, "command_unknown", true);
                    return true;

            }

        }

        this.getMessenger().sendMessage(commandSender, "command_help_usage", true);
        return true;

    }
}
