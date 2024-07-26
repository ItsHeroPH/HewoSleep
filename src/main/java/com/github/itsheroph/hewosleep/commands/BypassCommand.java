package com.github.itsheroph.hewosleep.commands;

import com.github.itsheroph.hewosleep.HewoSleep;
import com.github.itsheroph.hewosleep.api.HewoSleepAPI;
import com.github.itsheroph.hewosleep.models.SleepUser;
import com.github.itsheroph.hewosleep.models.menu.bypass.BypassMenu;
import com.github.itsheroph.hewosleep.util.Permissions;
import com.github.itsheroph.hewoutil.gui.HewoMenuManager;
import com.github.itsheroph.hewoutil.plugin.command.HewoCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class BypassCommand extends HewoCommand {

    private final HewoSleepAPI api;

    public BypassCommand(HewoSleep plugin) {

        super(plugin.getLangConfig().getCmdMessenger());

        this.api = plugin.getAPI();

    }

    @Override
    public String getName() {

        return "bypass";

    }

    @Override
    public List<String> getAliases() {

        return List.of();

    }

    @Override
    public List<String> getOptions(CommandSender commandSender, String[] strings) {

        return List.of();

    }

    @Override
    public String getPermission() {

        return Permissions.COMMAND_BYPASS;

    }

    @Override
    public boolean mayExecute(CommandSender commandSender) {

        return Permissions.playerExecuteOnly(commandSender);

    }

    @Override
    public boolean execute(CommandSender commandSender, String[] strings) {

        SleepUser user = this.getAPI().getUserManager().getUser((Player) commandSender);

        if(user == null) {

            this.getMessenger().sendMessage(commandSender, "command_bypass_world_not_found", true);
            return true;

        }

        new BypassMenu(HewoMenuManager.getPlayer(user.getBase()), user).open();

        return true;

    }

    private HewoSleepAPI getAPI() {

        return this.api;

    }
}
