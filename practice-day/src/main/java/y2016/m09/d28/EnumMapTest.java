package y2016.m09.d28;

import org.junit.Test;
import sun.misc.SharedSecrets;

import java.util.EnumMap;

/**
 * EnumMap以枚举为key,而且整个map是有序的，按照enum的ordinary排列
 *
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-09-28 AM10:02
 */
public class EnumMapTest {
    @Test
    public void enumMapTest() {
        Color[] keyUniverse = getKeyUniverse(Color.class);
        for (Color color : keyUniverse) {
            System.out.println(color);
        }
    }

    private enum Color {
        RED,
        GREEN,
        BLUE,
        YELLOW;
    }


    /**
     * 获取所有的枚举类型
     *
     * @param <K>     the type parameter
     * @param keyType the key type
     * @return the k [ ]
     * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
     * @date : 2016-09-28 10:39:33
     */
    private static <K extends Enum<K>> K[] getKeyUniverse(Class<K> keyType) {
        return SharedSecrets.getJavaLangAccess()
                .getEnumConstantsShared(keyType);
    }

}
