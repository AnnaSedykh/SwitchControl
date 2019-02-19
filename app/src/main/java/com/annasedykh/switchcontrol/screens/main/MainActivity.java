package com.annasedykh.switchcontrol.screens.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.annasedykh.switchcontrol.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.progress)
    ViewGroup progress;

    private MainViewModel viewModel;
    private MainAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(MainViewModelImpl.class);

        adapter = new MainAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

        refresh.setOnRefreshListener(() -> viewModel.loadSwitchList(true));
        viewModel.loadSwitchList(false);

        initInputs();
    }

    private void initInputs() {

        viewModel.switchList().observe(this,
                switchList -> {
                    adapter.setSwitchList(switchList);
                    refresh.setRefreshing(false);
                });
    }
}
