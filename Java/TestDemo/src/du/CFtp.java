package du;


import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CFtp {


    public static void main(String[] args) {

        FTPClient ftpClient = new FTPClient();
        try {
            InputStream inputStream = new FileInputStream(new File("/Users/haixiao/Documents/test.txt"));

            ftpClient.connect("101.132.98.71", 21);
            ftpClient.login("xd", "Monday3552");
            ftpClient.enterLocalPassiveMode();
            boolean result = ftpClient.storeFile("test.txt", inputStream);
            System.out.println("upload " + (result ? "success." : "failed."));

            ftpClient.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


   /* public static void main(String[] argc) {
        try {
            SimpleFTP ftp = new SimpleFTP();

            ftp.connect("101.132.98.71", 21, "xd", "Monday3552");
            ftp.bin();

            System.out.println("uploading...");
            boolean result = ftp.stor(new File("/Users/haixiao/Documents/test.txt"));
            System.out.println("upload " + (result ? "success." : "failed."));

            ftp.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}