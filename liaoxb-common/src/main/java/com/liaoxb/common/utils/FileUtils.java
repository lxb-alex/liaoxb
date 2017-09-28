package com.liaoxb.common.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * @Description 文件操作工具类<br/>
 * 实现文件的创建、删除、复制、压缩、解压以及目录的创建、删除、复制、压缩解压等功能
 * @Author liaoxb
 * @Date 2017/9/25 15:32:32
 */
public class FileUtils extends org.apache.commons.io.FileUtils {
        private static Log logger = LogFactory.getLog(FileUtils.class);

    /**
     * 获取文件类型<br/>
     * 根据“文件名的后缀”获取文件内容类型（而非根据File.getContentType()读取的文件类型）
     * @param fileName 带验证的文件名
     * @return 返回文件类型
     */
    public static String getContentType(String fileName) {
        String contentType = "application/octet-stream";
        if (fileName.lastIndexOf(".") < 0){
            return contentType;
        }
        fileName = fileName.toLowerCase();
        fileName = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (fileName.equals("html") || fileName.equals("htm") || fileName.equals("shtml")) {
            contentType = "text/html";
        } else if (fileName.equals("apk")) {
            contentType = "application/vnd.android.package-archive";
        } else if (fileName.equals("sis")) {
            contentType = "application/vnd.symbian.install";
        } else if (fileName.equals("sisx")) {
            contentType = "application/vnd.symbian.install";
        } else if (fileName.equals("exe")) {
            contentType = "application/x-msdownload";
        } else if (fileName.equals("msi")) {
            contentType = "application/x-msdownload";
        } else if (fileName.equals("css")) {
            contentType = "text/css";
        } else if (fileName.equals("xml")) {
            contentType = "text/xml";
        } else if (fileName.equals("gif")) {
            contentType = "image/gif";
        } else if (fileName.equals("jpeg") || fileName.equals("jpg")) {
            contentType = "image/jpeg";
        } else if (fileName.equals("js")) {
            contentType = "application/x-javascript";
        } else if (fileName.equals("atom")) {
            contentType = "application/atom+xml";
        } else if (fileName.equals("rss")) {
            contentType = "application/rss+xml";
        } else if (fileName.equals("mml")) {
            contentType = "text/mathml";
        } else if (fileName.equals("txt")) {
            contentType = "text/plain";
        } else if (fileName.equals("jad")) {
            contentType = "text/vnd.sun.j2me.app-descriptor";
        } else if (fileName.equals("wml")) {
            contentType = "text/vnd.wap.wml";
        } else if (fileName.equals("htc")) {
            contentType = "text/x-component";
        } else if (fileName.equals("png")) {
            contentType = "image/png";
        } else if (fileName.equals("tif") || fileName.equals("tiff")) {
            contentType = "image/tiff";
        } else if (fileName.equals("wbmp")) {
            contentType = "image/vnd.wap.wbmp";
        } else if (fileName.equals("ico")) {
            contentType = "image/x-icon";
        } else if (fileName.equals("jng")) {
            contentType = "image/x-jng";
        } else if (fileName.equals("bmp")) {
            contentType = "image/x-ms-bmp";
        } else if (fileName.equals("svg")) {
            contentType = "image/svg+xml";
        } else if (fileName.equals("jar") || fileName.equals("var")
                || fileName.equals("ear")) {
            contentType = "application/java-archive";
        } else if (fileName.equals("doc")) {
            contentType = "application/msword";
        } else if (fileName.equals("pdf")) {
            contentType = "application/pdf";
        } else if (fileName.equals("rtf")) {
            contentType = "application/rtf";
        } else if (fileName.equals("xls")) {
            contentType = "application/vnd.ms-excel";
        } else if (fileName.equals("ppt")) {
            contentType = "application/vnd.ms-powerpoint";
        } else if (fileName.equals("7z")) {
            contentType = "application/x-7z-compressed";
        } else if (fileName.equals("rar")) {
            contentType = "application/x-rar-compressed";
        } else if (fileName.equals("swf")) {
            contentType = "application/x-shockwave-flash";
        } else if (fileName.equals("rpm")) {
            contentType = "application/x-redhat-package-manager";
        } else if (fileName.equals("der") || fileName.equals("pem")
                || fileName.equals("crt")) {
            contentType = "application/x-x509-ca-cert";
        } else if (fileName.equals("xhtml")) {
            contentType = "application/xhtml+xml";
        } else if (fileName.equals("zip")) {
            contentType = "application/zip";
        } else if (fileName.equals("mid") || fileName.equals("midi")
                || fileName.equals("kar")) {
            contentType = "audio/midi";
        } else if (fileName.equals("mp3")) {
            contentType = "audio/mpeg";
        } else if (fileName.equals("ogg")) {
            contentType = "audio/ogg";
        } else if (fileName.equals("m4a")) {
            contentType = "audio/x-m4a";
        } else if (fileName.equals("ra")) {
            contentType = "audio/x-realaudio";
        } else if (fileName.equals("3gpp")
                || fileName.equals("3gp")) {
            contentType = "video/3gpp";
        } else if (fileName.equals("mp4")) {
            contentType = "video/mp4";
        } else if (fileName.equals("mpeg") || fileName.equals("mpg")) {
            contentType = "video/mpeg";
        } else if (fileName.equals("mov")) {
            contentType = "video/quicktime";
        } else if (fileName.equals("flv")) {
            contentType = "video/x-flv";
        } else if (fileName.equals("m4v")) {
            contentType = "video/x-m4v";
        } else if (fileName.equals("mng")) {
            contentType = "video/x-mng";
        } else if (fileName.equals("asx") || fileName.equals("asf")) {
            contentType = "video/x-ms-asf";
        } else if (fileName.equals("wmv")) {
            contentType = "video/x-ms-wmv";
        } else if (fileName.equals("avi")) {
            contentType = "video/x-msvideo";
        }
        return contentType;
    }

    /**
     * 创建目录
     * @param descDirName 目录名,包含路径
     * @return 如果创建成功，则返回true，否则返回false
     */
    public static boolean createDirectory(String descDirName) {
        String descDirNames = descDirName;
        if (!descDirNames.endsWith(File.separator)) {
            descDirNames = descDirNames + File.separator;
        }
        File descDir = new File(descDirNames);
        if (descDir.exists()) {
            logger.debug("目录 " + descDirNames + " 已存在!");
            return false;
        }
        // 创建目录
        if (descDir.mkdirs()) {
            logger.debug("目录 " + descDirNames + " 创建成功!");
            return true;
        } else {
            logger.warn("目录 " + descDirNames + " 创建失败!");
            return false;
        }
    }

    /**
     * 删除单个文件
     * @param fileName 被删除的文件名
     * @return 如果删除成功，则返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                logger.debug("删除文件 " + fileName + " 成功!");
                return true;
            } else {
                logger.debug("删除文件 " + fileName + " 失败!");
                return false;
            }
        } else {
            logger.debug(fileName + " 文件不存在!");
            return true;
        }
    }

    public static void fileUpload(HttpServletRequest request,HttpServletResponse response, MultipartHttpServletRequest mreq){
        // 获取配置文件中配置的上传相对路径
        String path = propertiesUtil.getPropertiesValue("upload.path");
        // 获取指定的临时 绝对路径（用于创建文件路径）
        /** getRealPath使用位置：
         * 1、servlet、service、action：request.getServletContext.getRealPath("/");
         * 2、Struts：request.getServlet().getServletContext.getRealPath("/");
         * 3、业务、控制层之外，如utils：
         *      request.getSession().getServletContext().getRealPath("/");
         */
        String uploadPath = request.getSession().getServletContext().getRealPath(path);
        File up = new File( uploadPath );
        if ( !up.exists() )
        {
            up.mkdir(); //创建临时目录
        }
        //得到上传的文件
        MultipartFile file = mreq.getFile("Filedata");
        if(!file.isEmpty()){
            String name = file.getOriginalFilename(); // 文件名
            long size = file.getSize(); // 文件大小
            String extName = null;  // 文件后缀
            if (name == null || name.trim().equals("")) {
                return ;
            }
            try {
                // 为每一个文件添加一个4位随机数
                int random = (int) (Math.random() * 9000 + 1000);
                extName = name.substring(name.lastIndexOf("."));
//                name = name.substring(0, name.lastIndexOf("."))+ "_" + random + extName;
                // 重新命名上传文件名称（防止中文乱码）
                name = DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss")+ "_" + random + extName;

                // 获取文件保存的真实的（网络）路径，如：http:///127.0.0.1:8080/SharedPlatform/upload/images
                //String savePath =  req.getScheme() + "://"+ req.getServerName() + ":" + req.getServerPort()+req.getContextPath() + path;
                // 等同于 File saveFile = new File(savePath, name);
                File saveFile = new File(uploadPath + name);    // 如果是相对路径，则创建的文件就为磁盘的根目录的相对路径

                if(!saveFile.exists()){
                    // 创建临时的空文件
                    saveFile.mkdirs();
                }
                //保存 将上传文件写到服务器上指定的文件
                file.transferTo(saveFile);
                // 反馈个浏览器文件路径及名称
                response.getWriter().print(name);
            } catch (IOException e) {
                logger.error("上传文件IO异常", e);
                e.printStackTrace();
            }
        }
    }

    /**
     *  write 出文件
     * */
    public static void writeFile(InputStream inputStream, OutputStream out) throws IOException{
        int length = 0;
        byte buffer[] = new byte[1024];
        while((length = inputStream.read(buffer)) != -1){
            out.write(buffer, 0, length);
        }
        inputStream.close();
        out.flush();
        out.close();
    }

    /**
     * @Description: 写入文件（默认UTF-8）
     * @param fileName 文件名称
     * @param content 要写入的内容
     * @param append 是否追加
     * @Date 2017/9/27 11:16
     */
    public static void writeToFile(String fileName, String content, boolean append) {
        try {
            FileUtils.write(new File(fileName), content, "utf-8", append);
            logger.debug("文件 " + fileName + " 写入成功!");
        } catch (IOException e) {
            logger.debug("文件 " + fileName + " 写入失败! " + e.getMessage());
        }
    }

    /**
     * @Description: 写入文件
     * @param fileName 文件名称
     * @param content 要写入的内容
     * @param encoding 写入文件字符类型
     * @param append 是否追加
     * @Date 2017/9/27 11:16
     */
    public static void writeToFile(String fileName, String content, String encoding, boolean append) {
        try {
            FileUtils.write(new File(fileName), content, encoding, append);
            logger.debug("文件 " + fileName + " 写入成功!");
        } catch (IOException e) {
            logger.debug("文件 " + fileName + " 写入失败! " + e.getMessage());
        }
    }

    /**
     * 向浏览器发送文件下载，支持断点续传
     * @param file 要下载的文件
     * @param request 请求对象
     * @param response 响应对象
     * @return 返回错误信息，无错误信息返回null
     */
//    public static String downFile(File file, HttpServletRequest request, HttpServletResponse response){
//        return downFile(file, request, response, null);
//    }

    /**
     * 向浏览器发送文件下载，支持断点续传
     * @param file 要下载的文件
     * @param request 请求对象
     * @param response 响应对象
     * @param fileName 指定下载的文件名
     * @return 返回错误信息，无错误信息返回null
     */
//    public static String downFile(File file, HttpServletRequest request, HttpServletResponse response, String fileName){
//        String error  = null;
//        if (file != null && file.exists()) {
//            String name = file.getName();
//            if (file.isFile()) {
//                if (file.length() <= 0) {
//                    error = name + "文件是一个空文件。";
//                }
//                if (!file.canRead()) {
//                    error = name + "文件没有读取权限。";
//                }
//            } else {
//                error = name + "文件是一个文件夹。";
//            }
//        } else {
//            error = "文件已丢失或不存在！";
//        }
//        if (error != null){
//            logger.warn( file + " " + error);
//            return error;
//        }
//
//        long fileLength = file.length(); // 记录文件大小
//        long pastLength = 0; 	// 记录已下载文件大小
//        int rangeSwitch = 0; 	// 0：从头开始的全文下载；1：从某字节开始的下载（bytes=27000-）；2：从某字节开始到某字节结束的下载（bytes=27000-39000）
//        long toLength = 0; 		// 记录客户端需要下载的字节段的最后一个字节偏移量（比如bytes=27000-39000，则这个值是为39000）
//        long contentLength = 0; // 客户端请求的字节总量
//        String rangeBytes = ""; // 记录客户端传来的形如“bytes=27000-”或者“bytes=27000-39000”的内容
//        RandomAccessFile raf = null; // 负责读取数据
//        OutputStream os = null; 	// 写出数据
//        OutputStream out = null; 	// 缓冲
//        byte b[] = new byte[1024]; 	// 暂存容器
//
//        if (request.getHeader("Range") != null) { // 客户端请求的下载的文件块的开始字节
//            response.setStatus(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);
//            logger.debug("request.getHeader(\"Range\") = " + request.getHeader("Range"));
//            rangeBytes = request.getHeader("Range").replaceAll("bytes=", "");
//            if (rangeBytes.indexOf('-') == rangeBytes.length() - 1) {// bytes=969998336-
//                rangeSwitch = 1;
//                rangeBytes = rangeBytes.substring(0, rangeBytes.indexOf('-'));
//                pastLength = Long.parseLong(rangeBytes.trim());
//                contentLength = fileLength - pastLength; // 客户端请求的是 969998336  之后的字节
//            } else { // bytes=1275856879-1275877358
//                rangeSwitch = 2;
//                String temp0 = rangeBytes.substring(0, rangeBytes.indexOf('-'));
//                String temp2 = rangeBytes.substring(rangeBytes.indexOf('-') + 1, rangeBytes.length());
//                pastLength = Long.parseLong(temp0.trim()); // bytes=1275856879-1275877358，从第 1275856879 个字节开始下载
//                toLength = Long.parseLong(temp2); // bytes=1275856879-1275877358，到第 1275877358 个字节结束
//                contentLength = toLength - pastLength; // 客户端请求的是 1275856879-1275877358 之间的字节
//            }
//        } else { // 从开始进行下载
//            contentLength = fileLength; // 客户端要求全文下载
//        }
//
//        /*
//        如果设设置了Content-Length，则客户端会自动进行多线程下载。如果不希望支持多线程，则不要设置这个参数。 响应的格式是:
//         Content-Length: [文件的总大小] - [客户端请求的下载的文件块的开始字节]
//         ServletActionContext.getResponse().setHeader("Content- Length", new Long(file.length() - p).toString());*/
//        response.reset(); // 告诉客户端允许断点续传多线程连接下载,响应的格式是:Accept-Ranges: bytes
//        if (pastLength != 0) {
//            response.setHeader("Accept-Ranges", "bytes");// 如果是第一次下,还没有断点续传,状态是默认的 200,无需显式设置;响应的格式是:HTTP/1.1 200 OK
//            // 不是从最开始下载, 响应的格式是: Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
//            logger.debug("---------------不是从开始进行下载！服务器即将开始断点续传...");
//            switch (rangeSwitch) {
//                case 1: { // 针对 bytes=27000- 的请求
//                    String contentRange = new StringBuffer("bytes ").append(new Long(pastLength).toString()).append("-")
//                            .append(new Long(fileLength - 1).toString()).append("/").append(new Long(fileLength).toString()).toString();
//                    response.setHeader("Content-Range", contentRange);
//                    break;
//                }
//                case 2: { // 针对 bytes=27000-39000 的请求
//                    String contentRange = rangeBytes + "/" + new Long(fileLength).toString();
//                    response.setHeader("Content-Range", contentRange);
//                    break;
//                }
//                default: {
//                    break;
//                }
//            }
//        } else {
//            // 是从开始下载
//            logger.debug("新文件下载，非断点下载！");
//        }
//
//        try {
//            response.addHeader("Content-Disposition", "attachment; filename=\"" +
//                    Encodes.urlEncode(StringUtils.isBlank(fileName) ? file.getName() : fileName) + "\"");
//            response.setContentType(getContentType(file.getName())); // set the MIME type.
//            response.addHeader("Content-Length", String.valueOf(contentLength));
//            os = response.getOutputStream();
//            out = new BufferedOutputStream(os);
//            raf = new RandomAccessFile(file, "r");
//            try {
//                switch (rangeSwitch) {
//                    case 0: { // 普通下载，或者从头开始的下载 同1
//                    }
//                    case 1: { // 针对 bytes=27000- 的请求
//                        raf.seek(pastLength); // 形如 bytes=969998336- 的客户端请求，跳过 969998336 个字节
//                        int n = 0;
//                        while ((n = raf.read(b, 0, 1024)) != -1) {
//                            out.write(b, 0, n);
//                        }
//                        break;
//                    }
//                    case 2: { // 针对 bytes=27000-39000 的请求
//                        raf.seek(pastLength); // 形如 bytes=1275856879-1275877358 的客户端请求，找到第 1275856879 个字节
//                        int n = 0;
//                        long readLength = 0; // 记录已读字节数
//                        while (readLength <= contentLength - 1024) {// 大部分字节在这里读取
//                            n = raf.read(b, 0, 1024);
//                            readLength += 1024;
//                            out.write(b, 0, n);
//                        }
//                        if (readLength <= contentLength) { // 余下的不足 1024 个字节在这里读取
//                            n = raf.read(b, 0, (int) (contentLength - readLength));
//                            out.write(b, 0, n);
//                        }
//                        break;
//                    }
//                    default: {
//                        break;
//                    }
//                }
//                out.flush();
//                logger.debug("---------------下载完成！");
//            } catch (IOException ie) {
//                /**
//                 * 在写数据的时候， 对于 ClientAbortException 之类的异常，
//                 * 是因为客户端取消了下载，而服务器端继续向浏览器写入数据时， 抛出这个异常，这个是正常的。
//                 * 尤其是对于迅雷这种吸血的客户端软件， 明明已经有一个线程在读取 bytes=1275856879-1275877358，
//                 * 如果短时间内没有读取完毕，迅雷会再启第二个、第三个。。。线程来读取相同的字节段， 直到有一个线程读取完毕，迅雷会 KILL
//                 * 掉其他正在下载同一字节段的线程， 强行中止字节读出，造成服务器抛 ClientAbortException。
//                 * 所以，我们忽略这种异常
//                 */
//                logger.debug("提醒：向客户端传输时出现IO异常，但此异常是允许的，有可能客户端取消了下载，导致此异常，不用关心！");
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//        } finally {
//            if (out != null) {
//                try {
//                    out.close();
//                } catch (IOException e) {
//                    logger.error(e.getMessage(), e);
//                }
//            }
//            if (raf != null) {
//                try {
//                    raf.close();
//                } catch (IOException e) {
//                    logger.error(e.getMessage(), e);
//                }
//            }
//        }
//        return null;
//    }

    /**
     * 修正路径，将 \\ 或 / 等替换为 File.separator
     * @param path 待修正的路径
     * @return 修正后的路径
     */
    public static String path(String path){
        String p = StringUtils.replace(path, "\\", "/");
        p = StringUtils.join(StringUtils.split(p, "/"), "/");
        if (!StringUtils.startsWith(p, "/") && StringUtils.startsWith(path, "\\") && StringUtils.startsWith(path, "/")){
            p += "/";
        }
        if (!StringUtils.endsWith(p, "/") && StringUtils.endsWith(path, "\\") && StringUtils.endsWith(path, "/")){
            p = p + "/";
        }
        if (path != null && path.startsWith("/")){
            p = "/" + p; // linux下路径
        }
        return p;
    }

    /**
     * 获目录下的文件列表
     * @param dir 搜索目录
     * @param searchDirs 是否是搜索目录
     * @return 文件列表
     */
    public static List<String> findChildrenList(File dir, boolean searchDirs) {
        List<String> files = Lists.newArrayList();
        for (String subFiles : dir.list()) {
            File file = new File(dir + "/" + subFiles);
            if (((searchDirs) && (file.isDirectory())) || ((!searchDirs) && (!file.isDirectory()))) {
                files.add(file.getName());
            }
        }
        return files;
    }

    /**
     * 获取文件扩展名(返回小写)
     * @param fileName 文件名
     * @return 文件后缀 或 null
     */
    public static String getFileExtension(String fileName) {
        if ((fileName == null) || (fileName.lastIndexOf(".") == -1) || (fileName.lastIndexOf(".") == fileName.length() - 1)) {
            return null;
        }
        return StringUtils.lowerCase(fileName.substring(fileName.lastIndexOf(".") + 1));
    }
    /**
     * 获取文件名，不包含扩展名
     * @param fileName 文件名
     * @return 例如：d:\files\test.jpg  返回：d:\files\test
     */
    public static String getFileNameWithoutExtension(String fileName) {
        if ((fileName == null) || (fileName.lastIndexOf(".") == -1)) {
            return null;
        }
        return fileName.substring(0, fileName.lastIndexOf("."));
    }
}
