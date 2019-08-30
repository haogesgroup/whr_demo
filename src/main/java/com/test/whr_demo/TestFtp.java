package com.test.whr_demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 * test1
 */
public class TestFtp {

  public static void main(String[] args) throws IOException {
    FTPClient ftpClient = FtpUtil.getFTPClient("123.207.230.76", "dajia", "dajia", 21);
    /*try {
      ftpClient.disconnect();
    } catch (IOException e) {
      e.printStackTrace();
    }
    ftpClient.setControlEncoding("UTF-8"); // 中文支持
    ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
    ftpClient.enterLocalPassiveMode();
    ftpClient.changeWorkingDirectory("/test2/");
    // TEST.docx
    File localFile = new File("D:" + File.separatorChar + "test.txt");
    OutputStream os = new FileOutputStream(localFile);
    ftpClient.retrieveFile("test.txt", os);
    os.close();
    ftpClient.logout();
    //FtpUtil.uploadFile("123.207.230.76","dajia","dajia","21",);*/

    ftpClient.changeWorkingDirectory("/test2/");
    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
    ftpClient.enterLocalPassiveMode();
    File file = new File("D:" + File.separatorChar + "test2.txt");
    FileInputStream input = new FileInputStream(file);
    // String fileName = new String("test.txt".getBytes("GBK"),"iso-8859-1");
    ftpClient.storeFile("test2.txt", input);
    input.close();
    ftpClient.logout();
    System.out.println("upload succes!");

  }

}
