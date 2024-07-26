package com.github.itsheroph.hewosleep.api.events.user;

import com.github.itsheroph.hewosleep.models.SleepUser;

public class UserRemovedEvent extends UserEvent {

    public UserRemovedEvent(SleepUser user) {

        super(user);

    }
}
