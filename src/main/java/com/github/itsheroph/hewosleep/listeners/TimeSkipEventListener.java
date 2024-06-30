package com.github.itsheroph.hewosleep.listeners;

import com.github.itsheroph.hewosleep.models.SleepPlayer;
import com.github.itsheroph.hewosleep.models.SleepWorld;
import com.github.itsheroph.hewosleep.models.SleepWorldManager;
import com.github.itsheroph.hewosleep.util.TimeState;
import com.github.itsheroph.hewoutil.messaging.HewoMessenger;
import com.github.itsheroph.hewoutil.messaging.HewoMsgEntry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.TimeSkipEvent;


public class TimeSkipEventListener implements Listener {

    private final SleepWorldManager manager;

    public TimeSkipEventListener(SleepWorldManager manager) {

        this.manager = manager;

    }

    @EventHandler
    public void onTimeSkips(TimeSkipEvent event) {

        if(!this.manager.getAPI().isEnable()) return;

        HewoMessenger messenger = this.manager.getAPI().getMessenger();
        SleepWorld world = this.manager.getSleepWorld(event.getWorld());

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

                        messenger.sendMessage(player.getPlayer(), "dayTime_message",
                                new HewoMsgEntry("<player>", player.getPlayer().getName())
                        );

                        player.setSleeping(false);

                    }

                    break;
            }
        }
    }
}
