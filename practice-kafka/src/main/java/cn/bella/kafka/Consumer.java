package cn.bella.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Iterator;
import java.util.Properties;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/6/16
 */
public class Consumer {
    private org.apache.kafka.clients.consumer.Consumer<String, String> consumer;
    private String topic   = "185DB";
    private String group   = "test1";
    private long   timeout = 2000;
    private long   count   = 0;


    private Properties getProperties() {
        Properties prop = new Properties();
//        prop.put("topic", topic);
//        prop.put("timeout", timeout);
        // kafka集群地址
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.4.0.186:9092");
        // group
        prop.put(ConsumerConfig.GROUP_ID_CONFIG, group);
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        return prop;
    }

    public Consumer() {
        consumer = new KafkaConsumer<String, String>(getProperties());
        consumer.subscribe(Collections.singleton(topic));
    }

    public Consumer(String topic) {
        this.topic = topic;
        consumer = new KafkaConsumer<String, String>(getProperties());
        consumer.subscribe(Collections.singleton(this.topic));
    }

    public void receive() {
        while (true) {
//            Map<MetricName, ? extends Metric> metrics = consumer.metrics();
//            System.out.println(metrics);
            ConsumerRecords<String, String> records = consumer.poll(timeout);
            if (records != null && !records.isEmpty()) {
                Iterator<ConsumerRecord<String, String>> iterator = records.iterator();
                while (iterator.hasNext()) {
                    ConsumerRecord<String, String> next = iterator.next();
                    String key = next.key();
                    String value = next.value();
                    System.out.println(key + ":" + value);
                    count++;
                }
                consumer.commitSync();
//                consumer.commitAsync(new OffsetCommitCallback() {
//                    @Override
//                    public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
//                        if (exception != null) {
//                            throw new RuntimeException(exception);
//                        }
//
//                        System.out.println(offsets);
//                    }
//                });
            }

            System.out.println(Thread.currentThread().getName() + " count : " + count);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Consumer consumer = new Consumer();
//                consumer.receive();
//            }
//        }).start();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Consumer consumer3 = new Consumer("193DB");
//                consumer3.receive();
//            }
//        }).start();

        Consumer consumer2 = new Consumer("oracle");
        consumer2.receive();

    }
}
