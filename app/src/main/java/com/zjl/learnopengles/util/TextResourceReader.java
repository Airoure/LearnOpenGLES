package com.zjl.learnopengles.util; /**
 * Created by glumes on 2017/7/9.
 */

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 读取 GLSL 语言
 */
public class TextResourceReader {

    public static String readTextFileFromResource(Context context, int resourceId) {
        StringBuilder body = new StringBuilder();
        InputStream inputStream = context.getResources().openRawResource(resourceId);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String nextLine;
        try {
            while ((nextLine = bufferedReader.readLine()) != null) {
                body.append(nextLine);
                body.append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return body.toString();
    }

    public static String readTextFileFromAsset(Context context, String filename) {
        return readTextFileFromAsset(context.getResources(), filename);
    }

    public static String readTextFileFromAsset(Resources res, String filename) {
        String result = null;
        try {
            InputStream in = res.getAssets().open(filename);
            int ch = 0;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((ch = in.read()) != -1) {
                baos.write(ch);
            }
            byte[] buff = baos.toByteArray();
            baos.close();
            in.close();
            result = new String(buff, "UTF-8");
            result = result.replaceAll("\\r\\n", "\n");
            Log.d("res","read result is " + result);
        } catch (IOException e) {
            Log.d("res","exception");
            Log.e("res",e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

}
