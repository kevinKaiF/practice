package y2016.m07.day20160725;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import kafka.serializer.Decoder;
import org.springframework.beans.factory.InitializingBean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-07-25 AM11:36
 */
public abstract class KafkaConsumer<E extends Serializable> implements InitializingBean{
    private ExecutorService executorService = null;
    private Decoder decoder;
    private int threadNum = 1;
    private String topic;

    public void afterPropertiesSet() throws Exception {
        Properties propAll = new Properties();
        ConsumerConfig config = new ConsumerConfig(propAll);
        ConsumerConnector consumerConnector = Consumer.createJavaConsumerConnector(config);

        HashMap topicMap = new HashMap();
        topicMap.put(this.topic, threadNum);
        Map streamMap = consumerConnector.createMessageStreams(topicMap, null, this.decoder);
        List list = (List) streamMap.get(this.topic);

        this.executorService = Executors.newFixedThreadPool(this.threadNum);
        for(Object item : list) {
            final KafkaStream kafkaStream = (KafkaStream) item;
            executorService.execute(new Runnable() {
                public void run() {
                    ConsumerIterator iterator = kafkaStream.iterator();
                    while (true) {
                        while (!iterator.hasNext()) {
                            ;
                        }

                        MessageAndMetadata messageAndMetadata = iterator.next();

                    }
                }
            });
        }
    }

    public abstract void onMessage(E e);
}
