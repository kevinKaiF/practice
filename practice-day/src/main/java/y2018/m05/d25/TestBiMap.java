package y2018.m05.d25;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.junit.Test;

public class TestBiMap {

    /**
     * BiMap在创建的时候，已经创建的k,v对应的v,k map,每次对k,v进行写操作的时候，对v,k 也进行写操作
     */
    @Test
    public void testBiMap() {
        BiMap<String, Integer> biMap = HashBiMap.create();
        biMap.put("name", 1);
        biMap.put("name", 1);
        biMap.put("age", 2);
        // BiMap要求value强唯一性，
//        biMap.put("age", 1);

        BiMap<Integer, String> inverse = biMap.inverse();
        System.out.println(inverse);
    }

}
