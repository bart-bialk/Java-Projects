import org.apache.kafka.clients.producer.ProducerRecord;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

public class Chat extends JFrame{
    private JTextArea messages;
    private JPanel mainPanel;
    private JTextField messageField;
    private JButton sendButton;
    private JButton loginButton;
    private JTextField loginField;
    private JList userList;

    private final MessageConsumer messageConsumer;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private boolean loggedIn;
    private ConcurrentHashMap<String, String> consumers;

    public Chat(String topic, String id) throws HeadlessException {
        this.add(mainPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle(topic + "(" + id + ")");
        this.pack();
        consumers = new ConcurrentHashMap<>();
        loggedIn = true;
        MessageProducer.send(new ProducerRecord<>(topic, id + " logged in"));

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loggedIn && !messageField.getText().isEmpty()){
                    MessageProducer.send(new ProducerRecord<>(topic, LocalDateTime.now().format(FORMATTER) + "-" + id + ": " + messageField.getText()));
                    messageField.setText("");
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loggedIn) {
                    MessageProducer.send(new ProducerRecord<>(topic, id + " logged out"));
                    loginField.setText("");
                    loginButton.setText("Login");
                    loggedIn = false;
                    setTitle(topic);

                } else {
                    if (!loginField.getText().isEmpty()) {
                        MessageProducer.send(new ProducerRecord<>(topic, loginField.getText() + " logged in"));
                        loggedIn = true;
                        setTitle(topic + "(" + loginField.getText() + ")");
                        loginField.setText("");
                        loginButton.setText("Logout");

                    }
                }

            }
        });

        messageConsumer = new MessageConsumer(topic, id);
        Executors.newSingleThreadExecutor().submit(() -> {
            while (true) {
                if (loggedIn) {
                    messageConsumer.getConsumer().poll(Duration.of(1, ChronoUnit.SECONDS)).forEach(
                            m -> messages.append(m.value() + System.lineSeparator())
                    );
                }
            }
        });


    }
}
