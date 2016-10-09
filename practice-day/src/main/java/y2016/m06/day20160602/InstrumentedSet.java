package y2016.m06.day20160602;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-06-02 PM02:00
 */
public class InstrumentedSet<E> extends HashSet<E> {
    private int amountCount;

    public InstrumentedSet() {
    }

    @Override
    public boolean add(E e) {
        amountCount++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        // 这里尽管调用父类的方法，但是父类方法中，this是当前对象，所以一旦所调用的方法被覆盖了，会调用this中的覆盖方法
        amountCount += c.size();
        return super.addAll(c);
    }

    public int getAmountCount() {
        return amountCount;
    }

    public static void main(String[] args) {
        InstrumentedSet<String> instrumentedSet = new InstrumentedSet<>();
        instrumentedSet.addAll(Arrays.asList(new String[] {"22", "11"}));
        System.out.println(instrumentedSet.getAmountCount());
    }
}
