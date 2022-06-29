package com.jsm.exptool.entities.eventbus;

import com.jsm.exptool.core.exceptions.BaseException;

import java.util.Set;

public class WorkFinishedEvent {
    private final Set<String> tags;
    private final boolean successful;
    private final int numRegisters;
    private final BaseException exception;

    public WorkFinishedEvent(Set<String> tags, boolean successful, int numRegisters, BaseException exception) {
        this.tags = tags;
        this.successful = successful;
        this.numRegisters = numRegisters;
        this.exception = exception;
    }

    public WorkFinishedEvent(Set<String> tags, boolean successful, int numRegisters) {
        this.tags = tags;
        this.successful = successful;
        this.numRegisters = numRegisters;
        this.exception = null;
    }

    public Set<String> getTags() {
        return tags;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public int getNumRegisters() {
        return numRegisters;
    }

    public BaseException getException() {
        return exception;
    }
}
