package com.xszj.mba.personImage;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public  class ActivityBasePhoto extends Activity {

	
	// 以下是和拍照有关的数据
	/**
	 * 本页面打开相册的请求码。
	 */
	public static final int FLAG_CHOOSE_IMG = 1;

	/**
	 * 本页面打开Camera APP的请求码。
	 */
	public static final int FLAG_CHOOSE_PHONE = 2;

	/**
	 * 打开CropImageActivity页面的请求码。
	 */
	public static final int FLAG_MODIFY_FINISH = 3;

	// 相机拍照
	public static final String IMAGE_PATH = "yunshuxie";
	public static final File FILE_SDCARD = Environment
			.getExternalStorageDirectory();
	public static final File FILE_LOCAL = new File(FILE_SDCARD, IMAGE_PATH);
	public static final File FILE_PIC_SCREENSHOT = new File(FILE_LOCAL,
			"images/screenshots");

	private String file_url;
	/**
	 * 拍摄的照片名称。
	 */
	private static String localTempImageFileName = "";
	public Bitmap bitmap = null;
	public Handler mHandler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 和拍照有关参数初始化
		IMAGE_FILE_LOCATION = "file://" + FileCache(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}


	// 和拍照有关
	private String FileCache(Context context) {
		// 如果有SD卡则在SD卡中建一个自定义的目录存放缓存的图片
		// 没有SD卡就放在系统的缓存目录中
		File cacheDir;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
			cacheDir = new File(
					Environment.getExternalStorageDirectory(),
					"yunshuxie");
		else
			cacheDir = context.getCacheDir();
		if (!cacheDir.exists())
			cacheDir.mkdirs();
		return cacheDir.getPath();
	}

	// 一下和拍照有关
	/**
	 * 打开添加图片对话框。
	 */
	private String IMAGE_FILE_LOCATION;
	public String str_path = "";

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK) {
			// result is not correct
			return;
		} else {
			switch (requestCode) {
			case FLAG_CHOOSE_IMG:
				// 表示从相册APP返回到当前页面了。
				if (data != null) {
					Uri uri = data.getData();
					if (!TextUtils.isEmpty(uri.getAuthority())) {
						Cursor cursor = getContentResolver().query(uri,
								new String[] { MediaStore.Images.Media.DATA },
								null, null, null);
						if (null == cursor) {
							// Toast.makeText("", "图片没找到", 0).show();
							return;
						}
						cursor.moveToFirst();
						String path = cursor.getString(cursor
								.getColumnIndex(MediaStore.Images.Media.DATA));
						cursor.close();
						Intent intent = new Intent(this,
								CropImageActivity.class);
						intent.putExtra("path", path);
						startActivityForResult(intent, FLAG_MODIFY_FINISH);
					} else {
						Intent intent = new Intent(this,
								CropImageActivity.class);
						intent.putExtra("path", uri.getPath());
						startActivityForResult(intent, FLAG_MODIFY_FINISH);
					}
				}
				break;

			case FLAG_CHOOSE_PHONE:
				File f = new File(FILE_PIC_SCREENSHOT, localTempImageFileName);
				Intent intent = new Intent(this, CropImageActivity.class);
				intent.putExtra("path", f.getAbsolutePath());

				file_url = f.getPath();
				Log.i("MSG", file_url + "d");
				startActivityForResult(intent, FLAG_MODIFY_FINISH);
				break;
			case FLAG_MODIFY_FINISH:
				if (data != null) {
					final String path = data.getStringExtra("path");
					str_path = path + "";
					Log.i("MSG", "xxxx" + path);
					bitmap = BitmapFactory.decodeFile(path);
					mHandler.obtainMessage(3).sendToTarget();
				}
				break;
			default:
				break;
			}
		}

	}

	/***
	 * android获取系统当前时间
	 */
	public static String getDateToPicName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}

	/**
	 * 打开相册APP。
	 */
	public void gallery() {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, FLAG_CHOOSE_IMG);

	}

	/**
	 * 打开拍照APP。
	 */
	public void camera() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			try {
				localTempImageFileName = "";
				localTempImageFileName = String.valueOf((new Date()).getTime())
						+ ".png";
				File filePath = FILE_PIC_SCREENSHOT;
				if (!filePath.exists()) {
					filePath.mkdirs();
				}
				Intent intent2 = new Intent(
						MediaStore.ACTION_IMAGE_CAPTURE);
				File f = new File(filePath, localTempImageFileName);
				// localTempImgDir和localTempImageFileName是自己定义的名字
				Uri u = Uri.fromFile(f);
				intent2.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
				intent2.putExtra(MediaStore.EXTRA_OUTPUT, u);
				startActivityForResult(intent2, FLAG_CHOOSE_PHONE);
			} catch (ActivityNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
