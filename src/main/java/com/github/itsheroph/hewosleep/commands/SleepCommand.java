package com.github.itsheroph.hewosleep.commands;

import com.github.itsheroph.hewosleep.HewoSleep;
import com.github.itsheroph.hewosleep.models.SleepPlayer;
import com.github.itsheroph.hewosleep.models.SleepWorld;
import com.github.itsheroph.hewosleep.models.SleepWorldManager;
import com.github.itsheroph.hewosleep.util.TimeUtil;
import com.github.itsheroph.hewoutil.messaging.HewoMessenger;
import com.github.itsheroph.hewoutil.messaging.HewoMsgEntry;
import com.github.itsheroph.hewoutil.messaging.commands.HewoCMDMessenger;
import com.github.itsheroph.hewoutil.plugin.commands.HewoCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SleepCommand extends HewoCommand {

    private final HewoSleep plugin;

    public SleepCommand(HewoSleep plugin) {

        super(new HewoCMDMessenger(plugin, plugin.getPluginLogger(), "HewoSleepV1"));

        this.plugin = plugin;

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
    public List<String> getOptions() {

        return List.of();

    }

    @Override
    public String getPermission() {

        return "hewosleep.command.sleep";

    }

    @Override
    public boolean mayExecute(CommandSender commandSender) {

        return (commandSender instanceof Player);

    }

    @Override
    public boolean execute(CommandSender commandSender, String[] strings) {

        Player player = (Player) commandSender;
        SleepWorldManager manager = this.plugin.getAPI().getManager();
        SleepWorld world = manager.getSleepWorld(player);
        SleepPlayer sleepPlayer = world.getPlayer(player);
        HewoMessenger messenger = manager.getAPI().getMessenger();

        if(!this.plugin.getAPI().isEnable()) {

            this.getMessenger().sendMessage(commandSender, "command_sleep_notEnable", true);

            return true;

        }

        if(world == null) {

            this.getMessenger().sendMessage(commandSender, "command_sleep_worldNotFound", true);

            return true;

        }

        if(!TimeUtil.isSleepPossible(world.getWorld())) {

            this.getMessenger().sendMessage(commandSender, "command_sleep_notPossible", true);

            return true;

        }

        if(sleepPlayer.isSleeping()) {

            sleepPlayer.setSleeping(false);

            messenger.sendMessage(world.getAllPlayersInWorld(), "command_sleep_wakeUp",
                    new HewoMsgEntry("<player>", player.getName())
            );

        } else {

            sleepPlayer.setSleeping(true);

            messenger.sendMessage(world.getAllPlayersInWorld(), "command_sleep_sleeping",
                    new HewoMsgEntry("<player>", player.getName())
            );

        }

        return true;
    }
}
