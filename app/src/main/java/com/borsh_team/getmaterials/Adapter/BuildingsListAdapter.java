package com.borsh_team.getmaterials.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.borsh_team.getmaterials.Data.BuildingTypeMenu;
import com.borsh_team.getmaterials.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BuildingsListAdapter extends RecyclerView.Adapter<BuildingsListAdapter.ViewHolder> {
    private ArrayList<BuildingTypeMenu> dataSet;
    private BuildingsOnClickListener listener;

    public void setDataSet(ArrayList<BuildingTypeMenu> newDataSet){
        this.dataSet = newDataSet;
        notifyDataSetChanged();
    }

    public void setListener(BuildingsOnClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView buildingTitle;
        private final ImageView buildingImage;

        public TextView getBuildingTitle() {
            return buildingTitle;
        }
        public ImageView getBuildingImage() {
            return buildingImage;
        }

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            buildingTitle = itemView.findViewById(R.id.tv_type_building_desc);
            buildingImage = itemView.findViewById(R.id.img_type_building);

        }

        @Override
        public void onClick(View view) {
            BuildingTypeMenu typeBuildingModel = dataSet.get(getAdapterPosition());
            listener.onBuildingItemClick(typeBuildingModel);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.building_adapter_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BuildingTypeMenu building = dataSet.get(position);

        holder.getBuildingTitle().setText(building.getBuildingType());
        Glide.with(holder.getBuildingImage().getContext())
                .load(building.getMenuIcon())
                .into(holder.getBuildingImage());
    }

    @Override
    public int getItemCount() {
        try {
            return dataSet.size();
        } catch (Exception e){
            return 0;
        }
    }

    public interface BuildingsOnClickListener{
        void onBuildingItemClick(BuildingTypeMenu building);
    }
}

