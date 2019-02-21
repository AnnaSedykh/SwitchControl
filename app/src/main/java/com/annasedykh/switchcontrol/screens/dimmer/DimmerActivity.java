package com.annasedykh.switchcontrol.screens.dimmer;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.annasedykh.switchcontrol.R;
import com.annasedykh.switchcontrol.data.model.SwitchEntity;
import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DimmerActivity extends AppCompatActivity {
    private static final String TAG = "DimmerActivity";

    public static void start(Context context, SwitchEntity switchEntity) {
        Intent starter = new Intent(context, DimmerActivity.class);
        starter.putExtra(SwitchEntity.SWITCH, switchEntity);
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

    private SwitchEntity entity;
    private DimmerViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimmer);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(DimmerViewModelImpl.class);
        entity = getIntent().getParcelableExtra(SwitchEntity.SWITCH);

        setupViews();
        initInputs();
    }

    private void initInputs() {
        viewModel.updated().observe(this,
                updated -> {
                    if (updated != null && updated) {
                        Toast.makeText(this, getText(R.string.updated_ok), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, getText(R.string.update_failed), Toast.LENGTH_LONG).show();
                    }
                    finish();
                });
    }

    private void setupViews() {

        setupToolbar();
        setupSwitchNameView();
        setupCircleBars();

        btnSave.setOnClickListener(v -> {

            applyNewSettings();
            viewModel.updateSwitchSettings(entity);
        });
    }

    private void applyNewSettings() {
        if (!switchName.getText().toString().isEmpty()) {
            entity.name = switchName.getText().toString();
        }

        String newStatus1 = convertToStatus(circleBar1.getValue());
        String newStatus2 = convertToStatus(circleBar2.getValue());

        if (newStatus1 != null && !SwitchEntity.STATUS_DISABLED.equals(entity.status0)) {
            entity.status0 = newStatus1;
        }
        if (newStatus2 != null && !SwitchEntity.STATUS_DISABLED.equals(entity.status1)) {
            entity.status1 = newStatus2;
        }
    }

    private void setupCircleBars() {
        if (SwitchEntity.STATUS_DISABLED.equals(entity.status0)) {
            layout1.setVisibility(View.GONE);
        } else {
            Float brightness1 = convertToBrightness(entity.status0);
            if (brightness1 != null) {
                circleBar1.setValue(brightness1);
            }
        }
        if (SwitchEntity.STATUS_DISABLED.equals(entity.status1)) {
            layout2.setVisibility(View.GONE);
        } else {
            Float brightness2 = convertToBrightness(entity.status1);
            if (brightness2 != null) {
                circleBar2.setValue(brightness2);
            }
        }
    }

    private void setupSwitchNameView() {
        switchName.setText(entity.name);
        switchName.setOnClickListener(v -> switchName.setFocusableInTouchMode(true));
        switchName.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                switchName.setFocusable(false);
            }
        });
    }

    private void setupToolbar() {
        toolbar.setTitle(R.string.dimmer_setting);
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private String convertToStatus(int brightness) {
        String status;

        switch (brightness) {
            case 0:
                status = SwitchEntity.STATUS_OFF;
                break;
            case 100:
                status = SwitchEntity.STATUS_MAX;
                break;
            default:
                status = String.valueOf(brightness + 1);
                break;
        }
        return status;
    }

    private Float convertToBrightness(String status) {
        Float brightness;

        switch (status) {
            case SwitchEntity.STATUS_OFF:
                brightness = null;
                break;
            case SwitchEntity.STATUS_MAX:
                brightness = HoloCircleSeekBar.MAX_POINT_DEF_VALUE - 0.1F;
                break;
            default:
                brightness = Float.parseFloat(status) - 1F;
                break;
        }
        return brightness;
    }

    //Clear focus and hide soft keyboard after clicking outside EditText
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    Log.d(TAG, "focus: touchevent");
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

}
