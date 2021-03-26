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
	    testImplementation 'com.github.kattlo:piemok:v0.10.0'
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
	    <version>v0.10.0</version>
	</dependency>
    ```

  - [See other options](https://jitpack.io/#kattlo/piemok)

2. Configure
```properties

```

3. Use to test code that produces events
```java

```

4. Use to test code that consumes events
```java

```

5. Use to test code with Kafka Avro Serializer
```java

```

6. Use to test code with Kafka Avro Deserializer
```java

```
