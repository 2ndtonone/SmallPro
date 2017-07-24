package com.Funcgo.Outline.ui.dialog;

/**
 * Created by ydh on 2015/8/11.
 */

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.Funcgo.Outline.R;

public class LoadingProgressDialog extends Dialog {
    private static LoadingProgressDialog customProgressDialog = null;
    private Context context = null;

    private LoadingProgressDialog(Context paramContext) {
        super(paramContext);
        this.context = paramContext;
    }

    private LoadingProgressDialog(Context paramContext, int paramInt) {
        super(paramContext, paramInt);
    }

    public static LoadingProgressDialog createDialog(Context paramContext) {
        customProgressDialog = new LoadingProgressDialog(paramContext, R.style.CustomProgressDialog);
        customProgressDialog.setContentView(R.layout.progress_dialog);
        customProgressDialog.getWindow().getAttributes().gravity = 17;
        customProgressDialog.setCanceledOnTouchOutside(false);
        return customProgressDialog;
    }

    public void onWindowFocusChanged(boolean paramBoolean) {
        if (customProgressDialog == null)
            return;
        ((AnimationDrawable) ((ImageView) customProgressDialog.findViewById(R.id.loadingImageView)).getBackground()).start();
    }

    public LoadingProgressDialog setMessage(String paramString) {
        TextView localTextView = (TextView) customProgressDialog.findViewById(R.id.id_tv_loadingmsg);
        if (localTextView != null)
            localTextView.setText(paramString);
        return customProgressDialog;
    }

    public LoadingProgressDialog setTitile(String paramString) {
        return customProgressDialog;
    }
}