package com.annasedykh.switchcontrol.screens.dimmer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.annasedykh.switchcontrol.R;
import com.annasedykh.switchcontrol.data.model.SwitchEntity;
import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DimmerActivity extends AppCompatActivity {
    
    public static void start(Context context, String switchId, boolean hasTwoChannels) {
        Intent starter = new Intent(context, DimmerActivity.class);
        starter.putExtra(SwitchEntity.ID, switchId);
        starter.putExtra(SwitchEntity.HAS_TWO_CHANNELS, hasTwoChannels);
        context.startActivity(starter);
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.switch_name)
    EditText switchName;
    @BindView(R.id.layout_1)
    ViewGroup layout1;
    @BindView(R.id.circle_bar_1)
    HoloCircleSeekBar circleBar1;
    @BindView(R.id.layout_2)
    ViewGroup layout2;
    @BindView(R.id.circle_bar_2)
    HoloCircleSeekBar circleBar2;
    @BindView(R.id.btn_save)
    Button btnSave;

    private String switchId;
    private boolean hasTwoChannels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimmer);
        ButterKnife.bind(this);

        switchId = getIntent().getStringExtra(SwitchEntity.ID);
        hasTwoChannels = getIntent().getBooleanExtra(SwitchEntity.HAS_TWO_CHANNELS, false);

        setupViews();

    }

    private void setupViews() {

        toolbar.setTitle(R.string.dimmer_setting);
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(v -> finish());

        if(!hasTwoChannels){
            layout2.setVisibility(View.GONE);
        }

        btnSave.setOnClickListener(v -> {

        });
    }

}
