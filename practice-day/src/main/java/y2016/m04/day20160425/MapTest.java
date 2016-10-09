package y2016.m04.day20160425;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-04-25 PM02:29
 */
public class MapTest {
    public static void main(String[] args) {
        Map map = new LinkedHashMap(){
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return super.removeEldestEntry(eldest);
            }

        };

    }
}
