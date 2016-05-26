# Loop Android Sample - Setting up test users

These instructions will get you a copy of a Loop sample app for pulling user profiles up and running for development and testing purposes.

  0. If you haven’t already, signup for a loop account and create an app on the Loop developer portal. It takes seconds - `https://developer.dev.loop.ms`
  0. Get the sample app
    0. Clone this sample app `git clone https://github.com/Microsoft/Loop-Sample-TestUser-Android.git`
    0. Open it in Android Studio
    0. Add your appId and appToken in `LoopTestUserApplication.java OnCreate`
    ```
        String appId = "YOUR_APP_ID";
        String appToken = "YOUR_APP_TOKEN";
    ```
  0. Create test users in your user dashboard (user link in the left navigation)
  0. Replace the `your_test_userid` with your test user id in `LoopTestUserApplication.java OnCreate`
    ```
        LoopSDK.userId = "YOUR_USER_ID";
        LoopSDK.deviceId = "YOUR_DEVICE_ID";
    ```
  0. Run the app. 

After this is done, you can create test profiles from the dashboard and see them updated in your app. This is the test version of what Loop does when it creates home & work profiles and updates your app.
