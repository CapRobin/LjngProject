package com.zfg.org.myexample.activity;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;

import com.zfg.org.myexample.R;

public class MyTestActivity extends BasicActivity {

    private Drawable oldBackground = null;


    private Drawable bg_a;
    private Drawable bg_b;
    private LinearLayout up;
    private int isWhat = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_test);
        up = (LinearLayout) findViewById(R.id.mLayout);
        up.setBackgroundResource(R.drawable.dp_k_200);

        bg_a = ContextCompat.getDrawable(MyTestActivity.this, R.drawable.dp_k_200);
        bg_b = ContextCompat.getDrawable(MyTestActivity.this, R.drawable.dp_g_200);

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isWhat == 1)
                    aTob();
                else if (isWhat == 2)
                    bToa();
            }
        });
    }

    private void aTob() {
        up.setBackgroundResource(R.drawable.dp_k_200);
        //渐变切换
        TransitionDrawable td = new TransitionDrawable(new Drawable[]{bg_a, bg_b});
        up.setBackgroundDrawable(td);
        td.startTransition(3000);
        isWhat = 2;
    }

    private void bToa() {
        up.setBackgroundResource(R.drawable.dp_g_200);
        //渐变切换
        TransitionDrawable td = new TransitionDrawable(new Drawable[]{bg_b, bg_a});
        up.setBackgroundDrawable(td);
        td.startTransition(3000);
        isWhat = 1;
    }
}
