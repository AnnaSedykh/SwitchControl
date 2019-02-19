package com.annasedykh.switchcontrol.screens.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.annasedykh.switchcontrol.R;
import com.annasedykh.switchcontrol.data.model.SwitchEntity;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.SwitchEntityViewHolder> {

    private static final String TAG = "MainAdapter";

    private List<SwitchEntity> switchList = Collections.emptyList();

    public void setSwitchList(List<SwitchEntity> switchList) {
        this.switchList = switchList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SwitchEntityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_switch, parent, false);
        return new SwitchEntityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SwitchEntityViewHolder holder, int position) {
        holder.bind(switchList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return switchList.size();
    }


    static class SwitchEntityViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.switch_compat)
        SwitchCompat toggle;

        @BindView(R.id.switch_name)
        TextView switchName;

        @BindView(R.id.percentage)
        TextView percentage;

        private Context context;

        SwitchEntityViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        void bind(SwitchEntity switchEntity, int position) {
            bindName(switchEntity);
            bindPercentage(switchEntity);
            bindToggle();
            bindBackground(position);
        }

        private void bindName(SwitchEntity switchEntity) {
            switchName.setText(switchEntity.name);
        }

        private void bindPercentage(SwitchEntity switchEntity) {
            StringBuilder sb = new StringBuilder();
            String channel_first = formatBrightness(switchEntity.status0);
            String channel_second = formatBrightness(switchEntity.status1);

            if (channel_first != null) {
                sb.append(channel_first);
                if (channel_second != null) {
                    sb.append(" / ");
                }
            }
            if (channel_second != null) {
                sb.append(channel_second);
            }
            percentage.setText(sb.toString());
        }

        private String formatBrightness(String status) {
            String result = null;
            try {
                int statusInt = Integer.parseInt(status);
                if (statusInt == 0) {
                    result = context.getString(R.string.off);
                } else if (statusInt == 1) {
                    result = context.getString(R.string.max);
                } else if (statusInt >= 2 && statusInt <= 101) {
                    result = context.getString(R.string.percentage, statusInt - 1);
                }
            } catch (NumberFormatException e) {
                Log.e(TAG, "formatBrightness: ", e);
            }
            return result;
        }

        private void bindToggle() {
            if(switchName.getText().length() > 0){
                toggle.setChecked(true);
            }
        }

        private void bindBackground(int position) {
            if (position % 2 == 0) {
                itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.item_background_even));
            } else {
                itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.item_background_odd));
            }
        }
    }
}
