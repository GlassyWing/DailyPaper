package com.example.manlier.dailypaper.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.example.manlier.dailypaper.R;
import com.example.manlier.dailypaper.modules.news.listeners.OnLoadImageListener;

/**
 * 图片加载工具类
 * 使用Glide库来加载图片
 */
public class ImageLoaderUtils {



    public static void display(Context context, ImageView imageView, String url) {
        if(imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url).placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_image_loadfail).crossFade().into(imageView);
    }

    public static void display(Context context, ImageView imageView, String url, OnLoadImageListener listener) {
        if(imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url).placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_image_loadfail).crossFade().into(new ImageViewTarget<GlideDrawable>(imageView) {
            @Override
            protected void setResource(GlideDrawable resource) {
                if (listener != null) {
                    listener.onResourceReady(resource);
                }
            }
        });
    }

}
