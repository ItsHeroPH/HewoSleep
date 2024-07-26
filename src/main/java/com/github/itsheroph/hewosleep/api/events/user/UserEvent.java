package com.github.itsheroph.hewosleep.api.events.user;

import com.github.itsheroph.hewosleep.api.events.HewoSleepEvent;
import com.github.itsheroph.hewosleep.models.SleepUser;

public class UserEvent extends HewoSleepEvent {

    private final SleepUser user;

    public UserEvent(SleepUser user) {

        super(user.getManager().getAPI());

        this.user = user;

    }

    public SleepUser getUser() {

        return this.user;

    }
}
