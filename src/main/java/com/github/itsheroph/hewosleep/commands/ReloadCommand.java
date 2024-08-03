package com.github.itsheroph.hewosleep.commands;

import com.github.itsheroph.hewosleep.HewoSleep;
import com.github.itsheroph.hewosleep.util.Permissions;
import com.github.itsheroph.hewoutil.commands.HewoSubCommand;
import com.github.itsheroph.hewoutil.messages.HewoMsgEntry;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class ReloadCommand extends HewoSubCommand {

    private final HewoSleep plugin;

    public ReloadCommand(HewoSleep plugin) {

        super(plugin.getLangConfig().getCmdMessenger());

        this.plugin = plugin;

    }

    @Override
    public String getName() {

        return "reload";

    }

    @Override
    public List<String> getOptions(CommandSender sender, String[] arguments) {

        if(arguments.length == 2) {

            return List.of("confirm");

        }

        return List.of();

    }

    @Override
    public String getPermission() {

        return Permissions.COMMAND_RELOAD;

    }

    @Override
    public boolean mayExecute(CommandSender commandSender) {

        return Permissions.canExecuteByAll(commandSender);

    }

    @Override
    public boolean execute(CommandSender commandSender, String[] arguments) {

        if(arguments.length == 1) {

            if(commandSender instanceof Player) {

                Player player = (Player) commandSender;

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);

            }

            this.getMessenger().sendMessage(commandSender, "command_reload_confirmation", true);
            return true;
        }

        if(arguments[1].equals("confirm")) {

            Instant initialTime = Instant.now();
            this.plugin.reload();

            Duration timeTaken = Duration.between(initialTime, Instant.now());
            this.getMessenger().sendMessage(commandSender, "command_reload_success",
                    new HewoMsgEntry("<time>", timeTaken.toMillis() + "ms")
            );

            if(commandSender instanceof Player) {

                Player player = (Player) commandSender;

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 2);

            }

            return true;

        }

        this.getMessenger().sendMessage(commandSender, "command_reload_usage", true);
        return false;

    }
}
