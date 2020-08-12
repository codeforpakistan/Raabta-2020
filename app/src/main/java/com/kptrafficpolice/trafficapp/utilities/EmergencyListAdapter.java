package com.kptrafficpolice.trafficapp.utilities;

/**
 * Created by Asus on 6/15/2017.
 */

//raabta
//rabta
import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.kptrafficpolice.trafficapp.R;
import com.kptrafficpolice.trafficapp.fragments.EmergencyFragmentList;

public class EmergencyListAdapter extends RecyclerView.Adapter<EmergencyListAdapter.ViewHolder> {
    private ArrayList<String> mDataset;
    List<EmergencyHelper> emergencyHelperList;
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private FirebaseAnalytics mFirebaseAnalytics;

    public class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        CardView cvEmergencyItem;
        TextView tvEmergencyHelperName, tvEmergencyPhone, tvDivisionName, tvEmergencyLocation, tvEmergencyDistance, tvCallNow;
        ImageView ivCallNow, ivHelperIcon;
        LinearLayout lLCallNow;


        public ViewHolder(View v) {
            super(v);
            sharedPreferences = context.getSharedPreferences("com.ghosttech.kptraffic", 0);
            editor = sharedPreferences.edit();

            tvEmergencyHelperName = (TextView) itemView.findViewById(R.id.tv_emergency_helper_name);
            tvEmergencyPhone = (TextView) itemView.findViewById(R.id.tv_emergency_phone);
            tvEmergencyDistance = (TextView) itemView.findViewById(R.id.tvDistance);
            tvEmergencyLocation = (TextView) itemView.findViewById(R.id.tv_emergency_location);
            ivCallNow = (ImageView) itemView.findViewById(R.id.ivCallNow);
            ivHelperIcon = (ImageView) itemView.findViewById(R.id.iv_emergency_icon);
            lLCallNow = (LinearLayout) itemView.findViewById(R.id.ll_call_now);
            tvCallNow = (TextView) itemView.findViewById(R.id.tvCallNow);
            cvEmergencyItem = (CardView)itemView.findViewById(R.id.cv_item_view);
            SpannableString content = new SpannableString("Call Now");
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            tvCallNow.setText(content);

        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public EmergencyListAdapter(Context context, List<EmergencyHelper> emergencyHelperList) {
        this.emergencyHelperList = emergencyHelperList;
        ;
        this.context = context;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public EmergencyListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_emergency_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String strClickedItem = sharedPreferences.getString("clicked_item","");
        if (strClickedItem.equals("health")){
            holder.ivHelperIcon.setImageResource(R.drawable.emergency_health);
        }else if (strClickedItem.equals("mechanics")){
            holder.ivHelperIcon.setImageResource(R.drawable.mechanics_icon_emergency);
        }else if (strClickedItem.equals("officer")){
            holder.ivHelperIcon.setImageResource(R.drawable.emergency_highway_officer);
        }
        final EmergencyHelper helper = emergencyHelperList.get(position);
        holder.tvEmergencyHelperName.setText(helper.getStrHelperName());
        String strDistance = helper.getStrHelperDistance();
        if (!EmergencyFragmentList.DistanceORDivision) {
            if (!strDistance.equals(""))
                strDistance = Double.parseDouble(strDistance) * 1000 + "";

            if (Double.parseDouble(strDistance) > 1000) {
                String strDistanceNew = helper.getStrHelperDistance();

                strDistanceNew = strDistanceNew.substring(0, 3);
                holder.tvEmergencyDistance.setText(strDistanceNew + " km");
            } else {
                strDistance = strDistance.substring(0, 3);
                holder.tvEmergencyDistance.setText(strDistance + " m");
            }
        }else {
            holder.tvEmergencyDistance.setVisibility(View.GONE);
        }
        holder.tvEmergencyLocation.setText(helper.getStrHelperLocation());
        holder.tvEmergencyPhone.setText(helper.getStrHelperPhoneNumber());
        holder.lLCallNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "9");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Emergency Contacts personal");
                mFirebaseAnalytics.logEvent(strClickedItem+" Call made", bundle);


                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + helper.getStrHelperPhoneNumber()));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                context.startActivity(callIntent);
                }
            });
        holder.cvEmergencyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + helper.getStrHelperPhoneNumber()));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                context.startActivity(callIntent);
            }
        });


        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return emergencyHelperList.size();
        }
        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

    }

