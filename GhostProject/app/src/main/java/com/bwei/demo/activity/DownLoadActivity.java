package com.bwei.demo.activity;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bwei.demo.R;
import com.bwei.demo.base.BaseActivity;
import com.bwei.demo.base.BasePresenter;
import com.bwei.demo.utils.download.service.DownloadProgressListener;
import com.bwei.demo.utils.download.service.FileDownloader;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.OnClick;

public class DownLoadActivity extends BaseActivity {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.resultView)
    TextView resultView;
    @BindView(R.id.downloadbutton)
    ImageView downloadbutton;
    @BindView(R.id.stopbutton)
    ImageView stopbutton;

    private String path = null;
    private static final int PROCESSING = 1;//正在下载实时数据传输Message标志
    private static final int FAILURE = -1;     //下载失败时的Message标志

    private Handler handler = new UIHandler();

    private final class UIHandler extends Handler {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PROCESSING:
                    // 更新进度
                    progressBar.setProgress(msg.getData().getInt("size"));
                    float num = (float) progressBar.getProgress()
                            / (float) progressBar.getMax();
                    int result = (int) (num * 100); // 计算进度
                    resultView.setText(result + "%");
                    if (progressBar.getProgress() == progressBar.getMax()) {
                        // 下载完成
                        Toast.makeText(DownLoadActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case FAILURE: // 下载失败
                    Toast.makeText(DownLoadActivity.this, "下载失败", Toast.LENGTH_SHORT).show();

                    break;
            }
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_down_load;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

        path = getIntent().getStringExtra("url");
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @OnClick({R.id.downloadbutton, R.id.stopbutton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.downloadbutton:

                Toast.makeText(DownLoadActivity.this, "开始下载", Toast.LENGTH_SHORT).show();
                startLoad();
                break;
            case R.id.stopbutton:

                exit();
                Toast.makeText(DownLoadActivity.this, "暂停下载", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //开始下载
    public void startLoad() {

        String filename = path.substring(path.lastIndexOf('/') + 1);
        try {
            // URL编码（这里是为了将中文进行URL编码）
            filename = URLEncoder.encode(filename, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        path = path.substring(0, path.lastIndexOf("/") + 1) + filename;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // File savDir =
            // Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
            // 保存路径
            File savDir = Environment.getExternalStorageDirectory();
            Log.i("路径", savDir + "");
            download(path, savDir);
        } else {
            Toast.makeText(DownLoadActivity.this, "没有SD卡", Toast.LENGTH_SHORT).show();

        }
    }

    /*
     * 由于用户的输入事件(点击button, 触摸屏幕....)是由主线程负责处理的，如果主线程处于工作状态，
     * 此时用户产生的输入事件如果没能在5秒内得到处理，系统就会报“应用无响应”错误。
     * 所以在主线程里不能执行一件比较耗时的工作，否则会因主线程阻塞而无法处理用户的输入事件，
     * 导致“应用无响应”错误的出现。耗时的工作应该在子线程里执行。
     */
    private DownloadTask task;

    private void exit() {
        if (task != null)
            task.exit();
    }

    private void download(String path, File savDir) {
        task = new DownloadTask(path, savDir);
        new Thread(task).start();
    }

    /**
     * UI控件画面的重绘(更新)是由主线程负责处理的，如果在子线程中更新UI控件的值，更新后的值不会重绘到屏幕上
     * 一定要在主线程里更新UI控件的值，这样才能在屏幕上显示出来，不能在子线程中更新UI控件的值
     */
    private final class DownloadTask implements Runnable {
        private String path;
        private File saveDir;
        private FileDownloader loader;

        public DownloadTask(String path, File saveDir) {
            this.path = path;
            this.saveDir = saveDir;
        }

        /**
         * 退出下载
         */
        public void exit() {
            if (loader != null)
                loader.exit();
        }

        DownloadProgressListener downloadProgressListener = new DownloadProgressListener() {
            @Override
            public void onDownloadSize(int size) {
                Message msg = new Message();
                msg.what = PROCESSING;
                msg.getData().putInt("size", size);
                handler.sendMessage(msg);
            }
        };

        public void run() {
            try {
                // 实例化一个文件下载器
                loader = new FileDownloader(getApplicationContext(), path, saveDir, 3);
                // 设置进度条最大值
                progressBar.setMax(loader.getFileSize());
                loader.download(downloadProgressListener);
            } catch (Exception e) {
                e.printStackTrace();
                handler.sendMessage(handler.obtainMessage(FAILURE)); // 发送一条空消息对象
            }
        }
    }

}
