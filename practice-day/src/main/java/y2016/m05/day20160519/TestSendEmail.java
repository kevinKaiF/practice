package y2016.m05.day20160519;

import org.junit.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Map;
import java.util.Properties;
import java.util.WeakHashMap;
import java.util.regex.Pattern;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-05-19 AM10:20
 */
public class TestSendEmail {
    private final static Pattern EMAIL_PATTERN  = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");

    /**
     * 1.这里使用163网易邮箱作为测试
     * 163邮箱默认没有开启SMTP服务，需要手动去个人邮箱开通，但是整个流程是这样子的
     * 具体的配置见<a>http://help.163.com/09/1223/14/5R7P3QI100753VB8.html</a>
     * 2.sun的java.mail包只是个接口，并未implements，需要添加javax.mail的依赖
     *  <dependency>
     *      <groupId>javax.mail</groupId>
     *      <artifactId>mail</artifactId>
     *      <version>1.4.7</version>
     *  </dependency>
     */
    @Test
    public void testSendEmail() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setUsername("xxx@163.com");
        mailSender.setPassword("");
        mailSender.setHost("smtp.163.com");
        mailSender.setDefaultEncoding("UTF-8");
        Properties pros = new Properties();
        pros.put("mail.smtp.auth", true);
        pros.put("mail.smtp.timeout", 25000);
        pros.put("mail.smtp.port", 465);  //
        pros.put("mail.smtp.socketFactory.port", 465);
        pros.put("mail.smtp.socketFactory.fallback", false);
        pros.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        mailSender.setJavaMailProperties(pros);

        SimpleMailMessage mail = new SimpleMailMessage(); // 只发送纯文本
        mail.setText("test");// 邮件内容
        mail.setSubject("TEST");// 主题
        mail.setFrom("xxx@163.com");
        mail.setTo("xxxxxx");
        mailSender.send(mail);
    }


    @Test
    public void testJAF() {
//        javax.activation.FileTypeMap
    }

    @Test
    public void testWeakHashMap() throws InterruptedException {
        Map map = new WeakHashMap();
        map.put("name", "kevin");
        System.out.println(map.get("name"));
        System.out.println(map.size());
        Thread.sleep(10000);
        System.out.println(map.get("name"));
    }

    @Test
    public void testLabel() {
        System.out.println(get(null));
    }

    /**
     * 这个是labeled break statement适用于跳出多重循环
     * @param param
     * @return
     */
    private String get(String param) {
        label11 : {
            if(param == null) {
                break label11;
            }
            return "11";
        }
        return null;
    }


}
