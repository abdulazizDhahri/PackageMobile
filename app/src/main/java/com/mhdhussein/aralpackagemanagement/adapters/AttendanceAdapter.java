package com.mhdhussein.aralpackagemanagement.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mhdhussein.aralpackagemanagement.AttendanceActivity;
import com.mhdhussein.aralpackagemanagement.R;
import com.mhdhussein.aralpackagemanagement.ShipmentInsert;
import com.mhdhussein.aralpackagemanagement.api.ApiService;
import com.mhdhussein.aralpackagemanagement.api.AralClient;
import com.mhdhussein.aralpackagemanagement.model.CourierAttendance;
import com.mhdhussein.aralpackagemanagement.utils.UserHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.mhdhussein.aralpackagemanagement.Constants.SHIPMENT_ENTRY_INSERT;
import static com.mhdhussein.aralpackagemanagement.Constants.SHIPMENT_ENTRY_UPDATE;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {


    private Context mContext;
    private ArrayList<CourierAttendance> courierAttendances = new ArrayList<>();

    public AttendanceAdapter(Context mContext, ArrayList<CourierAttendance> courierAttendances) {
        this.mContext = mContext;
        this.courierAttendances = courierAttendances;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_attendance_card,
                viewGroup , false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.courierName.setText(courierAttendances.get(i).getCourier());
        viewHolder.courierId.setText("#" + courierAttendances.get(i).getCourierId());
        viewHolder.time.setText(courierAttendances.get(i).getTime());
        viewHolder.late.setText(courierAttendances.get(i).getLateTime());

        viewHolder.courierCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ApiService apiService = AralClient.getApiService();

                Observable<Integer> isShipmentExists = apiService.isShipmentExists(
                        UserHelper.getToken(mContext),
                        courierAttendances.get(i).getCourierId()
                )
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread());

                isShipmentExists.subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {

                        if (value >= 1){
                            Intent insertShipment = new Intent(mContext , ShipmentInsert.class);
                            insertShipment.putExtra("type" , SHIPMENT_ENTRY_UPDATE);
                            insertShipment.putExtra("courier_name" , courierAttendances.get(i).getCourier());
                            insertShipment.putExtra("courier_id" , courierAttendances.get(i).getCourierId());
                            mContext.startActivity(insertShipment);

                        }else {
                            Intent insertShipment = new Intent(mContext , ShipmentInsert.class);
                            insertShipment.putExtra("type" , SHIPMENT_ENTRY_INSERT);
                            insertShipment.putExtra("courier_name" , courierAttendances.get(i).getCourier());
                            insertShipment.putExtra("courier_id" , courierAttendances.get(i).getCourierId());
                            mContext.startActivity(insertShipment);

                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return courierAttendances.size();
    }

    public void updateList(ArrayList<CourierAttendance> newList){
        courierAttendances = new ArrayList<>();
        courierAttendances.addAll(newList);
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.courier_name)
        TextView courierName;

        @BindView(R.id.courier_id)
        TextView courierId;

        @BindView(R.id.time)
        TextView time;

        @BindView(R.id.late)
        TextView late;

        @BindView(R.id.courierCard)
        CardView courierCard;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}
