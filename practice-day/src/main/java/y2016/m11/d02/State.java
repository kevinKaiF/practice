package y2016.m11.d02;

import java.util.Stack;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @description :
 * @date : 2016/11/2
 */
public class State {
    private Stack<String> stack;

    public State() {
        stack = new Stack<>();
    }

    public String push(String item) {
        return stack.push(item);
    }

    public String pop() {
        return stack.empty() ? null : stack.pop();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < stack.size(); x++) {
            if (x > 0) {
                sb.append("\n");
                for (int y = 0; y < x; y++) {
                    sb.append("\t");
                }
                sb.append("-> ");
            }
            sb.append(stack.get(x));
        }
        return sb.toString();
    }
}
