/**
 *ClassName : CropImageHelper</br>
 * 
 * <p>2013© e-future.com.cn 版权所有 翻版必究</p>
 * <p>未经允许不得使用</p>
 *
 */
package com.xszj.mba.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * ClassName : CropImageHelper
 * <p>
 * 功能描述：
 * </p>
 * <p>
 * History
 * </p>
 * <p>
 * Create User: Chen limiao
 * </p>
 * <p>
 * Create Date: 2013-9-24 下午12:30:39
 * </p>
 * <p>
 * Update User:
 * </p>
 * <p>
 * Update Date:
 * </p>
 */
public class CropImageHelper {
	private Activity mActivity;
	private Fragment mFragment;
	private boolean mIsFragment = false;
	public final static int REQUEST_IMAGE_CHOICE = 10;
	public final static int REQUEST_IMAGE_CAPTURE = 20;
	public final static int REQUEST_IMAGE_CROP = 2000;

	/**
	 * 
	 */
	public CropImageHelper(Activity activity) {
		mActivity = activity;
	}

	public CropImageHelper(Fragment fragment, Activity activity, boolean isFragment) {
		mFragment = fragment;
		mActivity = activity;
		mIsFragment = isFragment;
	}

	/**
	 * 跳转到图库
	 */
	public void goImageChoice() {
		try {
			Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			intent.setType("image/*"); // 设置文件类型
			if (!mIsFragment) {
				mActivity.startActivityForResult(intent, REQUEST_IMAGE_CHOICE);// 转到图片
			} else {
				mFragment.startActivityForResult(intent, REQUEST_IMAGE_CHOICE);// 转到图片
			}
		} catch (Exception e) {
			Toast.makeText(mActivity, "请先安装图库", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 转到相机
	 */
	public Uri goCamera() {
		Intent captrueIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
		// camerSaveUri = getUri();
		// if (camerSaveUri == null) {
		// return camerSaveUri;
		// }
		File picFile = getUri();
		if (picFile == null) {
			return null;
		}
		captrueIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picFile));
		if (!mIsFragment) {
			mActivity.startActivityForResult(captrueIntent, REQUEST_IMAGE_CAPTURE);
		} else {
			mFragment.startActivityForResult(captrueIntent, REQUEST_IMAGE_CAPTURE);
		}
		return Uri.fromFile(picFile);
	}

	/**
	 * 跳转到图片裁剪
	 * 
	 * @param uri
	 */
	public void goZoomImage(Uri uri, int width, int height) {
		try {

			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(uri, "image/*");
			// 设置裁剪
			intent.putExtra("crop", "true");
			intent.putExtra("outputX", width);
			intent.putExtra("outputY", height);
			// aspectX aspectY 是宽高的比例
			intent.putExtra("aspectX", width);
			intent.putExtra("aspectY", height);
			intent.putExtra("return-data", true);
			//            intent.putExtra(MediaStore.EXTRA_OUTPUT, Utils.getTempUri("temp"));
			if (!mIsFragment) {
				mActivity.startActivityForResult(intent, REQUEST_IMAGE_CROP);
			} else {
				mFragment.startActivityForResult(intent, REQUEST_IMAGE_CROP);
			}
		} catch (Exception e) {
			Toast.makeText(mActivity, "请先安装图库", Toast.LENGTH_SHORT).show();
		}
	}

	public void goZoomImage2(Uri uri, int width, int height) {
		try {

			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(uri, "image/*");
			// 设置裁剪
			intent.putExtra("crop", "true");
			intent.putExtra("outputX", width);
			intent.putExtra("outputY", height);
			// aspectX aspectY 是宽高的比例
			intent.putExtra("aspectX", width);
			intent.putExtra("aspectY", height);
			intent.putExtra("return-data", false);
			//            intent.putExtra(MediaStore.EXTRA_OUTPUT, Utils.getTempUri("temp"));
			if (!mIsFragment) {
				mActivity.startActivityForResult(intent, REQUEST_IMAGE_CROP);
			} else {
				mFragment.startActivityForResult(intent, REQUEST_IMAGE_CROP);
			}
		} catch (Exception e) {
			Toast.makeText(mActivity, "请先安装图库", Toast.LENGTH_SHORT).show();
		}
	}

	private File getUri() {
		File dcimFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
		if (!dcimFile.exists()) {
			Toast.makeText(mActivity, "使用相机前请插入sd卡！", Toast.LENGTH_SHORT).show();
			return null;
		}
		File file = new File(dcimFile.getAbsolutePath() + "/dcim" + System.currentTimeMillis() + ".jpg");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * 查询给定的URI，获取图片，并按给定的最大高度和宽度保持长宽比压缩 <br>
	 * <b>注： maxwidth或者maxheight小于等于0时，保持原图片大小
	 * 
	 * @param contentResolver
	 *            用于查询
	 * @param uri
	 *            需要查询的URI
	 * @param maxwidth
	 *            图片的最大宽度
	 * @param maxheight
	 *            图片的最大高度
	 * @return
	 */
	public static Bitmap getBmpFromPath(String path, int maxwidth, int maxheight) {
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				orientation = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				orientation = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				orientation = 270;
				break;
			default:
				orientation = 0;
			}
			return compressBitmap(path, orientation, maxwidth, maxheight);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * @param uri
	 *            通过图片的Uri获取后缀名
	 * @return
	 */
	public static String getEntryName(String picturePath) {
		if (!TextUtils.isEmpty(picturePath)) {
			int start = picturePath.lastIndexOf(".");
			String format = "";
			if (start < 0) {
				format = picturePath;
			} else {
				format = picturePath.substring(start + 1);
			}
			return format;
		}
		return "";
	}

	/**
	 * 极限压缩
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static Bitmap compressBitmap(String filepath, int degrees, int maxwidth, int maxheight) {
		if (degrees == 90 || degrees == 270) {// 经过旋转
			int tmp = maxheight;
			maxheight = maxwidth;
			maxwidth = tmp;
		}
		return compressBitmap(filepath, degrees, getSimplesize(filepath, maxwidth, maxheight));
	}

	public static int[] getSize(String filepath, int maxwidth, int maxheight) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filepath, options);
		int width = options.outWidth;
		int height = options.outHeight;
		try {
			ExifInterface exifInterface = new ExifInterface(filepath);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
			case ExifInterface.ORIENTATION_ROTATE_270:
				width = options.outHeight;
				height = options.outWidth;
			default:
			}
		} catch (Exception e) {
		}
		float rate = getSimplesize(filepath, maxwidth, maxheight);
		return new int[] { (int) (width * rate), (int) (height * rate) };
	}

	private static float getSimplesize(String filepath, int maxwidth, int maxheight) {
		if (maxheight <= 0 || maxwidth <= 0) {
			return 1;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filepath, options);
		int width = options.outWidth;
		int height = options.outHeight;
		float rate = 1;
		if (maxheight < height) {
			rate = (float) maxheight / (float) height;
		}
		if (maxwidth < width) {
			rate = Math.min(rate, (float) maxwidth / (float) width);
		}
		return rate;
	}

	public static Bitmap compressBitmap(String filepath, int degrees, float scale) {
		Bitmap bitmap = null;
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeFile(filepath, options);
			if (degrees != 0 || scale < 1) {
				Matrix matrix = new Matrix();
				matrix.setRotate(degrees);
				matrix.setScale(scale, scale);
				Bitmap nbitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
				bitmap.recycle();
				bitmap = nbitmap;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	@SuppressLint("NewApi")
	public synchronized static String getImgLocalPath(final Context context, final Uri uri) {

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/" + split[1];
				}

				// TODO handle non-primary volumes
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {

				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
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
				final String[] selectionArgs = new String[] { split[1] };

				return getDataColumn(context, contentUri, selection, selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {

			// Return the remote address
			if (isGooglePhotosUri(uri))
				return uri.getLastPathSegment();

			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 * 
	 * @param context
	 *            The context.
	 * @param uri
	 *            The Uri to query.
	 * @param selection
	 *            (Optional) Filter used in the query.
	 * @param selectionArgs
	 *            (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = { column };

		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}

	public synchronized static void compressImage(final Bitmap image, final ImageProcessListener listener) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
				int options = 100;
				while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于50kb,大于继续压缩
					baos.reset();// 重置baos即清空baos
					image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
					options -= 5;// 每次都减少5
				}
				ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
				Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
				if (listener != null) {
					listener.onProcessed(bitmap);
				}
			}
		}).start();
	}

	public static String parasImageName(String imgPath) {
		String imgName = "";
		int start = imgPath.lastIndexOf("/");
		if (start < 0) {
			imgName = imgPath;
		} else {
			imgName = imgPath.substring(start + 1);
		}
		return imgName;
	}

	public interface ImageProcessListener {
		public void onProcessed(Bitmap bitmap);
	}

	/**
	 * 把bitmap转换成String
	 * 
	 * @param filePath
	 * @return
	 */
	public static String bitmapToString(Bitmap bm) {

		// Bitmap bm = getSmallBitmap(filePath);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
		byte[] b = baos.toByteArray();

		return Base64.encodeToString(b, Base64.DEFAULT);

	}

	/**
	 * 计算图片的缩放值
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	/**
	 * 根据路径获得突破并压缩返回bitmap用于显示
	 * 
	 * @param imagesrc
	 * @return
	 */
	public static void getSmallBitmap(final String filePath, final ImageProcessListener listener) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				final BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;
				BitmapFactory.decodeFile(filePath, options);

				// Calculate inSampleSize
				options.inSampleSize = calculateInSampleSize(options, 480, 800);

				// Decode bitmap with inSampleSize set
				options.inJustDecodeBounds = false;

				Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
				int orientation = 0;
				try {
					ExifInterface exifInterface = new ExifInterface(filePath);
					orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
					switch (orientation) {
					case ExifInterface.ORIENTATION_ROTATE_90:
						orientation = 90;
						break;
					case ExifInterface.ORIENTATION_ROTATE_180:
						orientation = 180;
						break;
					case ExifInterface.ORIENTATION_ROTATE_270:
						orientation = 270;
						break;
					default:
						orientation = 0;
					}
				} catch (Exception e) {
				}
				if (orientation != 0) {
					Matrix matrix = new Matrix();
					matrix.setRotate(orientation);
					bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
				}
				if (listener != null) {
					listener.onProcessed(bitmap);
				}
			}
		}).start();
	}

	/**
	 * 根据路径删除图片
	 * 
	 * @param path
	 */
	public static void deleteTempFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 添加到图库
	 */
	public static void galleryAddPic(Context context, String path) {
		Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File f = new File(path);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		context.sendBroadcast(mediaScanIntent);
	}

	public static Bitmap getBitmap(String path) {

		//先解析图片边框的大小
		BitmapFactory.Options ops = new BitmapFactory.Options();
		ops.inJustDecodeBounds = true;
		Bitmap bm = BitmapFactory.decodeFile(path, ops);
		ops.inSampleSize = 1;
		int oHeight = ops.outHeight;
		int oWidth = ops.outWidth;

		//控制压缩比
		int contentHeight = 0;
		int contentWidth = 0;
		//		if (isLargeFlg) {
		contentHeight = 500;
		contentWidth = 500;
		//		} else {
		//			contentHeight = ITEM_HEIGHT;
		//			contentWidth = ITEM_WIDTH;
		//		}
		if (((float) oHeight / contentHeight) < ((float) oWidth / contentWidth)) {
			ops.inSampleSize = (int) Math.ceil((float) oWidth / contentWidth);
		} else {
			ops.inSampleSize = (int) Math.ceil((float) oHeight / contentHeight);
		}
		ops.inJustDecodeBounds = false;
		bm = BitmapFactory.decodeFile(path, ops);
		return bm;
	}

}
