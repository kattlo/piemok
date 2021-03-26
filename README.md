# 🥧 Piemok

_Are you tired to run an Apache Kafka® broker for unit testing?_

Use 🥧 Piemok and run tests faster.

Mocks for:

- Producer API
- Consumer API
- Schema Registry

## Getting Started

1. Dependency

  - Gradle
    ```groovy
    repositories {
        // ...
        maven { url 'https://jitpack.io' }
    }

    dependencies {
	    testImplementation 'com.github.kattlo:piemok:v0.12.0'
	}

    ```

  - Apache Maven®
    ```xml
    <repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>

	<dependency>
	    <groupId>com.github.kattlo</groupId>
	    <artifactId>piemok</artifactId>
	    <version>v0.12.0</version>
	</dependency>
    ```

  - [See other options](https://jitpack.io/#kattlo/piemok)

2. To test code that produces events
```java
import io.github.kattlo.piemok.MockedProducer;

public class MyTestProducer {
    public void someTest() {

        var producer = MockedProducer.create();

        // --- pass the producer instance to your code --- //

        // get the produced records
        var records = producer.history();

        // do your assertions . ..

    }
}
```

3. To test code that consumes topics by subscription
```java
import io.github.kattlo.piemok.MockedConsumer;

public class MyTestBySubscription {
    public void someTest() {

        //                                          ***************
        var mocked = MockedConsumer.<String, String>forSubscribe();
        var topic = "my-topic";

        // add new record to be consumed in the poll() call
        mocked.reset(topic, "my-key", "my-value");

        // --- pass the mocked.consumer() to your code --- //

        // do your assertions . . .
    }
}
```

4. To test code that consumes topics by assignment and seek
```java
import io.github.kattlo.piemok.MockedConsumer;
import org.apache.kafka.common.TopicPartition;

public class MyTestByAssignment {
    public void someTest() {

        //                                          **********
        var mocked = MockedConsumer.<String, String>forSeek();
        var topic = "my-topic";

        // add new record to be consumed in the poll() call
        mocked.reset(topic, "my-key", "my-value");

        // --- pass the instance mocked.consumer() to your code --- //

        // do your assertions . . .
    }
}
```

5. To test code with Kafka Avro Serializer
```properties
value.serializer=io.github.kattlo.piemok.MockedKafkaAvroSerializer
# . . .
```

6. Use to test code with Kafka Avro Deserializer
```properties
value.deserializer=io.github.kattlo.piemok.MockedKafkaAvroDeserializer
# . . .
```
