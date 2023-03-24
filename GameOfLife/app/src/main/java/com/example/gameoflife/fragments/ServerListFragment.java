package com.example.gameoflife.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.gameoflife.R;
import com.example.gameoflife.models.ServerListViewModel;
import com.example.gameoflife.utils.ServerListViewAdapter;

import org.jetbrains.annotations.Contract;

import java.util.List;
import java.util.Objects;

public class ServerListFragment extends Fragment {

    private ListView serverListView;
    private ServerListViewAdapter serverListViewAdapter;
    private ServerListViewModel serverListViewModel;
    private ProgressBar progressBar;

    @androidx.annotation.NonNull
    @Contract(" -> new")
    public static ServerListFragment newInstance() {
        return new ServerListFragment();
    }

    @androidx.annotation.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_server_list, container, false);

        serverListView = (ListView) view.findViewById(R.id.fragmentListView);

        return view;
    }

    @Override
    public void onCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        serverListViewModel = new ViewModelProvider(this).get(ServerListViewModel.class);
    }

    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view,
                              @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        String username = requireActivity().getIntent().getStringExtra("username");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                requireContext(),
                R.layout.server_listview_row,
                R.id.serverIpTextView,
                serverListViewModel.getServerData().getValue()
        ) {

            @SuppressLint("SetTextI18n")
            @androidx.annotation.NonNull
            @Override
            public View getView(int position, @androidx.annotation.Nullable View convertView, @androidx.annotation.NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView serverIpTextView = (TextView) view.findViewById(R.id.serverIpTextView);

                String text = getItem(position) + " " + username;

                serverIpTextView.setText(text);

                return view;
            }
        };

        serverListViewModel.getServerData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> stringList) {
                if (stringList != null) {
                    arrayAdapter.clear();
                    arrayAdapter.addAll(stringList);

                    if (stringList.size() == 0) {
                        progressBar.setVisibility(View.VISIBLE);
                        serverListView.setVisibility(View.GONE);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        serverListView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        serverListView.setAdapter(arrayAdapter);
    }
}