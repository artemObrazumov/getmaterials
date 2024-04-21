package com.borsh_team.getmaterials.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.borsh_team.getmaterials.Data.Resource;
import com.borsh_team.getmaterials.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ResourcesAdapter extends RecyclerView.Adapter<ResourcesAdapter.ViewHolder> {
    private ArrayList<Resource> dataSet = new ArrayList<>();

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView resourceTitle, resourceInfo;
        private final ImageView resourceImage;

        public ViewHolder(View view) {
            super(view);
            resourceTitle = view.findViewById(R.id.resource_title);
            resourceInfo = view.findViewById(R.id.resource_info);
            resourceImage = view.findViewById(R.id.resource_image);
        }

        public TextView getResourceTitle() {
            return resourceTitle;
        }

        public TextView getResourceInfo() {
            return resourceInfo;
        }

        public ImageView getResourceImage() {
            return resourceImage;
        }
    }

    public void addResource(Resource resource) {
        dataSet.add(resource);
        notifyItemInserted(dataSet.size()-1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.resource_adapter_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getResourceTitle().setText(dataSet.get(position).getResourceString());
        holder.getResourceInfo().setText(dataSet.get(position).getResourceInfo());
        Glide.with(holder.getResourceImage())
                .load(dataSet.get(position).getResourceImage())
                .into(holder.getResourceImage());
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