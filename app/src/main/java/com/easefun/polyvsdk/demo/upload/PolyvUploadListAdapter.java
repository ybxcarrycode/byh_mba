package com.easefun.polyvsdk.demo.upload;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.easefun.polyvsdk.demo.upload.PolyvULNotificationService.BindListener;
import com.easefun.polyvsdk.upload.PolyvUploader;
import com.easefun.polyvsdk.upload.PolyvUploaderManager;
import com.xszj.mba.R;

import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class PolyvUploadListAdapter extends BaseAdapter {
	private static final String TAG = "PolyvUploadListAdapter";

	private static final int REFRESH_PROGRESS = 1;
	private static final int SUCCESS = 2;
	private static final int FAILURE = 3;

	private LinkedList<PolyvUploadInfo> data;
	private Context context;
	private LayoutInflater inflater;
	private PolyvUDBService service;
	private ViewHolder holder;

	private PolyvUploader uploader;

	private ListView listView;
	private PolyvULNotificationService notificationService;
	private ServiceConnection serconn;
	// 每个id的progress
	private SparseIntArray id_progress = new SparseIntArray();
	// 完成任务的key集合,key=filePath
	private List<String> finishKeys;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Button btn = null;
			int position = (int) msg.arg1;
			int offset = position - listView.getFirstVisiblePosition();
			int endset = position - listView.getLastVisiblePosition();
			if (offset < 0 || endset > 0)
				return;
			View view = (View) listView.getChildAt(offset);
			switch (msg.what) {
			case REFRESH_PROGRESS:
				btn = (Button) view.findViewById(R.id.upload);
				long downloaded = msg.getData().getLong("count");
				long total = msg.getData().getLong("total");
				ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
				progressBar.setMax((int) total);
				progressBar.setProgress((int) downloaded);
				TextView tv = (TextView) view.findViewById(R.id.rate);
				tv.setText("" + downloaded * 100 / total);
				break;

			case SUCCESS:
				btn = (Button) view.findViewById(R.id.upload);
				Toast.makeText(context, (position + 1) + "上传成功", Toast.LENGTH_SHORT).show();
				btn.setText("完成");
				break;

			case FAILURE:
				int errorType = msg.getData().getInt("error");
				btn = (Button) view.findViewById(R.id.upload);
				btn.setText("开始");

				switch (errorType) {
				case PolyvUploader.FFILE:
					Toast.makeText(context, "第" + (position + 1) + "个任务文件不存在，或者大小为0", 0).show();
					break;
				case PolyvUploader.FVIDEO:
					Toast.makeText(context, "第" + (position + 1) + "个任务不是支持上传的视频格式", 0).show();
					break;
				case PolyvUploader.NETEXCEPTION:
					Toast.makeText(context, "第" + (position + 1) + "个任务网络异常，请重试", 0).show();
					break;
				}

				break;
			}
		};
	};

	public ServiceConnection getSerConn() {
		return serconn;
	}

	public PolyvUploadListAdapter(Context context, LinkedList<PolyvUploadInfo> data, ListView listView) {
		serconn = PolyvULNotificationService.bindUploadService(context, new BindListener() {

			@Override
			public void bindSuccess(PolyvULNotificationService uploadService) {
				notificationService = uploadService;
			}
		});
		this.context = context;
		this.data = data;
		this.inflater = LayoutInflater.from(context);
		this.service = new PolyvUDBService(context);
		this.listView = listView;
		finishKeys = new ArrayList<String>();
		initUploaders();
	}

	private class MyUploadListener implements PolyvUploader.UploadListener {
		private int position;
		private PolyvUploadInfo info;
		private int id;

		public MyUploadListener(int position, PolyvUploadInfo info) {
			this.position = position;
			this.info = info;
			this.id = PolyvULNotificationService.getId(info.getFilepath());
		}

		public void setPosition(int position) {
			this.position = position;
		}

		@Override
		public void fail(int category) {
			if (notificationService != null) {
				notificationService.updateErrorNF(id, false);
			}
			Message msg = handler.obtainMessage();
			msg.arg1 = position;
			msg.what = FAILURE;
			Bundle bundle = new Bundle();
			bundle.putInt("error", category);
			msg.setData(bundle);
			handler.sendMessage(msg);
		}

		@Override
		public void upCount(long count, long total) {
			int progress = (int) (count * 100 / total);
			id_progress.put(id, progress);
			if (notificationService != null)
				notificationService.updateDownloadingNF(id, progress, false);
			Message msg = handler.obtainMessage();
			msg.arg1 = position;
			Bundle bundle = new Bundle();
			bundle.putLong("count", count);
			bundle.putLong("total", total);
			msg.setData(bundle);
			msg.what = REFRESH_PROGRESS;
			service.updatePercent(info, count, total);
			handler.sendMessage(msg);
		}

		@Override
		public void success(long total, String vid) {
			addFinishKeyToList(info.getFilepath());
			if (notificationService != null)
				notificationService.updateFinishNF(id);
			Message msg = handler.obtainMessage();
			msg.arg1 = position;
			msg.what = SUCCESS;
			service.updatePercent(info, total, total);
			handler.sendMessage(msg);
		}
	}

	private void initUploaders() {
		for (int i = 0; i < data.size(); i++) {
			final PolyvUploadInfo info = data.get(i);
			final int p = i;
			uploader = PolyvUploaderManager.getPolyvUploader(info.getFilepath(), info.getTitle(), info.getDesc());
			MyUploadListener uploadListener = new MyUploadListener(p, info);
			uploader.setUploadListener(uploadListener);
		}
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		final int i = position;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.view_item_upload, null);
			holder = new ViewHolder();
			holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			holder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
			holder.tv_filesize = (TextView) convertView.findViewById(R.id.tv_filesize);
			holder.progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
			holder.btn_upload = (Button) convertView.findViewById(R.id.upload);
			holder.btn_delete = (Button) convertView.findViewById(R.id.delete);
			holder.tv_rate = (TextView) convertView.findViewById(R.id.rate);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		data = service.getUploadFiles();
		final PolyvUploadInfo info = data.get(position);
		String desc = info.getDesc();
		String title = info.getTitle();
		String filePath = info.getFilepath();
		long filesize = info.getFilesize();
		long percent = info.getPercent();
		long total = info.getTotal();
		// 初始化progress
		int id = PolyvULNotificationService.getId(filePath);
		int progress = 0;
		if (total != 0)
			progress = (int) (percent * 100 / total);
		if (id_progress.get(id, -1) == -1)
			id_progress.put(id, progress);
		holder.tv_title.setText(info.getTitle());
		holder.tv_desc.setText(desc);
		holder.tv_filesize.setText("" + filesize);
		holder.progressBar.setTag("" + position);
		holder.progressBar.setMax((int) total);
		holder.progressBar.setProgress((int) percent);
		if (total != 0)
			holder.tv_rate.setText("" + percent * 100 / total);
		else
			holder.tv_rate.setText("" + 0);
		if (total != 0 && total == percent) {
			addFinishKeyToList(filePath);
			holder.btn_upload.setText("完成");
		} else if (PolyvUploaderManager.getPolyvUploader(filePath, title, desc).isUploading())
			holder.btn_upload.setText("暂停");
		else
			holder.btn_upload.setText("开始");
		holder.btn_upload.setOnClickListener(new UploadListener(filePath, title, desc, convertView));
		holder.btn_delete.setOnClickListener(new DeleteListener(info, position));
		return convertView;
	}

	// 把完成的任务加入到key集合中
	private void addFinishKeyToList(String filePath) {
		String key = filePath;
		if (!finishKeys.contains(key))
			finishKeys.add(key);
	}

	// 从集合中移除完成的任务key
	private void removeFinishKeyToList(String filePath) {
		String key = filePath;
		if (finishKeys.contains(key))
			finishKeys.remove(key);
	}

	public void uploadAllFile() {
		if (notificationService != null)
			notificationService.updateUnfinishedNF(data, finishKeys);
		PolyvUploaderManager.startUnfinished(finishKeys);
	}

	public void updateAllButton(boolean isStop) {
		for (int i = 0; i < listView.getChildCount(); i++) {
			Button down = (Button) listView.getChildAt(i).findViewById(R.id.upload);
			if (!down.getText().equals("完成")) {
				if (isStop)
					down.setText("暂停");
				else
					down.setText("开始");
			}
		}
	}

	private class ViewHolder {
		TextView tv_title, tv_desc, tv_filesize, tv_rate;
		ProgressBar progressBar;
		Button btn_upload, btn_delete;
	}

	class UploadListener implements View.OnClickListener {
		private final String filePath;
		private final String desc;
		private final String title;
		private View view;

		public UploadListener(String filePath, String title, String desc, View view) {
			this.filePath = filePath;
			this.title = title;
			this.desc = desc;
			this.view = view;
		}

		@Override
		public void onClick(View v) {
			Button upload = (Button) view.findViewById(R.id.upload);
			int id = PolyvULNotificationService.getId(filePath);
			if (upload.getText().equals("开始")) {
				((Button) v).setText("暂停");
				PolyvUploader uploader = PolyvUploaderManager.getPolyvUploader(filePath, title, filePath);
				// 先执行
				if (notificationService != null) {
					notificationService.updateStartNF(id, filePath, title, desc, id_progress.get(id));
				}
				if (upload != null)
					uploader.start();
			} else if (upload.getText().equals("暂停")) {
				((Button) v).setText("开始");
				PolyvUploader uploader = PolyvUploaderManager.getPolyvUploader(filePath, title, filePath);
				// 先执行
				if (notificationService != null) {
					notificationService.updatePauseNF(id);
				}
				if (uploader != null) {
					uploader.pause();
				}
			}
		}

	}

	class DeleteListener implements View.OnClickListener {
		private PolyvUploadInfo info;
		private int position;

		public DeleteListener(PolyvUploadInfo info, int position) {
			this.info = info;
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			removeFinishKeyToList(info.getFilepath());
			PolyvUploader uploader = PolyvUploaderManager.getPolyvUploader(info.getFilepath(), info.getTitle(),
					info.getDesc());
			PolyvUploaderManager.removePolyvUpload(info.getFilepath());
			int id = PolyvULNotificationService.getId(info.getFilepath());
			if (notificationService != null) {
				notificationService.updateDeleteNF(id);
			}
			if (uploader != null) {
				uploader.pause();
			}
			service.deleteUploadFile(info);
			data.remove(position);
			initUploaders();
			notifyDataSetChanged();
		}
	}

	public void stopAll() {
		if (notificationService != null)
			notificationService.updateAllPauseNF(data);
		PolyvUploaderManager.stopAll();
	}
}
