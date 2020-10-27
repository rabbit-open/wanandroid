package com.wanandroid.bslee.compat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

public class OverLayWindowCompact {

    public static void requestSettingCanDrawOverlays(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(activity)) {
                Toast.makeText(activity, "请打开显示悬浮窗开关!", Toast.LENGTH_LONG).show();
                int sdkInt = Build.VERSION.SDK_INT;
                if (sdkInt >= Build.VERSION_CODES.O) { //8.0以上
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                    activity.startActivityForResult(intent, 200);
                } else if (sdkInt >= Build.VERSION_CODES.M) { //6.0-8.0
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                    intent.setData(Uri.parse("package:$packageName"));
                    activity.startActivityForResult(intent, 200);
                }
            }
        } else { //4.4-6.0
            //无需处理了
        }

    }
}
