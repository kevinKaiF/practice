//package y2017.m03.d09;
//
//import org.apache.commons.lang.builder.ToStringBuilder;
//import org.junit.Test;
//import sun.misc.JavaLangAccess;
//import sun.misc.JavaLangRefAccess;
//import sun.misc.Launcher;
//import sun.misc.SharedSecrets;
//
//import java.io.File;
//import java.util.StringTokenizer;
//
///**
// * @author : kevin
// * @version : Ver 1.0
// * @description :
// * @date : 2017/3/9
// */
//public class TestRefAccess {
//    public void print() {
//        JavaLangAccess javaLangAccess = SharedSecrets.getJavaLangAccess();
//        System.out.println(ToStringBuilder.reflectionToString(javaLangAccess));
//        Throwable throwable = new Throwable();
//        int stackTraceDepth = javaLangAccess.getStackTraceDepth(throwable);
//
//        for (int i = 0; i < stackTraceDepth; i++) {
//            StackTraceElement stackTraceElement = javaLangAccess.getStackTraceElement(throwable, i);
//            System.out.println(stackTraceElement.toString());
//        }
//
//        System.out.println("\n\n");
//        throwable.printStackTrace();
//    }
//
//    @Test
//    public void testLangAccess() {
//        print();
//    }
//
//    @Test
//    public void testRefAccess() {
//        JavaLangRefAccess jlra = SharedSecrets.getJavaLangRefAccess();
//        System.out.println(jlra.getClass().toString());
//
////        System.out.println(jlra instanceof Reference.ReferenceHandler);
//        A a = new A() {
//            @Override
//            public void run() {
//
//            }
//        };
//        System.out.println(a.getClass());
//    }
//
//    abstract class A {
//        public abstract void run();
//    }
//
//    @Test
//    public void testContextClassLoader() throws InterruptedException {
//        // 在线程初始化的时候，会有this.contextClassLoader = parent.contextClassLoader;
//        // 将父类的contextClassLoader赋值给子线程，保持contextClassLoader一致
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(Thread.currentThread().getContextClassLoader());
//            }
//        }).start();
//
//        Thread.sleep(100);
//        System.out.println(Thread.currentThread().getContextClassLoader());
//    }
//
//    @Test
//    public void testSecurityManager() {
//        String var2 = System.getProperty("java.security.manager");
//        System.out.println(var2);
//
//        // 1 = {File@833} "C:\WINDOWS\Sun\Java\lib\ext"
//        // 0 = {File@832} "D:\Program Files\Java\jdk1.8.0_77\jre\lib\ext"
//        File[] extDirs = getExtDirs();
//        System.out.println(ToStringBuilder.reflectionToString(extDirs));
//    }
//
//
//    private static File[] getExtDirs() {
//        String var0 = System.getProperty("java.ext.dirs");
//        File[] var1;
//        if(var0 != null) {
//            StringTokenizer var2 = new StringTokenizer(var0, File.pathSeparator);
//            int var3 = var2.countTokens();
//            var1 = new File[var3];
//
//            for(int var4 = 0; var4 < var3; ++var4) {
//                var1[var4] = new File(var2.nextToken());
//            }
//        } else {
//            var1 = new File[0];
//        }
//
//        return var1;
//    }
//
//    @Test
//    public void testClassLoader() {
//        /**
//         * {@link Launcher#getLauncher()}会初始化launcher，以{@link sun.misc.Launcher.ExtClassLoader}初始化，
//         * 接着以extClassLoader为parent,初始化{@link sun.misc.Launcher.AppClassLoader}，AppClassLoader变成默认的classLoader
//         */
//        ClassLoader appClassLoader = ClassLoader.getSystemClassLoader();
//        ClassLoader extClassLoader = appClassLoader.getParent();
//        ClassLoader bootstrapLoader = extClassLoader.getParent();
//
//        System.out.println("appClassLoader:" + appClassLoader);
//        System.out.println("extClassLoader:" + extClassLoader);
//        System.out.println("bootstrapLoader:" + bootstrapLoader);
//
//        String extDirs = System.getProperty("java.ext.dirs");
//        String classpath = System.getProperty("java.class.path");
//        System.out.println("extDirs:" + extDirs);
//        System.out.println("classpath:" + classpath);
//    }
//}
