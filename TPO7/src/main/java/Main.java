import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.test.EmbeddedKafkaZKBroker;

import javax.swing.*;

@Slf4j
public class Main {

    public static void main(String[] args) {

        EmbeddedKafkaZKBroker broker = new EmbeddedKafkaZKBroker(1).kafkaPorts(9092);

        broker.afterPropertiesSet();

        SwingUtilities.invokeLater(() -> new Chat("chat", "Kinga"));
        SwingUtilities.invokeLater(() -> new Chat("chat", "Tomek"));
    }
}

