
import java.util.Properties;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class SimpleConsumer {
	public static void main(String[] args) throws Exception {

		// Kafka consumer configuration settings
		String topicName = "W5";
		Properties props = new Properties();

		props.put("bootstrap.servers", "localhost:9092");
		props.put("group.id", "test");
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);

		// Kafka Consumer subscribes list of topics here.
		consumer.subscribe(Arrays.asList(topicName));

		// print the topic name
		System.out.println("Subscribed to topic " + topicName);
		int i = 0;
		Map<String, ArrayList<Double>> temps = new HashMap<String, ArrayList<Double>>();
		while (i < 15) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			i = i + records.count();
			for (ConsumerRecord<String, String> record : records) {
				// print the offset,key and value for the consumer records.
				System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
				if (temps.containsKey(record.key())) {
					ArrayList<Double> k = temps.get(record.key());
					k.add(Double.valueOf(record.value()));
					temps.put(record.key(), k);
				} else {
					ArrayList<Double> k = new ArrayList<Double>();
					k.add(Double.valueOf(record.value()));
					temps.put(record.key(), k);
				}
			}

		}
		consumer.close();
		System.out.println();
		System.out.println("AVERAGE TEMPERATURE in celcius:");
		System.out.println();
		for (String ky : temps.keySet()) {
			ArrayList<Double> tmps = temps.get(ky);
			Double sum = 0.0;
			for (Double d : tmps) {
				sum = sum + d;
			}
			Double avg = sum / tmps.size();
			System.out.print(ky + " = ");
			System.out.printf(String.format("%.2f", avg));
			System.out.println();
		}

	}
}