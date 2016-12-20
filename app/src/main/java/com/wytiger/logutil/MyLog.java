package com.wytiger.logutil;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Author: wytiger
 * Time: 2016/12/12
 * Desc:日志工具类。
 * 能打印线程名、类名、方法名、源文件行数;点击行数跳转到源码;支持格式化json打印。
 */
public class MyLog {
    private static  String TAG = "MyLog";
    private static boolean isWriteLog2File = true;

    private MyLog() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("MyLog cannot be instantiated");
    }

    /**
     * 初始化
     * @param logTag 全局日志tag
     */
    public static void initTAG(String logTag){
        TAG = logTag;
    }
    public static void setWriteLog2File(boolean write){
        isWriteLog2File = write;
    }


    public static void v(String msg) {
        if (AppBuildConfig.DEBUG) {
            msg = createLog(msg);
            Log.v(TAG, msg);
        }
    }

    public static void i(String msg) {
        if (AppBuildConfig.DEBUG) {
            msg = createLog(msg);
            Log.i(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (AppBuildConfig.DEBUG) {
            msg = createLog(msg);
            Log.d(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (AppBuildConfig.DEBUG) {
            msg = createLog(msg);
            Log.w(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (AppBuildConfig.DEBUG) {
            msg = createLog(msg);
            Log.e(TAG, msg);
        }
        if (AppBuildConfig.DEBUG && isWriteLog2File) {
            writeLog2File(TAG, msg);
        }
    }

    public static void json(String json) {
        if (AppBuildConfig.DEBUG) {
            String msg = JsonFormatUtil.format(json);
            json = createLog("\n"+msg);
            Log.i(TAG, json);
        }
    }

    // 下面是传入自定义tag的函数
    public static void v(String tag, String msg) {
        if (AppBuildConfig.DEBUG) {
            msg = createLog(msg);
            Log.v(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (AppBuildConfig.DEBUG) {
            msg = createLog(msg);
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (AppBuildConfig.DEBUG) {
            msg = createLog(msg);
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (AppBuildConfig.DEBUG) {
            msg = createLog(msg);
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (AppBuildConfig.DEBUG) {
            msg = createLog(msg);
            Log.e(tag, msg);
        }
        if (AppBuildConfig.DEBUG && isWriteLog2File) {
            writeLog2File(tag, msg);
        }
    }

    public static void json(String tag, String json) {
        if (AppBuildConfig.DEBUG) {
            String msg = JsonFormatUtil.format(json);
            json = createLog("\n"+msg);
            Log.i(tag, json);
        }
    }

    private static String createLog(String log) {
//        printThreadStackTrace();
        StackTraceElement LogElement = Thread.currentThread().getStackTrace()[4];
        String fullClassName = LogElement.getClassName();
        String threadName = Thread.currentThread().getName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        String methodName = LogElement.getMethodName();
        String fileName = LogElement.getFileName();
        int lineNumber = LogElement.getLineNumber();

        StringBuffer buffer = new StringBuffer();
        buffer.append("at ");//链接到源码
        buffer.append("[");
        buffer.append(threadName);
        buffer.append(":");
        buffer.append(className);
        buffer.append(".");
        buffer.append(methodName);
        buffer.append("(");
        buffer.append(fileName);
        buffer.append(":");
        buffer.append(lineNumber);
        buffer.append(")");
        buffer.append("] ");
        buffer.append(log);

        return buffer.toString();
    }

    public static void printThreadStackTrace() {
        //通过线程栈帧元素获取相应信息
        Log.i("MyLog","sElements[0] = " + Thread.currentThread().getStackTrace()[0]);//VMStack
        Log.i("MyLog","sElements[1] = " + Thread.currentThread().getStackTrace()[1]);//Thread
        Log.i("MyLog","sElements[2] = " + Thread.currentThread().getStackTrace()[2]);//当前方法帧元素
        Log.i("MyLog","sElements[3] = " + Thread.currentThread().getStackTrace()[3]);//MyLog.x栈元素
        Log.i("MyLog","sElements[4] = " + Thread.currentThread().getStackTrace()[4]);//MyLog.x上层调用者
    }


    /**
     * 写日志信息到文件，调试用，日志信息会自动换行.
     * 需要写外部存储权限
     *
     * @param dir
     * @param data
     */
    public synchronized static void writeLog2File(String dir, String data) {
        if (TextUtils.isEmpty(dir)) {
            dir = TAG;
        }
        try {
            String logPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/MyApp/logs";
            File logDir = new File(logPath + "/" + dir);
            if (!logDir.exists()) {
                if (!logDir.mkdirs()) {
                    return;
                }
            }
            String fileName = new SimpleDateFormat("yyyyMMdd").format(new Date());
            File file = new File(logDir, fileName + ".txt");
            if (file.exists()) {
                if (file.length() > 200 * 1024) {
                    file.delete();
                    if (!file.createNewFile()) {
                        return;
                    }
                }
            } else {
                if (!file.createNewFile()) {
                    return;
                }
            }

            StringBuilder buffer = new StringBuilder();
            SimpleDateFormat sDateFormatYMD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
            String dateString = sDateFormatYMD.format(new Date(System.currentTimeMillis()));
            buffer.append(dateString).append("   ").append(data).append("\r\n");

            RandomAccessFile raf = new RandomAccessFile(file, "rw");// "rw" 读写权限
            raf.seek(file.length());
            raf.write(buffer.toString().getBytes());
            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

