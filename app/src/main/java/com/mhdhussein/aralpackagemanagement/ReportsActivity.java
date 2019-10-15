package com.mhdhussein.aralpackagemanagement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mhdhussein.aralpackagemanagement.adapters.ReportAdapter;
import com.mhdhussein.aralpackagemanagement.api.ApiService;
import com.mhdhussein.aralpackagemanagement.api.AralClient;
import com.mhdhussein.aralpackagemanagement.model.MainReport;
import com.mhdhussein.aralpackagemanagement.model.MainReportData;
import com.mhdhussein.aralpackagemanagement.utils.UserHelper;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.mhdhussein.aralpackagemanagement.Constants.PICKER_TYPE_FROM;
import static com.mhdhussein.aralpackagemanagement.Constants.PICKER_TYPE_TO;

public class ReportsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private static final String TAG = "ReportsActivity";



    private String fromDate;
    private String toDate;

    @BindView(R.id.recyclerView)
    RecyclerView reportList;
//
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.no_reports)
    TextView noReports;

    @BindView(R.id.fromDate)
    TextView fromDateTV;

    @BindView(R.id.toDate)
    TextView toDateTV;

    private static ArrayList<MainReport> mainReports = new ArrayList<>();
    private static ReportAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        ButterKnife.bind(this);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Report");

        fromDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDatePickerLight(PICKER_TYPE_FROM);
            }
        });

        toDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDatePickerLight(PICKER_TYPE_TO);
            }
        });


    }


    private void loadData(String from , String to){

        ApiService apiService = AralClient.getApiService();

        Observable<MainReportData> mainReports = apiService.getMainReport(
                UserHelper.getToken(this),
                from,
                to,
                UserHelper.getUser(this).getId()
        )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        mainReports.subscribe(new Observer<MainReportData>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(MainReportData summaryData) {

                ReportsActivity.mainReports = summaryData.getData();

                if (summaryData.getData().size() > 0){
                    noReports.setVisibility(View.GONE);
                    reportList.setVisibility(View.VISIBLE);
                    ReportsActivity.adapter = new ReportAdapter(ReportsActivity.this , summaryData.getData());
                    reportList.setAdapter(adapter);
                    reportList.setLayoutManager(new LinearLayoutManager(ReportsActivity.this));
                }else {
                    noReports.setVisibility(View.VISIBLE);
                    reportList.setVisibility(View.GONE);
                }

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG , e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void dialogDatePickerLight(final int type) {
        Calendar cur_calender = Calendar.getInstance();
        DatePickerDialog datePicker = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        Date selected = calendar.getTime();
                        if (type == PICKER_TYPE_FROM){

                            fromDate = new SimpleDateFormat("dd-MM-yyyy").format(selected);
                            fromDateTV.setText("FROM : " + fromDate);

//                            if (compareDates()){
                                loadData(arabicToDecimal(fromDate) , arabicToDecimal(toDate));
//                            }

                        }else {
                            toDate = new SimpleDateFormat("dd-MM-yyyy").format(selected);
                            toDateTV.setText("TO : " + toDate);


//                            if (compareDates()){
                                loadData(arabicToDecimal(fromDate) , arabicToDecimal(toDate));
//                            }
                        }
//
                    }
                },
                cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH)
        );
        //set dark light
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));

        datePicker.show(getFragmentManager(), "Datepickerdialog");
    }

    private boolean compareDates() {

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date from = df.parse(fromDate);
            Date to = df.parse(toDate);

            return to.after(from) || to.equals(from);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        fromDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date()).toString();
        toDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date()).toString();

        fromDateTV.setText("FROM : " +fromDate);
        toDateTV.setText("TO : " + toDate);

        loadData(arabicToDecimal(fromDate) , arabicToDecimal(toDate));

        Log.d(TAG , "DATE IS " + fromDate);
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

        ArrayList<MainReport> newList = new ArrayList<>();

        for (MainReport report : ReportsActivity.mainReports)
        {
            if (report.getCourierNumber().contains(courierNumber)){
                newList.add(report);
            }
        }

        ReportsActivity.adapter.updateList(newList);



        return true;
    }

    private static String arabicToDecimal(String number) {
        char[] chars = new char[number.length()];
        for(int i=0;i<number.length();i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669 && ch !='/')
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9 && ch !='/')
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
        Log.d("DATES" , new String(chars));
        return new String(chars);
    }

}
