package y2016.m09.d13;

import y2016.m08.day20160811.ParseLog;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-09-13 PM04:24
 */
public class ParseUserVisitPage {
    public static void main(String[] args) {
        String inputPath = "C:\\Users\\Administrator\\Desktop\\etl\\hdfs\\parse\\browser.log-2016-09-12.log";
        ParseLog parseLog = new ParseLog();
        parseLog.parseUserVisitPage(inputPath, 30);
    }
}
