package com.example.coursework.Adapter;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursework.R;
import com.example.coursework.Models.Hike;

public class HikeAdapter extends RecyclerView.Adapter<HikeAdapter.HikeViewHolder> {
    private List<Hike> hikes;
    private OnItemClickListener listener;

    public HikeAdapter(List<Hike> hikes) {
        this.hikes = hikes;
    }

    @NonNull
    @Override
    public HikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hike_card, parent, false);
        return new HikeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HikeViewHolder holder, int position) {
        Hike hike = hikes.get(position);
        holder.hikeName.setText(hike.name);
        holder.hikeLocation.setText(hike.location);
        holder.hikeDate.setText(hike.date);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
    }

    public void setHikes(List<Hike> hikes) {
        this.hikes = hikes;
    }

    @Override
    public int getItemCount() {
        return hikes.size();
    }

    public static class HikeViewHolder extends RecyclerView.ViewHolder {
        TextView hikeName, hikeLocation, hikeDate;

        public HikeViewHolder(@NonNull View itemView) {
            super(itemView);
            hikeName = itemView.findViewById(R.id.hikeName);
            hikeLocation = itemView.findViewById(R.id.hikeLocation);
            hikeDate = itemView.findViewById(R.id.hikeDate);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
