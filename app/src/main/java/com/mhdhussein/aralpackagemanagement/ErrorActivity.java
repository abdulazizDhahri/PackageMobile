package com.mhdhussein.aralpackagemanagement;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.mhdhussein.aralpackagemanagement.utils.UserHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mhdhussein.aralpackagemanagement.Constants.PREF_KEY_TOKEN;
import static com.mhdhussein.aralpackagemanagement.Constants.PREF_KEY_TOKEN_REFERESH;

public class ErrorActivity extends AppCompatActivity {


    @BindView(R.id.bt_signout)
    AppCompatButton signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        ButterKnife.bind(this);

        signOut.setOnClickListener(view -> {

            UserHelper.logout(ErrorActivity.this);

            Intent login = new Intent(ErrorActivity.this , LoginActivity.class);
            startActivity(login);
            finish();

        });

    }
}
