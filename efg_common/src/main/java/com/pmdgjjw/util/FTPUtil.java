package com.pmdgjjw.util;


import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


@Component
public class FTPUtil {

    //ftp服务器ip地址
    private static final String FTP_ADDRESS = "192.168.80.138";
    //端口号
    private static final int FTP_PORT = 20;
    //用户名
    private static final String FTP_USERNAME = "fspuser";
    //密码
    private static final String FTP_PASSWORD = "adminjjw";
    //路径都是/home/加上用户名
    public static final String FTP_BASEPATH = "/ftpfile";
    //

    //参数传过来了文件和文件的输入流
    public static String uploadFile(String originFileName, InputStream input,String fileType) {

        FTPClient ftp = new FTPClient();//这是最开始引入的依赖里的方法
        ftp.setControlEncoding("utf-8");
        try {
            int reply;
            ftp.connect(FTP_ADDRESS);// 连接FTP服务器
            ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
            reply = ftp.getReplyCode();//连接成功会的到一个返回状态码
            System.out.println(reply);//可以输出看一下是否连接成功
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);//设置文件类型
            ftp.changeWorkingDirectory(FTP_BASEPATH);//修改操作空间

            FTPFile[] ftpFiles = ftp.listDirectories();
            List<String> fileName = new ArrayList<>();
            for (FTPFile ftpFile : ftpFiles) {
                byte[] bytes = ftpFile.getName().getBytes("ISO-8859-1");
                ftpFile.setName(new String(bytes, "utf-8"));
                fileName.add(ftpFile.getName());
            }

            if ("index".equals(fileType)){
                if (!fileName.contains("index")){
                   ftp.makeDirectory("index");
                }
                ftp.changeWorkingDirectory(FTP_BASEPATH+"/index");
            }else if ("comment".equals(fileType)){
                if (!fileName.contains("comment")){
                    ftp.makeDirectory("comment");
                }
                ftp.changeWorkingDirectory(FTP_BASEPATH+"/comment");
            }else if ("user".equals(fileType)){
                if (!fileName.contains("user")){
                    ftp.makeDirectory("user");
                }

                ftp.changeWorkingDirectory(FTP_BASEPATH+"/user");
            }



            boolean b = ftp.storeFile(originFileName, input);//这里开始上传文件

            if (b){
                return originFileName;
            }

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return "连接失败";
            }

            input.close();
            ftp.logout();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
       return "false";
    }

    public static boolean downloadFile(String filename, HttpServletResponse response) {

        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        // 下载文件能正常显示中文
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        boolean flag = false;
        FTPClient ftp = new FTPClient();
        OutputStream os = null;
        try {
            System.out.println("开始下载文件");
            ftp.connect(FTP_ADDRESS);// 连接FTP服务器
            ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
            int reply = ftp.getReplyCode();//得到连接成功的返回状态码
            System.out.println(reply);
            ftp.enterLocalActiveMode();//主动，一定要加上这几句设置为主动
//下面是将这个文件夹的所有文件都取出来放在ftpFiles这个文件数组里面
            FTPFile[] ftpFiles = ftp.listFiles();
//然后便利这个数组找出和我们要下载的文件的文件名一样的文件
            for (FTPFile file : ftpFiles) {
                byte[] bytes = file.getName().getBytes("ISO-8859-1");
                file.setName(new String(bytes, "utf-8"));
                System.out.println("name: " + file.getName());//
                if (filename.equalsIgnoreCase(file.getName())) {//判断找到所下载的文件，file.getName就是服务器上对应的文件

                    os = response.getOutputStream();
                    ftp.retrieveFile(file.getName(), os);//开始下载文
                    os.close();

                }
            }
            ftp.logout();
            flag = true;
            System.out.println("下载文件成功");
        } catch (Exception e) {
            System.out.println("下载文件失败");
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    public static byte[] getFile(String filename,String fileType) {

        FTPClient ftp = new FTPClient();


        InputStream in = null;
        ByteArrayOutputStream out = null;
        byte[] fileByte = null;
        try {
            System.out.println("开始下载文件");
            ftp.connect(FTP_ADDRESS);// 连接FTP服务器
            ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
            int reply = ftp.getReplyCode();//得到连接成功的返回状态码
            ftp.enterLocalActiveMode();//主动，一定要加上这几句设置为主动
            ftp.changeWorkingDirectory(FTP_BASEPATH+"/"+fileType);

            FTPFile[] ftpFiles = ftp.listFiles();

            for (FTPFile file : ftpFiles) {
                byte[] bytes = file.getName().getBytes("ISO-8859-1");
                file.setName(new String(bytes, "utf-8"));

                System.out.println("name: " + file.getName());//
                ftp.changeWorkingDirectory(FTP_BASEPATH+"/"+file.getName());
                FTPFile[] ftpDirFiles = ftp.listFiles();
                if(ftpDirFiles.length>0){
                    for (FTPFile ftpDirFile : ftpDirFiles) {
                        if (filename.equalsIgnoreCase(ftpDirFile.getName())) {//判断找到所下载的文件，file.getName就是服务器上对应的文件

                            in = ftp.retrieveFileStream(ftpDirFile.getName());
                            out = new ByteArrayOutputStream();
                            byte[] buffer = new byte[1024 * 4];
                            int n = 0;
                            while ((n = in.read(buffer)) != -1) {
                                out.write(buffer, 0, n);
                            }
                            fileByte = out.toByteArray();
                            if (fileByte!= null && fileByte.length>0){
                                return fileByte;
                            }else {
                                return null;
                            }

                        }
                    }
                }

            }
            ftp.logout();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileByte;
    }
}
