package io.github.kattlo.piemok;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.acme.EventExample;
import org.apache.avro.generic.GenericRecord;

public class MockedKafkaAvroSerializerTest {

    @Test
    public void should_serialize() throws Exception {

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

            var subjects = MockedKafkaAvroSerializer.MOCK_REGISTRY.getAllSubjects();
            assertFalse(subjects.isEmpty());

            assertEquals(topic + "-value", subjects.iterator().next());

        }
    }
}
