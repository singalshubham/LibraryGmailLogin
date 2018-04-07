package com.ranosys.gmailloginsample.utils;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.ProgressBar;

import com.ranosys.gmailloginsample.R;


/**
 * A circular progress dialog with porterDuff mode SRC_IN Indeterminate drawable.
 */
public class CustomProgressDialog extends ProgressDialog {

    private CustomProgressDialog(Context context) {
        super(context);
    }
    public static ProgressDialog progressDialog(Context context) {
        CustomProgressDialog dialog = new CustomProgressDialog(context);
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);
        if (null!=dialog.getWindow()) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        return dialog;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom_progress);
        ProgressBar progress1 = (ProgressBar) findViewById(R.id.progressBar);
        if (null!=progress1) {
            progress1.setBackgroundColor(ContextCompat.getColor(getContext(),android.R.color.transparent));
            progress1.setBackground(null);
            progress1.setIndeterminate(isIndeterminate());
            progress1.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        }
    }
}