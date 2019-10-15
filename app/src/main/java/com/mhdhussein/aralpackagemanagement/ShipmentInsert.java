package com.mhdhussein.aralpackagemanagement;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mhdhussein.aralpackagemanagement.api.ApiService;
import com.mhdhussein.aralpackagemanagement.api.AralClient;
import com.mhdhussein.aralpackagemanagement.model.ShipmentEntryData;
import com.mhdhussein.aralpackagemanagement.utils.UserHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.mhdhussein.aralpackagemanagement.Constants.SHIPMENT_ENTRY_INSERT;
import static com.mhdhussein.aralpackagemanagement.Constants.SHIPMENT_ENTRY_UPDATE;

public class ShipmentInsert extends AppCompatActivity {


    private int formType;
    private String courierName;
    private int courierNumber;

    @BindView(R.id.ofd)
    TextInputEditText ofd;

    @BindView(R.id.package_value)
    TextInputEditText packageValue;

    @BindView(R.id.credit)
    TextInputEditText creditInput;

    @BindView(R.id.paidInCredit)
    TextInputEditText paidInCredit;

    @BindView(R.id.cash)
    TextInputEditText cashInput;

    @BindView(R.id.paidInCash)
    TextInputEditText paidInCash;

    @BindView(R.id.deposited)
    TextInputEditText deposited;

    @BindView(R.id.delivered)
    TextInputEditText delivered;

    @BindView(R.id.returned)
    TextInputEditText returned;

    @BindView(R.id.lost)
    TextInputEditText lost;

    @BindView(R.id.deficit)
    TextInputEditText deficit;

    @BindView(R.id.fuel)
    TextInputEditText fuel;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab_done)
    FloatingActionButton  doneBtn;

    private int shipmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment_insert);

        ButterKnife.bind(this);

        formType = getIntent().getIntExtra("type" , 0);
        courierName = getIntent().getStringExtra("courier_name");
        courierNumber = getIntent().getIntExtra("courier_id" , 0);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(courierName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        disableIfNew();

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (formType == SHIPMENT_ENTRY_INSERT){
                    insertInitialData();
                }

                if (formType == SHIPMENT_ENTRY_UPDATE){
                    updateData();
                }
            }
        });


        if (formType == SHIPMENT_ENTRY_UPDATE)
        {
            loadShipmentData();
        }




    }

    private void updateData() {
        ApiService apiService = AralClient.getApiService();
        Observable<ShipmentEntryData> shipmentUpdate = apiService.insertShipmentEnd(
                UserHelper.getToken(this),
                shipmentId,
                courierNumber,
                UserHelper.getUser(this).getId(),
                Double.valueOf(ofd.getText().toString()),
                Double.valueOf(packageValue.getText().toString()),
                Double.valueOf(cashInput.getText().toString()),
                Double.valueOf(paidInCash.getText().toString()),
                Double.valueOf(creditInput.getText().toString()),
                Double.valueOf(paidInCredit.getText().toString()),
                Double.valueOf(deposited.getText().toString()),
                Double.valueOf(returned.getText().toString()),
                Double.valueOf(delivered.getText().toString()),
                Double.valueOf(deficit.getText().toString()),
                Double.valueOf(lost.getText().toString()),
                Double.valueOf(fuel.getText().toString())
        )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        shipmentUpdate.subscribe(new Observer<ShipmentEntryData>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ShipmentEntryData shipmentEntryData) {
                toastIconSuccess("Shipment Data Added Successfully");
                onBackPressed();
            }

            @Override
            public void onError(Throwable e) {
                Log.d("DATA_ENTRY_UPDATE" , e.getMessage());
                toastIconError("Error Updating Data, Please Check your connection");
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void loadShipmentData() {

        ApiService apiService = AralClient.getApiService();

        Observable<ShipmentEntryData> getShipment = apiService.getShipment(
                UserHelper.getToken(this),
                courierNumber
        )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        getShipment.subscribe(new Observer<ShipmentEntryData>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ShipmentEntryData shipmentEntryData) {

                shipmentId = shipmentEntryData.getData().getId();
                ofd.setText(shipmentEntryData.getData().getTotalSent());
                packageValue.setText(shipmentEntryData.getData().getAmountSent());
                cashInput.setText(shipmentEntryData.getData().getTotalCash());
                creditInput.setText(shipmentEntryData.getData().getTotalCredit());
                deposited.setText(shipmentEntryData.getData().getDeposited());
                delivered.setText(shipmentEntryData.getData().getDelivered());
                returned.setText(shipmentEntryData.getData().getReturned());
                lost.setText(shipmentEntryData.getData().getLost());
                deficit.setText(shipmentEntryData.getData().getDeficit());
                fuel.setText(shipmentEntryData.getData().getFuel());
                paidInCash.setText(shipmentEntryData.getData().getTotalCashPackages());
                paidInCredit.setText(shipmentEntryData.getData().getTotalCreditPackages());

                Log.d("PACKAGE_COUNT" , "data is " + shipmentEntryData.getData().getTotalCashPackages());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void insertInitialData() {

        ApiService apiService = AralClient.getApiService();

        Observable<ShipmentEntryData> shipmentInsert = apiService.insertShipment(
                UserHelper.getToken(this),
                courierNumber,
                UserHelper.getUser(this).getId(),
                Integer.valueOf(ofd.getText().toString()),
                Double.valueOf(packageValue.getText().toString())

        )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        shipmentInsert.subscribe(new Observer<ShipmentEntryData>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ShipmentEntryData shipmentEntryData) {

                toastIconSuccess("Shipment Data Added Successfully");
                onBackPressed();
            }

            @Override
            public void onError(Throwable e) {
                toastIconError("Error Inserting Data, Please Check your connection");
            }

            @Override
            public void onComplete() {

            }
        });
    }


    public void disableIfNew(){

        if (formType == SHIPMENT_ENTRY_INSERT){
            cashInput.setEnabled(false);
            creditInput.setEnabled(false);
            deposited.setEnabled(false);
            delivered.setEnabled(false);
            returned.setEnabled(false);
            lost.setEnabled(false);
            deficit.setEnabled(false);
            fuel.setEnabled(false);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void toastIconError(String message) {
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);

        //inflate view
        View custom_view = getLayoutInflater().inflate(R.layout.toast_icon_text, null);
        ((TextView) custom_view.findViewById(R.id.message))
                .setText(message);
        ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_close);
        ((CardView) custom_view.findViewById(R.id.parent_view)).setCardBackgroundColor(getResources().getColor(R.color.red_600));

        toast.setView(custom_view);
        toast.show();
    }

    private void toastIconSuccess(String message) {
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);

        //inflate view
        View custom_view = getLayoutInflater().inflate(R.layout.toast_icon_text, null);
        ((TextView) custom_view.findViewById(R.id.message))
                .setText(message);
        ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_check);
        ((CardView) custom_view.findViewById(R.id.parent_view)).setCardBackgroundColor(getResources().getColor(R.color.green_600));

        toast.setView(custom_view);
        toast.show();
    }

}
