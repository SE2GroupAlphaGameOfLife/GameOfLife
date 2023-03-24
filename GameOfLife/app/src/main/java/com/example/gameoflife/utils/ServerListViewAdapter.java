package com.example.gameoflife.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.gameoflife.R;

import java.util.List;

public class ServerListViewAdapter extends ArrayAdapter<String> {

    private List<String> list;

    public ServerListViewAdapter(@NonNull Context context, List<String> list) {
        super(context, 0, list);
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.server_listview_row, parent, false);
        }

        TextView serverIpTextView = convertView.findViewById(R.id.serverIpTextView);
        String serverIp = getItem(position);

        serverIpTextView.setText(serverIp);

        return convertView;
    }

}
