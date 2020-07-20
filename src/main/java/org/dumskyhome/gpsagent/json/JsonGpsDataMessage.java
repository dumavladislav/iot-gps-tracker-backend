package org.dumskyhome.gpsagent.json;

public class JsonGpsDataMessage extends JsonMqttMessage {

    JsonGpsData data;

    public JsonGpsData getData() {
        return data;
    }

    public void setData(JsonGpsData data) {
        this.data = data;
    }
}
