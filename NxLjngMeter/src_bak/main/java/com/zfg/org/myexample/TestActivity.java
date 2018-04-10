package com.zfg.org.myexample;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.zfg.org.myexample.activity.BasicActivity;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends BasicActivity {
    private BasicActivity context;
    private int userType = 0;
    private List<String> uTypeList;
    private ArrayAdapter<String> spannerAdapter;

    @ViewInject(id = R.id.selectUserType)
    private Spinner mSpinner;

    @ViewInject(id = R.id.login_input_password)
    private TextView login_input_password;

    @ViewInject(id = R.id.login_input_name)
    private TextView login_input_name;

    @ViewInject(id = R.id.login_switchBtn)
    private CheckBox login_switchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login_layout_2);
        context = this;
//        login_input_password = (TextView) findViewById(R.id.login_input_password);
        setSpinner();
        onChangePassLook listener = new onChangePassLook();
        login_switchBtn.setOnCheckedChangeListener(listener);

    }
    
    /**
     * Describe：设置Spanner样式
     * Params:
     * Date：2018-03-24 13:30:43
     */
    
    private void setSpinner(){
        //添加List数据
        uTypeList = new ArrayList<String>();
        uTypeList.add("水表");
        uTypeList.add("电表");
        uTypeList.add("气表");
        uTypeList.add("热表");
        spannerAdapter = new ArrayAdapter<String>(this,R.layout.spanner,uTypeList);
        mSpinner.setAdapter(spannerAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i !=0){
                    Toast.makeText(TestActivity.this, "您选择了："+mSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    userType = i;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        
    }	
    /**
     * Describe：密码切换监听器
     * Params: 
     * Date：2018-03-24 15:07:51
     */
    
    class onChangePassLook implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // TODO Auto-generated method stub

            if(isChecked){
                //如果选中，显示密码  		
                login_input_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }else{
                //否则隐藏密码
                login_input_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }

        }

    }

    /**
     * Describe：点击按钮进行验证
     * Params: 传入Button
     * Date：2018-03-24 15:30:11
     */
    public void checkLogin(View v) {
        if (checkLoginName(login_input_name.getText().toString())&& checkLoginPass(login_input_password.getText().toString())) {
            if(userType == 0 ){
                Toast.makeText(TestActivity.this, "您选择了："+mSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }else{
                Intent intent =new Intent(this, LoginActivity.class);
                startActivity(intent);
            }

        }
    }

    /**
     * Describe：账号验证
     * Params:
     * Date：2018-03-24 15:43:43
     */

    private boolean checkLoginName(String s) {
        //判读输入是否为空
        if (s.length() == 0) {
            login_input_name.setError(getResources().getString(R.string.user_warn1));
            login_input_name.requestFocus();
            return false;
        }

        /**
         * 此处可进行多种判断
         */
        return true;
    }

    /**
     * Describe：密码验证
     * Params:
     * Date：2018-03-24 15:44:23
     */

    private boolean checkLoginPass(String s) {
        //判断密码是否为空
        if (s.length() == 0) {
            login_input_password.setError(getResources().getString(R.string.pwd_warn1));
            login_input_password.requestFocus();
            return false;
        }

        /**
         * 此处可进行多种判断
         */

        return true;
    }
}
