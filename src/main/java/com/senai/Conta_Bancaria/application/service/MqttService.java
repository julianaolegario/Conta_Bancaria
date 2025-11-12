package com.senai.Conta_Bancaria.application.service;

import jakarta.annotation.PostConstruct;
import lombok.Value;
import org.springframework.stereotype.Service;

@Service
public class MqttService {
    private MqttClient client;

    @Value("${mqtt.broker-uri:tcp://localhost:1883}")
    private String broker;

    @Value("${mqtt.client-id:ContaBancariaBackend}")
    private String clientId;

    @PostConstruct
    public void init() {
        try {
            client = new MqttClient(broker, clientId + "-" + System.currentTimeMillis());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            client.connect(options);
            // subscribe patterns if needed - we'll subscribe to validation topic
        } catch (MqttException e) {
            throw new RuntimeException("Erro ao conectar MQTT: " + e.getMessage(), e);
        }
    }

    public void publish(String topic, String payload) {
        try {
            if (client == null || !client.isConnected()) init();
            client.publish(topic, new MqttMessage(payload.getBytes()));
        } catch (MqttException e) {
            throw new RuntimeException("Erro ao publicar MQTT: " + e.getMessage(), e);
        }
    }

    public void subscribe(String topic, IMqttMessageListener listener) {
        try {
            if (client == null || !client.isConnected()) init();
            client.subscribe(topic, listener);
        } catch (MqttException e) {
            throw new RuntimeException("Erro ao subscrever MQTT: " + e.getMessage(), e);
        }
    }

    @PreDestroy
    public void destroy() {
        try {
            if (client != null && client.isConnected()) client.disconnect();
        } catch (MqttException ignored) {}
    }
}
