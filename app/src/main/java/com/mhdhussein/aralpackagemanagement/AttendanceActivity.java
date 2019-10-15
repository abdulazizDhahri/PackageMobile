package com.mhdhussein.aralpackagemanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mhdhussein.aralpackagemanagement.adapters.AttendanceAdapter;
import com.mhdhussein.aralpackagemanagement.api.ApiService;
import com.mhdhussein.aralpackagemanagement.api.AralClient;
import com.mhdhussein.aralpackagemanagement.model.CourierAttendance;
import com.mhdhussein.aralpackagemanagement.model.CourierAttendanceData;
import com.mhdhussein.aralpackagemanagement.utils.UserHelper;
import com.mhdhussein.aralpackagemanagement.widgets.ViewLoadingDotsBounce;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AttendanceActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.no_entries)
    TextView noEntries;

    @BindView(R.id.recyclerView)
    RecyclerView attendanceList;


    private static ArrayList<CourierAttendance> attendances;
    private static AttendanceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Attentance");

        loadData();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadData(){

        ApiService apiService = AralClient.getApiService();

        Observable<CourierAttendanceData> attendance = apiService.getAttendedCourier(
                UserHelper.getToken(this),
                UserHelper.getUser(this).getId()
        )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        attendance.subscribe(new Observer<CourierAttendanceData>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(CourierAttendanceData courierAttendanceData) {

                AttendanceActivity.attendances = courierAttendanceData.getData();

                if (courierAttendanceData.getData().size() > 0) {
                    noEntries.setVisibility(View.GONE);
                    attendanceList.setVisibility(View.VISIBLE);

                    AttendanceActivity.adapter = new AttendanceAdapter(AttendanceActivity.this , courierAttendanceData.getData());
                    attendanceList.setAdapter(AttendanceActivity.adapter);
                    attendanceList.setLayoutManager(new LinearLayoutManager(AttendanceActivity.this));

                }else {
                    noEntries.setVisibility(View.VISIBLE);
                    attendanceList.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d("CourierAttendance" , e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu , menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(this);

        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String s) {


        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {


        String courierNumber = s;

        ArrayList<CourierAttendance> newList = new ArrayList<>();

        for (CourierAttendance courierAttendance : AttendanceActivity.attendances){
            if (String.valueOf(courierAttendance.getCourierId()).contains(courierNumber)){
                newList.add(courierAttendance);
            }
        }

        AttendanceActivity.adapter.updateList(newList);

        return true;
    }
}
