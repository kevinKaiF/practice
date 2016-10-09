package y2016.m05.day20160530;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-05-30 AM09:27
 */
public class TestReg{
    private static final Logger LOGGER = Logger.getLogger(TestReg.class);
    @Test
    public void testReg() {
        String[] arr = "$123千万".split("\\d+");
        System.out.println(Arrays.toString(arr));

        String[] arr1 = "$12.3千万".split("\\d+[\\.]{0,1}\\d+");
        System.out.println(Arrays.toString(arr1));

        String[] arr2 = "AA_".split("_");
        System.out.println(Arrays.toString(arr2));
    }

    @Test
    public void testFormat() {
        Pattern pattern = Pattern.compile("\\d+[\\.]{0,1}\\d+");
        String value = "$12.3千万";
        Matcher matcher = pattern.matcher(value);
        if(matcher.find()) {
            String matchValue = matcher.group();
            BigDecimal decimal = BigDecimal.valueOf(Double.valueOf(matchValue));
            System.out.println(decimal.multiply(BigDecimal.valueOf(1.0)).toString());
        }
    }
    @Test
    public void testLogger() {
//        LOGGER.info("{}表，{}列", "A", "A1");
    }

    @Test
    public void testReplace() {
        // replaceAll方法则是按照正则表达式匹配，底层的Patter模式是0
        System.out.println("a , b ，  c".replaceAll("\\s*[,，]\\s*", ","));

        // replace方法只是单纯的字符匹配，底层的Patter模式是Pattern.LITERAL
        System.out.println("a , b ，  c".replace("  ", "*"));
    }

    @Test
    public void testMatch() {
        System.out.println("我你他".matches(".*你.*"));
        System.out.println("我你他".matches(".*你"));

        System.out.println("我你".matches(".*你"));
        System.out.println("你ta".matches("你.*"));
    }

    @Test
    public void testFileAbsolutePath() {
        File file = new File("test.xml");
        System.out.println(file.getAbsolutePath());

    }

    @Test
    public void testNumberFormat() {
        System.out.println(numberFormat("$12.3千万","$_8.9,千万_10000000", "", ""));
        System.out.println(numberFormat("$ 12.3 千万", "$ _ 8.9 , 千万_10000000 ， ", "", ""));
    }

    @Test
    public void testDoubleFormat() {
        try {
            Double.valueOf(null);
        } catch (NumberFormatException e) {

            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testException() {
        try {
            Double d = Double.valueOf("XX");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getCause().getMessage());
        }
    }

    private String numberFormat(String value, String matchValue, String tableName, String column) {
        String reg = "\\d+[\\.]{0,1}\\d+";
        String[] valueArr = value.split(reg);
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(value);
        BigDecimal decimalNum = null;

        if(matcher.find()) {
            decimalNum = BigDecimal.valueOf(Double.valueOf(matcher.group()));
        } else {
            // 如果没有匹配的数字直接返回
            return value;
        }

        if(valueArr != null && valueArr.length > 0) {
            Map<String, String> matchMap = getMatchMap(matchValue);
            System.out.println(matchMap.toString());
            for(String val : valueArr) {
                String replace = matchMap.get(val.trim()).trim();
                System.out.println(replace);
                BigDecimal mulVal = null;
                if(replace.startsWith("*")) {
                    mulVal = BigDecimal.valueOf(Double.valueOf(replace.substring(1)));
                } else {
                    mulVal = BigDecimal.valueOf(Double.valueOf(replace));
                }

                decimalNum = decimalNum.multiply(mulVal);
            }
        }

        return decimalNum.toPlainString();
    }

    private Map<String, String> getMatchMap(String matchValue) {
        String[] matchArr = matchValue.split(",|，");
        Map<String, String> matchMap = new HashMap<>();
        for(String matchStr : matchArr) {
            String[] arr = matchStr.split("_");
            if(arr != null) {
                if(arr.length == 1 && StringUtils.isNotEmpty(arr[0].trim())) {
                    matchMap.put(arr[0].trim(), "");
                } else if(arr.length == 2 && StringUtils.isNotEmpty(arr[0].trim())) {
                    matchMap.put(arr[0].trim(), arr[1].trim());
                } else {
                    // ignore
                }
            }
        }
        return matchMap;
    }
}
