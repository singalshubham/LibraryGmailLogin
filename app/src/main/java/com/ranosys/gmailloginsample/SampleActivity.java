package com.ranosys.gmailloginsample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.ranosys.gmailLogin.GmailConstant;
import com.ranosys.gmailLogin.GmailLogin;
import com.ranosys.gmailloginsample.utils.CustomProgressDialog;

public class SampleActivity extends AppCompatActivity {

    private static final String TAG = "SampleActivity";
    private final int GMAIL_LOGIN_REQUEST = 1;
    private final int GMAIL_LOGOUT_REQUEST = 2;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //this function is called when press login with gmail button
    public void gmailLogin(View v) {
        mProgressDialog = CustomProgressDialog.progressDialog(this);
        mProgressDialog.show();
        Intent gmailActivity = new Intent(SampleActivity.this, GmailLogin.class);
        gmailActivity.putExtra(GmailConstant.CLIENT_ID,"905719263858-5lirdltnghncss2qmh0nvudeps1af50s.apps.googleusercontent.com");
        gmailActivity.putExtra(GmailConstant.ACTION, GmailConstant.LOG_IN);
        startActivityForResult(gmailActivity, GMAIL_LOGIN_REQUEST);
    }


    //this function is called when press logout with gmail button
    public void gmailLogout(View v) {
        //GmailLogin gmailLogin = new GmailLogin(new ProgressDialog(this));
        Intent gmailActivity = new Intent(SampleActivity.this, GmailLogin.class);
        gmailActivity.putExtra(GmailConstant.ACTION, GmailConstant.LOG_OUT);
        startActivityForResult(gmailActivity, GMAIL_LOGOUT_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);//if request for gmail login
        if (requestCode == GMAIL_LOGIN_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                mProgressDialog.dismiss();
                GoogleSignInAccount googleSignInAccount = data.getParcelableExtra(GmailConstant.ACCOUNT_INFO);
                Log.d(TAG, "accont info is  " + googleSignInAccount.getDisplayName() + "  email " + googleSignInAccount.getEmail()
                        + " tokenID  " + googleSignInAccount.getIdToken() + "  photo url " + googleSignInAccount.getPhotoUrl() + " family name   " + googleSignInAccount.getFamilyName()
                        + "  getAccount " + googleSignInAccount.getAccount() + " get ID " + googleSignInAccount.getId());

            } else {
                mProgressDialog.dismiss();
                Log.d(TAG, "app is unauthenticate or passing wrong client id");
            }
        }
        //if request for gmail logout
        if (requestCode == GMAIL_LOGOUT_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "logout from gmail", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "problem during logout", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
