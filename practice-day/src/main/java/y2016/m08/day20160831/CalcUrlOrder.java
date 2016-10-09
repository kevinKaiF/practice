package y2016.m08.day20160831;

import java.io.File;
import java.util.Map;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-08-31 PM04:40
 */
public class CalcUrlOrder {
    /**
     * .
     *
     * @param inputDir   计算的目录
     * @param outputPath the output path
     * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
     * @date : 2016-08-31 16:22:12
     */
    public void calc(String inputDir, String outputPath) {
        File dir = new File(inputDir);
        if(dir.isDirectory()) {
            File[] files = dir.listFiles();
            Map<String, Integer>[] calcMapArr = new Map[files.length];
            for (File file : files) {

            }
        }
    }
}
