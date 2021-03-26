package io.github.kattlo.piemok;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;

public class MockedProducerTest {

    @Test
    public void should_produce() {

        // setup
        var topic = "my-topic";
        var key = "my-key";
        var value = "my-value";

        var expected = new ProducerRecord<>(topic, key, value);
        var mocked = MockedProducer.<String, String>create();

        // act
        mocked.send(expected);

        // assert
        var actual = mocked.history();
        assertFalse(actual.isEmpty());

        assertEquals(expected, actual.iterator().next());
    }
}
