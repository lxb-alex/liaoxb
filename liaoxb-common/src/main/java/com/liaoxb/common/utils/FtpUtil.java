package com.liaoxb.common.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description ftp 工具类<br/>
 * 实现文件上传下载
 * @Author liaoxb
 * @Date 2017/9/27 13:48:48
 */
public class FtpUtil {
    private static Log log = LogFactory.getLog(FtpUtil.class);

    private static final String ftpHost = propertiesUtil.getPropertiesValue("ftpHost");
    private static final String ftpUserName = propertiesUtil.getPropertiesValue("userName");
    private static final String ftpPassword = propertiesUtil.getPropertiesValue("password");
    private static final int ftpPort = Integer.parseInt(propertiesUtil.getPropertiesValue("ftpPort"));

    public FTPClient ftpClient;
    public String token = null; // 上传、下载进度的唯一标识

    /**
     * 连接ftp服务
     * @param ftpClient FTPClinet 对象
     * @return boolen
     * @Date 2017/9/27 16:03
     */
    public boolean connect(FTPClient ftpClient, String token){
        try {
            this.token = token;
            ftpClient.connect(ftpHost, ftpPort); // 连接ftp服务
            // 设置别动模式，不能在登录后设置，在登录后设置，listFiles()方法获取不到文件，为null
            ftpClient.enterLocalPassiveMode();
            ftpClient.login(ftpUserName, ftpPassword);
            //ftpClient.login("anonymous", ""); // 匿名用户登录
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE); // 设置二进制方式读取
            ftpClient.setControlEncoding("GBK"); // 中文支持
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())){
                log.debug("连接FTP失败，用户或密码错误！");
                ftpClient.disconnect();
            }else{
                log.debug("FTP连接成功！");
                return true;
            }
        } catch (SocketException e) {
            e.printStackTrace();
            log.error(e.getMessage()+"FTP的IP地址可能错误，请正确配置。");
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage()+"FTP的端口错误,请正确配置。");
        }
        return false;
    }

    /**
     * 断开服务器
     */
    public static void disconnect(FTPClient ftpClient) {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从FTP服务器下载文件<br/>
     * 没有指定下载路径，浏览器下载
     * @param request 请求消息
     * @param response 返回消息
     * @param ftpPath FTP服务器中文件所在路径 格式： ftptest/aa
     * @param fileName 文件名称
     * @param token 文件进度唯一标识
     */
    public static boolean download(HttpServletRequest request, HttpServletResponse response, FTPClient ftpClient,
                                          String ftpPath, String fileName, String token) {
        try {
            // 获取指定文件
            FTPFile[] files = ftpClient.listFiles(ftpPath + fileName);
            if (files.length == 0) {
                return false;
            }
            InputStream in = ftpClient.retrieveFileStream(new String((ftpPath+fileName).getBytes("GBK"), "iso-8859-1"));
            if (in ==null)return false;
            long lRemoteSize = files[0].getSize();
            // 会自动判断下载文件类型
            response.setContentType("multipart/octet-stream");
            String name = new String(fileName.substring(1).getBytes("GBK"),"ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ name);//以下载方式接收
            response.setHeader("Content-Length", String.valueOf(lRemoteSize));
            OutputStream out = response.getOutputStream();
            // 服务器文件大小
            long step = lRemoteSize/100;
            long process = 0;
            long localSize = 0;
            byte[] bytes = new byte[1024];
            int c;
            while ((c = in.read(bytes)) != -1) {
                out.write(bytes, 0, c);
                localSize += c;
                long nowProcess = localSize/step;
                if (nowProcess > process) {
                    process = nowProcess;
                    if (localSize == lRemoteSize) // 防止文件太小造成process超过100
                        process = 100;
                    // TODO 保存更新下载进度
                }
            }
            log.debug("下载成功，总大小为："+localSize);
            in.close();
            out.flush();
            out.close();
            boolean isDo = ftpClient.completePendingCommand();
            if (isDo) {
                log.debug("下载成功");
                return true;
            } else {
                log.debug("下载失败");
            }
        } catch (FileNotFoundException e1) {
            log.error(e1.getMessage()+"没有找到" + ftpPath + "文件",e1);
            e1.printStackTrace();
        } catch (SocketException e2) {
            log.error(e2.getMessage()+"连接FTP失败.");
            e2.printStackTrace();
        } catch (IOException e3) {
            log.error(e3.getMessage()+"文件读取错误。");
            e3.printStackTrace();
        }finally {
            disconnect(ftpClient);
        }
        return false;
    }


}
