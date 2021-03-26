package io.github.kattlo.piemok;

import java.util.HashMap;
import java.util.Map;

import org.apache.avro.Schema;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * Avro Deserializer with Mocked Schema Registry.
 *
 * @author fabiojose
 */
@Slf4j
public class MockedKafkaAvroDeserializer extends KafkaAvroDeserializer {

    public MockedKafkaAvroDeserializer(){
        super(MockedKafkaAvroSerializer.MOCK_REGISTRY);

        Map<String, Object> configs = new HashMap<>();
        configs.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG,
            "http://do.not.use:8081");

        configure(configs, Boolean.FALSE);

        log.info("Using Mocked schema registry for " + KafkaAvroDeserializer.class);
    }

    public Object deserialize(Schema schema, byte[] b) {
        return deserialize("none", b, schema);
    }
}
