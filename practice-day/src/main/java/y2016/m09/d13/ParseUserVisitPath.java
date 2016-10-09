package y2016.m09.d13;

import org.junit.Test;
import y2016.m08.day20160811.ParseLog;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-09-13 PM04:20
 */
public class ParseUserVisitPath {
    public static void main(String[] args) {
        String dayPath = "C:\\Users\\Administrator\\Desktop\\etl\\hdfs\\browser\\09\\12";
//        final String type = "b1d760e5806e41e9ad509c8acab30bc5";  // 日志系统
        final String type = "ae3bae0cbe534dc7a3ed9e332606de18"; // 悦采
//        final String type = "9b2978902fa64498a2d3330d1f49962d"; // 比联网-广告
//        final String type = "35b2b795d689428f848d1edf059a9b7a"; // 比联网2.0
//        final String type = "0a6ce57d790d4e3bb00456516cf406d1"; // 老必联
        ParseLog parseLog = new ParseLog();
        parseLog.parseUserVisitPath(dayPath, type, 30);
    }

    @Test
    public void testString() {
        String str = "http://tj.ebnew.com/index.html^^http://tj.ebnew.com/index.html^^";
        String[] split = str.split("\\^\\^");
        System.out.println(split[0].equals(split[1]));
    }
}
