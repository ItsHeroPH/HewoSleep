package com.github.itsheroph.hewosleep.listeners;

import com.github.itsheroph.hewosleep.models.SleepPlayer;
import com.github.itsheroph.hewosleep.models.SleepWorld;
import com.github.itsheroph.hewosleep.models.SleepWorldConfig;
import com.github.itsheroph.hewosleep.models.SleepWorldManager;
import com.github.itsheroph.hewosleep.util.TimeState;
import com.github.itsheroph.hewoutil.messaging.HewoMessenger;
import com.github.itsheroph.hewoutil.messaging.HewoMsgEntry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.TimeSkipEvent;
import org.bukkit.potion.PotionEffect;

import java.util.List;


public class TimeSkipEventListener implements Listener {

    private final SleepWorldManager manager;

    public TimeSkipEventListener(SleepWorldManager manager) {

        this.manager = manager;

    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onTimeSkips(TimeSkipEvent event) {

        HewoMessenger messenger = this.manager.getAPI().getMessenger();
        SleepWorld world = this.manager.getSleepWorld(event.getWorld());

        if(world == null) return;

        SleepWorldConfig worldConfig = world.getConfig();

        if(!worldConfig.isEnable()) return;

        TimeState nextState = TimeState.getState(world);
        if(world.getTimeState() != nextState) {

            world.setTimeState(nextState);

            switch(nextState) {

                case CAN_SLEEP_SOON:

                    messenger.sendMessage(world.getAllPlayersInWorld(), "can_sleep_soon", true);

                    break;
                case CAN_SLEEP_NOW:

                    messenger.sendMessage(world.getAllPlayersInWorld(), "can_sleep_now", true);

                    break;
                case CANNOT_SLEEP:

                    for(SleepPlayer player : world.getAllPlayers().values()) {

                        if(!world.getBypassConfig().isPlayerIgnored(player)) {

                            if(player.isSleeping()) {

                                List<PotionEffect> buffs = world.getBuffConfig().getBuffsList();;

                                player.setSleeping(false);

                                if(!buffs.isEmpty()) {

                                    player.getPlayer().addPotionEffects(buffs);
                                    messenger.sendMessage(player.getPlayer(), "buff_received",
                                            new HewoMsgEntry("<effects_list>", world.getBuffConfig().getToString(buffs))
                                    );

                                }

                            } else {

                                List<PotionEffect> deBuffs = world.getBuffConfig().getDeBuffsList();

                                if(!deBuffs.isEmpty()) {

                                    player.getPlayer().addPotionEffects(deBuffs);
                                    messenger.sendMessage(player.getPlayer(), "deBuff_received",
                                            new HewoMsgEntry("<effects_list>", world.getBuffConfig().getToString(deBuffs))
                                    );

                                }

                            }

                        }

                        messenger.sendMessage(player.getPlayer(), "dayTime_message",
                                new HewoMsgEntry("<player>", player.getPlayer().getName())
                        );

                    }

                    break;
            }
        }
    }
}
