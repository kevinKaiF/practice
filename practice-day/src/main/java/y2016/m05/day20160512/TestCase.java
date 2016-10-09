package y2016.m05.day20160512;

import org.junit.Test;

import java.util.*;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-05-12 AM11:19
 */
public class TestCase {
    // 集合类型强转时，强转类型必须是父类或同类型。
    public static void main(String[] args) {
        Object[] objects = new Object[] {new ArrayList<String>() {{
            add("yes");
        }}};
        List<CharSequence> objectList = (List<CharSequence>)objects[0];
        CharSequence charSequence = objectList.get(0);
        if(charSequence instanceof  CharSequence) {
            System.out.println("CharSequence");
        }

        if(charSequence instanceof String) {
            System.out.println("String");
        }

        if(charSequence instanceof Object) {
            System.out.println("Object");
        }
    }

    @Test
    public void testLinkedHashMap() {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        map.put("123","123");
        map.put("234", "234");
        Iterator<Map.Entry<String, String >> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            System.out.println("key:" + entry.getKey() + ", value:" + entry.getValue());
        }
    }
}
