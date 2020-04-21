package com.getscriba.sampleapp.android.Game.Game.Data;

public class Entry {

    private String entryId;
    private String data;

    public Entry(String entryId, String data) {
        this.entryId = entryId;
        this.data = data;
    }

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
