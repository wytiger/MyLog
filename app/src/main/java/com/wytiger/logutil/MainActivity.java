package com.wytiger.logutil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        MyLog.printThreadStackTrace();

        //默认TAG
        MyLog.d("Test");
        //指定TAG
        MyLog.i("TAG","Test2");
        //错误级别的，可以将日志写到文件，方便查看
        MyLog.e("TAG","Crash");


        JSONObject jsonObject = null;
        try{
             jsonObject = new JSONObject();
            jsonObject.put("name","xiaoming");
            jsonObject.put("age","16");
        }catch (Exception e){
            e.printStackTrace();
        }
        String json = jsonObject.toString();
        //打印json数据，会自动格式化
        MyLog.json("TAG",json);
    }
}
