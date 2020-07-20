package org.dumskyhome.gpsagent.persistence.datamodel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GpsData extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String deviceId;

    private long millisSinceStart;
    private Double lat;
    private Double lng;

    public GpsData() {

    }

    public GpsData(String deviceId, long millisSinceStart, Double lat, Double lng) {
        this.deviceId = deviceId;
        this.millisSinceStart = millisSinceStart;
        this.lat = lat;
        this.lng = lng;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public long getMillisSinceStart() {
        return millisSinceStart;
    }

    public void setMillisSinceStart(long millisSinceStart) {
        this.millisSinceStart = millisSinceStart;
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
}
