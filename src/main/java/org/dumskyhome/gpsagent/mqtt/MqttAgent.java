package org.dumskyhome.gpsagent.mqtt;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dumskyhome.gpsagent.json.JsonGpsDataMessage;
import org.dumskyhome.gpsagent.persistence.dao.GpsAgentDao;
import org.dumskyhome.gpsagent.persistence.datamodel.GpsData;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:mqtt.properties")
public class MqttAgent implements MqttCallback {

    private static final Logger logger = LoggerFactory.getLogger(MqttAgent.class);
    private ObjectMapper objectMapper;

    private MqttClient mqttClient;

    @Value("${mqtt.clientId}")
    private String mqttClientId;
    //private ThreadPoolExecutor executor;
//    @Autowired
//    MqttMessageProcessor mqttMessageProcessor;

    @Autowired
    //private GPSAgentService gpsAgentService;
    private GpsAgentDao gpsAgentDao;

    @Autowired
    Environment env;

    MqttAgent() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.disable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES);
        objectMapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
    }

    private void init() {
        try {
            mqttClient = new MqttClient(env.getProperty("mqtt.serverUrl"), mqttClientId);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private boolean connect() {

        try {
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setUserName(env.getProperty("mqtt.user"));
            mqttConnectOptions.setPassword(env.getProperty("mqtt.password").toCharArray());
            mqttConnectOptions.setConnectionTimeout(Integer.parseInt(env.getProperty("mqtt.connectionTimeout")));
            mqttConnectOptions.setAutomaticReconnect(true);
            mqttConnectOptions.setCleanSession(true);

            mqttClient.connect(mqttConnectOptions);
            //if (mqttClient.isConnected()) executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
            return mqttClient.isConnected();

        } catch (MqttException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean subscribeToTopics() {
        try {
            mqttClient.subscribe(env.getProperty("mqtt.topic.gpsData"));
            mqttClient.setCallback(this);
            return true;
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean runMqttService() {
        init();
        if(connect()) {
            logger.info("Connected to MQTT");
            return subscribeToTopics();
        }
        return false;
    }

    public void sendMessage(String topic, String messageString, int QoS) {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(QoS);
        mqttMessage.setPayload(messageString.getBytes());
        try {
            mqttClient.publish(topic, mqttMessage);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void connectionLost(Throwable throwable) {
        logger.error("CONNECTION LOST");
        while(!connect()) {
            logger.info("RECONNECTION ATTEMPT");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        logger.error("CONNECTION RESTORED");
        subscribeToTopics();
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        logger.info("TOPIC:"+ s + " || MESSAGE RECEIVED: " + mqttMessage.toString());
        if (s.equals(env.getProperty("mqtt.topic.gpsData"))) {
            logger.info("GPS Data received. Parsing....");
            JsonGpsDataMessage jsonGpsDataMessage = objectMapper.readValue(mqttMessage.toString(), JsonGpsDataMessage.class);

            logger.info(jsonGpsDataMessage.getData().getLat().toString());
            //gpsAgentService.saveGpsData(jsonGpsDataMessage).<ResponseEntity>thenApply(ResponseEntity::ok);
            GpsData gpsData = gpsAgentDao.saveGpsData(new GpsData(
                    jsonGpsDataMessage.getHeader().getMacAddress(),
                    jsonGpsDataMessage.getData().getMillis(),
                    jsonGpsDataMessage.getData().getLat(),
                    jsonGpsDataMessage.getData().getLng()
            ));

        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
