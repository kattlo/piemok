package io.github.kattlo.piemok;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.MockConsumer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;

import lombok.extern.slf4j.Slf4j;

/**
 * @author fabiojose
 */
@Slf4j
public class MockedConsumerForSeek<K, V> implements MockedConsumer<K, V> {

    private static final int DEFAULT_PARTITION = 0;
    private static final long DEFAULT_OFFSET = 0;

    private final MockConsumer<K, V> consumer;

    public MockedConsumerForSeek(){
        this.consumer = new MockConsumer<>(OffsetResetStrategy.EARLIEST);
    }

    @Override
    public void reset(ConsumerRecord<K, V> record) {
        log.debug("reset consumer with record: {}", record);
        consumer.schedulePollTask(() -> {
            var tp = new TopicPartition(record.topic(), record.partition());

            consumer.updateBeginningOffsets(Map.of(tp, Long.valueOf(DEFAULT_OFFSET)));
            consumer.addRecord(record);
        });
    }

    @Override
    public void reset(String topic, K key, V value) {
        reset(new ConsumerRecord<>(topic, DEFAULT_PARTITION, DEFAULT_OFFSET, key, value));
    }

    @Override
    public void reset(ProducerRecord<K, V> record) {
        log.debug("reset consumer with producer record {}", record);

        reset(new ConsumerRecord<>(record.topic(), record.partition(),
            DEFAULT_OFFSET, record.key(), record.value()));
    }

    @Override
    public MockConsumer<K, V> consumer() {
        return consumer;
    }

}
