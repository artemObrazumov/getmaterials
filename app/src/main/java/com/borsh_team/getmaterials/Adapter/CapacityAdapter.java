package com.borsh_team.getmaterials.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.borsh_team.getmaterials.Data.CapacityType;
import com.borsh_team.getmaterials.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CapacityAdapter extends RecyclerView.Adapter<CapacityAdapter.ViewHolder> {
    private ArrayList<CapacityType> dataSet;
    private CapacityOnClickListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView capacityString;
        private final ImageView peopleImage;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            capacityString = view.findViewById(R.id.capacityString);
            peopleImage = view.findViewById(R.id.peopleImage);
        }

        public TextView getCapacityString() {
            return capacityString;
        }

        public ImageView getPeopleImage() {
            return peopleImage;
        }

        @Override
        public void onClick(View view) {
            listener.onCapacityItemClick(dataSet.get(getAdapterPosition()));
        }
    }

    public void setDataSet(ArrayList<CapacityType> newDataSet) {
        dataSet = newDataSet;
        notifyDataSetChanged();
    }

    public void setListener(CapacityOnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.capacity_adapter_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getCapacityString().setText(dataSet.get(position).toString());
        Glide.with(viewHolder.getPeopleImage())
                .load(dataSet.get(position).getCapacityIcon())
                .into(viewHolder.getPeopleImage());
    }

    @Override
    public int getItemCount() {
        try {
            return dataSet.size();
        } catch (Exception e){
            return 0;
        }
    }

    public interface CapacityOnClickListener{
        void onCapacityItemClick(CapacityType capacity);
    }
}
