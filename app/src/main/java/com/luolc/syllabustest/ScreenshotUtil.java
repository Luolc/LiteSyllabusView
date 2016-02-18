package com.luolc.syllabustest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by LuoLiangchen on 16/2/17.
 */
public class ScreenshotUtil {
    public static final String SAVE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Develop";
    public static String filePath = SAVE_DIR + "/mySyllabus.png";

    public static void saveScreenshot(Context context, ScrollView scrollView) {
        int height = 0;
        for (int i = 0; i < scrollView.getChildCount(); ++i) {
            height += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundResource(R.drawable.mypku_bg);
            Log.i("mLog", "i="+i);
            Log.i("mLog", scrollView.getChildAt(i).toString());
        }
        Log.i("mLog", "height"+height);
        Bitmap bitmap = Bitmap.createBitmap(scrollView.getWidth(), height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);

        Log.i("mLog", SAVE_DIR);
        File saveDir = new File(SAVE_DIR);
        if (!saveDir.exists()) saveDir.mkdirs();
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("mLog", "create failed");
            }
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "保存失败0", Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fos != null) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();
                }
                Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "保存失败1", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
