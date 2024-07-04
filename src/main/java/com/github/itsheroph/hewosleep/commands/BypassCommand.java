package com.github.itsheroph.hewosleep.commands;

import com.github.itsheroph.hewosleep.HewoSleep;
import com.github.itsheroph.hewosleep.models.SleepWorld;
import com.github.itsheroph.hewoutil.messaging.HewoMsgEntry;
import com.github.itsheroph.hewoutil.messaging.commands.HewoCMDMessenger;
import com.github.itsheroph.hewoutil.plugin.commands.HewoCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class BypassCommand extends HewoCommand {

    private final HewoSleep plugin;

    public BypassCommand(HewoSleep plugin) {

        super(new HewoCMDMessenger(plugin, plugin.getPluginLogger(), "HewoSleepV1"));

        this.plugin = plugin;

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
    public List<String> getOptions() {
        return List.of(
                "ignore_survival",
                "ignore_creative",
                "ignore_adventure",
                "ignore_spectator",
                "ignore_afk_player",
                "ignore_vanish_player"
        );
    }

    @Override
    public String getPermission() {
        return "hewosleep.command.bypass";
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

            this.getMessenger().sendMessage(commandSender, "command_bypass_world_not_found", true);

            return true;

        }

        if(arguments.length == 1) {

            this.getMessenger().sendMessage(commandSender, "command_bypass_header", false);
            this.getMessenger().sendMessage(commandSender, "command_bypass_ignore_survival", false,
                    new HewoMsgEntry("<value>", world.getBypassConfig().ignore_survival())
            );
            this.getMessenger().sendMessage(commandSender, "command_bypass_ignore_creative", false,
                    new HewoMsgEntry("<value>", world.getBypassConfig().ignore_creative())
            );
            this.getMessenger().sendMessage(commandSender, "command_bypass_ignore_adventure", false,
                    new HewoMsgEntry("<value>", world.getBypassConfig().ignore_adventure())
            );
            this.getMessenger().sendMessage(commandSender, "command_bypass_ignore_spectator", false,
                    new HewoMsgEntry("<value>", world.getBypassConfig().ignore_spectator())
            );
            this.getMessenger().sendMessage(commandSender, "command_bypass_ignore_afk_player", false,
                    new HewoMsgEntry("<value>", world.getBypassConfig().ignore_afk_player())
            );
            this.getMessenger().sendMessage(commandSender, "command_bypass_ignore_vanish_player", false,
                    new HewoMsgEntry("<value>", world.getBypassConfig().ignore_vanish_player())
            );
            this.getMessenger().sendMessage(commandSender, "command_bypass_footer", false);

            return true;

        }

        String config = arguments[1];

        switch(config) {
            case "ignore_survival":

                if(arguments.length == 2) {

                    this.getMessenger().sendMessage(commandSender, "command_bypass_ignore_survival_current",
                            new HewoMsgEntry("<value>", world.getBypassConfig().ignore_survival())
                    );

                    return true;

                }

                if(arguments[2].equals("true") || arguments[2].equals("false")) {

                    world.getBypassConfig().setIgnore_survival(Boolean.parseBoolean(arguments[2]));
                    this.getMessenger().sendMessage(commandSender, "command_bypass_ignore_survival_change",
                            new HewoMsgEntry("<value>", world.getBypassConfig().ignore_survival())
                    );

                    return true;
                }

                break;
            case "ignore_creative":

                if(arguments.length == 2) {

                    this.getMessenger().sendMessage(commandSender, "command_bypass_ignore_creative_current",
                            new HewoMsgEntry("<value>", world.getBypassConfig().ignore_creative())
                    );

                    return true;

                }

                if(arguments[2].equals("true") || arguments[2].equals("false")) {

                    world.getBypassConfig().setIgnore_creative(Boolean.parseBoolean(arguments[2]));
                    this.getMessenger().sendMessage(commandSender, "command_bypass_ignore_creative_change",
                            new HewoMsgEntry("<value>", world.getBypassConfig().ignore_creative())
                    );

                    return true;
                }

                break;
            case "ignore_adventure":

                if(arguments.length == 2) {

                    this.getMessenger().sendMessage(commandSender, "command_bypass_ignore_adventure_current",
                            new HewoMsgEntry("<value>", world.getBypassConfig().ignore_adventure())
                    );

                    return true;

                }

                if(arguments[2].equals("true") || arguments[2].equals("false")) {

                    world.getBypassConfig().setIgnore_adventure(Boolean.parseBoolean(arguments[2]));
                    this.getMessenger().sendMessage(commandSender, "command_bypass_ignore_adventure_change",
                            new HewoMsgEntry("<value>", world.getBypassConfig().ignore_adventure())
                    );

                    return true;
                }

                break;
            case "ignore_spectator":

                if(arguments.length == 2) {

                    this.getMessenger().sendMessage(commandSender, "command_bypass_ignore_spectator_current",
                            new HewoMsgEntry("<value>", world.getBypassConfig().ignore_spectator())
                    );

                    return true;

                }

                if(arguments[2].equals("true") || arguments[2].equals("false")) {

                    world.getBypassConfig().setIgnore_spectator(Boolean.parseBoolean(arguments[2]));
                    this.getMessenger().sendMessage(commandSender, "command_bypass_ignore_spectator_change",
                            new HewoMsgEntry("<value>", world.getBypassConfig().ignore_spectator())
                    );

                    return true;
                }

                break;
            case "ignore_afk_player":

                if(arguments.length == 2) {

                    this.getMessenger().sendMessage(commandSender, "command_bypass_ignore_afk_player_current",
                            new HewoMsgEntry("<value>", world.getBypassConfig().ignore_afk_player())
                    );

                    return true;

                }

                if(arguments[2].equals("true") || arguments[2].equals("false")) {

                    world.getBypassConfig().setIgnore_afk_player(Boolean.parseBoolean(arguments[2]));
                    this.getMessenger().sendMessage(commandSender, "command_bypass_ignore_afk_player_change",
                            new HewoMsgEntry("<value>", world.getBypassConfig().ignore_afk_player())
                    );

                    return true;
                }

                break;
            case "ignore_vanish_player":

                if(arguments.length == 2) {

                    this.getMessenger().sendMessage(commandSender, "command_bypass_ignore_vanish_player_current",
                            new HewoMsgEntry("<value>", world.getBypassConfig().ignore_vanish_player())
                    );

                    return true;

                }

                if(arguments[2].equals("true") || arguments[2].equals("false")) {

                    world.getBypassConfig().setIgnore_vanish_player(Boolean.parseBoolean(arguments[2]));
                    this.getMessenger().sendMessage(commandSender, "command_bypass_ignore_vanish_player_change",
                            new HewoMsgEntry("<value>", world.getBypassConfig().ignore_vanish_player())
                    );

                    return true;
                }

                break;
            default:

                this.getMessenger().sendMessage(commandSender, "command_bypass_config_list",
                        new HewoMsgEntry("<bypass_list>", this.getOptions().toString().replace("[", "").replace("]", ""))
                );

                return true;

        }

        this.getMessenger().sendMessage(commandSender, "command_bypass_usage",true);

        return true;
    }
}
