package com.zfg.org.myexample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zfg.org.myexample.activity.BasicActivity;

/**
 * Created by Administrator on 2016-11-30.
 */

public class QRCodeActivity extends BasicActivity implements View.OnClickListener {

    @ViewInject(id = R.id.back_btn)
    private Button backBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        initActivity();
    }

    private void initActivity() {
        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                finish();
                break;
        }
    }
}
