package org.dumskyhome.gpsagent.service;

import org.dumskyhome.gpsagent.json.JsonGpsDataMessage;
import org.dumskyhome.gpsagent.mqtt.MqttAgent;
import org.dumskyhome.gpsagent.persistence.dao.GpsAgentDao;
import org.dumskyhome.gpsagent.persistence.datamodel.GpsData;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class GPSAgentService {

    private static final Logger logger = LoggerFactory.getLogger(GPSAgentService.class);

    @Autowired
    MqttAgent mqttAgent;

    @Autowired
    GpsAgentDao gpsAgentDao;

    public boolean runService() {
        return mqttAgent.runMqttService();
    }

    @Async
    public CompletableFuture<Long> saveGpsData(JsonGpsDataMessage jsonGpsDataMessage) {
        GpsData gpsData = gpsAgentDao.saveGpsData(new GpsData(
                jsonGpsDataMessage.getHeader().getMacAddress(),
                jsonGpsDataMessage.getData().getMillis(),
                jsonGpsDataMessage.getData().getLat(),
                jsonGpsDataMessage.getData().getLng()
        ));
        return CompletableFuture.completedFuture(gpsData.getId());
    }


}
