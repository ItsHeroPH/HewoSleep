package com.github.itsheroph.hewosleep.commands;

import com.github.itsheroph.hewosleep.HewoSleep;
import com.github.itsheroph.hewoutil.messaging.HewoMsgEntry;
import com.github.itsheroph.hewoutil.messaging.commands.HewoCMDMessenger;
import com.github.itsheroph.hewoutil.plugin.commands.HewoCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SetFlagCommand extends HewoCommand {

    private final HewoSleep plugin;

    public SetFlagCommand(HewoSleep plugin) {

        super(new HewoCMDMessenger(plugin, plugin.getPluginLogger(), "HewoSleepV1"));

        this.plugin = plugin;

    }

    @Override
    public String getName() {

        return "setflag";

    }

    @Override
    public List<String> getAliases() {

        return List.of();

    }

    @Override
    public List<String> getOptions() {

        return List.of(
                "enable",
                "sleeping_percentage",
                "bed_enter_delay",
                "night_skip_length",
                "ignore_afk_players",
                "ignore_vanish_players"
        );

    }

    @Override
    public String getPermission() {

        return "hewosleep.command.setflag";

    }

    @Override
    public boolean mayExecute(CommandSender commandSender) {

        return (commandSender instanceof Player || commandSender instanceof ConsoleCommandSender);

    }

    @Override
    public boolean execute(CommandSender commandSender, String[] arguments) {

        if(arguments.length == 1) {

            this.getMessenger().sendMessage(commandSender, "command_setflag_config_header", false);
            this.getMessenger().sendMessage(commandSender, "command_setflag_config_enable", false,
                    new HewoMsgEntry("<value>", this.plugin.getAPI().isEnable())
            );
            this.getMessenger().sendMessage(commandSender, "command_setflag_config_percentage", false,
                    new HewoMsgEntry("<value>", this.plugin.getAPI().getPercentage() + "%")
            );
            this.getMessenger().sendMessage(commandSender, "command_setflag_config_bedEnterDelay", false,
                    new HewoMsgEntry("<value>", this.plugin.getAPI().getBedEnterDelay() + "s")
            );
            this.getMessenger().sendMessage(commandSender, "command_setflag_config_nightSkipLength", false,
                    new HewoMsgEntry("<value>", this.plugin.getAPI().getNightSkipLength() + "s")
            );
            this.getMessenger().sendMessage(commandSender, "command_setflag_config_ignoreAfkPlayers", false,
                    new HewoMsgEntry("<value>", this.plugin.getAPI().ignoreAfkPlayers())
            );
            this.getMessenger().sendMessage(commandSender, "command_setflag_config_ignoreVanishPlayers", false,
                    new HewoMsgEntry("<value>", this.plugin.getAPI().ignoreVanishPlayers())
            );
            this.getMessenger().sendMessage(commandSender, "command_setflag_config_footer", false);

            return true;

        }
        String flag = arguments[1];

        try {
            switch (flag) {
                case "enable":

                    if (arguments.length == 2) {

                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_enable_current",
                                new HewoMsgEntry("<value>", this.plugin.getAPI().isEnable())
                        );

                        return true;

                    }

                    String enable = arguments[2];

                    if(enable.equalsIgnoreCase("true") || enable.equalsIgnoreCase("false")) {

                        this.plugin.getAPI().setEnable(Boolean.parseBoolean(enable));
                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_enable_change",
                                new HewoMsgEntry("<value>", Boolean.parseBoolean(enable))
                        );

                        return true;
                    }

                    break;
                case "sleeping_percentage":

                    if (arguments.length == 2) {

                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_percentage_current",
                                new HewoMsgEntry("<value>", this.plugin.getAPI().getPercentage() + "%")
                        );

                        return true;

                    }

                    int percentage = Integer.parseInt(arguments[2]);

                    if(percentage >= 0) {

                        this.plugin.getAPI().setPercentage(percentage);
                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_percentage_change",
                                new HewoMsgEntry("<value>", percentage + "%")
                        );

                        return true;
                    }

                    break;
                case "bed_enter_delay":

                    if (arguments.length == 2) {

                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_bedEnterDelay_current",
                                new HewoMsgEntry("<value>", this.plugin.getAPI().getBedEnterDelay() + "s")
                        );

                        return true;

                    }


                    int bedEnterDelay = Integer.parseInt(arguments[2]);

                    if(bedEnterDelay >= 0) {

                        this.plugin.getAPI().setBedEnterDelay(bedEnterDelay);
                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_bedEnterDelay_change",
                                new HewoMsgEntry("<value>", bedEnterDelay + "s")
                        );

                        return true;
                    }

                    break;
                case "night_skip_length":

                    if (arguments.length == 2) {

                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_nightSkipLength_current",
                                new HewoMsgEntry("<value>", this.plugin.getAPI().getNightSkipLength() + "s")
                        );

                        return true;

                    }


                    int nightSkipLength = Integer.parseInt(arguments[2]);

                    if(nightSkipLength >= 0) {

                        this.plugin.getAPI().setNightSkipLength(nightSkipLength);
                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_nightSkipLength_change",
                                new HewoMsgEntry("<value>", nightSkipLength + "s")
                        );

                        return true;
                    }

                    break;
                case "ignore_afk_players":

                    if (arguments.length == 2) {

                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_ignoreAfkPlayers_current",
                                new HewoMsgEntry("<value>", this.plugin.getAPI().ignoreAfkPlayers())
                        );

                        return true;

                    }


                    String ignoreAfkPlayers = arguments[2];

                    if(ignoreAfkPlayers.equalsIgnoreCase("true") || ignoreAfkPlayers.equalsIgnoreCase("false")) {

                        this.plugin.getAPI().setIgnoreAfkPlayers(Boolean.parseBoolean(ignoreAfkPlayers));
                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_ignoreAfkPlayers_change",
                                new HewoMsgEntry("<value>", Boolean.parseBoolean(ignoreAfkPlayers))
                        );

                        return true;
                    }

                    break;
                case "ignore_vanish_players":

                    if (arguments.length == 2) {

                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_ignoreVanishPlayers_current",
                                new HewoMsgEntry("<value>", this.plugin.getAPI().ignoreAfkPlayers())
                        );

                        return true;

                    }


                    String ignoreVanishPlayers = arguments[2];

                    if(ignoreVanishPlayers.equalsIgnoreCase("true") || ignoreVanishPlayers.equalsIgnoreCase("false")) {

                        this.plugin.getAPI().setIgnoreVanishPlayers(Boolean.parseBoolean(ignoreVanishPlayers));
                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_ignoreVanishPlayers_change",
                                new HewoMsgEntry("<value>", Boolean.parseBoolean(ignoreVanishPlayers))
                        );

                        return true;
                    }

                    break;
                default:

                    this.getMessenger().sendMessage(commandSender, "command_setflag_flag_list",
                            new HewoMsgEntry("<flag_list>", this.getOptions().toString().replace("[", "").replace("]", ""))
                    );

                    break;
            }
        } catch(NumberFormatException err) {

            this.getMessenger().sendMessage(commandSender, "command_setflag_usage", true);
            return true;

        }

        this.getMessenger().sendMessage(commandSender, "command_setflag_usage", true);
        return true;

    }
}
