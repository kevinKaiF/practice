package y2016.m09.d28;

/**
 * 1.外部类初始化的时候，内部类未被引用的话，不会初始化，所以单例模式下很安全
 * 2.静态变量和静态代码块与初始化的顺序有关系，谁在前谁先执行
 * 3.如果只是调用静态变量或静态方法，构造函数是不会初始化的，内部类也是如此
 * 4.如果要调用内部类的属性或方法，外部类的静态属性，静态块要进行初始化
 *
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-09-28 PM01:51
 */
public class SingletonInstance {
    public static Ob ob = new Ob("static variable");
    static {
//        System.out.println("init static");
        ob = new Ob("static block");
    }

    public static void sayHello() {
        System.out.println("hello");
    }

    public SingletonInstance() {
        System.out.println("init SingletonInstance , " + (this.getClass().getEnclosingClass() == null));
    }

    public static class SingletonInstanceHolder {
        public SingletonInstanceHolder() {
            System.out.println("init SingletonInstanceHolder , " + (this.getClass().getEnclosingClass() == null));
        }

        public static final SingletonInstance singletonInstance = new SingletonInstance();
    }

}
