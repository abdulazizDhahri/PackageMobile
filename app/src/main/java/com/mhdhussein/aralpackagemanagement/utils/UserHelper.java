package com.mhdhussein.aralpackagemanagement.utils;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;

import com.mhdhussein.aralpackagemanagement.ErrorActivity;
import com.mhdhussein.aralpackagemanagement.LoginActivity;
import com.mhdhussein.aralpackagemanagement.MainActivity;
import com.mhdhussein.aralpackagemanagement.api.ApiService;
import com.mhdhussein.aralpackagemanagement.api.AralClient;
import com.mhdhussein.aralpackagemanagement.model.User;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.mhdhussein.aralpackagemanagement.Constants.PREF_KEY_ID;
import static com.mhdhussein.aralpackagemanagement.Constants.PREF_KEY_NAME;
import static com.mhdhussein.aralpackagemanagement.Constants.PREF_KEY_ROLE;
import static com.mhdhussein.aralpackagemanagement.Constants.PREF_KEY_TOKEN;
import static com.mhdhussein.aralpackagemanagement.Constants.PREF_KEY_TOKEN_REFERESH;


public class UserHelper {

    private static final String TAG = "LOGIN_ATTEMPT";


    public static boolean isLoggedIn(Context context){

        String token = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_KEY_TOKEN , "");

        return !token.equals("");

    }

    public static boolean isSupervisor(Context context){



        String token = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_KEY_TOKEN , "");

        String role = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_KEY_ROLE , "");


        return role.equals("Supervisor");


    }

    public static void setUser(Context context){


        String token = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_KEY_TOKEN , "");

        ApiService apiService = AralClient.getApiService();


        Observable<User> user = apiService.currentUser("Bearer " + token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        user.subscribe(new Observer<User>() {
            @Override
            public void onSubscribe(Disposable d) {


            }

            @Override
            public void onNext(User user) {


                PreferenceManager.getDefaultSharedPreferences(context)
                        .edit().putString(PREF_KEY_ROLE , user.getRole()).apply();

                Log.d(TAG , user.getRole());

                PreferenceManager.getDefaultSharedPreferences(context)
                        .edit().putString(PREF_KEY_NAME , user.getName()).apply();

                PreferenceManager.getDefaultSharedPreferences(context)
                        .edit().putInt(PREF_KEY_ID , user.getId()).apply();


                if (UserHelper.isSupervisor(context)) {



                    Intent mainPage = new Intent(context, MainActivity.class);
                    mainPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(mainPage);


                } else{
                    Intent errorPage = new Intent(context, ErrorActivity.class);
                    errorPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(errorPage);

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

    public static void logout(Context context){

        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(PREF_KEY_ROLE , "").apply();

        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(PREF_KEY_NAME , "").apply();

        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(PREF_KEY_ID , "").apply();

        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(PREF_KEY_TOKEN , "").apply();

        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(PREF_KEY_TOKEN_REFERESH , "").apply();
    }

    public static String getToken(Context context){
        return "Bearer " + PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_KEY_TOKEN , "");
    }

    public static User getUser(Context context){

        String name = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_KEY_NAME , "");

        String role = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_KEY_ROLE , "");

        int id = PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(PREF_KEY_ID , 0);

        User user = new User();

        user.setName(name);
        user.setRole(role);
        user.setId(id);

        return user;

    }
}
