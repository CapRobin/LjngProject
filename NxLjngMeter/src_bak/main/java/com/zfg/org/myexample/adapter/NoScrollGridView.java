package com.zfg.org.myexample.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.ListView;

/**
 * Copyright © 2018 LJNG All rights reserved.
 *
 * Name：NoScrollGridView
 * Describe：此处可写成类似GridView的形式展现直接替换成方式二即可
 * Date：2018-03-30 14:30:51
 * Author: CapRobin@yeah.net
 *
 */

//ListView的方式展现
public class NoScrollGridView extends ListView{

     public NoScrollGridView(Context context, AttributeSet attrs){
          super(context, attrs);
     }

     public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
          int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
          super.onMeasure(widthMeasureSpec, mExpandSpec);
     }
}

//GridView的方式展现(如需要直接替换即可)
//public class NoScrollGridView extends GridView{
//
//     public NoScrollGridView(Context context, AttributeSet attrs){
//          super(context, attrs);
//     }
//
//     public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
//          int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
//          super.onMeasure(widthMeasureSpec, mExpandSpec);
//     }
//}

