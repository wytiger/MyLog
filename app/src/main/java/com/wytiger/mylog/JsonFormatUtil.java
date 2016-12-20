package com.wytiger.mylog;

/**
 * description:JSON格式化工具类
 * Created by wytiger on 2016-11-30.
 */

public class JsonFormatUtil {
    /**
     * 默认每次缩进两个空格
     */
    private static final String empty = "  ";

    public static String format(String json) {
        try {
            int empty = 0;
            char[] chs = json.toCharArray();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < chs.length; ) {
                //若是双引号，则为字符串，下面if语句会处理该字符串
                if (chs[i] == '\"') {

                    stringBuilder.append(chs[i]);
                    i++;
                    //查找字符串结束位置
                    for (; i < chs.length; ) {
                        //如果当前字符是双引号，且前面有连续的偶数个\，说明字符串结束
                        if (chs[i] == '\"' && isDoubleSerialBackslash(chs, i - 1)) {
                            stringBuilder.append(chs[i]);
                            i++;
                            break;
                        } else {
                            stringBuilder.append(chs[i]);
                            i++;
                        }

                    }
                } else if (chs[i] == ',') {
                    stringBuilder.append(',').append('\n').append(getEmpty(empty));

                    i++;
                } else if (chs[i] == '{' || chs[i] == '[') {
                    empty++;
                    stringBuilder.append(chs[i]).append('\n').append(getEmpty(empty));

                    i++;
                } else if (chs[i] == '}' || chs[i] == ']') {
                    empty--;
                    stringBuilder.append('\n').append(getEmpty(empty)).append(chs[i]);

                    i++;
                } else {
                    stringBuilder.append(chs[i]);
                    i++;
                }
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return json;
        }

    }

    /**
     * 是否有两个连续的反斜杠\
     *
     * @param chs
     * @param i
     * @return
     */
    private static boolean isDoubleSerialBackslash(char[] chs, int i) {
        int count = 0;
        for (int j = i; j > -1; j--) {
            if (chs[j] == '\\') {
                count++;
            } else {
                return count % 2 == 0;
            }
        }

        return count % 2 == 0;
    }

    /**
     * 缩进
     *
     * @param count
     * @return
     */
    private static String getEmpty(int count) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            stringBuilder.append(empty);
        }

        return stringBuilder.toString();
    }

}
