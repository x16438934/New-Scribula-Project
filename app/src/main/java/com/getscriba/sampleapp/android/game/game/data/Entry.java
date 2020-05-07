package com.getscriba.sampleapp.android.game.game.data;

public class Entry {

    private String entryId;
    private int data;

    public Entry(String entryId, int data) {
        this.entryId = entryId;
        this.data = data;
    }

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

}
