package io.github.kattlo.piemok;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.acme.EventExample;
import org.apache.avro.generic.GenericRecord;
import org.junit.jupiter.api.Test;

import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;

public class MockedKafkaAvroDeserializerTest {

    @Test
    public void should_deserialize_generic_value() {

        // setup
        var topic = "my-topic";

        var event = new EventExample();
        event.setCode(1l);
        event.setName("name");
        event.setDescription("description");

        try(var ser = new MockedKafkaAvroSerializer();
            var des = new MockedKafkaAvroDeserializer()){

            // act
            var bytes = ser.serialize(topic, event);

            // assert
            var actual = des.deserialize(topic, bytes);
            assertTrue(actual instanceof GenericRecord);

            var avro = (GenericRecord)actual;
            assertEquals(event.getCode(), avro.get("code"));
            assertEquals(event.getName(), avro.get("name").toString());
            assertEquals(event.getDescription(), avro.get("description").toString());

        }
    }

    @Test
    public void should_deserialize_specific_value() {

        // setup
        Map<String, Object> config = new HashMap<>();
        config.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG,
            true);
        config.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG,
            "http://do.not.use:8081");

        var topic = "my-topic";

        var expected = new EventExample();
        expected.setCode(1l);
        expected.setName("name");
        expected.setDescription("description");

        try(var ser = new MockedKafkaAvroSerializer();
            var des = new MockedKafkaAvroDeserializer()){

            des.configure((Map<String, ?>)config, false);

            // act
            var bytes = ser.serialize(topic, expected);

            // assert
            var avro = des.deserialize(topic, bytes);
            assertTrue(avro instanceof EventExample);

            var actual = (GenericRecord)avro;
            assertEquals(expected, actual);

        }
    }
}
