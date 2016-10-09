package y2016.m04.day20160422;

/**
 * 以下探究基于JDK1.7
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-04-22 AM09:53
 */
public class StringIntern {

    public static void main(String[] args) throws InterruptedException {
        String str = new StringBuffer("a").append("z").toString();
        System.out.println(str.intern() == "az");

        String str11 = new StringBuffer().append("a").append("z").toString();
        System.out.println(str11.intern() == str11);

        String str2 = new String("中国人");
        String str4 = new String("中国人");
        System.out.println(str2.intern() == str4.intern());

        String str3 = new StringBuffer("中国").append("人").toString();
        System.out.println(str3.intern() == str3);
        System.out.println(str3.intern() == str4.intern());

        // 由于“外国人”在方法区中的常量池并未缓存，所以str5.intern只是保存的String在堆上的引用
        String str5 = new StringBuffer("外国").append("人").toString();
        System.out.println(str5.intern() == str5);

        // new String(String str)方式在堆上创建对象，并在该对象copy到方法区
        // 所以str6是堆上的引用，str61是方法区的引用
        String str6 = new String("test");
        String str61 = "test";
        String str62 = "test";
        System.out.println(str6.intern() == str6);
        System.out.println(str6.intern() == str61);
        System.out.println(str6.intern() == str61.intern());
        System.out.println(str62 == str61);

        // new String(char[] chars) 以及类型的方式，都是直接在堆上直接创建对象，在方法区缓存引用
        // 所以str7.intern中存放的是堆对象的引用，
       /* String str7 = new String(new char[] {'中', '国', '人', '1'});
        System.out.println(str7.intern() == str7);

        String str9 = new String(new char[] {'中', '国', '人', '1'});
        System.out.println(str9.intern() == str7);*/

//        String str10 = new String("" + '中' + '国' + '人' + '1');
//        System.out.println(str10.intern() == str10);
        // 这个为什么会相等？？
        // 直接量从方法区查找的时候，找到了“中国人1”堆引用
       /* System.out.println("中国人1" == str7);

        String str8 = new String(new char[] {'a', '1', 'z'});
        System.out.println(str8.intern() == str8);*/

    }
}
