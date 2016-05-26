package loop.ms.looptestuser;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import ms.loop.loopsdk.core.ILoopSDKCallback;
import ms.loop.loopsdk.core.LoopSDK;
import ms.loop.loopsdk.util.LoopError;

public class LoopTestUserApplication extends Application implements ILoopSDKCallback {
    public static final String LOOP_SDK_STATE = "loopSdkState";

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOOP_SDK_STATE, "uninitialized");
        editor.apply();

        // initialize the Loop SDK. create an account to get your appId and appToken
        String appId = "test-project-2-dev-fd24bef4";
        String appToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6InRlc3QtcHJvamVjdC0yLWRldi1mZDI0YmVmNCIsImFwcEtleSI6IjE2YzA2MTk3OTU5Mi00ODNjLWJmOWMtZmY5NDJiNWRmYTEyIiwiYWxsb3dlZFJvdXRlcyI6W3sibWV0aG9kIjoicG9zdCIsInBhdGgiOiIvdjIuMC9hcHAvdGVzdC1wcm9qZWN0LTItZGV2LWZkMjRiZWY0L3VzZXIifSx7Im1ldGhvZCI6ImRlbGV0ZSIsInBhdGgiOiIvdjIuMC9hcHAvdGVzdC1wcm9qZWN0LTItZGV2LWZkMjRiZWY0L3VzZXIifSx7Im1ldGhvZCI6InBvc3QiLCJwYXRoIjoiL3YyLjAvYXBwL3Rlc3QtcHJvamVjdC0yLWRldi1mZDI0YmVmNC9sb2dpbiJ9LHsibWV0aG9kIjoiZ2V0IiwicGF0aCI6Ii92Mi4wL2FwcC90ZXN0LXByb2plY3QtMi1kZXYtZmQyNGJlZjQvdXNlciJ9XSwiaWF0IjoxNDY0MzAyMzk3LCJpc3MiOiJMb29wIEF1dGggdjIiLCJzdWIiOiJ0ZXN0LXByb2plY3QtMi1kZXYtZmQyNGJlZjQifQ.nW61elgXldZ0-7dt0EIuwMW5O7D5afjg0IHwL_bCRQ4";
        LoopSDK.userId = "82fda612-cbbc-474f-bf1d-700ecf93623a";
        LoopSDK.deviceId = "c2250c6d-2366-46b9-8180-d2e8a616ea9d";

        LoopSDK.initialize(this, appId, appToken);
    }

    // called by the Loop SDK on successful initialization
    @Override
    public void onInitialized() {
        Log.d("loop_sdk", "Successfully Initialized");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOOP_SDK_STATE, "initialized");
        editor.apply();
    }

    // called by the Loop SDK when the provider status changes
    @Override
    public void onServiceStatusChanged(String provider, String status, Bundle bundle) {
        Log.d("loop_sdk", "Provider: " + provider + " status: " + status);
    }

    // called by the Loop SDK when initialization fails
    @Override
    public void onInitializeFailed(LoopError loopError) {
        Log.d("loop_sdk", "Loop SDK failed: " + loopError.toString());

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOOP_SDK_STATE, "error");
        editor.apply();
    }
}
