package com.github.itsheroph.hewosleep.models.managers;

import com.github.itsheroph.hewosleep.api.HewoSleepAPI;
import com.github.itsheroph.hewosleep.api.events.user.UserAddedEvent;
import com.github.itsheroph.hewosleep.api.events.user.UserRemovedEvent;
import com.github.itsheroph.hewosleep.models.SleepUser;
import com.github.itsheroph.hewosleep.models.SleepWorld;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserManager {

    private final HewoSleepAPI API;
    private final Map<Player, SleepUser> userMap;

    public UserManager(HewoSleepAPI api) {

        this.API = api;
        this.userMap = new HashMap<>();

        for(Player player : Bukkit.getServer().getOnlinePlayers()) {

            SleepWorld world = this.getAPI().getWorldManager().getWorld(player.getWorld());

            if(world == null) return;

            SleepUser user = new SleepUser(this, player);

            this.addUser(user);


        }

    }

    public HewoSleepAPI getAPI() {

        return this.API;

    }

    public Map<Player, SleepUser> getUserMap() {

        return this.userMap;

    }

    public List<SleepUser> getUsers() {

        return new ArrayList<>(this.getUserMap().values());

    }

    public @Nullable SleepUser getUser(Player player) {

        return this.getUserMap().getOrDefault(player, null);

    }

    public void addUser(SleepUser user) {

        this.getUserMap().put(user.getBase(), user);
        this.getAPI().fireEvent(new UserAddedEvent(user));

    }

    public void removeUser(SleepUser user) {

        user.stopRunnable();
        this.getUserMap().remove(user.getBase());
        this.getAPI().fireEvent(new UserRemovedEvent(user));

    }

    public void removeAllUser() {

        for(SleepUser user : this.getUsers()) {

            this.removeUser(user);

        }

    }
}
