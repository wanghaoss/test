package com.etuo.kucun.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.etuo.kucun.FrameworkApp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
	
	public static String SDPATH = FrameworkApp.CACHE_PIC_ROOT_DIR;

	public static void saveBitmap(Bitmap bm, String picName) {
		Log.e("", "保存图片");
		try {
			if (!isFileExist("")) {
				createSDDir("");
			}
			File f = new File(SDPATH, picName + ".PNG");
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
			Log.e("", "已经保存");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static File createSDDir(String dirName) throws IOException {
		File dir = new File(SDPATH + dirName);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			System.out.println("createSDDir:" + dir.getAbsolutePath());
			System.out.println("createSDDir:" + dir.mkdir());
		}
		return dir;
	}

	public static boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		file.isFile();
		return file.exists();
	}
	
	public static void delFile(String fileName){
		File file = new File(SDPATH + fileName);
		if(file.isFile()){
			file.delete();
        }
		file.exists();
	}

	public static void deleteDir() {
		File dir = new File(SDPATH);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;
		
		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete(); // 删除所有文件
			else if (file.isDirectory())
				deleteDir(); // 递规的方式删除文件夹
		}
		dir.delete();// 删除目录本身
	}

	public static boolean fileIsExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public static final byte[] input2byte(InputStream inStream) {
		if (inStream == null)
			return null;
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		try {
			while ((rc = inStream.read(buff, 0, 100)) > 0) {
				swapStream.write(buff, 0, rc);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return swapStream.toByteArray();
	}
}
