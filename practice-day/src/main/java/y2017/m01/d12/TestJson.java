package y2017.m01.d12;

import org.junit.Test;
import y2016.m05.day20160520.JsonUtils;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/1/12
 */
public class TestJson {
    @Test
    public void testJson() {
        String json = "{\n" +
                "           \"reviewIndices\": {\n" +
                "                \"openingBidPrice\" : {\n" +
                "                    \"name\" : \"开标价格(万元)\",  \n" +
                "                    \"type\" :  1,\n" +
                "                    \"data\" : {\n" +
                "                        \"1001\" : 400,\n" +
                "                        \"1002\" : 300\n" +
                "                    }\n" +
                "                },\n" +
                "                \"arithmeticalCorrection\" : {\n" +
                "                    \"name\" : \"算术修正值\",\n" +
                "                    \"type\" : 4,\n" +
                "                    \"data\" : {\n" +
                "                        \"1001\" : -20,\n" +
                "                        \"1002\" : 30\n" +
                "                    }\n" +
                "                },\n" +
                "                \"arithmeticalCorrectionPrice\" : {\n" +
                "                    \"name\" : \"算术修正后的投标价格\",\n" +
                "                    \"type\" : 1,\n" +
                "                    \"data\" : {\n" +
                "                        \"1001\" : 420,\n" +
                "                        \"1002\" : 330\n" +
                "                    }\n" +
                "                },\n" +
                "                \"bidDeclaration\" : {\n" +
                "                    \"name\" : \"投标声明(折扣等)\",\n" +
                "                    \"type\" : 3,\n" +
                "                    \"data\" : {\n" +
                "                        \"1001\" : 20,\n" +
                "                        \"1002\" : 30\n" +
                "                    }\n" +
                "                },\n" +
                "                \"bidPrice\" : {\n" +
                "                    \"name\" : \"投标报价\",\n" +
                "                    \"type\" : 1,\n" +
                "                    \"data\" : {\n" +
                "                        \"1001\" : 440,\n" +
                "                        \"1002\" : 340\n" +
                "                    }\n" +
                "                },\n" +
                "                \"businessOffset\" : {\n" +
                "                    \"name\" : \"商务偏离\",\n" +
                "                    \"type\" : 3,\n" +
                "                    \"data\" : {\n" +
                "                        \"1001\" : 40,\n" +
                "                        \"1002\" : 30\n" +
                "                    }\n" +
                "                },\n" +
                "                \"technologyOffset\" : {\n" +
                "                    \"name\" : \"技术偏离\",\n" +
                "                    \"type\" : 3,\n" +
                "                    \"data\" : {\n" +
                "                        \"1001\" : 40,\n" +
                "                        \"1002\" : 30\n" +
                "                    }\n" +
                "                },\n" +
                "                \"otherOffset\" : {\n" +
                "                    \"name\" : \"其他偏离\",\n" +
                "                    \"type\" : 3,\n" +
                "                    \"data\" : {\n" +
                "                        \"1001\" : 40,\n" +
                "                        \"1002\" : 30\n" +
                "                    }\n" +
                "                },\n" +
                "                \"offsetPrice\" : {\n" +
                "                    \"name\" : \"投标报价(偏离调整后)\",\n" +
                "                    \"type\" : 1,\n" +
                "                    \"data\" : {\n" +
                "                        \"1001\" : 450,\n" +
                "                        \"1002\" : 360\n" +
                "                    }\n" +
                "                },\n" +
                "                \"otherAdjustment\" : {\n" +
                "                    \"name\" : \"其他调整\",\n" +
                "                    \"type\" : 2,\n" +
                "                    \"data\" : {\n" +
                "                        \"1001\" : 40,\n" +
                "                        \"1002\" : 30\n" +
                "                    }\n" +
                "                }\n" +
                "           },\n" +
                "           \"result\" : [{\n" +
                "                \"name\" : \"上海贝尔\",\n" +
                "                \"id\" : 1001,\n" +
                "                \"value\" : 400\n" +
                "            }, {\n" +
                "                \"name\" : \"亿阳信通\",\n" +
                "                \"id\" : 1002,\n" +
                "                \"value\" : 300\n" +
                "            }]\n" +
                "        }";
        System.out.println(JsonUtils.json2Object(json, Map.class, null));
    }

    @Test
    public void testClassPath() {
        String path = Thread.currentThread().getContextClassLoader().getResource(".").getPath();
        System.out.println(path);
    }


    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("yes");
            }
        });
        executorService.shutdown();
        System.out.println(executorService.isShutdown());
        while (!executorService.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("wait");
        }
    }
}
