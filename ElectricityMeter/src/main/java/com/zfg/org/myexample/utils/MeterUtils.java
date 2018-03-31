package com.zfg.org.myexample.utils;

/**
 * Created by Administrator on 2016-05-30.
 */
public class MeterUtils {

    public static String FillIn(int rlength,int rtype,int startpos,String str){
        StringBuilder sb=new StringBuilder();
        switch(rtype)
        {
//          转2进制填充
            case 2:
                System.out.println(2);
                break;
//          转8进制填充
            case 8:
                System.out.println(3);
                break;
//          转10进制填充
            case 10:
                System.out.println(3);
                break;
//          转16进制后填充
            case 16:
                System.out.println(3);
                break;
            default:
                System.out.println("default");
                break;
        }

        return "";
    }

//  拼接命令帧
    public static String commandgen(String addr){
        return "";
    }


}
