package com.jsm.exptool.data.network.responses;

public class RemoteSyncResponse {
    private long id;
    private String status;

    public RemoteSyncResponse(long id, String status) {
        this.id = id;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}