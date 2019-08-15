package com.example.android.diamondcell;

public class KeyValuePair {
    private String mName;
    private String mValue;

    public KeyValuePair(String mName, String mValue) {
        this.mName = mName;
        this.mValue = mValue;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmValue() {
        return mValue;
    }

    public void setmValue(String mValue) {
        this.mValue = mValue;
    }
}
