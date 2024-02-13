package spcs.aws.web.controller.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import spcs.aws.web.plant1.service.alarm.AlarmService;

//########## Final Modified by Epick (Sahassawat O.) on 07/11/2022 ##########

@EnableScheduling
@Service
public class AlarmController {
	private static final Logger log = LoggerFactory.getLogger(AlarmController.class);
	
	@Autowired
	private AlarmService alarmService;
	
	//Shipment uncompleted will only run every 1 minute 
		@Scheduled(cron = "0 */1 * ? * *", zone="Asia/Bangkok")
		public void scheduleTaskWithCronExpression() {
			log.info("-- task schedule for S -------");
			boolean alarmship = this.alarmService.displayPopupShipment();
		    log.debug("alarm shipment complete!!");
			log.info(String.format("%1$50s\r\n", "").replace(' ', '-'));
			
		}
		
	//Truck unloading will only run every 15 minute
			@Scheduled(cron = "0 */15 * ? * *", zone="Asia/Bangkok")
			public void scheduleTaskWithCronExpression1() {
				log.info("-- task schedule for S -------");
				boolean alarmship = this.alarmService.displayPopupTruck();
			    log.debug("alarm Truck complete!!");
				log.info(String.format("%1$50s\r\n", "").replace(' ', '-'));

			}
}
