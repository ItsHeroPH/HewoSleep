package com.github.itsheroph.hewosleep.commands;

import com.github.itsheroph.hewosleep.HewoSleep;
import com.github.itsheroph.hewosleep.api.HewoSleepAPI;
import com.github.itsheroph.hewosleep.models.SleepUser;
import com.github.itsheroph.hewosleep.models.menu.buff.BuffMenu;
import com.github.itsheroph.hewosleep.util.Permissions;
import com.github.itsheroph.hewoutil.gui.HewoMenuManager;
import com.github.itsheroph.hewoutil.plugin.command.HewoCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class BuffCommand extends HewoCommand {

    private final HewoSleepAPI api;

    public BuffCommand(HewoSleep plugin) {

        super(plugin.getLangConfig().getCmdMessenger());

        this.api = plugin.getAPI();

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
    public List<String> getOptions(CommandSender sender, String[] arguments) {

        return List.of();

    }

    @Override
    public String getPermission() {

        return Permissions.COMMAND_BUFF;

    }

    @Override
    public boolean mayExecute(CommandSender commandSender) {

        return Permissions.playerExecuteOnly(commandSender);

    }

    @Override
    public boolean execute(CommandSender commandSender, String[] arguments) {

        SleepUser user = this.getAPI().getUserManager().getUser((Player) commandSender);

        if(user == null) {

            this.getMessenger().sendMessage(commandSender, "command_buff_world_not_found", true);
            return true;

        }

        new BuffMenu(HewoMenuManager.getPlayer(user.getBase()), user).open();
        return true;

    }

    private HewoSleepAPI getAPI() {

        return this.api;

    }
}
