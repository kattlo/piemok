package io.github.kattlo.piemok;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerRecord;

import org.apache.kafka.clients.consumer.MockConsumer;

/**
 * @author fabiojose
 */
public interface MockedConsumer<K, V> {

    /**
     * Adds new {@link ConsumerRecord} and reset the consumer state
     */
    void reset(ConsumerRecord<K, V> record);

    /**
     * Resets the consumer state and add new record with key and value
     */
    void reset(String topic, K key, V value);

    /**
     * Transforms {@link ProducerRecord} into {@link ConsumerRecord} and reset
     * the consumer state
     */
    void reset(ProducerRecord<K, V> record);

    /**
     * Returns the instance of {@link KafkaConsumer}
     */
    MockConsumer<K, V> consumer();

    /**
     * Creates an instance to be used for
     * {@link KafkaConsumer#subscribe(java.util.Collection)},
     * {@link KafkaConsumer#subscribe(java.util.Collection, org.apache.kafka.clients.consumer.ConsumerRebalanceListener)},
     * {@link KafkaConsumer#subscribe(java.util.regex.Pattern)},
     * {@link KafkaConsumer#subscribe(java.util.regex.Pattern, org.apache.kafka.clients.consumer.ConsumerRebalanceListener)}
     * @param <K> Key type
     * @param <V> Value type
     */
    public static <K, V> MockedConsumer<K, V> forSubscribe() {
        return new MockedConsumerForSubscribe<>();
    }

    /**
     * Crestes an instance to be user for
     * {@link KafkaConsumer#assign(java.util.Collection)},
     * {@link KafkaConsumer#seekToBeginning(java.util.Collection)},
     * {@link KafkaConsumer#seek(org.apache.kafka.common.TopicPartition, long)},
     * {@link KafkaConsumer#seek(org.apache.kafka.common.TopicPartition, org.apache.kafka.clients.consumer.OffsetAndMetadata)} or
     * {@link KafkaConsumer#seekToEnd(java.util.Collection)}
     * @param <K> Key type
     * @param <V> Value type
     */
    public static <K, V> MockedConsumer<K, V> forSeek() {
        return new MockedConsumerForSeek<>();
    }
}
