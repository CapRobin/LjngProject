package com.zfg.org.myexample;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.activity.BasicActivity;
import com.zfg.org.myexample.adapter.ItemNewsAdapter;
import com.zfg.org.myexample.dto.QuestionDto;
//import com.zfg.org.myexample.adapter.ItemNewsAdapter;
//import com.zfg.org.myexample.dto.QuestionDto;


public class HelpActivity extends BasicActivity implements OnClickListener {

//    @ViewInject(id = R.id.back_btn)
//    private Button backBtn;

    @ViewInject(id = R.id.backHome)
    private Button backHome;
    @ViewInject(id = R.id.pageTitle)
    private TextView pageTitle;
    @ViewInject(id = R.id.settingBtn)
    private ImageView settingBtn;
    @ViewInject(id = R.id.list)
    private ListView listview;

    private List<List<QuestionDto>> data;
    private ItemNewsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_layout);
        initActivity();
    }

    private void initActivity() {
        backHome.setOnClickListener(this);
        pageTitle.setText("常见问题");
        settingBtn.setVisibility(View.GONE);
        data = new ArrayList<List<QuestionDto>>();
        data.addAll(readFile().values());
        adapter = new ItemNewsAdapter(context, data);
//        listview.setDivider(new ColorDrawable(Color.GRAY));
//        listview.setDividerHeight(1);
        listview.setAdapter(adapter);
    }

    private Map<String, List<QuestionDto>> readFile() {
        Map<String, List<QuestionDto>> maps = new LinkedHashMap<String, List<QuestionDto>>();
        InputStream in = null;
        BufferedReader bufferReader = null;
        String str = "";
        String[] supstr;
        int index = 1;
        try {
//			查找答疑
            in = context.getAssets().open("help.txt");
            bufferReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            List<QuestionDto> dtolist =null;
            while ((str = bufferReader.readLine()) != null) {
                supstr = str.split("_");
//                List<QuestionDto> dtolist = maps.get(supstr[0]);
               if (dtolist == null)
                {
                    dtolist = new ArrayList<QuestionDto>();
//                    maps.put(supstr[0], dtolist);
                }
                QuestionDto dto = new QuestionDto();
                dto.index = index;
                dto.name = supstr[1];
                dto.parent = supstr[0];
                dtolist.add(dto);
                index++;
            }
            maps.put("燃气表疑难排除",dtolist);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return maps;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backHome:
                finish();
                break;
        }
    }

}
