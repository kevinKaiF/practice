package cn.bella.kafka;


import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.Semaphore;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/6/16
 */
public class Producer {
    private org.apache.kafka.clients.producer.Producer<String, String> producer;
    private String topic = "test-kevin";

    private Properties getProperties() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // ack 0异步，
//        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }

    public Producer() {
        producer = new KafkaProducer<String, String>(getProperties());
    }

    public void send(Object o) {
        for (int i = 0; i < 10; i++) {
            producer.send(new ProducerRecord<String, String>(topic, JSON.toJSONString(o)));
        }
        producer.flush();
        producer.close();
    }

    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);
        Producer producer = new Producer();
        producer.send(Arrays.asList(new Integer[] {1001, 1002, 1003, 1004}));
        semaphore.acquire();
    }
}
