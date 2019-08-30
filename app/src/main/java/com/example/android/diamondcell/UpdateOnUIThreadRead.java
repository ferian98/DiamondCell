package com.example.android.diamondcell;

import java.util.ArrayList;

public interface UpdateOnUIThreadRead<T> {
    public void updateOnUIThread(ArrayList<T> objek);
}
