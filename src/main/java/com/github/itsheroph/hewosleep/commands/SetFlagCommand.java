package com.github.itsheroph.hewosleep.commands;

import com.github.itsheroph.hewosleep.HewoSleep;
import com.github.itsheroph.hewosleep.models.SleepWorld;
import com.github.itsheroph.hewoutil.messaging.HewoMsgEntry;
import com.github.itsheroph.hewoutil.messaging.commands.HewoCMDMessenger;
import com.github.itsheroph.hewoutil.plugin.commands.HewoCommand;
import org.bukkit.command.CommandSender;
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
                "day_length",
                "night_length",
                "night_skip_length"
        );

    }

    @Override
    public String getPermission() {

        return "hewosleep.command.setflag";

    }

    @Override
    public boolean mayExecute(CommandSender commandSender) {

        return (commandSender instanceof Player);

    }

    @Override
    public boolean execute(CommandSender commandSender, String[] arguments) {

        Player player = (Player) commandSender;
        SleepWorld world = this.plugin.getAPI().getManager().getSleepWorld(player);

        if(world == null) {

            this.getMessenger().sendMessage(commandSender, "command_setflag_world_not_found", true);

            return true;
        }

        if(arguments.length == 1) {

            this.getMessenger().sendMessage(commandSender, "command_setflag_config_header", false);
            this.getMessenger().sendMessage(commandSender, "command_setflag_config_enable", false,
                    new HewoMsgEntry("<value>", world.getConfig().isEnable())
            );
            this.getMessenger().sendMessage(commandSender, "command_setflag_config_percentage", false,
                    new HewoMsgEntry("<value>", world.getConfig().getPercentage() + "%")
            );
            this.getMessenger().sendMessage(commandSender, "command_setflag_config_bedEnterDelay", false,
                    new HewoMsgEntry("<value>", world.getConfig().getBedEnterDelay() + "s")
            );
            this.getMessenger().sendMessage(commandSender, "command_setflag_config_dayLength", false,
                    new HewoMsgEntry("<value>", world.getConfig().getDayLength() + "s")
            );
            this.getMessenger().sendMessage(commandSender, "command_setflag_config_nightLength", false,
                    new HewoMsgEntry("<value>", world.getConfig().getNightLength() + "s")
            );
            this.getMessenger().sendMessage(commandSender, "command_setflag_config_nightSkipLength", false,
                    new HewoMsgEntry("<value>", world.getConfig().getNightSkipLength() + "s")
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
                                new HewoMsgEntry("<value>", world.getConfig().isEnable())
                        );

                        return true;

                    }

                    String enable = arguments[2];

                    if(enable.equalsIgnoreCase("true") || enable.equalsIgnoreCase("false")) {

                        world.getConfig().setEnable(Boolean.parseBoolean(enable));
                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_enable_change",
                                new HewoMsgEntry("<value>", Boolean.parseBoolean(enable))
                        );

                        return true;
                    }

                    break;
                case "sleeping_percentage":

                    if (arguments.length == 2) {

                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_percentage_current",
                                new HewoMsgEntry("<value>", world.getConfig().getPercentage() + "%")
                        );

                        return true;

                    }

                    int percentage = Integer.parseInt(arguments[2]);

                    if(percentage >= 0) {

                        world.getConfig().setPercentage(percentage);
                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_percentage_change",
                                new HewoMsgEntry("<value>", percentage + "%")
                        );

                        return true;
                    }

                    break;
                case "bed_enter_delay":

                    if (arguments.length == 2) {

                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_bedEnterDelay_current",
                                new HewoMsgEntry("<value>", world.getConfig().getBedEnterDelay() + "s")
                        );

                        return true;

                    }


                    int bedEnterDelay = Integer.parseInt(arguments[2]);

                    if(bedEnterDelay >= 0) {

                        world.getConfig().setBedEnterDelay(bedEnterDelay);
                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_bedEnterDelay_change",
                                new HewoMsgEntry("<value>", bedEnterDelay + "s")
                        );

                        return true;
                    }

                    break;
                case "day_length":

                    if (arguments.length == 2) {

                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_dayLength_current",
                                new HewoMsgEntry("<value>", world.getConfig().getDayLength() + "s")
                        );

                        return true;

                    }


                    int dayLength = Integer.parseInt(arguments[2]);

                    if(dayLength >= 0) {

                        world.getConfig().setDayLength(dayLength);
                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_dayLength_change",
                                new HewoMsgEntry("<value>", dayLength + "s")
                        );

                        return true;
                    }

                    break;
                case "night_length":

                    if (arguments.length == 2) {

                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_nightLength_current",
                                new HewoMsgEntry("<value>", world.getConfig().getNightLength() + "s")
                        );

                        return true;

                    }


                    int nightLength = Integer.parseInt(arguments[2]);

                    if(nightLength >= 0) {

                        world.getConfig().setNightLength(nightLength);
                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_nightLength_change",
                                new HewoMsgEntry("<value>", nightLength + "s")
                        );

                        return true;
                    }

                    break;
                case "night_skip_length":

                    if (arguments.length == 2) {

                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_nightSkipLength_current",
                                new HewoMsgEntry("<value>", world.getConfig().getNightSkipLength() + "s")
                        );

                        return true;

                    }


                    int nightSkipLength = Integer.parseInt(arguments[2]);

                    if(nightSkipLength >= 0) {

                        world.getConfig().setNightSkipLength(nightSkipLength);
                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_nightSkipLength_change",
                                new HewoMsgEntry("<value>", nightSkipLength + "s")
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
