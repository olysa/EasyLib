package com.dingfang.org.example.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dingfang.org.easylib.base.BaseActivity;
import com.dingfang.org.easylib.view.NumberProgressBar;
import com.dingfang.org.example.BuildConfig;
import com.dingfang.org.example.R;
import com.dingfang.org.example.constant.ApiInterface;
import com.dingfang.org.example.okhttp.callback.FileCallback;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.File;
import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 下载任务activity
 * Created by zuoqing on 2017/10/12.
 */

public class DownloadActivity extends BaseActivity {

    private static final int REQUEST_PERMISSION_STORAGE = 0x01;

    @BindView(R.id.fileDownload)
    Button btnFileDownload;
    @BindView(R.id.installapk)
    Button installapk;
    @BindView(R.id.downloadSize)
    TextView tvDownloadSize;
    @BindView(R.id.tvProgress)
    TextView tvProgress;
    @BindView(R.id.netSpeed)
    TextView tvNetSpeed;
    @BindView(R.id.pbProgress)
    NumberProgressBar pbProgress;

    private NumberFormat numberFormat; //数字格式转换类

    private String apkPath;
    private DownloadActivity _this;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_download;
    }

    @Override
    protected void initViewsAndEvents() {
        init();
    }


    /**
     * 全局初始化
     */
    private void init() {
        _this = this;

        numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMinimumFractionDigits(2);


        checkSDCardPermission();
    }

    /**
     * 检查SD卡权限
     */
    protected void checkSDCardPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //获取权限
            } else {
                Toast.makeText(this, "权限被禁止，无法下载文件！", Toast.LENGTH_SHORT).show();
                Log.e("error", "权限被禁止，无法下载文件！");
            }
        }
    }

    @OnClick(R.id.fileDownload)
    public void onClicked(View view) {
        //下载任务
        OkGo.<File>get(ApiInterface.APP_DOWNLOAD_PATH)
                .tag(this)
                .execute(new FileCallback("ytzhihui.apk") {

                    @Override
                    public void onStart(Request<File, ? extends Request> request) {
                        btnFileDownload.setText("正在下载中");
                    }

                    @Override
                    public void onSuccess(Response<File> response) {
//                handleResponse(response);
//                       btnFileDownload.setText("下载完成");
                        if (response.isSuccessful()) {
                            apkPath = response.body().getAbsolutePath();
                            installapk.setVisibility(View.VISIBLE);
                            Log.e("error", "" + response.body().getAbsolutePath());
                        }
                    }

                    @Override
                    public void onError(Response<File> response) {
//                handleError(response);
                        btnFileDownload.setText("下载出错");
                        Log.e("error", "下载出错，出错信息：" + response.getException().getMessage());
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        System.out.println(progress);

                        String downloadLength = Formatter.formatFileSize(getApplicationContext(), progress.currentSize);
                        String totalLength = Formatter.formatFileSize(getApplicationContext(), progress.totalSize);
                        tvDownloadSize.setText(downloadLength + "/" + totalLength);
                        String speed = Formatter.formatFileSize(getApplicationContext(), progress.speed);
                        tvNetSpeed.setText(String.format("%s/s", speed));
                        tvProgress.setText(numberFormat.format(progress.fraction));
                        pbProgress.setMax(10000);
                        pbProgress.setProgress((int) (progress.fraction * 10000));
                    }
                });
    }

    @OnClick(R.id.installapk)
    public void btnInstallApkClicked() {
        installApk(new File(apkPath), _this);
    }

    /**
     * 安装app
     */
    public static void installApk(File file, Activity activity) {
//        String apkFileSignature = SignatureUtil.getSignatureOfApk(activity, file.getAbsolutePath());
//        String currentAppSign = SignatureUtil.getCurrentAppSign(activity);
        //一般要检查签名是否一致
//        if (!TextUtils.equals(apkFileSignature, currentAppSign)) {
//            return;
//        }

        Intent intent = new Intent();
        // 执行动作
        intent.setAction(Intent.ACTION_VIEW);
        // 执行的数据类型
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".fileProvider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        activity.startActivity(intent);
        //kill 本程序
        android.os.Process.killProcess(android.os.Process.myPid());
        //正常退出程序
//        System.exit(0);
    }


    @Override
    protected void onDestroy() {
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(this);
        super.onDestroy();
    }
}
