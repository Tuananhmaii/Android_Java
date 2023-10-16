package com.example.coursework.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursework.Models.Observation;
import com.example.coursework.R;

import java.util.List;

public class ObservationAdapter extends RecyclerView.Adapter<ObservationAdapter.ObservationViewHolder>{
    private List<Observation> observations;
    private OnItemClickListener listener;

    public ObservationAdapter(List<Observation> observations) {
        this.observations = observations;
    }

    @NonNull
    @Override
    public ObservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.observation_card, parent, false);
        return new ObservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ObservationViewHolder holder, int position) {
        Observation observation = observations.get(position);
        holder.observationName.setText(observation.name);
        holder.observationTime.setText(observation.time);
        holder.observationDescription.setText(observation.description);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
    }

    public void setObservations(List<Observation> observations) {
        this.observations = observations;
    }

    @Override
    public int getItemCount() {
        return observations.size();
    }

    public static class ObservationViewHolder extends RecyclerView.ViewHolder {
        TextView observationName, observationTime, observationDescription;
        public ObservationViewHolder(@NonNull View itemView) {
            super(itemView);
            observationName = itemView.findViewById(R.id.observationName);
            observationTime = itemView.findViewById(R.id.observationTime);
            observationDescription = itemView.findViewById(R.id.observationDescription);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(ObservationAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
