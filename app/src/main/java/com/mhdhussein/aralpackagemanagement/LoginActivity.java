package com.mhdhussein.aralpackagemanagement;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mhdhussein.aralpackagemanagement.api.AralClient;
import com.mhdhussein.aralpackagemanagement.api.ApiService;
import com.mhdhussein.aralpackagemanagement.model.Token;
import com.mhdhussein.aralpackagemanagement.model.User;
import com.mhdhussein.aralpackagemanagement.utils.UserHelper;
import com.mhdhussein.aralpackagemanagement.widgets.ViewLoadingDotsBounce;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mhdhussein.aralpackagemanagement.Constants.CLIENT_ID;
import static com.mhdhussein.aralpackagemanagement.Constants.CLIENT_SECRET;
import static com.mhdhussein.aralpackagemanagement.Constants.GRANT_TYPE;
import static com.mhdhussein.aralpackagemanagement.Constants.PREF_KEY_TOKEN;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "LOGIN_ATTEMPT";

    @BindView(R.id.id_number)
    TextInputEditText idNumber;

    @BindView(R.id.password)
    TextInputEditText password;

    @BindView(R.id.sign_in)
    Button signIn;

    @BindView(R.id.loading)
    ViewLoadingDotsBounce loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);



        ApiService apiService = AralClient.getApiService();


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (idNumber.getText().toString().length() > 0 &&
                        password.getText().toString().length() > 0
                        ) {

                    Scheduler networkThread = Schedulers.newThread();
                    Observable<Token> attemptLogin = apiService.login(
                            Integer.valueOf(idNumber.getText().toString()),
                            password.getText().toString(),
                            GRANT_TYPE,
                            CLIENT_SECRET,
                            CLIENT_ID
                    )

                            .subscribeOn(networkThread)
                            .observeOn(AndroidSchedulers.mainThread());

                    attemptLogin.subscribe(new Observer<Token>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            loading.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onNext(Token token) {
                            Log.d(TAG , token.getTokenType());

                            PreferenceManager.getDefaultSharedPreferences(LoginActivity.this)
                                    .edit().putString(PREF_KEY_TOKEN , token.getAccessToken()).apply();

                            UserHelper.setUser(LoginActivity.this);
                            loading.setVisibility(View.GONE);




                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG , e.getMessage());
                            toastIconError();
                            loading.setVisibility(View.GONE);
                        }

                        @Override
                        public void onComplete() {


//                        finish();

                        }
                    });

                }

            }
        });


    }

    private void toastIconError() {
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);

        //inflate view
        View custom_view = getLayoutInflater().inflate(R.layout.toast_icon_text, null);
        ((TextView) custom_view.findViewById(R.id.message)).setText("ID Number Or Password is Incorrect");
        ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_close);
        ((CardView) custom_view.findViewById(R.id.parent_view)).setCardBackgroundColor(getResources().getColor(R.color.red_600));

        toast.setView(custom_view);
        toast.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (UserHelper.isLoggedIn(this)){
            finish();
        }
    }
}
