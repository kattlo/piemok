package io.github.kattlo.piemok;

import java.util.HashMap;
import java.util.Map;

import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * Avro Serializer with Mocked Schema Registry.
 *
 * @author fabiojose
 */
@Slf4j
public class MockedKafkaAvroSerializer extends KafkaAvroSerializer {

    static final MockSchemaRegistryClient MOCK_REGISTRY =
        new MockSchemaRegistryClient();

    public MockedKafkaAvroSerializer() {
        super(MOCK_REGISTRY);

        Map<String, Object> configs = new HashMap<>();
        configs.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG,
            "http://do.not.use:8081");

        log.info("Using Mocked schema registry for " + KafkaAvroSerializer.class);

        configure(configs, Boolean.FALSE);
    }

    public byte[] serialize(Object record) {
        return super.serialize("none", record);
    }

}
