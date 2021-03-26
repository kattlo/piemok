package io.github.kattlo.piemok;

import java.util.Collections;
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
public class MockedConsumerForSubscribe<K, V> implements MockedConsumer<K, V> {

    private static final int DEFAULT_PARTITION = 0;
    private static final long DEFAULT_OFFSET = 0;

    private final MockConsumer<K, V> consumer;
    public MockedConsumerForSubscribe() {
        consumer = new MockConsumer<>(OffsetResetStrategy.EARLIEST);
    }

    @Override
    public void reset(ConsumerRecord<K, V> record) {
        log.debug("reset consumer with record: {}", record);

        consumer.schedulePollTask(() -> {
            consumer.rebalance(Collections.singletonList(
                new TopicPartition(record.topic(), DEFAULT_PARTITION)));

            consumer.addRecord(record);

            var tp = new TopicPartition(record.topic(), DEFAULT_PARTITION);
            consumer.updateBeginningOffsets(Map.of(tp, Long.valueOf(DEFAULT_OFFSET)));
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
