package com.github.itsheroph.hewosleep.api.events.user;

import com.github.itsheroph.hewosleep.models.SleepUser;

public class UserAddedEvent extends UserEvent{

    public UserAddedEvent(SleepUser user) {

        super(user);

    }

}
