# MyLog
日志工具类，欢迎star。   
1, 日志附加线程名、类名、方法名、源文件行数；
2, 自动格式化json字符串；
3，点击日志行数跳转到源码


简单使用：
        //默认TAG
        MyLog.d("Test");
        //指定TAG
        MyLog.i("TAG","Test2");
        //错误级别的，可以将日志写到文件，方便查看
        MyLog.e("TAG","Crash");

        String json = '[{"CityId":18,"CityName":"西安","ProvinceId":27,"CityOrder":1},
                        {"CityId":53,"CityName":"广州","ProvinceId":27,"CityOrder":1}]';
        //打印json数据，会自动格式化
        MyLog.json("TAG",json);
