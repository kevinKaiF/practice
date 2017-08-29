package y2017.m04.d28;

import java.io.Serializable;
import java.util.*;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/4/28
 */
public class AutoPopulatingList<E> implements List<E>, Serializable {

    private List<E> backingList;

    private ElementFactory<E> elementFactory;

    public AutoPopulatingList(Class<? extends E> elementClass) {
        this(new ArrayList<E>(), elementClass);
    }

    public AutoPopulatingList(List<E> backingList, Class<? extends E> elementClass) {
        this.backingList = backingList;
        this.elementFactory = new ReflectElementFactory<E>(elementClass);
    }

    @Override
    public int size() {
        return this.backingList.size();
    }

    @Override
    public boolean isEmpty() {
        return this.backingList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.backingList.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return this.backingList.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.backingList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.backingList.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return this.backingList.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return this.backingList.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.backingList.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return this.backingList.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return this.backingList.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.backingList.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.backingList.retainAll(c);
    }

    @Override
    public void clear() {
        this.backingList.clear();
    }

    @Override
    public E get(int index) {
        // 不存在的元素自动填充
        E element;
        int backingListSize = this.backingList.size();
        // 界内元素，重新创建
        if (index < backingListSize) {
            element = this.backingList.get(index);
            if (element == null) {
                element = this.elementFactory.createElement();
                this.backingList.set(index, element);
            }
        } else { // 否则，空白元素，创建新元素
            for (int i = backingListSize; i < index; i++) {
                this.backingList.add(null);
            }

            element = this.elementFactory.createElement();
            this.backingList.add(element);
        }
        return element;
    }

    @Override
    public E set(int index, E element) {
        return this.backingList.set(index, element);
    }

    @Override
    public void add(int index, E element) {
        this.backingList.add(index, element);
    }

    @Override
    public E remove(int index) {
        return this.backingList.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return this.backingList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.backingList.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return this.backingList.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return this.backingList.listIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return this.backingList.subList(fromIndex, toIndex);
    }

    public interface ElementFactory<E> {
        E createElement();
    }

    private class ReflectElementFactory<E> implements ElementFactory<E> {
        private Class<? extends E> elementClass;

        public ReflectElementFactory(Class<? extends E> elementClass) {
            this.elementClass = elementClass;
        }

        @Override
        public E createElement() {
            try {
                E element = elementClass.newInstance();
                return element;
            } catch (InstantiationException e) {
                throw new ElementInstantiationException("Unable to instantiation element class [" +
                        this.elementClass.getName() + "]. Root cause is " + e);
            } catch (IllegalAccessException e) {
                throw new ElementInstantiationException("Cannot access element class [" +
                        this.elementClass.getName() + "]. Root cause is " + e);
            }
        }
    }

    /**  */
    private static class ElementInstantiationException extends RuntimeException {
        public ElementInstantiationException(String message) {
            super(message);
        }
    }

    public class A {

    }

    public static void main(String[] args) {
        AutoPopulatingList.A a1 =  new AutoPopulatingList<>(String.class).new A();
        boolean synthetic = AutoPopulatingList.A.class.isSynthetic();
        System.out.println(synthetic);
        System.out.println(a1.getClass().isMemberClass());
        System.out.println(a1.getClass().isLocalClass());

        System.out.println(AutoPopulatingList.class.isMemberClass());
        System.out.println(AutoPopulatingList.class.isLocalClass());
    }

    public static class B {

    }

    /** 从字节码看，静态方法和普通方法还是有差异的 */
    private static void c () {}
    private void d () {}
}
