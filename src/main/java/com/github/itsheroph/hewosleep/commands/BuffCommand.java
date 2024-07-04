package com.github.itsheroph.hewosleep.commands;

import com.github.itsheroph.hewosleep.HewoSleep;
import com.github.itsheroph.hewosleep.models.SleepWorld;
import com.github.itsheroph.hewosleep.models.SleepWorldManager;
import com.github.itsheroph.hewoutil.messaging.HewoMsgEntry;
import com.github.itsheroph.hewoutil.messaging.commands.HewoCMDMessenger;
import com.github.itsheroph.hewoutil.plugin.commands.HewoCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class BuffCommand extends HewoCommand {

    private final HewoSleep plugin;

    public BuffCommand(HewoSleep plugin) {

        super(new HewoCMDMessenger(plugin, plugin.getPluginLogger(), "HewoSleepV1"));

        this.plugin = plugin;

    }


    @Override
    public String getName() {

        return "buff";

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

        return "hewosleep.command.buff";

    }

    @Override
    public boolean mayExecute(CommandSender commandSender) {

        return (commandSender instanceof Player);

    }

    @Override
    public boolean execute(CommandSender commandSender, String[] arguments) {

        Player player = (Player) commandSender;
        SleepWorldManager manager = this.plugin.getAPI().getManager();

        SleepWorld world = manager.getSleepWorld(player);

        if(world == null) {

            this.getMessenger().sendMessage(commandSender, "command_buff_world_not_found", true);
            return true;

        }

        List<PotionEffect> buffList = world.getBuffConfig().getBuffsList();
        List<PotionEffect> deBuffList = world.getBuffConfig().getDeBuffsList();

        this.getMessenger().sendMessage(commandSender, "command_buff_header", false);
        if(buffList.isEmpty()) {

            this.getMessenger().sendMessage(commandSender, "command_buff_no_buffs", false);

        } else {

            this.getMessenger().sendMessage(commandSender, "command_buff_buffs", false);
            this.getMessenger().sendMessage(commandSender, "command_buff_buffs_list", false,
                    new HewoMsgEntry("<effect_list>", world.getBuffConfig().getToString(buffList))
            );

        }
        this.getMessenger().sendMessage(commandSender, "command_buff_separator", false);
        if(deBuffList.isEmpty()) {

            this.getMessenger().sendMessage(commandSender, "command_buff_no_deBuffs", false);

        } else {

            this.getMessenger().sendMessage(commandSender, "command_buff_deBuffs", false);
            this.getMessenger().sendMessage(commandSender, "command_buff_deBuffs_list", false,
                    new HewoMsgEntry("<effect_list>", world.getBuffConfig().getToString(deBuffList))
            );

        }
        this.getMessenger().sendMessage(commandSender, "command_buff_footer", false);
        return true;
    }
}
