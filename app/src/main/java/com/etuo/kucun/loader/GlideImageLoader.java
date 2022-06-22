package com.etuo.kucun.loader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView,int imageId) {
        //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择


        if (imageId != 0){ // 加载默认失败的图片
            Glide.with(context.getApplicationContext())
                    .load(path)
                    .placeholder(imageId) // 添加占位符，
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(0.1f)
                    .crossFade()
                    .into(imageView);
        }else {
            Glide.with(context.getApplicationContext())
                    .load(path)

                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(0.1f)
                    .crossFade()
                    .into(imageView);
        }

    }


}
