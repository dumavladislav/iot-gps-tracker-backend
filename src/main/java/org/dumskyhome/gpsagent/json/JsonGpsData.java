package org.dumskyhome.gpsagent.json;

import java.sql.Time;
import java.sql.Timestamp;

public class JsonGpsData {

    private long millis;
    private Double lat;
    private Double lng;
    private Timestamp rtcTimestamp;

    public long getMillis() {
        return millis;
    }

    public void setMillis(long millis) {
        this.millis = millis;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Timestamp getRtcTimestamp() {
        return rtcTimestamp;
    }

    public void setRtcTimestamp(Timestamp rtcTimestamp) {
        this.rtcTimestamp = rtcTimestamp;
    }
}
