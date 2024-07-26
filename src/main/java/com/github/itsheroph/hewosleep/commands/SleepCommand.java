package com.github.itsheroph.hewosleep.commands;

import com.github.itsheroph.hewosleep.HewoSleep;
import com.github.itsheroph.hewosleep.api.HewoSleepAPI;
import com.github.itsheroph.hewosleep.api.events.user.UserSleepingStateChangeEvent;
import com.github.itsheroph.hewosleep.api.events.user.UserSleepingStateChangeEvent.Cause;
import com.github.itsheroph.hewosleep.models.SleepUser;
import com.github.itsheroph.hewosleep.util.Permissions;
import com.github.itsheroph.hewosleep.util.TimeUtil;
import com.github.itsheroph.hewoutil.messages.HewoMsgEntry;
import com.github.itsheroph.hewoutil.plugin.command.HewoCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SleepCommand extends HewoCommand {

    private final HewoSleepAPI api;

    public SleepCommand(HewoSleep plugin) {

        super(plugin.getLangConfig().getCmdMessenger());

        this.api = plugin.getAPI();

    }

    @Override
    public String getName() {

        return "sleep";

    }

    @Override
    public List<String> getAliases() {

        return List.of();

    }

    @Override
    public List<String> getOptions(CommandSender commandSender, String[] arguments) {

        return List.of();

    }

    @Override
    public String getPermission() {

        return Permissions.COMMAND_SLEEP;

    }

    @Override
    public boolean mayExecute(CommandSender commandSender) {

        return Permissions.playerExecuteOnly(commandSender);

    }

    @Override
    public boolean execute(CommandSender commandSender, String[] arguments) {

        Player player = (Player) commandSender;
        SleepUser user = this.getAPI().getUserManager().getUser(player);

        if(user == null || !user.getWorld().getWorldConfig().isEnable()) {

            this.getMessenger().sendMessage(commandSender, "command_sleep_world_not_found", true);
            return true;

        }

        if(!TimeUtil.isSleepPossible(user.getWorld().getBase())) {

            this.getMessenger().sendMessage(commandSender, "command_sleep_day_time", true);
            return true;

        }

        if(user.isSleeping()) {

            user.setSleeping(false);
            this.getAPI().fireEvents(new UserSleepingStateChangeEvent(user, false, Cause.COMMAND));
            this.getMessenger().sendMessage(commandSender, "command_sleep_wake_up",
                    new HewoMsgEntry("<user>", user.getBase().getName())
            );

        } else {

            user.setSleeping(true);
            this.getAPI().fireEvents(new UserSleepingStateChangeEvent(user, true, Cause.COMMAND));
            this.getMessenger().sendMessage(commandSender, "command_sleep_sleeping",
                    new HewoMsgEntry("<user>", user.getBase().getName())
            );

        }

        return true;

    }

    public HewoSleepAPI getAPI() {

        return api;

    }
}
