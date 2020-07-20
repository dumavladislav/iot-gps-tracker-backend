package org.dumskyhome.gpsagent;

import org.dumskyhome.gpsagent.service.GPSAgentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GpsAgentApplication {

	private static final Logger logger = LoggerFactory.getLogger(GpsAgentApplication.class);

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(GpsAgentApplication.class, args);
		logger.info("========= Application started =========");

		GPSAgentService gpsAgentService = applicationContext.getBean(GPSAgentService.class);
		while(!gpsAgentService.runService()) {
			logger.error("Failed to start GPS Agent service");
			try {
				Thread.sleep(5000);
				gpsAgentService.runService();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		logger.info("========= GPS Agent service started =========");
	}

}
