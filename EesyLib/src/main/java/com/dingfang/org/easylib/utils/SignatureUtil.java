package com.dingfang.org.easylib.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by fuguang.zongfg on 15/10/13.
 */
public class SignatureUtil {
    private static String TAG = SignatureUtil.class.getSimpleName();

    public static String getCurrentAppSign(Context context) {
        PackageManager pm = context.getPackageManager();
        String pkgName = context.getPackageName();

        /** 通过包管理器获得指定包名包含签名的包信息 **/
        PackageInfo packageInfo = null;
        try {
            packageInfo = pm.getPackageInfo(pkgName, PackageManager.GET_SIGNATURES);
            /******* 通过返回的包信息获得签名数组 *******/
            Signature[] signatures = packageInfo.signatures;
            /************** 得到应用签名 **************/
            String signature = signatures[0].toCharsString();
            Log.i(TAG, "current app:" + signature);

            return signature;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getSignatureOfApk(Context context, String apkFilePath) {
        Log.i(TAG, "SignatureUtil.getSignatureOfApk");
        if (TextUtils.isEmpty(apkFilePath)) return null;

        PackageManager pm = context.getPackageManager();

        PackageInfo pi = pm.getPackageArchiveInfo(apkFilePath, PackageManager.GET_SIGNATURES);
        Signature[] signatures = pi.signatures;

        if (signatures != null && signatures.length > 0) {

            String signature = signatures[0].toCharsString();
            Log.i(TAG, "Signature of " + apkFilePath + " is : " + signature);

            return signature;
        }

        return null;
    }
}
