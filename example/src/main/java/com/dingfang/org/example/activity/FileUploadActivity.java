package com.dingfang.org.example.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dingfang.org.easylib.base.BaseActivity;
import com.dingfang.org.easylib.view.NumberProgressBar;
import com.dingfang.org.example.R;
import com.dingfang.org.example.constant.ApiInterface;
import com.dingfang.org.example.okhttp.callback.JsonCallback;
import com.dingfang.org.example.okhttp.model.LzyResponse;
import com.dingfang.org.example.utils.GlideImageLoader;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 上传任务activity
 * Created by zuoqing on 2017/10/13.
 */

public class FileUploadActivity extends BaseActivity {

    @BindView(R.id.formUpload)
    Button btnFormUpload;
    @BindView(R.id.downloadSize)
    TextView tvDownloadSize;
    @BindView(R.id.tvProgress)
    TextView tvProgress;
    @BindView(R.id.netSpeed)
    TextView tvNetSpeed;
    @BindView(R.id.pbProgress)
    NumberProgressBar pbProgress;
    @BindView(R.id.images)
    TextView tvImages;
    @BindView(R.id.show_images)
    ImageView show_images;



    //图片多选
    private ArrayList<ImageItem> imageItems;
    private ImageItem imageItem;

    //上传进度条格式转换
    private NumberFormat numberFormat;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_file_upload;
    }

    @Override
    protected void initViewsAndEvents() {
        init();
    }

    /**
     * 全局初始化
     */
    private void init() {
        numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMinimumFractionDigits(2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(this);
    }

    /**
     * 多选
     * @param view
     */
//    @OnClick(R.id.selectImage)
//    public void selectImage(View view) {
//        ImagePicker imagePicker = ImagePicker.getInstance();
//        imagePicker.setImageLoader(new GlideImageLoader());
//        imagePicker.setMultiMode(true);   //多选
//        imagePicker.setShowCamera(true);  //显示拍照按钮
//        imagePicker.setSelectLimit(9);    //最多选择9张
//        imagePicker.setCrop(false);       //不进行裁剪
//        Intent intent = new Intent(this, ImageGridActivity.class);
//        startActivityForResult(intent, 100);
//    }

    /**
     * 单选图片
     * @param view
     */
    @OnClick(R.id.selectImage)
    public void selectImage(View view) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setMultiMode(false);   //单选
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setSelectLimit(9);    //最多选择9张
        imagePicker.setCrop(false);       //不进行裁剪
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, 100);
    }

    /**
     * 多选图片回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
/*    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                imageItems = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (imageItems != null && imageItems.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < imageItems.size(); i++) {
                        if (i == imageItems.size() - 1)
                            sb.append("图片").append(i + 1).append(" ： ").append(imageItems.get(i).path);
                        else
                            sb.append("图片").append(i + 1).append(" ： ").append(imageItems.get(i).path).append("\n");
                    }
                    tvImages.setText(sb.toString());
                } else {
                    tvImages.setText("--");
                }
            } else {
                Toast.makeText(this, "没有选择图片", Toast.LENGTH_SHORT).show();
                tvImages.setText("--");
            }
        }
    }*/

    /**
     * 单选图片回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                //noinspection unchecked
                imageItems = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (imageItems != null && imageItems.size() > 0) {
                    imageItem = imageItems.get(0);
                    tvImages.setText(imageItem.path);
                    Log.e("error", ""+imageItem.path);
                    Bitmap bm = BitmapFactory.decodeFile(imageItem.path);
                    show_images.setImageBitmap(bm);
                } else {
                    tvImages.setText("--");
                }
            } else {
                Toast.makeText(this, "没有选择图片", Toast.LENGTH_SHORT).show();
                tvImages.setText("--");
            }
        }
    }


    @OnClick(R.id.formUpload)
    public void formUpload(View view) {
        ArrayList<File> files = new ArrayList<>();
        if (imageItems != null && imageItems.size() > 0) {
            for (int i = 0; i < imageItems.size(); i++) {
                files.add(new File(imageItems.get(i).path));
            }
        }
        //拼接参数
        OkGo.<LzyResponse<Object>>post(ApiInterface.URL_FORM_UPLOAD)//
                .tag(this)//
//                .headers("header1", "headerValue1")//
//                .headers("header2", "headerValue2")//
//                .params("param1", "paramValue1")//
//                .params("param2", "paramValue2")//
//                .params("file1",new File("文件路径"))   //这种方式为一个key，对应一个文件
//                .params("file2",new File("文件路径"))
                .params("p123.jpg",new File(imageItem.path))
//                .addFileParams("file", files)           // 这种方式为同一个key，上传多个文件
                .execute(new JsonCallback<LzyResponse<Object>>() {
                    @Override
                    public void onStart(Request<LzyResponse<Object>, ? extends Request> request) {
                        btnFormUpload.setText("正在上传中...");
                    }

                    @Override
                    public void onSuccess(Response<LzyResponse<Object>> response) {
//                        handleResponse(response);
                        btnFormUpload.setText("上传完成");
                    }

                    @Override
                    public void onError(Response<LzyResponse<Object>> response) {
//                        handleError(response);
                        btnFormUpload.setText("上传出错");
                    }

                    @Override
                    public void uploadProgress(Progress progress) {
                        System.out.println("uploadProgress: " + progress);

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

}
