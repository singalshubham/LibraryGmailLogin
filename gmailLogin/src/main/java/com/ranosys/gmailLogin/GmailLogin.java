package com.ranosys.gmailLogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/**
 * This is class for the login with gmail and this library use the 10.0.1 version of Google Play lib
 * min sdk version required for this library is 16.
 * for do login and logout user start this activity using intent and get result in onActivity result
 * for help how to use read README.md file
 * if after the successfull login object of GoogleSignInAccount is pass to the calling activity in the RESULT_OK result code
 * object of googleSignInAccount is used for getting user account info
 * If the failure occur during login then result code is RESULT_CANCELED pass to the calling activitty
 */

public class GmailLogin extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 12345;
    private static final String TAG = "GmailLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_gmail_log);

        GoogleSignInOptions gso;

        String clientId = getIntent().getStringExtra(GmailConstant.CLIENT_ID);
        if (clientId == null) {
            // Configure sign-in to request the user's ID, email address, and basic
            // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

        } else {
            // Configure sign-in to request the user's ID, email address, and basic
            // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
            //aslo request for token ID of user
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(clientId)
                    .requestEmail()
                    .build();
        }

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this, this, this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        String action = getIntent().getStringExtra(GmailConstant.ACTION);
        //if the passing action is GmailConstant.LOG_IN gmail login activity is open
        if (action.equals(GmailConstant.LOG_IN)) {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    /**
     * This is method for handle the sign in result get from gmail
     * and pass the signInAccount to the calling activity
     *
     * @param result is the result get from googleSignApi
     */
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());

        //if after the successfull login object of GoogleSignInAccount is pass to the calling activity
        //object of googleSignInAccount is used for getting user account info
        if (result.isSuccess()) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra(GmailConstant.ACCOUNT_INFO, result.getSignInAccount());
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
        //If the failure occur during login then result code is RESULT_CANCELED pass to the calling activitty
        else {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();
            Log.d(TAG, "Application is unAuthenicate please generate configuration file for app or client id that you pass is incorrect");
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "connection is failed during connecting to api client");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        String action = getIntent().getStringExtra(GmailConstant.ACTION);
        //if passing action is GmailConstant.LOG_OUT logout from gmail
        if (action.equals(GmailConstant.LOG_OUT)) {
            //this is function for logout from gmail
            logout();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    /**
     * This is function for the logout from gmail
     */
    public void logout() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()) {
                            Log.d(TAG, "logout successfull from gmail");
                            Intent returnIntent = new Intent();
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();
                        } else {
                            Log.d(TAG, "error during logout");
                            Intent returnIntent = new Intent();
                            setResult(Activity.RESULT_CANCELED, returnIntent);
                            finish();
                        }
                    }
                });
    }

}
