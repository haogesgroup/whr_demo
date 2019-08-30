package com.test.whr_demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtil {

  private final static Log logger = LogFactory.getLog(FtpUtil.class);

  /**
   * 获取FTPClient对象
   *
   * @param ftpHost
   *            FTP主机服务器
   * @param ftpPassword
   *            FTP 登录密码
   * @param ftpUserName
   *            FTP登录用户名
   * @param ftpPort
   *            FTP端口 默认为21
   * @return
   */
  public static FTPClient getFTPClient(String ftpHost, String ftpUserName,
      String ftpPassword, int ftpPort) {
    FTPClient ftpClient = new FTPClient();
    try {
      ftpClient = new FTPClient();
      ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
      ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
      if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
        logger.info("未连接到FTP，用户名或密码错误。");
        ftpClient.disconnect();
      } else {
        logger.info("FTP连接成功。");
      }
    } catch (SocketException e) {
      e.printStackTrace();
      logger.info("FTP的IP地址可能错误，请正确配置。");
    } catch (IOException e) {
      e.printStackTrace();
      logger.info("FTP的端口错误,请正确配置。");
    }
    return ftpClient;
  }

  /**
   * 下载文件
   *
   * @param ftpHost ftp服务器地址
   * @param ftpUserName anonymous匿名用户登录，不需要密码。administrator指定用户登录
   * @param ftpPassword 指定用户密码
   * @param ftpPort ftp服务员器端口号
   * @param ftpPath  ftp文件存放物理路径
   * @param fileName 文件路径
   */
  public static void downloadFile(String ftpHost, String ftpUserName,
      String ftpPassword, int ftpPort, String ftpPath, String localPath,
      String fileName) {
    FTPClient ftpClient = null;

    try {
      ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
      ftpClient.setControlEncoding("UTF-8"); // 中文支持
      ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
      ftpClient.enterLocalPassiveMode();
      ftpClient.changeWorkingDirectory(ftpPath);

      File localFile = new File(localPath + File.separatorChar + fileName);
      /*if (!localFile.exists()) {
        localFile.mkdir();
      }*/
      OutputStream os = new FileOutputStream(localFile);
      ftpClient.retrieveFile(fileName, os);
      os.close();
      ftpClient.logout();

    } catch (FileNotFoundException e) {
      logger.error("没有找到" + ftpPath + "文件");
      e.printStackTrace();
    } catch (SocketException e) {
      logger.error("连接FTP失败.");
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("文件读取错误。");
      e.printStackTrace();
    }
  }

  /**
   * 上传文件
   *
   * @param ftpHost ftp服务器地址
   * @param ftpUserName anonymous匿名用户登录，不需要密码。administrator指定用户登录
   * @param ftpPassword 指定用户密码
   * @param ftpPort ftp服务员器端口号
   * @param ftpPath  ftp文件存放物理路径
   * @param fileName 文件路径
   * @param input 文件输入流，即从本地服务器读取文件的IO输入流
   */
  public static void uploadFile(String ftpHost, String ftpUserName,
      String ftpPassword, int ftpPort, String ftpPath,
      String fileName,InputStream input){
    FTPClient ftpClient=null;
    try {
      ftpClient=getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
      ftpClient.changeWorkingDirectory(ftpPath);
      ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
      ftpClient.enterLocalPassiveMode();
      // fileName=new String(fileName.getBytes("GBK"),"iso-8859-1");
      ftpClient.storeFile(fileName, input);
      input.close();
      ftpClient.logout();
      System.out.println("upload succes!");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws IOException {
    // 上传 （ftppath：服务器的文件夹路径  filename：要上传的文件名）
    /*
    File file = new File("D:" + File.separatorChar + "test3.txt");
    FileInputStream input = new FileInputStream(file);
    FtpUtil.uploadFile("123.207.230.76","dajia","dajia",21,"/test2/","test3.txt",input);
    */
    // 下载 （ftppath：服务器的文件夹路径  localPath：本地要下载的文件路径 filename：要下载的文件名）
    FtpUtil.downloadFile("123.207.230.76","dajia","dajia",21,"/test2/","d:","test3.txt");


  }

}
