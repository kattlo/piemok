package io.github.kattlo.piemok;

import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.common.serialization.Serializer;

/**
 * @author fabiojose
 */
public class MockedProducer {

    public static <K, V> MockProducer<K, V> create() {
        return create(true, null, null);
    }

    public static <K, V> MockProducer<K, V> create(boolean autoComplete,
        Serializer<K> keySerializer, Serializer<V> valueSerializer) {

        return new MockProducer<>();
    }

}
