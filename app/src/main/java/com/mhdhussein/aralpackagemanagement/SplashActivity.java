package com.mhdhussein.aralpackagemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mhdhussein.aralpackagemanagement.utils.UserHelper;

import static com.mhdhussein.aralpackagemanagement.Constants.PREF_KEY_TOKEN;

public class SplashActivity extends AppCompatActivity {


    private String token = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Intent intent = new Intent(SplashActivity.this , LoginActivity.class);
        startActivity(intent);

        if (UserHelper.isLoggedIn(SplashActivity.this)){

            if(UserHelper.isSupervisor(SplashActivity.this)){
                Intent mainPage = new Intent(SplashActivity.this , MainActivity.class);
                mainPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainPage);
                finish();
            }else{
                Intent errorPage = new Intent(SplashActivity.this , ErrorActivity.class);
                errorPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(errorPage);
                finish();
            }

        }


    }
}
