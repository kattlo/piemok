package io.github.kattlo.piemok;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.common.serialization.Serializer;

/**
 * @author fabiojose
 */
public class MockedProducer {

    /**
     * Returns the instance of {@link KafkaProducer}
     */
    public static <K, V> MockProducer<K, V> create() {
        return create(true, null, null);
    }

    /**
     * Returns the instance of {@link KafkaProducer}
     */
    public static <K, V> MockProducer<K, V> create(boolean autoComplete,
        Serializer<K> keySerializer, Serializer<V> valueSerializer) {

        return new MockProducer<>(autoComplete, keySerializer, valueSerializer);
    }

}
