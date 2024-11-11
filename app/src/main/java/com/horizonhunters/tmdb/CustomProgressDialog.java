package com.horizonhunters.tmdb;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

public class CustomProgressDialog extends Dialog {

    public CustomProgressDialog(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        // Remove the default background
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        // Inflate the custom layout
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_custom_progress, null);
        setContentView(view);
        setCancelable(false); // Make the dialog non-cancelable
    }
}