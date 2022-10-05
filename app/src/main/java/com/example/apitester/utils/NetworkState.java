package com.example.apitester.utils;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.window.SplashScreen;

import androidx.annotation.Nullable;

import com.example.apitester.views.MainActivity;

public class NetworkState  {

    public static boolean isConnectedToInternet(Context context){

        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager!= null){
            NetworkInfo activeNetwork =  connectivityManager.getActiveNetworkInfo();
            if (activeNetwork != null){
               if (activeNetwork.getState() == NetworkInfo.State.CONNECTED) {
                   return true;
               }
            }
        }
        return false;
    }
}
