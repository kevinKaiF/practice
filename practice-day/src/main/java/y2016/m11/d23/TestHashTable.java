package y2016.m11.d23;

import org.junit.Test;

import java.util.Hashtable;
import java.util.Map;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/11/23
 */
public class TestHashTable {
    @Test
    public void testHashTable() {
        Map<String, String> hashTable = new Hashtable<>();
        // hashTable不允许key为null
        hashTable.put(null, "");
    }
}
