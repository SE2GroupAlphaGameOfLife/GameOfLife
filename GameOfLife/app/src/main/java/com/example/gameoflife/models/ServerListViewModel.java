package com.example.gameoflife.models;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ServerListViewModel extends ViewModel {

    public MutableLiveData<List<String>> mutableLiveData;

    public LiveData<List<String>> getServerData() {
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
        }

        return mutableLiveData;
    }

    public void addServerListItem(String serverListItem) {
        List<String> serverListItems = mutableLiveData.getValue();
        assert serverListItems != null;
        serverListItems.add(serverListItem);
        mutableLiveData.setValue(serverListItems);
    }
}