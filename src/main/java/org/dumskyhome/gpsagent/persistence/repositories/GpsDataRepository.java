package org.dumskyhome.gpsagent.persistence.repositories;

import org.dumskyhome.gpsagent.persistence.datamodel.GpsData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GpsDataRepository extends JpaRepository<GpsData, Long> {
}
