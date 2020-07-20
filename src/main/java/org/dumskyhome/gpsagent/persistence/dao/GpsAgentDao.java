package org.dumskyhome.gpsagent.persistence.dao;

import org.dumskyhome.gpsagent.persistence.datamodel.GpsData;
import org.dumskyhome.gpsagent.persistence.repositories.GpsDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GpsAgentDao {

    @Autowired
    GpsDataRepository gpsDataRepository;

    public GpsData saveGpsData(GpsData gpsData) {
        return gpsDataRepository.save(gpsData);
    }

}
