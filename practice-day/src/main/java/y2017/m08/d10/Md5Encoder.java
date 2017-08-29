package y2017.m08.d10;

/**
 * @author : <a href='mailto:zikaifeng@ebnew.com'>冯子恺</a>
 * @version : Ver 1.0
 * @description :
 * @date : 2017/8/10
 */
public class Md5Encoder {
    private static char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

    public static String encode(byte[] bytes) {
        if (bytes.length != 16) {
            return null;
        }

        char[] chs = new char[bytes.length * 2];

        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            chs[2 * i] = chars[b & 0x0f];
            chs[2 * i + 1] = chars[(b & 0xf0) >> 4];
        }

        return new String(chs);
    }
}
