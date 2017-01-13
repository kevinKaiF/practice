package y2016.m11.d25;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/11/25
 */
public class Super {

    public Super() {
        print();
    }


    void print() {
        System.out.println("three");
    }
}

class Test extends Super {
    int three = 3;

    @Override
    void print() {
        System.out.println(three);
    }

    public static void main(String[] args) {
        Super test = new Test();
        test.print();

//        Test test = new Test();
//        test.print();
    }
}
