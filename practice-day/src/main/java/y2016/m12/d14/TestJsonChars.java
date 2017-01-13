package y2016.m12.d14;

import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/12/14
 */
public class TestJsonChars {
    protected char[] applyJsonQuoting(String content) {
        return JsonStringEncoder.getInstance().quoteAsString(content);
    }

    private String jsonStr;
    @Before
    public void initJsonStr() {
        jsonStr = "[{\"name\" : \"kevin\"}]";
    }

    @Test
    public void testJsonQuoting() throws UnsupportedEncodingException {
        char[] chars = applyJsonQuoting(jsonStr);
        System.out.println(Arrays.toString(chars));
        StringBuilder sb = new StringBuilder();
        for (char aChar : chars) {
            String str = Integer.toHexString(aChar).toString();
            sb.append("\\u");
            for (int i = 0; i < (4 - str.length()); i++) {
                sb.append(0);
            }
            sb.append(str);
        }
        System.out.println(sb.toString());

        System.out.println(Arrays.toString(jsonStr.toCharArray()));
        System.out.println(URLDecoder.decode(sb.toString(), "UTF-8"));
        System.out.println(jsonStr.getBytes(Charset.forName("UTF-8")));
    }

    @Test
    public void testJackson() throws IOException {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
        Map map = objectMapper.readValue(jsonStr, Map.class);
        System.out.println(map);
    }
}
