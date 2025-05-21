
package com.senai.controle_de_acesso_spring.infrastructure.mqtt;

import com.senai.controle_de_acesso_spring.interface_ui.controller.OcorrenciaController;
import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqttSubscriber {

    private static final String BROKER = "tcp://localhost:1883";
    private static final String CLIENT_ID = "ServidorJava";
    private static final String TOPICO = "catraca/rfid";
    @Autowired
    private OcorrenciaController criarOcorrenciaController;


    @PostConstruct
    public void iniciarMqtt() {
        try {
            MqttClient client = new MqttClient(BROKER, CLIENT_ID);
            client.connect();
            client.subscribe(TOPICO, (topic, msg) -> {
                String idAcesso = new String(msg.getPayload());
                criarOcorrenciaController.criarOcorrenciaDeAtraso(idAcesso); // este será redirecionado para a view em um próximo passo, se necessário
            });
            System.out.println("Inscrito no tópico MQTT: " + TOPICO);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}