
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import datasource.DataSourceImpl;
import datasource.DataSource;
import datasource.DataSource3;

public class Producer3 {
	static DataSource dataSource;
	static HashMap<String, String> data;

	public static void main(String[] args) throws Exception {

		dataSource = new DataSourceImpl(new DataSource3());
		data = dataSource.getData();
		Set<String> keys = data.keySet();
		Iterator<String> k = keys.iterator();
		String topicName = "W5";
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);

		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer<String, String>(props);

		while (k.hasNext()) {
			String key = (String) k.next();
			producer.send(new ProducerRecord<String, String>(topicName, key, data.get(key))).get();
		}
		System.out.println("Message sent successfully");
		producer.close();
	}
}
