package com.github.itsheroph.hewosleep.api.events.user;

import com.github.itsheroph.hewosleep.models.SleepUser;

public class UserSleepingStateChangeEvent extends UserEvent {

    public enum Cause {

        COMMAND,
        PLUGIN,
        BED,
        NIGHT_SKIPPED,
        UNKNOWN

    }

    private final boolean newState;
    private final Cause cause;

    public UserSleepingStateChangeEvent(SleepUser user, boolean newState, Cause cause) {

        super(user);

        this.newState = newState;
        this.cause = cause;

    }

    public boolean getNewState() {

        return this.newState;

    }

    public Cause getCause() {

        return this.cause;

    }
}
