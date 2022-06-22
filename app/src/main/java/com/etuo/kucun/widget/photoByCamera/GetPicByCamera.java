package com.etuo.kucun.widget.photoByCamera;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;


import com.etuo.kucun.FrameworkApp;
import com.etuo.kucun.utils.AppUtils;
import com.etuo.kucun.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Administrator on 2018/7/16.
 */

public class GetPicByCamera {


    public static final int CAMERA_REQUEST_CODE = 2;

    public static boolean isCanGetPic(Context context){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.CAMERA}, 2);
            return false;
        }  else if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            return false;
        }
        return true;
    }


    public static  File getPhotoFilePath (){
        //设置路径为默认相册路径
        File dirFile = new File(FileUtils.SDPATH);
        //判断路径是否存在
        if (!dirFile.exists()) {
            //不存在 创建该路径
            dirFile.mkdirs();
        }
        //设置图片存放的名字和路径
        String PicName = UUID.randomUUID().toString() + ".jpg";

        String path = FileUtils.SDPATH + "/" + PicName;

        //创建文件对象
        File file = new File(path);
        try {
            //创建该文件
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    //调用摄像头获取图片
    public static Uri initpositivepop(Activity activity, int requestCode,File file) {


        Uri uri =null;
        //获取刚刚创建的文件uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(activity, AppUtils.getPackageName(FrameworkApp.getAppContext())+".fileprovider", file);//通过FileProvider创建一个content类型的Uri
        } else {
            uri = Uri.fromFile(file);
        }

        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        Intent Photo = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        Intent chooserIntent = Intent.createChooser(Photo, "Image Chooser");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Parcelable[]{captureIntent});

        activity.startActivityForResult(chooserIntent, requestCode);

        return  uri;

    }


    //处理返回照片裁剪
    public static String initBitmap(final String path) {

        if (path != null && !"".equals(path)) {
            String newStr = "";
            // 高清的压缩过的 bmp 对象  都在 Bimp.bmp里面
            try {
                Bitmap bm = Bimp.revitionImageSize(path);
                newStr = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
                FileUtils.saveBitmap(bm, "" + newStr);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            return FileUtils.SDPATH + "/" + newStr + ".png";
        }
        return null;
    }


    /**
     * 根据图片的位置信息,转化为 经纬度
     * @param rationalString
     * @param ref
     * @return
     */
    public static float convertRationalLatLonToFloat(
            String rationalString, String ref) {

        String[] parts = rationalString.split(",");

        String[] pair;
        pair = parts[0].split("/");
        double degrees = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());

        pair = parts[1].split("/");
        double minutes = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());

        pair = parts[2].split("/");
        double seconds = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());

        double result = degrees + (minutes / 60.0) + (seconds / 3600.0);
        if ((ref.equals("S") || ref.equals("W"))) {
            return (float) -result;
        }
        return (float) result;
    }


    /**
     *
     * @param path 图片地址
     * @return 经纬度
     */
    public static String[] getLngLat(String path){
        String [] LngLat = new String[2];
        float output1 = 0;
        float output2 = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            String datetime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);// 拍摄时间
            String deviceName = exifInterface.getAttribute(ExifInterface.TAG_MAKE);// 设备品牌
            String deviceModel = exifInterface.getAttribute(ExifInterface.TAG_MODEL); // 设备型号
            String latValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            String lngValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
            String latRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
            String lngRef = exifInterface.getAttribute
                    (ExifInterface.TAG_GPS_LONGITUDE_REF);
            if (latValue != null && latRef != null && lngValue != null && lngRef != null) {
                try {
                    output1 = convertRationalLatLonToFloat(latValue, latRef);
                     output2 = convertRationalLatLonToFloat(lngValue, lngRef);


                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    return null;
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        LngLat[0]=""+output1;
        LngLat[1]=""+output2;
        return LngLat;

    }


    /**
     * 读取uri所在的图片
     *
     * @param uri      图片对应的Uri
     * @param mContext 上下文对象
     * @return 获取图像的Bitmap
     */
    public static Bitmap getBitmapFromUri(Uri uri, Context mContext) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param context 上下文对象
     * @param uri     当前相册照片的Uri
     * @return 解析后的Uri对应的String
     */
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        String pathHead = "file:///";
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return pathHead + Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);

                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return pathHead + getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return pathHead + getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return pathHead + getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return pathHead + uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


}
