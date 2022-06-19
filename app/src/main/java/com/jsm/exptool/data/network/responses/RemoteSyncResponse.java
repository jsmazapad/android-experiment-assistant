package com.jsm.exptool.data.network.responses;

public class RemoteSyncResponse {
    private String id;
    private String status;

    public RemoteSyncResponse(String id, String status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
