package y2016.m11.d02;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Stack;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @description :
 * @date : 2016/11/2
 */
public class TestStack {
    @Test
    public void testState() {
        State state = new State();
        state.push("1");
        state.push("2");
        state.push("3");
        state.push("4");
        System.out.println(state);
    }

    @Test
    public void testStack() {
        Stack stack = new Stack();
        stack.pop();
    }


    public static <A, O extends A> A[] addObjectToArray(A[] array, O obj) {
        Class<?> compType = Object.class;
        if (array != null) {
            compType = array.getClass().getComponentType();
        }
        else if (obj != null) {
            compType = obj.getClass();
        }
        int newArrLength = (array != null ? array.length + 1 : 1);
        @SuppressWarnings("unchecked")
        A[] newArr = (A[]) Array.newInstance(compType, newArrLength);
        if (array != null) {
            System.arraycopy(array, 0, newArr, 0, array.length);
        }
        newArr[newArr.length - 1] = obj;
        return newArr;
    }
}
