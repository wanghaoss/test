package com.example.new_androidclient.Util;

import android.annotation.SuppressLint;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingConversion;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BindingUtil {
    @BindingConversion
    public static String covertDataToString(Date date){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(date != null){
            return sdf.format(date.getTime());
        }
        return "点击选择时间";

    }

    @BindingAdapter("setImage")
    public static void showImageByUrl(final ImageView imageView, int resId) {
        imageView.setImageResource(resId);
    }
}
