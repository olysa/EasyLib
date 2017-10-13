package com.dingfang.org.example.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.dingfang.org.example.R;
import com.lzy.imagepicker.loader.ImageLoader;
import com.lzy.ninegrid.NineGridView;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * 结合ImagePicker使用
 */
public class PicassoImageLoader implements ImageLoader, NineGridView.ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Picasso.with(activity)                                  //配置上下文
                .load(Uri.fromFile(new File(path)))             //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .error(R.drawable.ic_default_color)             //设置错误图片
                .placeholder(R.drawable.ic_default_color)       //设置占位图片
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {
    }

    @Override
    public void onDisplayImage(Context context, ImageView imageView, String url) {
        Picasso.with(context).load(url)//
                .placeholder(R.drawable.ic_default_color)//
                .error(R.drawable.ic_default_color)//
                .into(imageView);
    }

    @Override
    public Bitmap getCacheImage(String url) {
        return null;
    }
}
