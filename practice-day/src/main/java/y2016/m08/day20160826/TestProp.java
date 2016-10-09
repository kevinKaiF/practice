package y2016.m08.day20160826;

import org.junit.Test;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-08-26 PM01:30
 */
public class TestProp {
    @Test
    public void testProp() {
//        Properties properties = new Properties();
        System.out.println(saveConvert("\t", true, true));
    }

    @Test
    public void test1() {
//        for (int i = 62; i < 128 ; i++) {
//            System.out.println(i + ":" + ((char) i));
//        }
//        for (int i = 62; i < 128 ; i++) {
//            System.out.print((char) i + " ");
//        }
//
//        System.out.println('\\');
        System.out.println('\u0081' == 129);
    }

    @Test
    public void test2() {
        char ch = 129;
        System.out.println((ch));
        System.out.println(saveConvert(new String(new char[]{ch}), true, true));
    }

    @Test
    public void testConvert() {
        char ch = 61;
        System.out.println(convert(new String(new char[]{ch})));
        System.out.println('\u003d' == 61);
    }

    /**
     * 将转义转为字符.
     *
     * @param theString     the the string
     * @param escapeSpace   the escape space
     * @param escapeUnicode the escape unicode
     * @return the string
     * @author : kevin
     * @date : 2016-08-26 13:46:52
     */
    public String saveConvert(String theString,
                              boolean escapeSpace,
                              boolean escapeUnicode) {
        int len = theString.length();
        int bufLen = len * 2;
        if (bufLen < 0) {
            bufLen = Integer.MAX_VALUE;
        }
        StringBuffer outBuffer = new StringBuffer(bufLen);

        for(int x=0; x<len; x++) {
            char aChar = theString.charAt(x);
            // Handle common case first, selecting largest block that
            // avoids the specials below
            // > ? @ A B C D E F G H I J K L M N O P Q R S T U V W X Y Z [ \ ] ^ _ ` a b c d e f g h i j k l m n o p q r s t u v w x y z { | } ~
            if ((aChar > 61) && (aChar < 127)) {
                if (aChar == '\\') {
                    outBuffer.append('\\'); outBuffer.append('\\');
                } else {
                    outBuffer.append(aChar);
                }
            } else {
                switch(aChar) {
                    case ' ':
                        if (x == 0 || escapeSpace)
                            outBuffer.append('\\');
                        outBuffer.append(' ');
                        break;
                    case '\t':outBuffer.append('\\'); outBuffer.append('t');
                        break;
                    case '\n':outBuffer.append('\\'); outBuffer.append('n');
                        break;
                    case '\r':outBuffer.append('\\'); outBuffer.append('r');
                        break;
                    case '\f':outBuffer.append('\\'); outBuffer.append('f');
                        break;
                    case '=': // Fall through
                    case ':': // Fall through
                    case '#': // Fall through
                    case '!':
                        outBuffer.append('\\'); outBuffer.append(aChar);
                        break;
                    default:
                        // aChar < 32 || aChar > 126
                        if (((aChar < 0x0020) || (aChar > 0x007e)) & escapeUnicode ) {
                            outBuffer.append('\\');
                            outBuffer.append('u');
                            // char为16位
                            outBuffer.append(toHex((aChar >> 12))); // 取高高四位 15-12
                            outBuffer.append(toHex((aChar >>  8))); // 取低高四位 11-8
                            outBuffer.append(toHex((aChar >>  4))); // 取高低四位 7-4
                            outBuffer.append(toHex( aChar       )); // 取低低四位 3-0
                        } else {
                            outBuffer.append(aChar);
                        }
                }
            }
        }
        return outBuffer.toString();
    }

    public String convert(String str) {
        int len = str.length();
        int bufferLen = (len * 2 < 0) ? Integer.MAX_VALUE : len * 2;
        StringBuilder stringBuilder = new StringBuilder(bufferLen);
        for (int i = 0; i < len; i++) {
            char aChar = str.charAt(i);
            switch (aChar) {
                case '\\' :
                    stringBuilder.append("\\\\");break;
                case '\t' :
                    stringBuilder.append("\\t");break;
                case '\n' :
                    stringBuilder.append("\\n");break;
                case 'r' :
                    stringBuilder.append("\\r");break;
                case 'f' :
                    stringBuilder.append("\\f");break;
                default:
                    stringBuilder.append("\\u");
                    stringBuilder.append(toHex(aChar >> 12));
                    stringBuilder.append(toHex(aChar >> 8 ));
                    stringBuilder.append(toHex(aChar >> 4 ));
                    stringBuilder.append(toHex(aChar      ));
            }
        }
        return stringBuilder.toString();
    }

    private static char toHex(int nibble) {
        return hexDigit[(nibble & 0xF)];
    }

    /** A table of hex digits */
    private static final char[] hexDigit = {
            '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
    };


}
