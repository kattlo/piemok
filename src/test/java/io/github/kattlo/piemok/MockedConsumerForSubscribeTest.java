package io.github.kattlo.piemok;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;
import java.util.Collections;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Test;

public class MockedConsumerForSubscribeTest {

    @Test
    public void should_reset_by_consumer_record() {

        // setup
        var topic = "my-topic";
        var partition = 0;
        var offset = 0;
        var key = "record-key";
        var value = "record-val";

        var expected = new ConsumerRecord<>(topic, partition, offset, key, value);
        var mock = MockedConsumer.<String, String>forSubscribe();

        // act
        mock.reset(expected);
        mock.consumer().subscribe(Collections.singletonList(topic));

        // assert
        var actual = mock.consumer().poll(Duration.ofMillis(10));

        assertFalse(actual.isEmpty());
        assertEquals(expected, actual.iterator().next());
    }

    @Test
    public void should_reset_by_producer_record() {

        // setup
        var topic = "my-topic";
        var partition = 0;
        var offset = 0;
        var key = "record-key";
        var value = "record-val";

        var mock = MockedConsumer.<String, String>forSubscribe();

        var producerRecord = new ProducerRecord<>(topic, partition, key, value);

        // act
        mock.reset(producerRecord);
        mock.consumer().subscribe(Collections.singletonList(topic));

        // assert
        var actual = mock.consumer().poll(Duration.ofMillis(10));

        assertFalse(actual.isEmpty());

        var record = actual.iterator().next();
        assertEquals(topic, record.topic());
        assertEquals(key, record.key());
        assertEquals(value, record.value());
        assertEquals(partition, record.partition());
        assertEquals(offset, record.offset());
    }

    @Test
    public void should_reset_by_key_value_and_topic_name() {

        // setup
        var topic = "my-topic";
        var partition = 0;
        var offset = 0;
        var key = "record-key";
        var value = "record-val";

        var mock = MockedConsumer.<String, String>forSubscribe();

        // act
        mock.reset(topic, key, value);
        mock.consumer().subscribe(Collections.singletonList(topic));

        // assert
        var actual = mock.consumer().poll(Duration.ofMillis(10));

        assertFalse(actual.isEmpty());

        var record = actual.iterator().next();
        assertEquals(topic, record.topic());
        assertEquals(key, record.key());
        assertEquals(value, record.value());
        assertEquals(partition, record.partition());
        assertEquals(offset, record.offset());
    }

    @Test
    public void should_throw_when_try_to_assing_and_seek() {

        // setup
        var topic = "my-topic";
        var partition = 0;
        var offset = 0;
        var key = "record-key";
        var value = "record-val";

        var mock = MockedConsumer.<String, String>forSubscribe();

        var tp = new TopicPartition(topic, partition);

        // act
        mock.reset(topic, key, value);

        mock.consumer().assign(Collections.singletonList(tp));
        mock.consumer().seek(tp, offset);

        // assert
        assertThrows(IllegalArgumentException.class, () ->
            mock.consumer().poll(Duration.ofMillis(10)));
    }

}
