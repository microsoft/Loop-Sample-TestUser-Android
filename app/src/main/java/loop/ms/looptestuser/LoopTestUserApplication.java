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
        String appId = "scott-locations-dev-dc113648";
        String appToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb3V0ZXMiOlt7Im1ldGhvZCI6InBvc3QiLCJwYXRoIjoiL3YyLjAvYXBwL3Njb3R0LWxvY2F0aW9ucy1kZXYtZGMxMTM2NDgvdXNlciJ9LHsibWV0aG9kIjoiZGVsZXRlIiwicGF0aCI6Ii92Mi4wL2FwcC9zY290dC1sb2NhdGlvbnMtZGV2LWRjMTEzNjQ4L3VzZXIifSx7Im1ldGhvZCI6InBvc3QiLCJwYXRoIjoiL3YyLjAvYXBwL3Njb3R0LWxvY2F0aW9ucy1kZXYtZGMxMTM2NDgvbG9naW4ifSx7Im1ldGhvZCI6ImdldCIsInBhdGgiOiIvdjIuMC9hcHAvc2NvdHQtbG9jYXRpb25zLWRldi1kYzExMzY0OC91c2VyIn0seyJtZXRob2QiOiJnZXQiLCJwYXRoIjoiL3YyLjAvYXBwL3Njb3R0LWxvY2F0aW9ucy1kZXYtZGMxMTM2NDgvdXNlci9bXi8uXSsifV0sImlzcyI6Ik1pY3Jvc29mdCBMT09QIEF1dGggVjIiLCJzdWIiOiJzY290dC1sb2NhdGlvbnMtZGV2LWRjMTEzNjQ4In0.7jYeL3f2uLtpN5oD2UPsS1D5cS4M6xkx6P1DWLiWiqQ";
        String userId = "fcdd8955-b4be-42fe-8a48-f5315686d0b9";
        String deviceId = "3430afb0-3970-4605-af33-af18b44e6bc4";

        LoopSDK.initialize(this, appId, appToken, userId, deviceId);
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

    @Override
    public void onDebug(String output) {

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
