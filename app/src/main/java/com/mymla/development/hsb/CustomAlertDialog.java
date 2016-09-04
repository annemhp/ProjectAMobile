package com.mymla.development.hsb;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

/**
 * Created by sandeep.vissamsetti on 9/4/16.
 */
public class CustomAlertDialog extends AlertDialog {

    private Button mButtonOk;

    public CustomAlertDialog(Context context) {
        super(context);
        this.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_report_submitted);
        mButtonOk = (Button) findViewById(R.id.btn_alert_ok);
        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
