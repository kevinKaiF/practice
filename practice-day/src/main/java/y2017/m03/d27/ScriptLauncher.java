package y2017.m03.d27;

import java.io.*;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/3/27
 */
public class ScriptLauncher {

    private static PrintStream getError() {
        return System.err;
    }

    private static InputStream getIn() {
        return System.in;
    }

    public static void main(String[] args) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getIn()));
            while (true) {
                getError().print("script>");
                String line = reader.readLine();
                if ("-q".equals(line)) {
                    break;
                }

                String[] split = null;
                if (line.trim().length() == 0) {
                    split = new String[0];
                } else {
                    split = line.trim().split("\\s+");
                }
                Main main = new Main();
                main.main(split);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
