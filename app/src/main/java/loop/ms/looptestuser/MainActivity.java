package loop.ms.looptestuser;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ms.loop.loopsdk.api.LoopHttpError;
import ms.loop.loopsdk.core.ILoopServiceCallback;
import ms.loop.loopsdk.core.LoopSDK;
import ms.loop.loopsdk.profile.IProfileDownloadCallback;
import ms.loop.loopsdk.profile.LoopTestItem;
import ms.loop.loopsdk.profile.LoopTestList;
import ms.loop.loopsdk.util.LoopError;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    public Button getTestProfileButton = null;
    public ImageView checkboxImageView = null;
    public ImageView errorImageView = null;
    public TextView userIdView = null;
    public TextView profileCountView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(loop.ms.looptestuser.R.layout.activity_main);

        getTestProfileButton = (Button) findViewById(R.id.getTestProfileButton);
        checkboxImageView = (ImageView) findViewById(R.id.checkBoxView);
        errorImageView = (ImageView) findViewById(R.id.errorView);
        userIdView = (TextView) findViewById(R.id.userIdView);
        profileCountView = (TextView) findViewById(R.id.profileCountView);

        if (getTestProfileButton == null
                || checkboxImageView == null
                || errorImageView == null
                || userIdView == null
                || profileCountView == null) {
            Log.d(TAG, "Error loading layout resources");
        }

        checkLoopSDKState();

        getTestProfileButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkboxImageView.setVisibility(View.INVISIBLE);
                errorImageView.setVisibility(View.INVISIBLE);
                userIdView.setVisibility(View.INVISIBLE);
                profileCountView.setVisibility(View.INVISIBLE);

                final LoopTestList testProfileItemList = LoopTestList.createAndLoad(LoopTestList.class, LoopTestItem.class);
                if (testProfileItemList != null) {
                    testProfileItemList.download(true, new IProfileDownloadCallback() {
                        @Override
                        public void onProfileDownloadComplete(final int itemCount) {
                            LoopTestItem loopTestItem = testProfileItemList.getMostRecentItem();
                            final double score = loopTestItem.score;

                            Log.d(TAG, "test profile items downloaded: " + itemCount);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run(){
                                    checkboxImageView.setVisibility(View.VISIBLE);

                                    userIdView.setText("Current User Id\n" + LoopSDK.userId + "\n\nTest Count");
                                    userIdView.setVisibility(View.VISIBLE);
                                    profileCountView.setText(String.format("%.0f", score));
                                    profileCountView.setVisibility(View.VISIBLE);
                                }
                            });
                        }

                        @Override
                        public void onProfileDownloadFailed(LoopError error) {
                            Log.d(TAG, error.toString());

                            String errorStart = "Download test profile error";
                            String errorEnd = "";

                            if (error.getClass() == LoopHttpError.class) {
                                LoopHttpError httpError = (LoopHttpError) error;
                                errorEnd = "\nStatus code: " + httpError.status + "\nReason: " + httpError.reason;
                                Log.d(TAG, errorStart + errorEnd);
                            }
                            else {
                                Log.d(TAG, errorStart);
                            }

                            final String errorMessage = errorStart + errorEnd;

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    errorImageView.setVisibility(View.VISIBLE);

                                    userIdView.setText(errorMessage);
                                    userIdView.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key == LoopTestUserApplication.LOOP_SDK_STATE) {
            String sdkState = sharedPreferences.getString(LoopTestUserApplication.LOOP_SDK_STATE, "uninitialized");
            if (sdkState != "uninitialized") {
                setupInitialUI(sdkState == "initialized");
            }
        }
    }

    private void checkLoopSDKState() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        String sdkState = sharedPreferences.getString(LoopTestUserApplication.LOOP_SDK_STATE, "uninitialized");
        if (sdkState != "uninitialized") {
            setupInitialUI(sdkState == "initialized");
        }
    }

    private void setupInitialUI(Boolean sdkSuccessfullyInitialized) {
        getTestProfileButton.setEnabled(sdkSuccessfullyInitialized);
        errorImageView.setVisibility(sdkSuccessfullyInitialized ? View.INVISIBLE : View.VISIBLE);

        if (!sdkSuccessfullyInitialized) {
            String errorMessage = "Error initializing Loop SDK";
            if (LoopSDK.appId == "YOUR_APP_ID" || LoopSDK.appToken == "YOUR_APP_TOKEN") {
                errorMessage = "No App Id or Token.\nSee onCreate in LoopTestUserApplication\nCreate an app at developer.dev.loop.ms";
            }

            userIdView.setText(errorMessage);
            userIdView.setVisibility(View.VISIBLE);
        }
    }
}
