package com.github.itsheroph.hewosleep.commands;

import com.github.itsheroph.hewosleep.HewoSleep;
import com.github.itsheroph.hewosleep.api.HewoSleepAPI;
import com.github.itsheroph.hewosleep.models.SleepUser;
import com.github.itsheroph.hewosleep.models.SleepWorld;
import com.github.itsheroph.hewosleep.util.Permissions;
import com.github.itsheroph.hewoutil.commands.HewoSubCommand;
import com.github.itsheroph.hewoutil.messages.HewoMsgEntry;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SetFlagCommand extends HewoSubCommand {

    private final HewoSleepAPI api;

    public SetFlagCommand(HewoSleep plugin) {

        super(plugin.getLangConfig().getCmdMessenger());

        this.api = plugin.getAPI();

    }

    @Override
    public String getName() {

        return "setflag";

    }

    @Override
    public List<String> getOptions(CommandSender commandSender, String[] arguments) {
        
        if(arguments.length == 2) {

            return List.of(
                    "enable",
                    "sleeping_percentage",
                    "bed_enter_delay",
                    "day_length",
                    "night_length",
                    "night_skip_length"
            );

        }
        return List.of();

    }

    @Override
    public String getPermission() {

        return Permissions.COMMAND_SETFLAG;

    }

    @Override
    public boolean mayExecute(CommandSender commandSender) {

        return Permissions.playerExecuteOnly(commandSender);

    }

    @Override
    public boolean execute(CommandSender commandSender, String[] arguments) {

        Player player = (Player) commandSender;
        SleepUser user = this.getAPI().getUserManager().getUser(player);

        if(user == null) {

            this.getMessenger().sendMessage(commandSender, "command_setflag_world_not_found", true);
            return true;

        }

        SleepWorld world = user.getWorld();

        if(arguments.length == 1) {

            this.getMessenger().sendMessage(commandSender, "command_setflag_config_header", false);
            this.getMessenger().sendMessage(commandSender, "command_setflag_config_enable", false,
                    new HewoMsgEntry("<value>", world.getWorldConfig().isEnable())
            );
            this.getMessenger().sendMessage(commandSender, "command_setflag_config_percentage", false,
                    new HewoMsgEntry("<value>", world.getWorldConfig().getPercentage() + "%")
            );
            this.getMessenger().sendMessage(commandSender, "command_setflag_config_bed_enter_delay", false,
                    new HewoMsgEntry("<value>", world.getWorldConfig().getBedEnterDelay() + "s")
            );
            this.getMessenger().sendMessage(commandSender, "command_setflag_config_day_length", false,
                    new HewoMsgEntry("<value>", world.getWorldConfig().getDayDuration() + "s")
            );
            this.getMessenger().sendMessage(commandSender, "command_setflag_config_night_length", false,
                    new HewoMsgEntry("<value>", world.getWorldConfig().getNightDuration() + "s")
            );
            this.getMessenger().sendMessage(commandSender, "command_setflag_config_night_skip_length", false,
                    new HewoMsgEntry("<value>", world.getWorldConfig().getNightSkippingDuration() + "s")
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
                                new HewoMsgEntry("<value>", world.getWorldConfig().isEnable())
                        );

                        return true;

                    }

                    String enable = arguments[2];

                    if(enable.equalsIgnoreCase("true") || enable.equalsIgnoreCase("false")) {

                        world.getWorldConfig().setEnable(Boolean.parseBoolean(enable));
                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_enable_change",
                                new HewoMsgEntry("<value>", Boolean.parseBoolean(enable))
                        );

                        return true;
                    }

                    break;
                case "sleeping_percentage":

                    if (arguments.length == 2) {

                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_percentage_current",
                                new HewoMsgEntry("<value>", world.getWorldConfig().getPercentage() + "%")
                        );

                        return true;

                    }

                    int percentage = Integer.parseInt(arguments[2]);

                    if(percentage >= 0) {

                        world.getWorldConfig().setPercentage(percentage);
                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_percentage_change",
                                new HewoMsgEntry("<value>", percentage + "%")
                        );

                        return true;
                    }

                    break;
                case "bed_enter_delay":

                    if (arguments.length == 2) {

                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_bed_enter_delay_current",
                                new HewoMsgEntry("<value>", world.getWorldConfig().getBedEnterDelay() + "s")
                        );

                        return true;

                    }


                    int bedEnterDelay = Integer.parseInt(arguments[2]);

                    if(bedEnterDelay >= 0) {

                        world.getWorldConfig().setBedEnterDelay(bedEnterDelay);
                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_bed_enter_delay_change",
                                new HewoMsgEntry("<value>", bedEnterDelay + "s")
                        );

                        return true;
                    }

                    break;
                case "day_length":

                    if (arguments.length == 2) {

                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_day_length_current",
                                new HewoMsgEntry("<value>", world.getWorldConfig().getDayDuration() + "s")
                        );

                        return true;

                    }


                    int dayLength = Integer.parseInt(arguments[2]);

                    if(dayLength >= 0) {

                        world.getWorldConfig().setDayDuration(dayLength);
                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_day_length_change",
                                new HewoMsgEntry("<value>", dayLength + "s")
                        );

                        return true;
                    }

                    break;
                case "night_length":

                    if (arguments.length == 2) {

                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_night_length_current",
                                new HewoMsgEntry("<value>", world.getWorldConfig().getNightDuration() + "s")
                        );

                        return true;

                    }


                    int nightLength = Integer.parseInt(arguments[2]);

                    if(nightLength >= 0) {

                        world.getWorldConfig().setNightDuration(nightLength);
                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_night_length_change",
                                new HewoMsgEntry("<value>", nightLength + "s")
                        );

                        return true;
                    }

                    break;
                case "night_skip_length":

                    if (arguments.length == 2) {

                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_night_skip_length_current",
                                new HewoMsgEntry("<value>", world.getWorldConfig().getNightSkippingDuration() + "s")
                        );

                        return true;

                    }


                    int nightSkipLength = Integer.parseInt(arguments[2]);

                    if(nightSkipLength >= 0) {

                        world.getWorldConfig().setNightSkippingDuration(nightSkipLength);
                        this.getMessenger().sendMessage(commandSender, "command_setflag_config_night_skip_length_change",
                                new HewoMsgEntry("<value>", nightSkipLength + "s")
                        );

                        return true;
                    }

                    break;
                default:

                    this.getMessenger().sendMessage(commandSender, "command_setflag_flag_list",
                            new HewoMsgEntry("<flag_list>", "enable, sleeping_percentage, bed_enter_delay, day_length, night_length, night_skip_length")
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

    public HewoSleepAPI getAPI() {

        return this.api;

    }
}
