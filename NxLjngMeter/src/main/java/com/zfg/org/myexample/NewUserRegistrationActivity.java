package com.zfg.org.myexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.zfg.org.myexample.activity.BasicActivity;

public class NewUserRegistrationActivity extends BasicActivity implements View.OnClickListener {

    @ViewInject(id = R.id.psw_right_img)
    private ImageView psw_right_img;
    @ViewInject(id = R.id.regist_psw)
    private EditText regist_psw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_registration);
        initActivity();
    }
    private void initActivity (){
        psw_right_img.setOnClickListener(this);
    }
    int flag = 0;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.psw_right_img:
                if (flag == 0){
                    regist_psw.setInputType(0x00000001);
                    flag = 1;
                }else{
                    regist_psw.setInputType(0x00000081);
                    flag = 0;
                }
                break;
        }
    }
}
