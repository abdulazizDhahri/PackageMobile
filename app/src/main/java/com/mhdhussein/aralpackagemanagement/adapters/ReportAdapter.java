package com.mhdhussein.aralpackagemanagement.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mhdhussein.aralpackagemanagement.R;
import com.mhdhussein.aralpackagemanagement.model.MainReport;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    private static final String TAG = "ReportAdapter";

    private Context mContext;
    private ArrayList<MainReport> mainReports = new ArrayList<>();

    public ReportAdapter(Context mContext, ArrayList<MainReport> mainReports) {
        this.mContext = mContext;
        this.mainReports = mainReports;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_report_card ,
                viewGroup , false);


        ViewHolder holder = new ViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG , "onBindViewHolder Called");

        viewHolder.courierId.setText("#" + mainReports.get(i).getCourierNumber());
        viewHolder.courierName.setText(mainReports.get(i).getCourier());
        viewHolder.ofd.setText(mainReports.get(i).getTotalSent());
        viewHolder.cod.setText(mainReports.get(i).getAmountSent());
        viewHolder.crd.setText(mainReports.get(i).getTotalCredit());
        viewHolder.tfd.setText(mainReports.get(i).getDelivered());
        viewHolder.tfr.setText(mainReports.get(i).getReturned());
        viewHolder.deposited.setText(mainReports.get(i).getDeposited());
        viewHolder.lost.setText(mainReports.get(i).getLost());
        viewHolder.deficit.setText(mainReports.get(i).getDeficit());
        viewHolder.workingDays.setText(mainReports.get(i).getTotalAttendance());

//        viewHolder.reportItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mContext , "Clicked  " + mainReports.get(i).getCourier() , Toast.LENGTH_LONG).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mainReports.size();
    }

    public void updateList(ArrayList<MainReport> newList){
        mainReports = new ArrayList<>();
        mainReports.addAll(newList);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.report_layout)
        CardView reportItem;

        @BindView(R.id.courier_name)
        TextView courierName;

        @BindView(R.id.working_days)
        TextView workingDays;


        @BindView(R.id.ofd)
        TextView ofd;


        @BindView(R.id.cod)
        TextView cod;


        @BindView(R.id.crd)
        TextView crd;


        @BindView(R.id.tfd)
        TextView tfd;


        @BindView(R.id.tfr)
        TextView tfr;


        @BindView(R.id.deposited)
        TextView deposited;


        @BindView(R.id.lost)
        TextView lost;


        @BindView(R.id.deficit)
        TextView deficit;

        @BindView(R.id.courier_id)
        TextView courierId;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this , itemView);
        }
    }
}
