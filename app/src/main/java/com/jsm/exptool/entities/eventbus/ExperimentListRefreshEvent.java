package com.jsm.exptool.entities.eventbus;

public class ExperimentListRefreshEvent {
    private boolean refreshNeeded;

    public ExperimentListRefreshEvent(boolean refreshNeeded) {
        this.refreshNeeded = refreshNeeded;
    }

    public boolean isRefreshNeeded() {
        return refreshNeeded;
    }
}
