package com.kptrafficpolice.trafficapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kptrafficpolice.trafficapp.Model.OffenceModel;
import com.kptrafficpolice.trafficapp.R;

import java.util.ArrayList;
import java.util.List;

public class OffenceAdapter extends RecyclerView.Adapter<OffenceAdapter.MyViewHolder> {

    ArrayList<OffenceModel> offence_list=new ArrayList<OffenceModel>();
    Context context;

    public OffenceAdapter(ArrayList offence_list, Context context) {
        this.offence_list = offence_list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offence_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OffenceModel offenceModel = offence_list.get(position);
        Log.d("check",offenceModel.getCar_offence_fee());
        holder.car_offence_txt.setText(offenceModel.getCar_offence_fee());
        holder.offence_title.setText(offenceModel.getOffence_title());
        holder.bike_offence_fee.setText(offenceModel.getBike_offence_fee());
        holder.jeep_offence_fee.setText(offenceModel.getJeep_offence_fee());
        holder.truck_offence_fee.setText(offenceModel.getTruck_offence_fee());
        holder.bus_offence_fee.setText(offenceModel.getBus_offence_fee());
        holder.trolley_offence_fee.setText(offenceModel.getTrolley_offence_fee());
        holder.offence_title_URDU.setText(offenceModel.getOffence_title_URDU());

    }

    @Override
    public int getItemCount() {
        return offence_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView car_offence_txt, offence_title, bike_offence_fee,jeep_offence_fee,truck_offence_fee,
                bus_offence_fee,trolley_offence_fee,offence_title_URDU;

        public MyViewHolder(View view) {
            super(view);
            car_offence_txt = (TextView) view.findViewById(R.id.car_offence_fee);
            offence_title = (TextView) view.findViewById(R.id.offence_title);
            bike_offence_fee = (TextView) view.findViewById(R.id.bike_offence_fee);
            jeep_offence_fee = (TextView) view.findViewById(R.id.jeep_offence_fee);
            truck_offence_fee = (TextView) view.findViewById(R.id.truck_offence_fee);
            bus_offence_fee = (TextView) view.findViewById(R.id.bus_offence_fee);
            trolley_offence_fee = (TextView) view.findViewById(R.id.trolley_offence_fee);
            offence_title_URDU = (TextView) view.findViewById(R.id.offence_title_URDU);
        }
    }
}
