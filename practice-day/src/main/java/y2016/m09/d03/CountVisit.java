package y2016.m09.d03;

import y2016.m08.day20160825.Closer;

import java.io.*;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-09-05 PM01:25
 */
public class CountVisit {
    public static void main(String[] args) {
        String inputPath = "C:\\Users\\Administrator\\Desktop\\vcLog.txt";
        CountVisit countVisit = new CountVisit();
        countVisit.parse(inputPath);
    }

    public void parse(String inputPath) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(inputPath));
            int count = 0;
            int cookieNull = 0;
            int cookieNotNull = 0;
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                String[] arr = temp.substring(0, temp.indexOf("\u0001")).split(",");
//                int num = Integer.parseInt(temp.substring(temp.indexOf("\u0001") + 1));
                int num = 1;
                count += Integer.parseInt(arr[0].split(":")[1].trim()) * num;
                cookieNull += Integer.parseInt(arr[1].split(":")[1].trim()) * num;
                cookieNotNull += Integer.parseInt(arr[2].split(":")[1].trim()) * num;
            }
            System.out.println("============== total :" + (count + cookieNull + cookieNotNull));
            System.out.println("count : " + count);
            System.out.println("cookieNull : " + cookieNull);
            System.out.println("cookieNotNull : " + cookieNotNull);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(reader);
        }


    }
}
