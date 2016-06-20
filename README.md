# Loop Android Sample - Setting up test users

These instructions will get you a copy of a Loop sample app for pulling user profiles for development and testing purposes.

  0. If you haven’t already, signup for a loop account and create an app on the [Loop Developer Portal](https://developer.dev.loop.ms)
  0. Get the sample app
    0. Clone this sample app `git clone https://github.com/Microsoft/Loop-Sample-TestUser-Android.git`
    0. Open it in Android Studio
    0. Add your appId and appToken in `LoopTestUserApplication.java OnCreate`

    ```
        String appId = "YOUR_APP_ID";
        String appToken = "YOUR_APP_TOKEN";
    ```
  0. Create test users in your user dashboard (user link in the left navigation)
  0. Replace the userId and deviceId with your test user id and device id in `LoopTestUserApplication.java OnCreate`

    ```
        String userId = "YOUR_USER_ID";
        String deviceId = "YOUR_DEVICE_ID";
    ```
  0. Build and run the app

After this is done, you can create test profiles from the dashboard and see them updated in your app. This is the same mechanism using test signals of what Loop does when it creates home & work profiles and updates your app.
