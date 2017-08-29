package y2017.m04.d21;

import org.junit.Test;

import java.util.*;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/4/21
 */
public class TestList {
    @Test
    public void testList() {
        List<String[][]> list = new MyArrayList<>();
        List<String[][]> list1 = new MyArrayList<>();
        list.add(new String[][] {new String[] {"2"}});
        list1.add(new String[][] {new String[] {"2"}});
        System.out.println(list.equals(list1));
    }

    static class MyArrayList<E> extends ArrayList<E> {
        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }

            if (!(o instanceof AbstractList)) {
                return false;
            }

            Iterator<E> iterator1 = this.iterator();
            Iterator iterator2 = ((AbstractList) o).iterator();
            while (iterator1.hasNext() && iterator2.hasNext()) {
                E next1 = iterator1.next();
                Object next2 = iterator2.next();
                if (next1 == null) {
                    if (next2 != null) {
                        return false;
                    }
                } else {
                    if (next2 == null) {
                        return false;
                    } else {
                        if (next1.getClass().isArray() && next2.getClass().isArray()
                                && next1.getClass().getComponentType().equals(next2.getClass().getComponentType())) {
                            return Arrays.deepEquals((E[]) next1, ((E[]) next2));
                        } else {
                            return false;
                        }
                    }
                }
            }

            return !(iterator1.hasNext() || iterator2.hasNext());
        }
    }
}
