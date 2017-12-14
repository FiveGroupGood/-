package com.bwei.demo.utils.download.service;

import android.content.Context;
import android.util.Log;

import com.bwei.demo.utils.download.db.FileService;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author ${张健}
 * @date 2017/11/14/14:31
 */

public class FileDownloader {
    private static final String TAG = "FileDownloader";
    private Context context;
    private FileService fileService;
    /* 停止下载 */
    private boolean exit;
    /* 已下载文件长度 */
    private int downloadSize = 0;
    /* 原始文件长度 */
    private int fileSize = 0;
    /* 线程数 */
    private DownloadThread[] threads;
    /* 本地保存文件 */
    private File saveFile;
    /* 缓存各线程下载的长度 */
    private Map<Integer, Integer> data = new ConcurrentHashMap<Integer, Integer>();
    /* 每条线程下载的长度 */
    private int block;
    /* 下载路径 */
    private String downloadUrl;

    /**
     * 获取线程数
     */
    public int getThreadSize() {
        return threads.length;
    }

    /**
     * 退出下载
     */
    public void exit() {
        this.exit = true;
    }

    public boolean getExit() {
        return this.exit;
    }

    /**
     * 获取文件大小
     *
     * @return
     */
    public int getFileSize() {
        return fileSize;
    }

    /**
     * 累计已下载大小
     *
     * @param size
     */
    protected synchronized void append(int size) {
        downloadSize += size;
    }

    /**
     * 更新指定线程最后下载的位置
     *
     * @param threadId
     *            线程id
     * @param pos
     *            最后下载的位置
     */
    protected synchronized void update(int threadId, int pos) {
        this.data.put(threadId, pos);
        this.fileService.update(this.downloadUrl, threadId, pos);
    }

    /**
     * 构建文件下载器
     *
     * @param downloadUrl
     *            下载路径
     * @param fileSaveDir
     *            文件保存目录
     * @param threadNum
     *            下载线程数
     */
    public FileDownloader(Context context, String downloadUrl,
                          File fileSaveDir, int threadNum) {


        try {
            this.context = context;
            this.downloadUrl = downloadUrl;
            fileService = new FileService(this.context);
            URL url = new URL(this.downloadUrl);
            if (!fileSaveDir.exists()) // 判断目录是否存在，如果不存在，创建目录
                fileSaveDir.mkdirs();
            this.threads = new DownloadThread[threadNum];// 实例化线程数组
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestMethod("GET");
            //设置用户端可以接收的媒体类型
            conn.setRequestProperty(
                    "Accept",
                    "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
            //设置用户语言
            conn.setRequestProperty("Accept-Language", "zh-CN");
            //设置请求的来源页面,便于服务端进行来源统计
            conn.setRequestProperty("Referer", downloadUrl);
            //设置客户端编码
            conn.setRequestProperty("Charset", "UTF-8");
            //设置用户代理
            conn.setRequestProperty(
                    "User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.connect();      //和远程资源建立正在的链接,但尚无返回的数据流
            printResponseHeader(conn);   //打印返回的Http的头字段集合
            //对返回的状态码进行判断,用于检查是否请求成功,返回200时执行下面的代码
            if (conn.getResponseCode() == 200) { // 响应成功
                // 根据响应获取文件大小
                this.fileSize = conn.getContentLength();
                if (this.fileSize <= 0)
                    throw new RuntimeException("Unkown file size ");

                String filename = getFileName(conn);// 获取文件名称
                this.saveFile = new File(fileSaveDir, filename);// 构建保存文件
                Map<Integer, Integer> logdata = fileService
                        .getData(downloadUrl);// 获取下载记录
                if (logdata.size() > 0) {// 如果存在下载记录
                    for (Map.Entry<Integer, Integer> entry : logdata.entrySet())
                        // 把各条线程已经下载的数据长度放入data中
                        data.put(entry.getKey(), entry.getValue());
                }
                //如果已下载的数据的线程数和现在设置的线程数相同时则计算所有现场已经下载的数据总长度
                if (this.data.size() == this.threads.length) {
                    //遍历每条线程已下载的数据
                    for (int i = 0; i < this.threads.length; i++) {
                        this.downloadSize += this.data.get(i + 1);
                    }
                    print("已经下载的长度" + this.downloadSize);
                }
                // 计算每条线程下载的数据长度
                this.block = (this.fileSize % this.threads.length) == 0 ? this.fileSize
                        / this.threads.length
                        : this.fileSize / this.threads.length + 1;
            } else {
                throw new RuntimeException("server no response ");
            }
        } catch (Exception e) {
            print(e.toString());
            throw new RuntimeException("don't connection this url");
        }
    }

    /**
     * 获取文件名
     */
    private String getFileName(HttpURLConnection conn) {
        //从下载的路径的字符串中获取文件的名称
        String filename = this.downloadUrl.substring(this.downloadUrl
                .lastIndexOf('/') + 1);
        if (filename == null || "".equals(filename.trim())) {// 如果获取不到文件名称
            for (int i = 0;; i++) {
                String mine = conn.getHeaderField(i);     //从返回的流中获取特定索引的头字段的值
                if (mine == null)
                    break;  //如果遍历到了返回头末尾则退出循环
                //获取content-disposition返回字段,里面可能包含文件名
                if ("content-disposition".equals(conn.getHeaderFieldKey(i)
                        .toLowerCase())) {
                    //使用正则表达式查询文件名
                    Matcher m = Pattern.compile(".*filename=(.*)").matcher(mine.toLowerCase());
                    if (m.find())
                        return m.group(1); //如果有符合正则表达式规则的字符串,返回
                }
            }
            filename = UUID.randomUUID() + ".tmp";//如果都没找到的话,默认取一个文件名
            //由网卡标识数字(每个网卡都有唯一的标识号)以及CPU时间的唯一数字生成的一个16字节的二进制作为文件名
        }
        return filename;
    }

    /**
     *  开始下载文件
     * @param listener 监听下载数量的变化,如果不需要了解实时下载的数量,可以设置为null
     * @return 已下载文件大小
     * @throws Exception
     */
    //进行下载,如果有异常的话,抛出异常给调用者
    public int download(DownloadProgressListener listener) throws Exception {
        try {
            RandomAccessFile randOut = new RandomAccessFile(this.saveFile, "rw");
            if (this.fileSize > 0)
                randOut.setLength(this.fileSize); // 预分配fileSize大小
            randOut.close();//关闭该文件,使设置生效
            URL url = new URL(this.downloadUrl);
            if (this.data.size() != this.threads.length) {
                // 如果原先未曾下载或者原先的下载线程数与现在的线程数不一致
                this.data.clear();
                for (int i = 0; i < this.threads.length; i++) {
                    this.data.put(i + 1, 0);// 初始化每条线程已经下载的数据长度为0
                }
                this.downloadSize = 0;
            }
            for (int i = 0; i < this.threads.length; i++) {// 开启线程进行下载
                int downLength = this.data.get(i + 1);
                if (downLength < this.block
                        && this.downloadSize < this.fileSize) {// 判断线程是否已经完成下载,否则继续下载
                    this.threads[i] = new DownloadThread(this, url,
                            this.saveFile, this.block, this.data.get(i + 1),
                            i + 1);
                    this.threads[i].setPriority(7); // 设置线程优先级
                    this.threads[i].start();
                } else {
                    this.threads[i] = null;
                }
            }
            fileService.delete(this.downloadUrl);// 如果存在下载记录，删除它们，然后重新添加
            fileService.save(this.downloadUrl, this.data);
            boolean notFinish = true;// 下载未完成
            while (notFinish) {// 循环判断所有线程是否完成下载
                Thread.sleep(900);
                notFinish = false;// 假定全部线程下载完成
                for (int i = 0; i < this.threads.length; i++) {
                    if (this.threads[i] != null && !this.threads[i].isFinish()) {// 如果发现线程未完成下载
                        notFinish = true;// 设置标志为下载没有完成
                        if (this.threads[i].getDownLength() == -1) {// 如果下载失败,再重新下载
                            this.threads[i] = new DownloadThread(this, url,
                                    this.saveFile, this.block,
                                    this.data.get(i + 1), i + 1);
                            this.threads[i].setPriority(7);
                            this.threads[i].start();
                        }
                    }
                }
                if (listener != null)
                    listener.onDownloadSize(this.downloadSize);// 通知目前已经下载完成的数据长度
            }
            if (downloadSize == this.fileSize)
                fileService.delete(this.downloadUrl);// 下载完成删除记录
        } catch (Exception e) {
            print(e.toString());
            throw new Exception("file download error");
        }
        return this.downloadSize;
    }

    /**
     * 获取Http响应头字段
     * @param http
     * @return
     */
    public static Map<String, String> getHttpResponseHeader(HttpURLConnection http) {
        //使用LinkedHashMap保证写入和便利的时候的顺序相同,而且允许空值
        Map<String, String> header = new LinkedHashMap<String, String>();
        //此处使用无线循环,因为不知道头字段的数量
        for (int i = 0;; i++) {
            String mine = http.getHeaderField(i);  //获取第i个头字段的值
            if (mine == null) break;      //没值说明头字段已经循环完毕了,使用break跳出循环
            header.put(http.getHeaderFieldKey(i), mine); //获得第i个头字段的键
        }
        return header;
    }

    /**
     * 打印Http头字段
     * @param http
     */
    public static void printResponseHeader(HttpURLConnection http){
        //获取http响应的头字段
        Map<String, String> header = getHttpResponseHeader(http);
        //使用增强for循环遍历取得头字段的值,此时遍历的循环顺序与输入树勋相同
        for(Map.Entry<String, String> entry : header.entrySet()){
            //当有键的时候则获取值,如果没有则为空字符串
            String key = entry.getKey()!=null ? entry.getKey()+ ":" : "";
            print(key+ entry.getValue());      //打印键和值得组合
        }
    }

    private static void print(String msg) {
        Log.i(TAG, msg);
    }
}
