package com.github.itsheroph.hewosleep.api.events.user;

import com.github.itsheroph.hewosleep.models.SleepUser;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class UserBuffsReceivedEvent extends UserEvent {

    private final List<PotionEffect> buffs;

    public UserBuffsReceivedEvent(SleepUser user, List<PotionEffect> buffs) {

        super(user);

        this.buffs = buffs;

    }

    public List<PotionEffect> getBuffs() {

        return this.buffs;

    }
}
