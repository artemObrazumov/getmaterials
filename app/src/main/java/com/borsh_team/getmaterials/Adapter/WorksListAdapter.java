package com.borsh_team.getmaterials.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.borsh_team.getmaterials.Data.WorkTypeMenu;
import com.borsh_team.getmaterials.R;

import java.util.ArrayList;

public class WorksListAdapter extends RecyclerView.Adapter<WorksListAdapter.ViewHolder> {
    private ArrayList<WorkTypeMenu> dataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox checkBox;
        private String workID;

        public ViewHolder(View view) {
            super(view);
            checkBox = view.findViewById(R.id.checkBox);
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }

        public Boolean isChecked() {
            return checkBox.isChecked();
        }

        public String getWorkID() {
            return workID;
        }
    }

    public void setDataSet(ArrayList<WorkTypeMenu> newDataSet) {
        dataSet = newDataSet;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.work_adapter_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getCheckBox().setText(dataSet.get(position).getWorkType());
        viewHolder.workID = dataSet.get(position).getWorkTypeID();
    }

    @Override
    public int getItemCount() {
        try {
            return dataSet.size();
        } catch (Exception e){
            return 0;
        }
    }
}
