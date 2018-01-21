package org.cc98.mycc98.utility;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by pipi6 on 2018/1/21.
 */

public class DialogStore {
    public static ProgressDialog genProcessDialog(Context context, String title, String msg) {
        ProgressDialog waitingDialog = new ProgressDialog(context);
        waitingDialog.setTitle(title);
        waitingDialog.setMessage(msg);
        waitingDialog.setIndeterminate(true);
        waitingDialog.setCancelable(false);

        return waitingDialog;
    }
}
