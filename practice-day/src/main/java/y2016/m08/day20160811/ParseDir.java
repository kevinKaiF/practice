package y2016.m08.day20160811;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 解析文件目录.
 *
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-08-12 PM04:17
 */
public interface ParseDir {
    /**
     * 解析所有的文件夹下所有的日志文件.
     *
     * @return the list
     * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
     * @date : 2016-08-12 16:58:47
     */
    List<File> parseSubFile();

    /**
     * 将每天所有的日志文件转为map形式，以当天日期为key,以当天每小时的日志文件名集合为value.
     *
     * @param subFiles the sub files
     * @return the map
     * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
     * @date : 2016-08-12 16:59:21
     */
    Map<String, List<String>> toMap(List<File> subFiles);
}
