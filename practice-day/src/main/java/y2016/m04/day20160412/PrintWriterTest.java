package y2016.m04.day20160412;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-04-12 AM11:53
 */
public class PrintWriterTest {
    protected final MyPrintWriter writer;

    public PrintWriterTest(MyPrintWriter writer) {
        this.writer = writer;
    }

    public void close() {
        if(writer != null) {
            writer.close();
        }
    }

    public void write(String msg) {
        writer.write(msg);
    }

    static class MyPrintWriter extends PrintWriter {

        public MyPrintWriter(Writer out) {
            super(out);
        }

        public Writer getOut() {
            return out;
        }
    }

    public static void main(String[] args) throws IOException {
        StringWriter stringWriter = new StringWriter();
        MyPrintWriter writer = new MyPrintWriter(stringWriter);
        PrintWriterTest test = new PrintWriterTest(writer);
        test.write("test");
//        test.close();
        writer.close();
        Writer writer1 = test.writer.getOut();
        if(writer1 == null) {
            System.out.println("is null");
            System.out.println(stringWriter == null);
        }
        if(writer1 instanceof StringWriter) {
            System.out.println(writer1 == stringWriter);
        }
//        System.out.println(stringWriter.getBuffer().toString());

        StringBuffer a = new StringBuffer();
        StringBuffer b = a;
        // java是值传递，并非引用传递，所以a = null是将a赋予新的引用，但是这不影响b
        // 反过来，b = null并不影响a
        a = null;
        System.out.println(Void.class);

    }
}
