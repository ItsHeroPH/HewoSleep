package com.github.itsheroph.hewosleep.api.events.user;

import com.github.itsheroph.hewosleep.models.SleepUser;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class UserDeBuffsReceivedEvent extends UserEvent{

    private final List<PotionEffect> deBuffs;

    public UserDeBuffsReceivedEvent(SleepUser user, List<PotionEffect> deBuffs) {

        super(user);

        this.deBuffs = deBuffs;

    }

    public List<PotionEffect> getDeBuffs() {

        return this.deBuffs;

    }

}
