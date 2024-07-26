package com.github.itsheroph.hewosleep.util;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Permissions {

    public static String COMMAND_BUFF = "hewosleep.command.buff";
    public static String COMMAND_BYPASS = "hewosleep.command.bypass";
    public static String COMMAND_HELP = "hewosleep.command.help";
    public static String COMMAND_RELOAD = "hewosleep.command.reload";
    public static String COMMAND_SETFLAG = "hewosleep.command.setflag";
    public static String COMMAND_SLEEP = "hewosleep.command.sleep";
    public static String COMMAND_VERSION = "hewosleep.command.version";

    public static String MONSTER_BYPASS = "hewosleep.monster";

    public static boolean playerExecuteOnly(CommandSender sender) {

        return (sender instanceof Player);

    }

    public static boolean consoleExecuteOnly(CommandSender sender) {

        return (sender instanceof ConsoleCommandSender);

    }

    public static boolean canExecuteByAll(CommandSender sender) {

        return (sender instanceof Player) || (sender instanceof ConsoleCommandSender);

    }

}
