package com.mhdhussein.aralpackagemanagement;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mhdhussein.aralpackagemanagement.api.ApiService;
import com.mhdhussein.aralpackagemanagement.api.AralClient;
import com.mhdhussein.aralpackagemanagement.model.Attendance;
import com.mhdhussein.aralpackagemanagement.model.Courier;
import com.mhdhussein.aralpackagemanagement.model.SummaryData;
import com.mhdhussein.aralpackagemanagement.utils.UserHelper;
import com.mhdhussein.aralpackagemanagement.widgets.ViewLoadingDotsBounce;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.mhdhussein.aralpackagemanagement.Constants.DAY_SUMMARY;
import static com.mhdhussein.aralpackagemanagement.Constants.MONTH_SUMMARY;
import static com.mhdhussein.aralpackagemanagement.Constants.WEEK_SUMMARY;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.reports_btn)
    FloatingActionButton reportsBtn;

    @BindView(R.id.logout_btn)
    FloatingActionButton logoutBtn;

    @BindView(R.id.couriers_btn)
    FloatingActionButton couriersBtn;

    @BindView(R.id.attendance)
    TextView attendance;

    @BindView(R.id.qr_attendance)
    FloatingActionButton qrAttendance;

    @BindView(R.id.deposited_amount)
    TextView deposited;

    @BindView(R.id.deficit)
    TextView deficit;

    @BindView(R.id.lost)
    TextView lost;

    @BindView(R.id.setting_btn)
    FloatingActionButton setting;

    @BindView(R.id.ofd)
    TextView ofdTv;

    @BindView(R.id.cod)
    TextView codTv;

    @BindView(R.id.tfd)
    TextView tfdTv;

    @BindView(R.id.avg)
    TextView avgTv;

    @BindView(R.id.summaryTv)
    TextView summaryTV;

    @BindView(R.id.loading)
    ViewLoadingDotsBounce loading;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private final String Tag = "home_tag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


        toolbar.setTitle(UserHelper.getUser(this).getName());



        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSettingPopup(view);
            }
        });

        reportsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reportIntent = new Intent(MainActivity.this , ReportsActivity.class);
                startActivity(reportIntent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserHelper.logout(MainActivity.this);

                Intent login = new Intent(MainActivity.this , LoginActivity.class);
                startActivity(login);
                finish();
            }
        });

        qrAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        couriersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent attendanceIntent = new Intent(MainActivity.this , AttendanceActivity.class);
                startActivity(attendanceIntent);
            }
        });

    }


    private void loadData(final int type){
        ApiService apiService = AralClient.getApiService();

        Observable<SummaryData> summary = apiService.getSummary(
                UserHelper.getToken(this),
                UserHelper.getUser(this).getId(),
                type
        )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        summary.subscribe(new Observer<SummaryData>() {
            @Override
            public void onSubscribe(Disposable d) {
                    loading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNext(SummaryData summary) {

                if (type == DAY_SUMMARY){
                    summaryTV.setText("TODAY'S SUMMARY");
                }

                if (type == WEEK_SUMMARY){
                    summaryTV.setText("7 DAY SUMMARY");
                }

                if (type == MONTH_SUMMARY){
                    summaryTV.setText("30 DAY SUMMARY");
                }

                ofdTv.setText(String.valueOf(summary.getData().getOfd()));
                tfdTv.setText(String.valueOf(summary.getData().getTfd()));
                codTv.setText(String.valueOf(summary.getData().getCod())    );
                avgTv.setText("AVG " + String.valueOf(summary.getData().getAvg()) + " %");
                deficit.setText(String.valueOf(summary.getData().getDeficit()) + " SAR");
                deposited.setText(String.valueOf(summary.getData().getDeposited()) + " SAR");
                lost.setText(String.valueOf(summary.getData().getLost()) + " PPACKAGES");


            }

            @Override
            public void onError(Throwable e) {

                Log.d(Tag , e.getMessage());

                if (e.getMessage().contains("Unauthorized")){
                    UserHelper.logout(MainActivity.this);
                    Intent login = new Intent(MainActivity.this , LoginActivity.class);
                    login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(login);
                    finish();
                }

            }

            @Override
            public void onComplete() {
                loading.setVisibility(View.GONE);
                setAttendanceCount();
                Observable<Integer> courierCount = apiService.getCourierCount(
                        UserHelper.getToken(MainActivity.this),
                        UserHelper.getUser(MainActivity.this).getId()
                )
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread());

                courierCount.subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        toolbar.setSubtitle(integer + " Active Couriers");
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

    private void showSettingPopup(View view) {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this , view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.action_day){
                    loadData(DAY_SUMMARY);


                }
                if (item.getItemId() == R.id.action_week){
                    loadData(WEEK_SUMMARY);

                }
                if (item.getItemId() == R.id.action_month){
                    loadData(MONTH_SUMMARY);


                }
                return true;
            }
        });
        popupMenu.inflate(R.menu.menu_data_setting);
        popupMenu.show();
    }

    @Override
    protected void onResume() {
        super.onResume();


        loadData(DAY_SUMMARY);

    }

    private void checkCourier(String idNumber)
    {
        ApiService apiService = AralClient.getApiService();
        Observable<Courier> courierOb = apiService.getCourier(
                UserHelper.getToken(MainActivity.this),
                idNumber
        )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        courierOb.subscribe(new Observer<Courier>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Courier courier) {

//                Log.d("ATTENDANCE" , courier.getName());



                if (! courier.getIdNumber().startsWith("9") ) {
                    toastIconError("The Courier Number is Wrong");
                }
                else if (!courier.getStatus().equals("Active")) {
                    toastIconError("This Courier has been suspended or terminated");
                }
                else if(!Integer.valueOf(courier.getSupervisorId()).equals(Integer.valueOf(UserHelper.getUser(MainActivity.this).getId())) ){
                    toastIconError("This courier belongs to another supervisor");
                } else {

                   checkAttended(Integer.valueOf(courier.getIdNumber()));

                }


            }

            @Override
            public void onError(Throwable e) {
                Log.d("ATTENDANCE" , e.getMessage());
                toastIconError("This Couiers doesnot exist in the database");
            }

            @Override
            public void onComplete() {

            }
        });
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode , resultCode , data);

        if (result != null){

            if (result.getContents() == null){
                toastIconError("You Canceled Scanning");
            }else {
//                toastIconSuccess("Courier " + result.getContents());
                checkCourier(result.getContents());
            }

        }else{

            super.onActivityResult(requestCode, resultCode, data);
        }


    }


    private void setAttendanceCount(){

        ApiService apiService = AralClient.getApiService();

        Observable<Integer> countOB = apiService.getAttendanceCount(
                UserHelper.getToken(MainActivity.this),
                UserHelper.getUser(MainActivity.this).getId()
        )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        countOB.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                    attendance.setText(String.valueOf(integer));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    private void checkAttended(int courierNumber){


        ApiService apiService = AralClient.getApiService();

        Observable<Integer> isAttended = apiService.isAttended(
                UserHelper.getToken(MainActivity.this),
               Integer.valueOf(courierNumber)
        )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        isAttended.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer attendedVal) {

                if (attendedVal >= 1){
                    toastIconError("Courier Attendance Added Already !");
                }else {
                    Log.d("ATTENDANCE" , "attending");
                    Observable<Attendance> createAttendance = apiService.createAttendance(
                            UserHelper.getToken(MainActivity.this),
                            courierNumber,
                            UserHelper.getUser(MainActivity.this).getId()
                    )

                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread());

                    createAttendance.subscribe(new Observer<Attendance>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            loading.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onNext(Attendance attendance) {

                            if (attendance != null){
                                toastIconSuccess("Attendance added");
                            }else {
                                Log.d("ATTENDANCE" , attendance.toString());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            toastIconError(e.getMessage());
                            Log.d("ATTENDANCE" , e.getMessage());
                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {
                            loading.setVisibility(View.GONE);
                            setAttendanceCount();
                        }
                    });


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
}
