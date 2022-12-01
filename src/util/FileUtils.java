package util;

import java.io.*;

public class FileUtils {
    private static final String FILE_PATH = "D:\\workspace\\java\\LoginSystemServer\\src\\resource\\userAvatar\\";

    public static boolean writeFile(byte[] data, String fileName) {
        String filePath = FILE_PATH + fileName;
        try {
            //将DataOutputStream与FileOutputStream连接可输出不同类型的数据
            //FileOutputStream类的构造函数负责打开文件kuka.dat，如果文件不存在，
            //则创建一个新的文件，如果文件已存在则用新创建的文件代替。然后FileOutputStream
            //类的对象与一个DataOutputStream对象连接，DataOutputStream类具有写
            //各种数据类型的方法。
            DataOutputStream out = new DataOutputStream(new FileOutputStream(filePath));
            out.write(data);
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static byte[] readFile(String fileName) {
        String filePath = FILE_PATH + fileName;
        if (!new File(filePath).exists())
            filePath = FILE_PATH + "defaultAvatar_ca9c5509-cb21-41c4-8a43-54a4b1ca4a0e";
        int sum = 0;
        try {
            DataInputStream in = new DataInputStream(
                    new BufferedInputStream(new FileInputStream(filePath)));
            byte[] data = new byte[in.available()];
            while (in.available() > 0) {
                int i = in.read(data);
                sum += i;
            }
            System.out.println("The sum is:" + sum);
            in.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
